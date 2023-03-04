package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetContactsResData (

  @SerializedName("list" ) var list : ArrayList<Contact> = arrayListOf()

)