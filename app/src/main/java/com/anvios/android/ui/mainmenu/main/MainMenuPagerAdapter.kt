package com.anvios.android.ui.mainmenu.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.anvios.android.ui.mainmenu.main.rootfragments.RootPostsFragment
import com.anvios.android.ui.mainmenu.main.rootfragments.RootUserFragment

class MainMenuPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RootPostsFragment.newInstance()
            1 -> RootUserFragment.newInstance()
            else -> RootPostsFragment.newInstance()
        }
    }

    override fun getItemCount() = 2
}