package com.mutefrog.myfolio.api

import com.mutefrog.myfolio.model.*
import retrofit2.Response
import retrofit2.http.*

interface MyFolioApi {

    @POST("signup")
    suspend fun signup(
        @Query("email") email: String
    ): Response<SignupResponse>

    @POST("signup-verify-otp")
    suspend fun signupVerifyOtp(
        @Query("email") email: String,
        @Query("otp") otp: String
    ): Response<SignupVerifyOtpResponse>

    @POST("resend-user-otp")
    suspend fun resend(
        @Query("email") email: String
    ): Response<ResendUserOtpResponse>

    @POST("complete-signup")
    suspend fun completeSignup(
        @Header("Authorization") auth: String?,
        @Query("name") name: String,
        @Query("phone_code") phoneCode: String,
        @Query("country_code") countryCode: String,
        @Query("mobile") mobile: String,
        @Query("dob") dob: String,
        @Query("gender") gender: String,
        @Query("country_of_residence") countryOfResidence: String
    ): Response<CompleteSignupResponse>

    @POST("signin-request-otp")
    suspend fun signin(
        @Query("email") email: String
    ): Response<SigninResponse>

    @POST("signin-verify-otp")
    suspend fun signinVerifyOtp(
        @Query("email") email: String?,
        @Query("otp") otp: String?
    ): Response<SignupVerifyOtpResponse>

    @POST("bank/add")
    suspend fun addBankAccount(
        @Header("Authorization") auth: String?,
        @Query("account_type") accountType: Int,
        @Query("acc_holder_name") accountHolderName: String,
        @Query("bank_name") bankName: String,
        @Query("branch_name") branchName: String,
        @Query("account_no") accountNumber: String,
        @Query("ifsc_code") ifscCode: String,
        @Query("customer_id") customerId: String,
        @Query("relationship_manager") relationshipMgr: String,
        @Query("comment") comment: String,
        @Query("share") share: Int
    ): Response<AddBankAccountResponse>

    @POST("bank/update/{id}")
    suspend fun updateBankAccount(
        @Path(value = "id", encoded = true) id: Int?,
        @Header("Authorization") auth: String?,
        @Query("account_type") accountType: Int,
        @Query("acc_holder_name") accountHolderName: String,
        @Query("bank_name") bankName: String,
        @Query("branch_name") branchName: String,
        @Query("account_no") accountNumber: String,
        @Query("ifsc_code") ifscCode: String,
        @Query("customer_id") customerId: String,
        @Query("relationship_manager") relationshipMgr: String,
        @Query("comment") comment: String,
        @Query("share") share: Int
    ): Response<AddBankAccountResponse>

    @GET("bank/list/{accountType}")
    suspend fun getBankAccounts(
        @Header("Authorization") auth: String?,
        @Path(value = "accountType", encoded = true) accountType: String?
    ): Response<GetBankAccountsResponse>

    @DELETE("bank/delete/{accountId}")
    suspend fun deleteBankAccount(
        @Header("Authorization") auth: String?,
        @Path(value = "accountId", encoded = true) accountId: String?
    ): Response<DeleteBankAccountResponse>

    @DELETE("loan/delete/{id}")
    suspend fun deleteLoan(
        @Header("Authorization") auth: String?,
        @Path(value = "id", encoded = true) accountId: String?
    ): Response<DeleteLoanResponse>

    @GET("ious/list/{accountType}")
    suspend fun getPious(
        @Header("Authorization") auth: String?,
        @Path(value = "accountType", encoded = true) accountType: String?
    ): Response<GetPersonalIousResponse>

    @DELETE("ious/delete/{accountId}")
    suspend fun deletePiou(
        @Header("Authorization") auth: String?,
        @Path(value = "accountId", encoded = true) accountId: String?
    ): Response<DeletePersonalIouResponse>

    @POST("ious/add")
    suspend fun addPersonalIou(
        @Header("Authorization") auth: String?,
        @Query("type") type: Int,
        @Query("reason") reason: String,
        @Query("lender_name") lenderName: String,
        @Query("borrower_name") borrowerName: String,
        @Query("total_amount") amount: String,
        @Query("currency_type_id") currencyType: Int,
        @Query("pending_amount") amountPending: String,
        @Query("returned_amount") amountReturned: String,
        @Query("deadline") deadline: String,
        @Query("comment") comment: String,
        @Query("share") share: Int
    ): Response<AddPersonalIouResponse>

    @POST("ious/update/{id}")
    suspend fun updatePersonalIou(
        @Path(value = "id", encoded = true) id: Int?,
        @Header("Authorization") auth: String?,
        @Query("type") type: Int,
        @Query("reason") reason: String,
        @Query("lender_name") lenderName: String,
        @Query("borrower_name") borrowerName: String,
        @Query("total_amount") amount: String,
        @Query("currency_type_id") currencyType: Int,
        @Query("pending_amount") amountPending: String,
        @Query("returned_amount") amountReturned: String,
        @Query("deadline") deadline: String,
        @Query("comment") comment: String,
        @Query("share") share: Int
    ): Response<AddPersonalIouResponse>


    @POST("loan/add")
    suspend fun addLoan(
        @Header("Authorization") auth: String?,
        @Query("loan_type_id") type: Int?,
        @Query("purpose_of_loan") purpose: String,
        @Query("loan_account_no") accountNo: String,
        @Query("acc_holder_name") accHolderName: String,
        @Query("loan_amount") loanAmount: String,
        @Query("loan_institute_name") loanInstituteName: String,
        @Query("emi_value") emiValue: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("status") status: Int,
        @Query("comment") comment: String,
        @Query("share") share: Int
    ): Response<AddLoanResponse>


    @POST("loan/update/{id}")
    suspend fun updateLoan(
        @Path(value = "id", encoded = true) id: Int?,
        @Header("Authorization") auth: String?,
        @Query("loan_type_id") type: Int?,
        @Query("purpose_of_loan") purpose: String,
        @Query("loan_account_no") accountNo: String,
        @Query("acc_holder_name") accHolderName: String,
        @Query("loan_amount") loanAmount: String,
        @Query("loan_institute_name") loanInstituteName: String,
        @Query("emi_value") emiValue: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("status") status: Int,
        @Query("comment") comment: String,
        @Query("share") share: Int
    ): Response<AddLoanResponse>

    @GET("loan/list/{loanType}")
    suspend fun getLoans(
        @Header("Authorization") auth: String?,
        @Path(value = "loanType", encoded = true) accountType: String?
    ): Response<GetLoansResponse>


    @GET("{category}/type/list")
    suspend fun getCategories(
        @Path(value = "category", encoded = true) category: String?,
        @Header("Authorization") auth: String?,
    ): Response<GetCategoriesResponse>


    @POST("personal-information")
    suspend fun updatePersonalInformation(
        @Header("Authorization") auth: String?,
        @Query("name") name: String,
        @Query("phone_code") phoneCode: String,
        @Query("country_code") countryCode: String,
        @Query("mobile") mobile: String,
        @Query("dob") dob: String,
        @Query("gender") gender: String,
        @Query("country_of_residence") countryOfResidence: String
    ): Response<CompleteSignupResponse>


    @POST("finance/add")
    suspend fun addFinInvestment(
        @Header("Authorization") auth: String?,
        @Query("finance_type_id") finance_type_id: Int?,
        @Query("acc_holder_name") acc_holder_name: String,
        @Query("broker_name") broker_name: String,
        @Query("equity_name") equity_name: String,
        @Query("customer_id") customer_id: String,
        @Query("no_of_shares") no_of_shares: String,
        @Query("amount_invested") amount_invested: String,
        @Query("fund_type") fund_type: String,
        @Query("fund_name") fund_name: String,
        @Query("sip_name") sip_name: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String,
        @Query("bank_name") bank_name: String,
        @Query("branch_name") branch_name: String,
        @Query("fixed_deposit_no") fixed_deposit_no: String,
        @Query("amount_deposit") amount_deposit: String,
        @Query("maturity_amount") maturity_amount: String,
        @Query("maturity_date") maturity_date: String,
        @Query("recurring_deposit_no") recurring_deposit_no: String,
        @Query("montly_installment") montly_installment: String,
        @Query("crypto_name") crypto_name: String,
        @Query("quantity") quantity: String,
        @Query("nps_type") nps_type: String,
        @Query("retirement_account_no") retirement_account_no: String,
        @Query("nps_amount") nps_amount: String,
        @Query("ppf_account_no") ppf_account_no: String,
        @Query("bond_name") bond_name: String,
        @Query("bond_no") bond_no: String,
        @Query("principle_amount") principle_amount: String,
        @Query("uan_number") uan_number: String,
        @Query("pf_amount") pf_amount: String,
        @Query("employee_number") employee_number: String,
        @Query("certificate_name") certificate_name: String,
        @Query("certificate_no") certificate_no: String,
        @Query("investment_type") investment_type: String,
        @Query("folio_no") folio_no: String,
        @Query("investment_amount") investment_amount: String,
        @Query("comment") comment: String,
        @Query("share") share: Int?
    ): Response<AddUpdateFinInvestmentResponse>

    @POST("finance/update/{id}")
    suspend fun updateFinInvestment(
        @Path(value = "id", encoded = true) id: Int?,
        @Header("Authorization") auth: String?,
        @Query("finance_type_id") finance_type_id: Int?,
        @Query("acc_holder_name") acc_holder_name: String,
        @Query("broker_name") broker_name: String,
        @Query("equity_name") equity_name: String,
        @Query("customer_id") customer_id: String,
        @Query("no_of_shares") no_of_shares: String,
        @Query("amount_invested") amount_invested: String,
        @Query("fund_type") fund_type: String,
        @Query("fund_name") fund_name: String,
        @Query("sip_name") sip_name: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String,
        @Query("bank_name") bank_name: String,
        @Query("branch_name") branch_name: String,
        @Query("fixed_deposit_no") fixed_deposit_no: String,
        @Query("amount_deposit") amount_deposit: String,
        @Query("maturity_amount") maturity_amount: String,
        @Query("maturity_date") maturity_date: String,
        @Query("recurring_deposit_no") recurring_deposit_no: String,
        @Query("montly_installment") montly_installment: String,
        @Query("crypto_name") crypto_name: String,
        @Query("quantity") quantity: String,
        @Query("nps_type") nps_type: String,
        @Query("retirement_account_no") retirement_account_no: String,
        @Query("nps_amount") nps_amount: String,
        @Query("ppf_account_no") ppf_account_no: String,
        @Query("bond_name") bond_name: String,
        @Query("bond_no") bond_no: String,
        @Query("principle_amount") principle_amount: String,
        @Query("uan_number") uan_number: String,
        @Query("pf_amount") pf_amount: String,
        @Query("employee_number") employee_number: String,
        @Query("certificate_name") certificate_name: String,
        @Query("certificate_no") certificate_no: String,
        @Query("investment_type") investment_type: String,
        @Query("folio_no") folio_no: String,
        @Query("investment_amount") investment_amount: String,
        @Query("comment") comment: String,
        @Query("share") share: Int?
    ): Response<AddUpdateFinInvestmentResponse>

    @GET("finance/list/{typeId}")
    suspend fun getFinInvestments(
        @Header("Authorization") auth: String?,
        @Path(value = "typeId", encoded = true) typeId: String?
    ): Response<GetFinInvestmentsResponse>

    @DELETE("finance/delete/{id}")
    suspend fun deleteFinInvestment(
        @Header("Authorization") auth: String?,
        @Path(value = "id", encoded = true) id: String?
    ): Response<DeleteFinInvestmentResponse>


    @POST("contact/add")
    suspend fun addContact(
        @Header("Authorization") auth: String?,
        @Query("contact_type_id") typeId: Int?,
        @Query("name") contactName: String,
        @Query("contact_number") ContactNumber: String,
        @Query("email") email: String,
        @Query("share") share: Int?
    ): Response<AddUpdateContactResponse>

    @POST("contact/update/{id}")
    suspend fun updateContact(
        @Path(value = "id", encoded = true) id: Int?,
        @Header("Authorization") auth: String?,
        @Query("contact_type_id") typeId: Int?,
        @Query("name") contactName: String,
        @Query("contact_number") ContactNumber: String,
        @Query("email") email: String,
        @Query("share") share: Int?
    ): Response<AddUpdateContactResponse>

    @GET("contact/list")
    suspend fun getContacts(
        @Header("Authorization") auth: String?,
    ): Response<GetContactsResponse>

    @DELETE("contact/delete/{id}")
    suspend fun deleteContact(
        @Header("Authorization") auth: String?,
        @Path(value = "id", encoded = true) id: String?
    ): Response<DeleteContactResponse>

    @POST("document/add")
    suspend fun addDocument(
        @Header("Authorization") auth: String?,
        @Query("document_type_id") typeId: Int?,
        @Query("document_holder_name") docHolderName: String,
        @Query("document_number") docNumber: String?,
        @Query("medical_type") medType: String,
        @Query("medical_date") medDate: String,
        @Query("prescription_type") presType: String,
        @Query("prescription_date") presDate: String,
        @Query("comment") comment: String,
        @Query("share") share: Int?
    ): Response<AddUpdateDocumentResponse>

    @POST("document/update/{id}")
    suspend fun updateDocument(
        @Path(value = "id", encoded = true) id: Int?,
        @Header("Authorization") auth: String?,
        @Query("document_type_id") typeId: Int?,
        @Query("document_holder_name") docHolderName: String,
        @Query("document_number") docNumber: String?,
        @Query("medical_type") medType: String,
        @Query("medical_date") medDate: String,
        @Query("prescription_type") presType: String,
        @Query("prescription_date") presDate: String,
        @Query("comment") comment: String,
        @Query("share") share: Int?
    ): Response<AddUpdateDocumentResponse>

    @GET("documents/list/{typeId}")
    suspend fun getDocuments(
        @Header("Authorization") auth: String?,
        @Path(value = "typeId", encoded = true) typeId: String?
    ): Response<GetDocumentsResponse>

    @DELETE("document/delete/{id}")
    suspend fun deleteDocument(
        @Header("Authorization") auth: String?,
        @Path(value = "id", encoded = true) id: String?
    ): Response<DeleteDocumentResponse>


    @POST("card/add")
    suspend fun addCard(
        @Header("Authorization") auth: String?,
        @Query("card_type") typeId: Int?,
        @Query("card_bank_name") cardBankName: String,
        @Query("card_no") cardNumber: String,
        @Query("expiry_date") expiryDate: String,
        @Query("card_limit") cardLimit: String,
        @Query("comment") comment: String,
        @Query("share") share: Int?
    ): Response<AddUpdateCardResponse>

    @POST("card/update/{id}")
    suspend fun updateCard(
        @Path(value = "id", encoded = true) id: Int?,
        @Header("Authorization") auth: String?,
        @Query("card_type") typeId: Int?,
        @Query("card_bank_name") cardBankName: String,
        @Query("card_no") cardNumber: String,
        @Query("expiry_date") expiryDate: String,
        @Query("card_limit") cardLimit: String,
        @Query("comment") comment: String,
        @Query("share") share: Int?
    ): Response<AddUpdateCardResponse>

    @GET("card/list/{typeId}")
    suspend fun getCards(
        @Header("Authorization") auth: String?,
        @Path(value = "typeId", encoded = true) typeId: String?
    ): Response<GetCardsResponse>

    @DELETE("card/delete/{id}")
    suspend fun deleteCard(
        @Header("Authorization") auth: String?,
        @Path(value = "id", encoded = true) id: String?
    ): Response<DeleteCardResponse>


    @POST("asset/add")
    suspend fun addAsset(
        @Header("Authorization") auth: String?,
        @Query("asset_type_id") typeId: Int?,
        @Query("owner_name") ownerName: String,
        @Query("property_type") propertyType: String,
        @Query("property_name") propertyName: String,
        @Query("property_address") propertyAddress: String,
        @Query("value_of_property") propertyValue: String,
        @Query("status") propertyStatus: String,
        @Query("vehicle_type") vehicleType: String,
        @Query("vehicle_brand") vehicleBrand: String,
        @Query("registration_no") vehicleRegNo: String,
        @Query("chassis_no") vehicleChassisNo: String,
        @Query("insurance_validity") vehicleInsValidity: String,
        @Query("asset_type") assetType: String,
        @Query("asset_name") assetName: String,
        @Query("asset_weight") assetWeight: String,
        @Query("asset_quantity") assetQty: String,
        @Query("comment") comment: String,
        @Query("share") share: Int?
    ): Response<AddUpdateAssetResponse>

    @POST("asset/update/{id}")
    suspend fun updateAsset(
        @Path(value = "id", encoded = true) id: Int?,
        @Header("Authorization") auth: String?,
        @Query("asset_type_id") typeId: Int?,
        @Query("owner_name") ownerName: String,
        @Query("property_type") propertyType: String,
        @Query("property_name") propertyName: String,
        @Query("property_address") propertyAddress: String,
        @Query("value_of_property") propertyValue: String,
        @Query("status") propertyStatus: String,
        @Query("vehicle_type") vehicleType: String,
        @Query("vehicle_brand") vehicleBrand: String,
        @Query("registration_no") vehicleRegNo: String,
        @Query("chassis_no") vehicleChassisNo: String,
        @Query("insurance_validity") vehicleInsValidity: String,
        @Query("asset_type") assetType: String,
        @Query("asset_name") assetName: String,
        @Query("asset_weight") assetWeight: String,
        @Query("asset_quantity") assetQty: String,
        @Query("comment") comment: String,
        @Query("share") share: Int?
    ): Response<AddUpdateAssetResponse>

    @GET("asset/list/{typeId}")
    suspend fun getAsset(
        @Header("Authorization") auth: String?,
        @Path(value = "typeId", encoded = true) typeId: String?
    ): Response<GetAssetsResponse>

    @DELETE("asset/delete/{id}")
    suspend fun deleteAsset(
        @Header("Authorization") auth: String?,
        @Path(value = "id", encoded = true) id: String?
    ): Response<DeleteAssetResponse>

    @GET("nominee/get-nominees-nominators-list")
    suspend fun getNomineeNominators(
        @Header("Authorization") auth: String?,
    ): Response<GetNomineeNominatorsResponse>

    @DELETE("nominee/delete/{id}")
    suspend fun deleteNominee(
        @Header("Authorization") auth: String?,
        @Path(value = "id", encoded = true) id: String?
    ): Response<DeleteNomineeResponse>

    @POST("nominee/add")
    suspend fun addNominee(
        @Header("Authorization") auth: String?,
        @Query("nominee_name") nomineeName: String,
        @Query("nominee_dob") nomineeDOB: String,
        @Query("nominee_gender") nomineeGender: String,
        @Query("nominee_mobile") nomineeMobile: String,
        @Query("nominee_email") nomineeEmail: String,
        @Query("nominee_aadhaar_card") nomineeAadhar: String,
        @Query("relationship_with_nominee") nomineeRelation: String,
    ): Response<AddNomineeResponse>


    @POST("nominee/update/{id}")
    suspend fun updateNominee(
        @Path(value = "id", encoded = true) id: Int?,
        @Header("Authorization") auth: String?,
        @Query("nominee_name") nomineeName: String,
        @Query("nominee_dob") nomineeDOB: String,
        @Query("nominee_gender") nomineeGender: String,
        @Query("nominee_mobile") nomineeMobile: String,
        @Query("nominee_email") nomineeEmail: String,
        @Query("nominee_aadhaar_card") nomineeAadhar: String,
        @Query("relationship_with_nominee") nomineeRelation: String,
    ): Response<AddNomineeResponse>

    @POST("nominee/verify-otp")
    suspend fun nomineeVerifyOtp(
        @Header("Authorization") auth: String?,
        @Query("id") email: String,
        @Query("otp") otp: String
    ): Response<NomineeVerifyOtpResponse>

    @POST("nominee/resend-otp")
    suspend fun nomineeResendOtp(
        @Header("Authorization") auth: String?,
        @Query("id") email: String
    ): Response<ResendUserOtpResponse>

    @DELETE("logout}")
    suspend fun logout(
        @Header("Authorization") auth: String?
    ): Response<LogoutResponse>

    @POST("insurance/add")
    suspend fun addInsurance(
        @Header("Authorization") auth: String?,
        @Query("insurances_type_id") typeId: Int?,
        @Query("insurance_type") type: String,
        @Query("insurance_holder_name") insHolderName: String,
        @Query("insurance_company_name") insCompanyName: String,
        @Query("insurance_number") insNumber: String,
        @Query("total_cover_amount") insCoverAmt: String,
        @Query("premium_amount") insPremiumAmt: String,
        @Query("note") comment: String,
        @Query("share") share: Int?,
        @Query("insurance_category") insCategory: String,
    ): Response<AddInsuranceResponse>

    @POST("insurance/update/{id}")
    suspend fun updateInsurance(
        @Path(value = "id", encoded = true) id: Int?,
        @Header("Authorization") auth: String?,
        @Query("insurances_type_id") typeId: Int?,
        @Query("insurance_type") type: String,
        @Query("insurance_holder_name") insHolderName: String,
        @Query("insurance_company_name") insCompanyName: String,
        @Query("insurance_number") insNumber: String,
        @Query("total_cover_amount") insCoverAmt: String,
        @Query("premium_amount") insPremiumAmt: String,
        @Query("note") comment: String,
        @Query("share") share: Int?,
        @Query("insurance_category") insCategory: String,
    ): Response<AddInsuranceResponse>

    @GET("insurance/list/{typeId}")
    suspend fun getInsurance(
        @Header("Authorization") auth: String?,
        @Path(value = "typeId", encoded = true) typeId: String?
    ): Response<GetInsuranceResponse>

    @DELETE("insurance/delete/{id}")
    suspend fun deleteInsurance(
        @Header("Authorization") auth: String?,
        @Path(value = "id", encoded = true) id: String?
    ): Response<DeleteInsuranceResponse>

    @GET("subscription-info")
    suspend fun getSubscriptionInfo(
        @Header("Authorization") auth: String?
    ): Response<GetSubscriptionInfoResponse>

    @POST("register-fcm-token")
    suspend fun registerFCMToken(
        @Query("device_type") deviceType: String?,
        @Query("device_id") deviceId: String?,
        @Query("fcm_token") fcmToken: String?
    ): Response<PushNotiTokenRegistrationResponse>

    @GET("push-notifications")
    suspend fun getNotifications(
        @Header("Authorization") auth: String?
    ): Response<GetNotificationsResponse>

    @GET("did-you-know")
    suspend fun getDidYouKnow(
        @Header("Authorization") auth: String?
    ): Response<GetDidYouKnowResponse>


    @POST("profile/delete/request")
    suspend fun deleteAccountRequest(
        @Header("Authorization") auth: String?
    ): Response<DeleteAccountReqResponse>

    @POST("profile/delete")
    suspend fun deleteAccount(
        @Header("Authorization") auth: String?,
        @Query("code") code: String?
    ): Response<DeleteAccountReqResponse>

}