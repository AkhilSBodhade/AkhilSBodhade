package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName
import com.mutefrog.myfolio.utils.decrypt


data class BankAccount (

  @SerializedName("id"                   ) var id                  : Int?             = null,
  @SerializedName("user_id"              ) var userId              : Int?             = null,
  @SerializedName("acc_holder_name"      ) var accHolderName       : String?          = null,
  @SerializedName("bank_name"            ) var bankName            : String?          = null,
  @SerializedName("branch_name"          ) var branchName          : String?          = null,
  @SerializedName("account_type"         ) var accountType         : String?          = null,
  @SerializedName("account_no"           ) var accountNo           : String?          = null,
  @SerializedName("ifsc_code"            ) var ifscCode            : String?          = null,
  @SerializedName("customer_id"          ) var customerId          : String?          = null,
  @SerializedName("relationship_manager" ) var relationshipManager : String?          = null,
  @SerializedName("comment"              ) var comment             : String?          = null,
  @SerializedName("created_at"           ) var createdAt           : String?          = null,
  @SerializedName("updated_at"           ) var updatedAt           : String?          = null,
  @SerializedName("share"           ) var share           : Int?          = null,
  @SerializedName("media"                ) var media               : ArrayList<Media> = arrayListOf()

){
  fun getAccountHolderNameValue() = accHolderName?.decrypt()
  fun getBankNameValue() = bankName?.decrypt()
  fun getBankAccountNumberValue() = accountNo?.decrypt()
  fun getIFSCCodeValue() = ifscCode?.decrypt()
  fun getCustomerIdValue() = customerId?.decrypt()
}