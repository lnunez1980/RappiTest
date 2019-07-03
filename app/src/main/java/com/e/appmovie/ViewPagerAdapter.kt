package com.e.appmovie

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.e.appmovie.ui.PopularMoviesFragment

class ViewPagerAdapter(fm: FragmentManager, private var tabCount: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        when (position) {
            0 -> {
                return PopularMoviesFragment()
            }
            1 -> {
                return PopularMoviesFragment()
            }
            2 -> {
                return PopularMoviesFragment()
            }
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}