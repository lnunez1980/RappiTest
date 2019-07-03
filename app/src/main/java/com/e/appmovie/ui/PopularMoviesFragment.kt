package com.e.appmovie.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
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
import com.e.appmovie.MoviesAdapter
import com.e.appmovie.R
import com.e.appmovie.api.MoviesApi
import com.e.appmovie.api.model.Movie
import com.e.appmovie.di.DaggerAppComponent
import com.e.appmovie.viewmodel.PopularMoviesViewModel
import com.e.appmovie.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import javax.inject.Inject


class PopularMoviesFragment : Fragment() {

    lateinit var moviesAdapter : MoviesAdapter
    lateinit var moviesList: MutableList<Movie>
    lateinit var linearLayoutManager : LinearLayoutManager
    lateinit var moviesViewModel : PopularMoviesViewModel
    lateinit var viewModelFactory: ViewModelFactory
    var isConnected : Boolean = false
    var pag: Int = 1
    @Inject
    lateinit var moviesApi: MoviesApi
    var isFiltering : Boolean = false


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_search,menu)
        val searchItem = menu!!.findItem(R.id.item_search)
        if(searchItem != null){
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(txtSearch: String): Boolean {
                    if (txtSearch.isNotEmpty()){
                        isFiltering = true
                        moviesViewModel.filter(txtSearch)
                    }
                    return true
                }
                override fun onQueryTextChange(txtSearch: String): Boolean {
                    if (txtSearch.isEmpty()){
                        if (isFiltering){
                            isFiltering = false
                            pag = 1
                            moviesViewModel.resetMovies()
                            moviesViewModel.getData(pag)
                        }
                    }
                    return true
                }
            })
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val view : View = inflater.inflate(R.layout.fragment_popular_movies, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNetworkStatus()
        setupDaggerViewmodel()
        setupReciclerView()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun setupDaggerViewmodel() {
        DaggerAppComponent.create().inject(this)
        viewModelFactory = ViewModelFactory(moviesApi,isConnected)
        moviesViewModel = ViewModelProviders.of(this,viewModelFactory).get(PopularMoviesViewModel::class.java)
    }

    private fun setupReciclerView() {
        moviesList = mutableListOf<Movie>()
        moviesAdapter = MoviesAdapter(moviesList)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = moviesAdapter

        with(recyclerView) {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (isFiltering == false){
                        if (moviesViewModel.isLoading() == false){
                            val totalItems : Int = (recyclerView.layoutManager as LinearLayoutManager).itemCount
                            val visibleItems : Int = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                            if ((totalItems - 1) == visibleItems) {
                                pag += 1

                                Toast.makeText(context, "Pagina: " +
                                        pag.toString(), Toast.LENGTH_SHORT).show()
                                moviesViewModel.getData(pag)
                            }
                        }
                    }
                }
            })
            setOnClickListener(object : OnClickListener{
                override fun onClick(v: View?) {
                    Toast.makeText(context, "Pagina: " +
                            pag.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun loadData() {

        moviesViewModel.getData(pag)

        val listObserver = Observer<List<Movie>> { moviesList ->
            moviesAdapter.setData(moviesList.toMutableList())
        }

        moviesViewModel.getMovies().observe(this,listObserver)

    }

    private fun getNetworkStatus() {

        val connectivityManager = getActivity()!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.detailedState == NetworkInfo.DetailedState.CONNECTED) {
            isConnected = true
        }

    }

    override fun onDestroyView() {
        moviesViewModel.dispose()
        super.onDestroyView()
    }
}
