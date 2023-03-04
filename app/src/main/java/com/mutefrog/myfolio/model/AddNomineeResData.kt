package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class AddNomineeResData (
  @SerializedName("id"  ) var id  : Int? = null,
  @SerializedName("otp" ) var otp : Int? = null
)