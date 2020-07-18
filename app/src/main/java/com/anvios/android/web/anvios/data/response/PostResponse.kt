package com.anvios.android.web.anvios.data.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class PostResponse(
    @SerializedName("post_id") val postId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("text") val text: String,
    @SerializedName("date_created") val dateCreated: Calendar,
    @SerializedName("creator") val creator: PostCreator
) {
    data class PostCreator(
        @SerializedName("user_id") val userId: Int,
        @SerializedName("username") val username: String
    )
}