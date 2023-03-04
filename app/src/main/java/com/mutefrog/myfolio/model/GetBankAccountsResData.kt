package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class GetBankAccountsResData (

  @SerializedName("list" ) var list : ArrayList<BankAccount> = arrayListOf()

)