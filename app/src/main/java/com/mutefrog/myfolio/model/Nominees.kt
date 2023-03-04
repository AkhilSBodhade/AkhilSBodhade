package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class Nominees (

  @SerializedName("id"                         ) var id                       : Int?    = null,
  @SerializedName("nominator_user_id"          ) var nominatorUserId          : Int?    = null,
  @SerializedName("nominee_user_id"            ) var nomineeUserId            : Int?    = null,
  @SerializedName("nominee_name"               ) var nomineeName              : String? = null,
  @SerializedName("nominee_dob"                ) var nomineeDob               : String? = null,
  @SerializedName("nominee_gender"             ) var nomineeGender            : String? = null,
  @SerializedName("nominee_mobile"             ) var nomineeMobile            : String? = null,
  @SerializedName("nominee_email"              ) var nomineeEmail             : String? = null,
  @SerializedName("nominee_aadhaar_card"       ) var nomineeAadhaarCard       : String? = null,
  @SerializedName("relationship_with_nominee"  ) var relationshipWithNominee  : String? = null,
  @SerializedName("otp"                        ) var otp                      : String? = null,
  @SerializedName("otp_expiry"                 ) var otpExpiry                : String? = null,
  @SerializedName("is_nominee_verified"        ) var isNomineeVerified        : Int?    = null,
  @SerializedName("information_requested"      ) var informationRequested     : Int?    = null,
  @SerializedName("app_user"                   ) var appUser                  : Int?    = null,
  @SerializedName("created_at"                 ) var createdAt                : String? = null,
  @SerializedName("updated_at"                 ) var updatedAt                : String? = null,
  @SerializedName("information_shared"         ) var informationShared        : Int?    = null,
  @SerializedName("information_requested_date" ) var informationRequestedDate : String? = null,
  @SerializedName("notification_day_count"     ) var notificationDayCount     : String? = null,
  @SerializedName("nominator_user_name"        ) var nominatorUserName        : String? = null,
  @SerializedName("nominee_user_name"          ) var nomineeUserName          : String? = null

)