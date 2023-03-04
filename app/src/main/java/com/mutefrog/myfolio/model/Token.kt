package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class Token (

  @SerializedName("token_type"    ) var tokenType    : String? = null,
  @SerializedName("expires_in"    ) var expiresIn    : Int?    = null,
  @SerializedName("access_token"  ) var accessToken  : String? = null,
  @SerializedName("refresh_token" ) var refreshToken : String? = null

)