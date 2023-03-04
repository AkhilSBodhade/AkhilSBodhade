package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class DeleteAccountResData (

  @SerializedName("otp" ) var otp : Int? = null
)