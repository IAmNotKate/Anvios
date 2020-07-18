package com.anvios.android.web.anvios.data.request

import com.google.gson.annotations.SerializedName

data class CreatePostRequest(
    @SerializedName("title") val title: String,
    @SerializedName("text") val text: String
)