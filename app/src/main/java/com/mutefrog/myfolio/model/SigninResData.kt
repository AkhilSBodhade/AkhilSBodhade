package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName

data class SigninResData(
    @SerializedName("otp") val otp: Int
)