package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName


data class Category(

    @SerializedName("id") var id: Int? = null,

    @SerializedName("loan_type") var loanType: String? = null,
    @SerializedName("finance_type") var financeType: String? = null,
    @SerializedName("asset_type") var assetType: String? = null,
    @SerializedName("insurance_type") var insuranceType: String? = null,
    @SerializedName("occupation") var contactType: String? = null,
    @SerializedName("document_type") var documentType: String? = null,

    @SerializedName("image") var image: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("doc_count") var docCount: Int? = null
)