package com.e.appmovie.ui.fragments.upcoming

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.appmovie.api.model.MovieUpcoming
import com.e.appmovie.ui.fragments.views.MovieItemViewUpcoming


class MoviesAdapterUpcoming constructor(
    private val movies: MutableList<MovieUpcoming>,
    private val clickListener: (MovieUpcoming) -> Unit
) : RecyclerView.Adapter<MoviesAdapterUpcoming.ViewHolderMovies>() {

    var moviesList: MutableList<MovieUpcoming> = mutableListOf()

    fun setData(movies: MutableList<MovieUpcoming>) {
        this.moviesList.addAll(movies)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.moviesList.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMovies {
        return ViewHolderMovies(MovieItemViewUpcoming(parent.context))
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: ViewHolderMovies, position: Int) {
        holder.view.setOnClickListener { clickListener(moviesList[position])}
        holder.view.bind(moviesList[position])

    }

    class ViewHolderMovies(val view: MovieItemViewUpcoming) : RecyclerView.ViewHolder(view)
}