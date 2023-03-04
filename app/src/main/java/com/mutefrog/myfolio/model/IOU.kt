package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName
import com.mutefrog.myfolio.utils.decrypt


data class IOU (

  @SerializedName("id"                  ) var id                 : Int?                 = null,
  @SerializedName("user_id"             ) var userId             : Int?                 = null,
  @SerializedName("currency_type_id"    ) var currencyTypeId     : String?              = null,
  @SerializedName("type"                ) var type               : String?              = null,
  @SerializedName("lender_name"         ) var lenderName         : String?              = null,
  @SerializedName("borrower_name"       ) var borrowerName       : String?              = null,
  @SerializedName("total_amount"        ) var totalAmount        : String?              = null,
  @SerializedName("returned_amount"     ) var returnedAmount     : String?              = null,
  @SerializedName("pending_amount"      ) var pendingAmount      : String?              = null,
  @SerializedName("interest_percentage" ) var interestPercentage : String?              = null,
  @SerializedName("interest_applicable" ) var interestApplicable : String?              = null,
  @SerializedName("deadline"            ) var deadline           : String?              = null,
  @SerializedName("contact_number"      ) var contactNumber      : String?              = null,
  @SerializedName("reason"              ) var reason             : String?              = null,
  @SerializedName("comment"             ) var comment            : String?              = null,
  @SerializedName("created_at"          ) var createdAt          : String?              = null,
  @SerializedName("updated_at"          ) var updatedAt          : String?              = null,
  @SerializedName("share"               ) var share              : Int?                 = null,
  @SerializedName("media"               ) var media              : ArrayList<Media>     = arrayListOf(),
  @SerializedName("share_info"          ) var shareInfo          : ArrayList<ShareInfo> = arrayListOf()

){
  fun getLenderNameValue() = lenderName?.decrypt()
  fun getTotalAmountValue() = totalAmount?.decrypt()
  fun getBorrowerNameValue() = borrowerName?.decrypt()
  fun getAmountReturnedValue() = returnedAmount?.decrypt()
  fun getPendingAmountValue() = pendingAmount?.decrypt()
  fun getReasonValue() = reason?.decrypt()
}