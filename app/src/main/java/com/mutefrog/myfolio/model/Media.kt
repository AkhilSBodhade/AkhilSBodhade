package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class Media (

  @SerializedName("id"            ) var id           : Int?    = null,
  @SerializedName("user_id"       ) var userId       : Int?    = null,
  @SerializedName("file_name"     ) var fileName     : String? = null,
  @SerializedName("file"          ) var file         : String? = null,
  @SerializedName("created_at"    ) var createdAt    : String? = null,
  @SerializedName("updated_at"    ) var updatedAt    : String? = null,
  @SerializedName("mediable_type" ) var mediableType : String? = null,
  @SerializedName("mediable_id"   ) var mediableId   : Int?    = null

)