package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class SignupVerifyOtpResData (

  @SerializedName("token" ) var token : Token? = Token(),
  @SerializedName("user"  ) var user  : User?  = User()

)