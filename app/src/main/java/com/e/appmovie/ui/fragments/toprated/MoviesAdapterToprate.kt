package com.e.appmovie.ui.fragments.toprated

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.appmovie.api.model.MovieTopRated
import com.e.appmovie.ui.fragments.views.MovieItemView
import com.e.appmovie.ui.fragments.views.MovieItemViewTopRated

class MoviesAdapterToprate constructor(
    private val movies: MutableList<MovieTopRated>,
    private val clickListener: (MovieTopRated) -> Unit
) : RecyclerView.Adapter<MoviesAdapterToprate.ViewHolderMovies>() {

    var moviesList: MutableList<MovieTopRated> = mutableListOf()

    fun setData(movies: MutableList<MovieTopRated>) {
        this.moviesList.addAll(movies)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.moviesList.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMovies {
        return ViewHolderMovies(MovieItemViewTopRated(parent.context))
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: ViewHolderMovies, position: Int) {
        holder.view.setOnClickListener { clickListener(moviesList[position])}
        holder.view.bind(moviesList[position])

    }

    class ViewHolderMovies(val view: MovieItemViewTopRated) : RecyclerView.ViewHolder(view)
}