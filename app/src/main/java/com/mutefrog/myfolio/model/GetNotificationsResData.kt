package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetNotificationsResData (

  @SerializedName("list" ) var list : ArrayList<Notification> = arrayListOf(),
  @SerializedName("meta"         ) var meta         : Meta?                   = Meta()

)