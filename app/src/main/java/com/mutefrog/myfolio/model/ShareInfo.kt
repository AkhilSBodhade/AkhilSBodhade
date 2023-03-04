package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class ShareInfo (

  @SerializedName("id"                ) var id              : Int?    = null,
  @SerializedName("nominee_id"        ) var nomineeId       : Int?    = null,
  @SerializedName("shareable_type"    ) var shareableType   : String? = null,
  @SerializedName("shareable_id"      ) var shareableId     : Int?    = null,
  @SerializedName("share_single_info" ) var shareSingleInfo : Int?    = null,
  @SerializedName("share_all_info"    ) var shareAllInfo    : Int?    = null,
  @SerializedName("type"              ) var type            : String? = null,
  @SerializedName("created_at"        ) var createdAt       : String? = null,
  @SerializedName("updated_at"        ) var updatedAt       : String? = null

)