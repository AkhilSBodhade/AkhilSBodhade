package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class CompleteSignupResData (

  @SerializedName("user" ) var user : User? = User()

)