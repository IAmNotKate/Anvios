package com.anvios.android.ui.mainmenu.posts.create

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.anvios.android.R
import com.anvios.android.preferences.AppPreferences
import com.anvios.android.repository.implementation.post.UploadPostIntoServerRepository
import com.anvios.android.repository.source.save.upload.UploadPostRepository
import com.anvios.android.web.anvios.main.AnviosNetworkService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

private const val UPLOADING_ERROR_TAG = "uploading_error"

class CreatePostViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = AppPreferences.getInstance(application.applicationContext)
    private val api = AnviosNetworkService.getInstance().getApi()
    private val uploadPostRepository: UploadPostRepository = UploadPostIntoServerRepository(
        api, preferences
    )
    private val compositeDisposable = CompositeDisposable()
    private val createButtonEnabledSubject = BehaviorSubject.createDefault(false)
    private val navigationSubject = BehaviorSubject.create<Boolean>()
    private val errorToast = Toast.makeText(
        application.applicationContext,
        R.string.uploading_post_error,
        Toast.LENGTH_SHORT
    )


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun getCreateButtonEnabledObservable(): Observable<Boolean> = createButtonEnabledSubject

    fun getNavigationObservable(): Observable<Boolean> = navigationSubject

    fun setCreateButtonEnabled(isEnabled: Boolean) {
        createButtonEnabledSubject.onNext(isEnabled)
    }

    fun uploadPost(title: String, text: String) {
        compositeDisposable.add(
            uploadPostRepository.upload(UploadPostRepository.UploadingPost(title, text))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    navigationSubject.onNext(it)
                }, {
                    Log.e(UPLOADING_ERROR_TAG, it.toString())
                    errorToast.show()
                })
        )
    }
}