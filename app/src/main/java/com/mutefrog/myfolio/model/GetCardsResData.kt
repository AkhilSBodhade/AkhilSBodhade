package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetCardsResData (

  @SerializedName("list" ) var list : ArrayList<Card> = arrayListOf()

)