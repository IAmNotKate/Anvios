package com.anvios.android.ui.mainmenu.posts.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anvios.android.R
import com.anvios.android.model.Post
import kotlin.collections.ArrayList

class PostsRecyclerAdapter(private val onSelectListener: OnPostItemSelectListener) :
    RecyclerView.Adapter<PostsRecyclerAdapter.RecyclerViewHolder>() {
    private val posts: MutableList<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_post, parent, false)
        val holder = RecyclerViewHolder(view)
        view.setOnClickListener { onSelectListener.onSelect(posts[holder.layoutPosition]) }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.postTitle.text = posts[position].title
        holder.postText.text = posts[position].text
        holder.userLogin.text = posts[position].user.username
    }

    override fun getItemCount() = posts.size

    fun setPosts(posts: MutableList<Post>) {
        this.posts.clear()
        this.posts.addAll(posts)
    }

    fun getPosts() = posts

    class RecyclerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val postTitle: TextView = view.findViewById(R.id.postTitle)
        val postText: TextView = view.findViewById(R.id.postText)
        val userLogin: TextView = view.findViewById(R.id.userLogin)
    }
}