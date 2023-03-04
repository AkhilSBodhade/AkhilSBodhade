package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class CompleteSignupResponse (

  @SerializedName("status"  ) var status  : Int?     = null,
  @SerializedName("success" ) var success : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : CompleteSignupResData?    = CompleteSignupResData()

)