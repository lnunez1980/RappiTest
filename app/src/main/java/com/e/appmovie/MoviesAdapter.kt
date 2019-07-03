package com.e.appmovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e.appmovie.api.model.Movie


class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ViewHolderMovies>{

    var moviesList: MutableList<Movie> = mutableListOf<Movie>()
    val IMAGE_URL = "https://image.tmdb.org/t/p/w300"

    constructor(movies: MutableList<Movie>) {
        this.moviesList = movies
    }

    fun setData(movies: MutableList<Movie>) {
        this.moviesList = movies
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMovies {
        val itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.list_item_movies, parent, false)
        return ViewHolderMovies(itemView)

    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: ViewHolderMovies, position: Int) {
        holder.originalTitle.setText(moviesList[position].title)
        holder.releaseDate.setText(holder.itemView.getContext().getString(R.string.releaseDate) + moviesList[position].releaseDate)
        holder.genere.setText(holder.itemView.getContext().getString(R.string.genre))
        holder.voteAverage.setText(holder.itemView.getContext().getString(R.string.vote) + moviesList[position].voteAverage)
        val url: String = IMAGE_URL + moviesList[position].posterPath
        holder.poster.setImage(url)
    }

    fun ImageView.setImage(url:String){
        Glide.with(this)
            .load(url)
            .into(this)
    }

    class ViewHolderMovies(view: View) : RecyclerView.ViewHolder(view) {
        val originalTitle = view.findViewById<TextView>(R.id.textView_title)
        val releaseDate = view.findViewById<TextView>(R.id.textView_festreno)
        val genere = view.findViewById<TextView>(R.id.textView_genere)
        val voteAverage = view.findViewById<TextView>(R.id.textView_voto)
        val poster = view.findViewById<ImageView>(R.id.imageView_poster)

    }

}