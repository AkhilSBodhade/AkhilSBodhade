package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName

data class SignupResData(
    @SerializedName("otp") val otp: Int
)