package com.anvios.android.ui.mainmenu.posts.create

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.anvios.android.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        val navController = Navigation.findNavController(this, navHostCreatePostFragment.id)
        NavigationUI.setupWithNavController(createPostToolbar as Toolbar, navController)

        val viewModel = ViewModelProvider(this).get(CreatePostViewModel::class.java)
        compositeDisposable.add(
            viewModel.getNavigationObservable()
                .distinctUntilChanged()
                .filter { doesNavigate -> doesNavigate }
                .subscribe {
                    setResult(Activity.RESULT_OK)
                    finish()
                })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}