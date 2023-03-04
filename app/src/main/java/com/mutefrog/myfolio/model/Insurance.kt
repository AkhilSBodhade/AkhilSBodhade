package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName
import com.mutefrog.myfolio.utils.decrypt


data class Insurance (

  @SerializedName("id"                     ) var id                   : Int?                 = null,
  @SerializedName("user_id"                ) var userId               : Int?                 = null,
  @SerializedName("insurance_type_id"      ) var insuranceTypeId      : Int?                 = null,
  @SerializedName("insurance_company_name" ) var insuranceCompanyName : String?              = null,
  @SerializedName("insurance_holder_name"  ) var insuranceHolderName  : String?              = null,
  @SerializedName("insurance_type"         ) var insuranceType        : String?              = null,
  @SerializedName("insurance_number"       ) var insuranceNumber      : String?              = null,
  @SerializedName("total_cover_amount"     ) var totalCoverAmount     : String?              = null,
  @SerializedName("premium_type"           ) var premiumType          : String?              = null,
  @SerializedName("note"                   ) var note                 : String?              = null,
  @SerializedName("premium_amount"         ) var premiumAmount        : String?              = null,
  @SerializedName("nominee_name"           ) var nomineeName          : String?              = null,
  @SerializedName("nominee_relationship"   ) var nomineeRelationship  : String?              = null,
  @SerializedName("file"                   ) var file                 : String?              = null,
  @SerializedName("created_at"             ) var createdAt            : String?              = null,
  @SerializedName("updated_at"             ) var updatedAt            : String?              = null,
  @SerializedName("share"                  ) var share                : Int?                 = null,
  @SerializedName("media"                  ) var media                : ArrayList<String>    = arrayListOf(),
  @SerializedName("share_info"             ) var shareInfo            : ArrayList<ShareInfo> = arrayListOf(),
  @SerializedName("insurance_category"             ) var insuranceCategory            : String?              = null,

){
  fun getInsuranceCompanyNameValue() = insuranceCompanyName?.decrypt()
  fun getInsuranceHolderNameValue() = insuranceHolderName?.decrypt()
  fun getInsuranceTypeValue() = insuranceType?.decrypt()
  fun getInsuranceNumberValue() = insuranceNumber?.decrypt()
  fun getTotalCoverAmountValue() = totalCoverAmount?.decrypt()
  fun getPremiumAmountValue() = premiumAmount?.decrypt()
  fun getInsuranceCategoryValue() = insuranceCategory?.decrypt()
}