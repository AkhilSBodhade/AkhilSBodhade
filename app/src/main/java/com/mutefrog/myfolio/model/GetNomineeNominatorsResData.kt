package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetNomineeNominatorsResData (

  @SerializedName("nominees"   ) var nominees   : ArrayList<Nominees>   = arrayListOf(),
  @SerializedName("nominators" ) var nominators : ArrayList<Nominators> = arrayListOf()

)