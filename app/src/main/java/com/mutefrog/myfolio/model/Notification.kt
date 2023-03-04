package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class Notification (

  @SerializedName("id"         ) var id        : Int?    = null,
  @SerializedName("user_id"    ) var userId    : String? = null,
  @SerializedName("type"       ) var type      : String? = null,
  @SerializedName("event"      ) var event     : String? = null,
  @SerializedName("title"      ) var title     : String? = null,
  @SerializedName("body"       ) var body      : String? = null,
  @SerializedName("created_at" ) var createdAt : String? = null,
  @SerializedName("updated_at" ) var updatedAt : String? = null

)