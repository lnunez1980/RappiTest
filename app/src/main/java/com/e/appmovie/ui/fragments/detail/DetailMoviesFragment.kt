package com.e.appmovie.ui.fragments.detail


import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.graphics.Point
import android.text.util.Linkify
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.e.appmovie.R
import com.e.appmovie.factory.ViewModelFactory
import com.e.appmovie.ui.fragments.Popular.PopularMoviesViewModel
import com.e.appmovie.ui.fragments.models.MovieDetailAction
import com.e.appmovie.ui.fragments.models.PopularMoviesAction
import com.e.appmovie.util.Constants.image_url
import com.e.appmovie.util.DrawableAlwaysCrossFadeFactory
import com.e.appmovie.util.ImageBinder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import javax.inject.Inject


class DetailMoviesFragment : DialogFragment() {

    var isLoading: Boolean = false
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: DetailMoviesViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail, container)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        listenToLiveDataObserver()
    }

    override fun onResume() {
        super.onResume()

        val window: Window? = dialog.window
        val size = Point()

        val display: Display = window!!.windowManager.defaultDisplay
        display.getSize(size)

        val width : Int = size.x
        val heigth : Int = size.y

        window.setLayout((width * 0.9).toInt(),(heigth * 0.9).toInt() )
        window.setGravity(Gravity.CENTER)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonexit.setOnClickListener {
            dismiss()
        }

     }

    fun ImageView.setImage(url:String){
        Glide.with(this)
            .load(url)
            .transform(RoundedCorners(16))
            .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
            .into(this)
    }

    private fun showErrors(action: MovieDetailAction.OnError) {
        Toast.makeText(context, action.error.message, Toast.LENGTH_SHORT).show()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailMoviesViewModel::class.java)
        lifecycle.addObserver(viewModel)
    }

    private fun listenToLiveDataObserver() {
        viewModel.liveData.observe(this, Observer { action ->
            when (action) {
                is MovieDetailAction.OnError -> showErrors(action)
                is MovieDetailAction.OnSuccess -> loadData(action)
                is MovieDetailAction.OnLoading -> isLoading = action.loading
            }
        })
    }

    private fun loadData(action: MovieDetailAction.OnSuccess) {
        textViewtitle.text = resources.getString(R.string.fragment_title,action.moviesDetail.originalTitle)
        textViewhomepage.text = resources.getString(R.string.fragment_detail_home_page,action.moviesDetail.homepage)
        Linkify.addLinks(textViewhomepage, Linkify.ALL);
        textViewoverview.text = resources.getString(R.string.fragment_detail_overview,action.moviesDetail.overview)
        val url: String = image_url + action.moviesDetail.posterPath
        imagemovie.setImage(url)


    }
}