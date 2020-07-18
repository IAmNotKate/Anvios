package com.anvios.android.ui.mainmenu.posts.main

import com.anvios.android.model.Post

interface OnPostItemSelectListener {
    fun onSelect(post: Post)
}