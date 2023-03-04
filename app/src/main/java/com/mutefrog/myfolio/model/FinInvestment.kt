package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class FinInvestment (

  @SerializedName("id"              ) var id            : Int?                 = null,
  @SerializedName("finance_type_id" ) var financeTypeId : Int?                 = null,
  @SerializedName("user_id"         ) var userId        : Int?                 = null,
  @SerializedName("finance_data"    ) var financeData   : FinanceData?         = FinanceData(),
  @SerializedName("created_at"      ) var createdAt     : String?              = null,
  @SerializedName("updated_at"      ) var updatedAt     : String?              = null,
  @SerializedName("share"           ) var share         : Int?                 = null,
  @SerializedName("media"           ) var media         : ArrayList<Media>     = arrayListOf(),
  @SerializedName("share_info"      ) var shareInfo     : ArrayList<ShareInfo> = arrayListOf()


)