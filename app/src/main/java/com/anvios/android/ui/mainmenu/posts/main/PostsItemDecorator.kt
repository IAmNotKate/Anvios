package com.anvios.android.ui.mainmenu.posts.main

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anvios.android.R

class PostsItemDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val distance = view.resources.getDimensionPixelSize(R.dimen.posts_recycler_items_distance)

        if (parent.adapter != null && parent.getChildAdapterPosition(view) != 0) {
            outRect.top = distance
        }
    }
}