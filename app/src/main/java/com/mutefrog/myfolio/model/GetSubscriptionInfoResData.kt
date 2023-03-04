package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetSubscriptionInfoResData (

  @SerializedName("nominee_count"     ) var nomineeCount     : Int?              = null,
  @SerializedName("subscription_info" ) var subscriptionInfo : SubscriptionInfo? = SubscriptionInfo()

)