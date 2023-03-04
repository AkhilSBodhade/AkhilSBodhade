package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetLoansResData (

  @SerializedName("list" ) var list : ArrayList<Loan> = arrayListOf()

)