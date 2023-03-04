package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName
import com.mutefrog.myfolio.utils.decrypt


data class Contact (

  @SerializedName("id"                        ) var id                     : Int?                 = null,
  @SerializedName("user_id"                   ) var userId                 : Int?                 = null,
  @SerializedName("emergency_contact_type_id" ) var emergencyContactTypeId : Int?                 = null,
  @SerializedName("name"                      ) var name                   : String?              = null,
  @SerializedName("contact_number"            ) var contactNumber          : String?              = null,
  @SerializedName("email"                     ) var email                  : String?              = null,
  @SerializedName("share"                     ) var share                  : Int?                 = null,
  @SerializedName("created_at"                ) var createdAt              : String?              = null,
  @SerializedName("updated_at"                ) var updatedAt              : String?              = null,
  @SerializedName("share_info"                ) var shareInfo              : ArrayList<ShareInfo> = arrayListOf()

){
  fun getNameValue() = name?.decrypt()
  fun getContactNumberValue() = contactNumber?.decrypt()

}