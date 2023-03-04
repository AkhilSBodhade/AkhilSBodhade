package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class Document (

  @SerializedName("id"               ) var id             : Int?                 = null,
  @SerializedName("user_id"          ) var userId         : Int?                 = null,
  @SerializedName("document_type_id" ) var documentTypeId : Int?                 = null,
  @SerializedName("document_data"    ) var documentData   : DocumentData?        = DocumentData(),
  @SerializedName("comment"          ) var comment        : String?              = null,
  @SerializedName("created_at"       ) var createdAt      : String?              = null,
  @SerializedName("updated_at"       ) var updatedAt      : String?              = null,
  @SerializedName("share"            ) var share          : Int?                 = null,
  @SerializedName("media"            ) var media          : ArrayList<Media>     = arrayListOf(),
  @SerializedName("share_info"       ) var shareInfo      : ArrayList<ShareInfo> = arrayListOf()

)