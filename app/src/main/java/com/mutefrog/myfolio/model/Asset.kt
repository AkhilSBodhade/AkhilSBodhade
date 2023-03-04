package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class Asset (

  @SerializedName("id"            ) var id          : Int?                 = null,
  @SerializedName("asset_type_id" ) var assetTypeId : Int?                 = null,
  @SerializedName("user_id"       ) var userId      : Int?                 = null,
  @SerializedName("asset_data"    ) var assetData   : AssetData?           = AssetData(),
  @SerializedName("created_at"    ) var createdAt   : String?              = null,
  @SerializedName("updated_at"    ) var updatedAt   : String?              = null,
  @SerializedName("share"         ) var share       : Int?                 = null,
  @SerializedName("media"         ) var media       : ArrayList<Media>     = arrayListOf(),
  @SerializedName("share_info"    ) var shareInfo   : ArrayList<ShareInfo> = arrayListOf()

)