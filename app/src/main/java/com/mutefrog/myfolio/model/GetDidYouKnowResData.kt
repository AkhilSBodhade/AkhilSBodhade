package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetDidYouKnowResData (

  @SerializedName("list" ) var list : ArrayList<DidYouKnow> = arrayListOf()

)