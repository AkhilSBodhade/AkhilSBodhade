package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetFinInvestmentsResData (

  @SerializedName("list" ) var list : ArrayList<FinInvestment> = arrayListOf()

)