package com.anvios.android.web.anvios.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("device_token ") val deviceToken: String
)