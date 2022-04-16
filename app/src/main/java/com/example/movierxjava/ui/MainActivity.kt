package com.example.movierxjava.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movierxjava.R
import com.example.movierxjava.data.network.RetrofitService
import com.example.movierxjava.data.network.repository.MainRepository
import com.example.movierxjava.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()
    val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get viewmodel instance using ViewModelProvider.Factory
        viewModel =
            ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        //set adapter in recyclerview
        binding.recyclerview.adapter = adapter

        binding.btnShowMovies.setOnClickListener {
            viewModel.getAllMovies()
            binding.btnShowMovies.visibility = View.GONE
            binding.recyclerview.visibility = View.VISIBLE

        }
        //the observer will only receive events if the owner(activity) is in active state
        //invoked when movieList data changes
        viewModel.movieList.observe(this, Observer {
            if (it != null) {
                Log.d(TAG, "movieList: $it")
                adapter.setMovieList(it)
            } else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })

        //invoked when a network exception occurred
        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "errorMessage: $it")
        })
    }

    override fun onDestroy() {
        // don't send events once the activity is destroyed
        viewModel.disposable.dispose()
        super.onDestroy()
    }
}