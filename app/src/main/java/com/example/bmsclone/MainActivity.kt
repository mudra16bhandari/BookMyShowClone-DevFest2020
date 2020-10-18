package com.example.bmsclone

import NetworkHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    companion object {
        private const val API_KEY = "7bc0651fe0ea5973822df3bd27e779d9"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchMovies()
    }

    private fun fetchMovies() {
        val networkHelper = NetworkHelper(this)
        if(networkHelper.isNetworkConnected()){
            val request = RetrofitBuilder.buildService()
            val call = request.getMovies(API_KEY)

            showProgress()

            call.enqueue(object : retrofit2.Callback<MovieResponse>{
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    hideProgress()
                    showErrorMessage(t.message)
                }

                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    hideProgress()
                    if(response.isSuccessful && response.body() !=null){
                        val movieResponse = response.body()!!
                        val movies = movieResponse.results
                        showMovies(movies)
                    }
                    else{
                        showErrorMessage(resources.getString(R.string.error_msg))
                    }
                }

            })
        }
        else{
            hideProgress()
            showErrorMessage(resources.getString(R.string.no_internet))
        }

    }

    private fun showMovies(movies: List<Movie>) {
        recyclerView.visibility = View.VISIBLE
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = MoviesAdapter(movies)
        recyclerView.itemAnimator= DefaultItemAnimator()
    }

    private fun showProgress(){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        progressBar.visibility = View.GONE
    }

    private fun showErrorMessage(errorMessage:String?){
        errorView.visibility = View.VISIBLE
        errorView.text = errorMessage
    }

}