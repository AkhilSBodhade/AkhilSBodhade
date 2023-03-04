package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class Meta (

  @SerializedName("notification_count" ) var notificationCount : Int? = null

)