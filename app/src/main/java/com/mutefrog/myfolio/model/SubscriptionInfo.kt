package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class SubscriptionInfo (

  @SerializedName("type"                    ) var type                  : String? = null,
  @SerializedName("subscription_start_date" ) var subscriptionStartDate : String? = null,
  @SerializedName("subscription_status"     ) var subscriptionStatus    : Int?    = null,
  @SerializedName("subscription_end_date"   ) var subscriptionEndDate   : String? = null,
  @SerializedName("grace_period_start_date" ) var gracePeriodStartDate  : String? = null,
  @SerializedName("grace_period_end_date"   ) var gracePeriodEndDate    : String? = null

)