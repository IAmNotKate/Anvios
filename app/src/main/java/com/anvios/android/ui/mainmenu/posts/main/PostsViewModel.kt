package com.anvios.android.ui.mainmenu.posts.main

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.anvios.android.R
import com.anvios.android.database.anvios.main.AnviosDatabase
import com.anvios.android.model.Post
import com.anvios.android.repository.implementation.post.AddPostsIntoBdRepository
import com.anvios.android.repository.implementation.post.ObtainPostsPageFromWebRepository
import com.anvios.android.repository.implementation.post.ObtainRecyclerPostsFromDbRepository
import com.anvios.android.repository.source.obtain.page.ObtainPostsPageRepository
import com.anvios.android.repository.source.obtain.instance.ObtainPostsRepository
import com.anvios.android.repository.source.save.add.AddPostsRepository
import com.anvios.android.web.anvios.main.AnviosNetworkService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlin.collections.ArrayList

private const val DB_LOADING_ERROR_TAG = "db_loading_error"
private const val WEB_LOADING_ERROR_TAG = "web_loading_error"

class PostsViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()

    private val database = AnviosDatabase.getInstance(application.applicationContext)
    private val anviosDao = database.anviosDao()
    private val anviosApi = AnviosNetworkService.getInstance().getApi()
    private val obtainPostPageRepository: ObtainPostsPageRepository =
        ObtainPostsPageFromWebRepository(anviosApi)
    private val recyclerPostsRepository: ObtainPostsRepository =
        ObtainRecyclerPostsFromDbRepository(anviosDao)
    private val addPostsRepository: AddPostsRepository = AddPostsIntoBdRepository(anviosDao)

    private val refreshingSubject = BehaviorSubject.createDefault(false)
    private var nextPage = 1

    private val errorToast = Toast.makeText(
        application.applicationContext,
        R.string.loading_page_error,
        Toast.LENGTH_SHORT
    )

    init {
        loadPostsPage(1)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun getPostsObservable(): Observable<MutableList<Post>> {
        return recyclerPostsRepository.obtain()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { Log.e(DB_LOADING_ERROR_TAG, it.toString()) }
            .onErrorReturn { ArrayList() }
    }

    fun getRefreshingObservable(): Observable<Boolean> = refreshingSubject

    fun reloadList() {
        setRefreshing(true)
        loadPostsPage(1)
    }

    fun loadNextPage() {
        loadPostsPage(nextPage)
    }

    private fun setRefreshing(isRefreshing: Boolean) {
        refreshingSubject.onNext(isRefreshing)
    }

    private fun loadPostsPage(page: Int) {
        compositeDisposable.add(
            obtainPostPageRepository.obtain(page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    if (it.isNotEmpty()) {
                        if (page == 1) database.clearAllTables()
                        addPostsRepository.add(it)
                        nextPage = page + 1
                    }
                    setRefreshing(false)
                }, {
                    errorToast.show()
                    Log.e(WEB_LOADING_ERROR_TAG, it.toString())
                    setRefreshing(false)
                })
        )
    }
}