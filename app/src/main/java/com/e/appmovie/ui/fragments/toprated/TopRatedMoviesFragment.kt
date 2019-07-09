package com.e.appmovie.ui.fragments.toprated

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.appmovie.R
import com.e.appmovie.api.model.MovieTopRated
import com.e.appmovie.factory.ViewModelFactory
import com.e.appmovie.ui.fragments.detail.DetailMoviesFragment
import com.e.appmovie.ui.fragments.models.TopRatedMoviesAction
import com.e.appmovie.util.Constants.listGenres
import com.e.appmovie.util.Constants.movieId
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import javax.inject.Inject


class TopRatedMoviesFragment : Fragment() {

    lateinit var moviesAdapter: MoviesAdapterToprate
    lateinit var moviesList: MutableList<MovieTopRated>
    lateinit var linearLayoutManager: LinearLayoutManager
    var isLoading: Boolean = false
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: TopRatedMoviesViewModel

    var isFiltering: Boolean = false

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_search, menu)
        val searchItem = menu!!.findItem(R.id.item_search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(txtSearch: String): Boolean {
                    if (txtSearch.isNotEmpty()){
                        isFiltering = true
                        viewModel.filter(txtSearch)
                    }
                    return true
                }
                override fun onQueryTextChange(txtSearch: String): Boolean {
                    if (txtSearch.isEmpty()){
                        viewModel.filter("")
                        isFiltering = false
                    }
                    return true
                }
            })
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        listenToLiveDataObserver()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_toprated_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TopRatedMoviesViewModel::class.java)
        lifecycle.addObserver(viewModel)
    }

    private fun listenToLiveDataObserver() {
        viewModel.liveData.observe(this, Observer { action ->
            when (action) {
                is TopRatedMoviesAction.OnError -> showErrors(action)
                is TopRatedMoviesAction.OnSuccess -> loadData(action)
                is TopRatedMoviesAction.OnLoading -> isLoading = action.loading
                is TopRatedMoviesAction.OnFilterQueryFound -> loadDataFilter(action)
                is TopRatedMoviesAction.OnSuccessGenre -> loadDataGenre(action)
            }
        })
    }

    private fun setupRecyclerView() {

        moviesList = mutableListOf<MovieTopRated>()
        moviesAdapter =
            MoviesAdapterToprate(moviesList, { movie: MovieTopRated -> movieClicked(movie) })
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = moviesAdapter

        with(recyclerView) {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                        if (!isFiltering) {
                            if (!isLoading) {
                            val totalItems: Int = (recyclerView.layoutManager as LinearLayoutManager).itemCount
                            val visibleItems: Int =
                                (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                            if ((totalItems - 1) == visibleItems) {
                                viewModel.loadMoreData()
                            }
                        }
                    }
                }
            })
            setOnClickListener(object : OnClickListener {
                override fun onClick(v: View?) {
                    Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun showErrors(action: TopRatedMoviesAction.OnError) {
        Toast.makeText(context, action.error.message, Toast.LENGTH_SHORT).show()
    }

    private fun loadData(action: TopRatedMoviesAction.OnSuccess) {
        moviesAdapter.setData(action.movies.toMutableList())
    }

    private fun loadDataGenre(action: TopRatedMoviesAction.OnSuccessGenre) {
        listGenres = action.genres
    }

    private fun loadDataFilter(action: TopRatedMoviesAction.OnFilterQueryFound) {
        moviesAdapter.clearData()
        moviesAdapter.setData(action.movies.toMutableList())
    }

    private fun movieClicked(movie : MovieTopRated) {

        movieId = movie.id.toString()
        val fm = getActivity()!!.getSupportFragmentManager()
        val detailmoviesFragment = DetailMoviesFragment()
        detailmoviesFragment.show(fm,"FragmentDetail")

    }
}
