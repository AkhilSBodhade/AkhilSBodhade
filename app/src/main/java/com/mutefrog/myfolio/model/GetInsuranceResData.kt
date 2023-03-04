package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetInsuranceResData (

  @SerializedName("list" ) var list : ArrayList<Insurance> = arrayListOf()

)