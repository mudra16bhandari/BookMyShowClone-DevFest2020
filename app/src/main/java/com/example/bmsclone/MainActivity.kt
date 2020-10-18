package com.example.bmsclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showMovies(Movies.list!!)
    }

    private fun showMovies(movies: ArrayList<Movie>) {
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = MoviesAdapter(movies)
        recyclerView.itemAnimator= DefaultItemAnimator()
    }
}