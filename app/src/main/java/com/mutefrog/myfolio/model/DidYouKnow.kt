package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class DidYouKnow (

  @SerializedName("id"              ) var id             : Int?    = null,
  @SerializedName("title"           ) var title          : String? = null,
  @SerializedName("type"            ) var type           : String? = null,
  @SerializedName("description"     ) var description    : String? = null,
  @SerializedName("url"             ) var url            : String? = null,
  @SerializedName("banner_image"    ) var bannerImage    : String? = null,
  @SerializedName("thumbnail_image" ) var thumbnailImage : String? = null,
  @SerializedName("position"        ) var position       : Int?    = null,
  @SerializedName("status"          ) var status         : Int?    = null,
  @SerializedName("created_at"      ) var createdAt      : String? = null,
  @SerializedName("updated_at"      ) var updatedAt      : String? = null

)