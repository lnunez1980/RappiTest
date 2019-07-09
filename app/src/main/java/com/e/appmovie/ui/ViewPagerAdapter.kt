package com.e.appmovie.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.e.appmovie.ui.fragments.Popular.PopularMoviesFragment
import com.e.appmovie.ui.fragments.toprated.TopRatedMoviesFragment
import com.e.appmovie.ui.fragments.upcoming.UpcomingMoviesFragment

class ViewPagerAdapter(fm: FragmentManager, private var tabCount: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        when (position) {
            0 -> {
                return PopularMoviesFragment()
            }
            1 -> {
                return TopRatedMoviesFragment()
            }
            2 -> {
                return UpcomingMoviesFragment()
            }
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}