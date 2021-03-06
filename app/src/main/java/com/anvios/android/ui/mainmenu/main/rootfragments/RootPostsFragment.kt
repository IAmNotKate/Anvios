package com.anvios.android.ui.mainmenu.main.rootfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.anvios.android.R
import kotlinx.android.synthetic.main.fragment_root_posts.*

class RootPostsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_root_posts, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val controller = Navigation.findNavController(requireActivity(), R.id.navHostPostsFragment)
        NavigationUI.setupWithNavController(postsToolbar as Toolbar, controller)
    }

    companion object {
        @JvmStatic
        fun newInstance() = RootPostsFragment()
    }
}