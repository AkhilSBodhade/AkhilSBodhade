package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetCategoriesData (

  @SerializedName("list" ) var list : ArrayList<Category> = arrayListOf()

)