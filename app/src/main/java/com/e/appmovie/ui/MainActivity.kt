package com.e.appmovie.ui

import android.os.Bundle
import com.e.appmovie.R
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity(), TabLayout.OnTabSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupUI()
    }

    override fun onDestroy() {
        finish()
        super.onDestroy()
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        viewPager.currentItem = tab.position
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {}

    override fun onTabReselected(tab: TabLayout.Tab) {}

    private fun setupUI() {
        tabs.addTab(tabs.newTab().setText(R.string.main_popular_tab_name).setIcon(R.drawable.ic_local_movies_black_24dp))
        tabs.addTab(tabs.newTab().setText(R.string.main_toprated_tab_name).setIcon(R.drawable.ic_local_movies_black_24dp))
        tabs.addTab(tabs.newTab().setText(R.string.main_upcoming_tab_name).setIcon(R.drawable.ic_local_movies_black_24dp))
        tabs.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ViewPagerAdapter(supportFragmentManager, tabs.tabCount)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(this)
    }
}
