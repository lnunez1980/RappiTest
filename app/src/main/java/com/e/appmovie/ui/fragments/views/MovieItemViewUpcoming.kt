package com.e.appmovie.ui.fragments.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.e.appmovie.R
import com.e.appmovie.api.model.MovieUpcoming
import com.e.appmovie.util.Constants
import com.e.appmovie.util.ImageBinder

class MovieItemViewUpcoming @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private val posterImageView by lazy { findViewById<ImageView>(R.id.imageView_poster) }
    private val voteAverageTextView by lazy { findViewById<TextView>(R.id.textView_voto) }
    private val releaseDateTextView by lazy { findViewById<TextView>(R.id.textView_festreno) }
    private val originalTitleTextView by lazy { findViewById<TextView>(R.id.textView_title) }
    private val genresTextView by lazy { findViewById<TextView>(R.id.textView_genre) }
    private var genres: String = ""
    init {
        View.inflate(context, R.layout.list_item_movies, this)
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun bind(movie: MovieUpcoming) {

        releaseDateTextView.text = context.getString(R.string.releaseDate, movie.releaseDate)
        voteAverageTextView.text = context.getString(R.string.vote, movie.voteAverage.toString())
        originalTitleTextView.text = movie.title
        genres = ""
        for(n in Constants.listGenres.listIterator()){
            for (g in movie.genre_ids.listIterator()){
                if(g == n.id){
                    genres = genres.plus(" ").plus(n.name)
                }
            }
        }
        genresTextView.text = context.getString(R.string.genre,genres)
        ImageBinder.load(posterImageView, movie.posterPath)


    }


}