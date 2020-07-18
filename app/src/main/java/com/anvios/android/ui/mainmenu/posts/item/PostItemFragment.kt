package com.anvios.android.ui.mainmenu.posts.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anvios.android.R
import com.anvios.android.model.Post
import kotlinx.android.synthetic.main.fragment_post_item.*

const val POST_KEY = "post"

class PostItemFragment : Fragment() {
    private var post: Post? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            post = it.getParcelable(POST_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (post != null) {
            postTitle.text = post!!.title
            postText.text = post!!.text
            userName.text = post!!.user.username
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(post: Post) = PostItemFragment().apply {
            Bundle().apply {
                putParcelable(POST_KEY, post)
            }
        }
    }
}