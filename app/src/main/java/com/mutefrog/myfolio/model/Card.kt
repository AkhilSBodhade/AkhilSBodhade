package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName
import com.mutefrog.myfolio.utils.decrypt


data class Card (

  @SerializedName("id"                    ) var id                  : Int?                 = null,
  @SerializedName("user_id"               ) var userId              : Int?                 = null,
  @SerializedName("card_type"             ) var cardType            : String?              = null,
  @SerializedName("card_bank_name"        ) var cardBankName        : String?              = null,
  @SerializedName("card_no"               ) var cardNo              : String?              = null,
  @SerializedName("expiry_date"           ) var expiryDate          : String?              = null,
  @SerializedName("card_limit"            ) var cardLimit           : String?              = null,
  @SerializedName("comment"               ) var comment             : String?              = null,
  @SerializedName("coverage_amount"       ) var coverageAmount      : String?              = null,
  @SerializedName("credit_accident_cover" ) var creditAccidentCover : String?              = null,
  @SerializedName("created_at"            ) var createdAt           : String?              = null,
  @SerializedName("updated_at"            ) var updatedAt           : String?              = null,
  @SerializedName("share"                 ) var share               : Int?                 = null,
  @SerializedName("media"                 ) var media               : ArrayList<Media>     = arrayListOf(),
  @SerializedName("share_info"            ) var shareInfo           : ArrayList<ShareInfo> = arrayListOf()

){
  fun getCardBankNameValue() = cardBankName?.decrypt()
  fun getCardNoValue() = cardNo?.decrypt()
  fun getCardLimitValue() = cardLimit?.decrypt()
}