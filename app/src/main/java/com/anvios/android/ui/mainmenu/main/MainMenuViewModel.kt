package com.anvios.android.ui.mainmenu.main

import androidx.lifecycle.ViewModel
import java.util.*

class MainMenuViewModel : ViewModel() {
    private val backStack = Stack<Int>()

    init {
        backStack.push(0)
    }

    fun pushToBackStack(position: Int) {
        backStack.push(position)
    }

    fun popAndPeekFromBackStack(): Int {
        backStack.pop()
        return backStack.peek()
    }

    fun isBackStackEmpty() = backStack.empty() || backStack.size == 1
}