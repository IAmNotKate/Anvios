package com.anvios.android.ui.mainmenu.posts.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anvios.android.R
import com.anvios.android.model.Post
import com.anvios.android.ui.mainmenu.posts.create.CreatePostActivity
import com.anvios.android.ui.mainmenu.posts.item.POST_KEY
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_posts.*

private const val CREATE_FRAGMENT_REQUEST_CODE = 1

class PostsFragment : Fragment() {
    private val compositeDisposable = CompositeDisposable()
    private var viewModel: PostsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PostsViewModel::class.java)

        addPostFab.setOnClickListener {
            startActivityForResult(
                Intent(requireContext(), CreatePostActivity::class.java),
                CREATE_FRAGMENT_REQUEST_CODE
            )
        }

        val adapter = PostsRecyclerAdapter(getSelectListener(view))
        postsRecyclerView.adapter = adapter
        postsRecyclerView.addItemDecoration(PostsItemDecorator())

        compositeDisposable.add(
            viewModel!!.getPostsObservable()
                .subscribe {
                    val diffUtilCallback = PostsDiffUtilCallback(adapter.getPosts(), it)
                    val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                    adapter.setPosts(it)
                    diffResult.dispatchUpdatesTo(adapter)
                }
        )

        compositeDisposable.add(
            viewModel!!.getRefreshingObservable().subscribe { postsSwipeRefresh.isRefreshing = it }
        )
        postsSwipeRefresh.setOnRefreshListener {
            viewModel?.reloadList()
        }

        postsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) viewModel?.loadNextPage()
            }
        })
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            CREATE_FRAGMENT_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) viewModel?.reloadList()
            }
        }
    }

    companion object {
        @JvmStatic
        fun getSelectListener(view: View) = object : OnPostItemSelectListener {
            override fun onSelect(post: Post) {
                Navigation.findNavController(view).navigate(R.id.action_navigate_post_item,
                    Bundle().apply {
                        putParcelable(POST_KEY, post)
                    })
            }
        }
    }
}