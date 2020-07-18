package com.anvios.android.ui.mainmenu.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.anvios.android.R
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {
    private var viewModel: MainMenuViewModel? = null
    private val menuItems = listOf(R.id.menu_item_posts/*, R.id.menu_item_user*/)
    private val navHostFragments = listOf(R.id.navHostPostsFragment, R.id.navHostUserFragment)
    private val rootDestinations =
        listOf(R.id.navigation_posts_fragment, R.id.navigation_user_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        viewModel = ViewModelProvider(this).get(MainMenuViewModel::class.java)

        mainViewPager.adapter = MainMenuPagerAdapter(supportFragmentManager, lifecycle)
        mainViewPager.isUserInputEnabled = false
        navView.setOnNavigationItemSelectedListener {
            val position = menuItems.indexOf(it.itemId)
            if (mainViewPager.currentItem != position) {
                viewModel!!.pushToBackStack(position)
                mainViewPager.currentItem = position
                return@setOnNavigationItemSelectedListener true
            }
            return@setOnNavigationItemSelectedListener false
        }

        navView.setOnNavigationItemReselectedListener {
            val controller = getCurrentNavController()
            val position = menuItems.indexOf(it.itemId)
            controller.popBackStack(rootDestinations[position], false)
        }

        mainViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val viewId = menuItems[position]
                if (navView.selectedItemId != viewId) {
                    navView.menu.getItem(position).isChecked = true
                }
            }
        })
    }

    override fun onBackPressed() {
        val controller = getCurrentNavController()
        if (!controller.navigateUp()) {
            if (!viewModel!!.isBackStackEmpty()) {
                mainViewPager.currentItem = viewModel!!.popAndPeekFromBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun getCurrentNavController(): NavController {
        val navHostId = navHostFragments[mainViewPager.currentItem]
        return Navigation.findNavController(this, navHostId)
    }
}