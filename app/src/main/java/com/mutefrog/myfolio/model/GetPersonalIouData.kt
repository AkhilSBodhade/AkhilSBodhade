package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetPersonalIouData (

  @SerializedName("list" ) var list : ArrayList<IOU> = arrayListOf()

)