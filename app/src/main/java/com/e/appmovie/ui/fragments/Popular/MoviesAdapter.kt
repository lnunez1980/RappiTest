package com.e.appmovie.ui.fragments.Popular

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.appmovie.api.model.Movie
import com.e.appmovie.ui.fragments.views.MovieItemView

class MoviesAdapter constructor(
    private val movies: MutableList<Movie>,
    private val clickListener: (Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.ViewHolderMovies>() {

    var moviesList: MutableList<Movie> = mutableListOf()

    fun setData(movies: MutableList<Movie>) {
        this.moviesList.addAll(movies)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.moviesList.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMovies {
        return ViewHolderMovies(MovieItemView(parent.context))
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: ViewHolderMovies, position: Int) {
        holder.view.setOnClickListener { clickListener(moviesList[position])}
        holder.view.bind(moviesList[position])

    }

    class ViewHolderMovies(val view: MovieItemView) : RecyclerView.ViewHolder(view)
}