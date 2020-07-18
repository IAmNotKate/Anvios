package com.anvios.android.repository.implementation.post

import com.anvios.android.preferences.AppPreferences
import com.anvios.android.repository.implementation.mapper.DataMapper
import com.anvios.android.repository.source.save.upload.UploadPostRepository
import com.anvios.android.web.anvios.data.request.CreatePostRequest
import com.anvios.android.web.anvios.main.AnviosApi
import io.reactivex.Single

class UploadPostIntoServerRepository(
    private val api: AnviosApi,
    private val preferences: AppPreferences
) : UploadPostRepository {
    override fun upload(source: UploadPostRepository.UploadingPost): Single<Boolean> {
        return api.createPost(preferences.getDeviceToken()!!, PostUploadingToRequest.map(source))
            .map {
                true
            }
    }
}

private object PostUploadingToRequest :
    DataMapper<UploadPostRepository.UploadingPost, CreatePostRequest> {
    override fun map(source: UploadPostRepository.UploadingPost): CreatePostRequest {
        return CreatePostRequest(source.title, source.text)
    }
}