package com.mutefrog.myfolio.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mutefrog.myfolio.utils.decrypt


data class Loan(

    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("user_id")
    var userId: Int? = null,
    @SerializedName("loan_type_id")
    var loanTypeId: Int? = null,
    @SerializedName("purpose_of_loan")
    var purposeOfLoan: String? = null,
    @SerializedName("acc_holder_name") var accHolderName: String? = null,
    @SerializedName("loan_account_no") var loanAccountNo: String? = null,
    @SerializedName("loan_amount") var loanAmount: String? = null,
    @SerializedName("loan_institute_name") var loanInstituteName: String? = null,
    @SerializedName("emi_value") var emiValue: String? = null,
    @SerializedName("start_date") var startDate: String? = null,
    @SerializedName("end_date") var endDate: String? = null,
    @SerializedName("duration") var duration: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("comment") var comment: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("share") var share: Int? = null,
    @SerializedName("media") var media: ArrayList<Media> = arrayListOf(),
    @SerializedName("share_info") var shareInfo: ArrayList<ShareInfo> = arrayListOf()
){
  fun getPurposeOfLoanValue() = purposeOfLoan?.decrypt()?:""
  fun getAccHolderNameValue() = accHolderName?.decrypt()?:""
  fun getLoanAccountNoValue() = loanAccountNo?.decrypt()?:""
  fun getLoanAmountValue() = loanAmount?.decrypt()?:""
  fun getLoanInstituteNameValue() = loanInstituteName?.decrypt()?:""
  fun getEmiValueValue() = emiValue?.decrypt()?:""
}

