package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetAssetsResData (

  @SerializedName("list" ) var list : ArrayList<Asset> = arrayListOf()

)