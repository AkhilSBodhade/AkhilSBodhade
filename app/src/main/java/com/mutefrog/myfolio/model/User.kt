package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class User (

  @SerializedName("id"                         ) var id                      : Int?    = null,
  @SerializedName("name"                       ) var name                    : String? = null,
  @SerializedName("email"                      ) var email                   : String? = null,
  @SerializedName("email_verified_at"          ) var emailVerifiedAt         : String? = null,
  @SerializedName("created_at"                 ) var createdAt               : String? = null,
  @SerializedName("updated_at"                 ) var updatedAt               : String? = null,
  @SerializedName("role"                       ) var role                    : String? = null,
  @SerializedName("profile_image"              ) var profileImage            : String? = null,
  @SerializedName("country_of_residence"       ) var countryOfResidence      : String? = null,
  @SerializedName("phone_code"                ) var phoneCode               : String? = null,
  @SerializedName("country_code"               ) var countryCode             : String? = null,
  @SerializedName("mobile"                     ) var mobile                  : String? = null,
  @SerializedName("status"                     ) var status                  : Int?    = null,
  @SerializedName("dob"                        ) var dob                     : String? = null,
  @SerializedName("gender"                     ) var gender                  : String? = null,
  @SerializedName("otp"                        ) var otp                     : String? = null,
  @SerializedName("otp_expiry"                 ) var otpExpiry               : String? = null,
  @SerializedName("is_verified"                ) var isVerified              : Int?    = null,
  @SerializedName("firebase_device_token"      ) var firebaseDeviceToken     : String? = null,
  @SerializedName("profile_delete_code"        ) var profileDeleteCode       : String? = null,
  @SerializedName("profile_delete_code_expiry" ) var profileDeleteCodeExpiry : String? = null,
  @SerializedName("profile_deleted"            ) var profileDeleted          : Int?    = null,
  @SerializedName("subscription_status"        ) var subscriptionStatus      : Int?    = null,
  @SerializedName("payment_status"             ) var paymentStatus           : Int?    = null,
  @SerializedName("subscription_end_date"      ) var subscriptionEndDate     : String? = null,
  @SerializedName("is_registration_completed" ) var isRegistrationCompleted : Int?    = null,
  @SerializedName("profile_image_full_url"    ) var profileImageFullUrl     : String? = null

)