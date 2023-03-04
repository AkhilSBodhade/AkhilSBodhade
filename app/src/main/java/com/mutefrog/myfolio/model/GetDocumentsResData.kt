package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetDocumentsResData (

  @SerializedName("list" ) var list : ArrayList<Document> = arrayListOf()

)