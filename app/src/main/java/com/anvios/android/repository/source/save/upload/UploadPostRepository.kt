package com.anvios.android.repository.source.save.upload

import io.reactivex.Single

interface UploadPostRepository : UploadDataRepository<UploadPostRepository.UploadingPost, Boolean> {
    override fun upload(source: UploadingPost): Single<Boolean>

    data class UploadingPost(
        val title: String,
        val text: String
    )
}