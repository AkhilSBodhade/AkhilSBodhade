package com.mutefrog.myfolio.di

import com.mutefrog.myfolio.api.MyFolioApi
import com.mutefrog.myfolio.model.*
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class NetworkRepository @Inject constructor(val myFolioApi: MyFolioApi) {

    suspend fun signup(email: String): Response<SignupResponse> {
        return myFolioApi.signup(email)
    }

    suspend fun signupVerifyOtp(email: String, otp: String): Response<SignupVerifyOtpResponse> {
        return myFolioApi.signupVerifyOtp(email, otp)
    }

    suspend fun resend(email: String): Response<ResendUserOtpResponse> {
        return myFolioApi.resend(email)
    }

    suspend fun completeSignup(
        token: String?, fullname: String, phoneCode: String, countryCode: String,
        mobile: String, dob: String, gender: String, countryOfResidence: String
    )
            : Response<CompleteSignupResponse> {
        return myFolioApi.completeSignup(
            token,
            fullname,
            phoneCode,
            countryCode,
            mobile,
            dob,
            gender,
            countryOfResidence
        )
    }

    suspend fun updatePersonalInformation(
        token: String?, fullname: String, phoneCode: String, countryCode: String,
        mobile: String, dob: String, gender: String, countryOfResidence: String
    )
            : Response<CompleteSignupResponse> {
        return myFolioApi.updatePersonalInformation(
            token,
            fullname,
            phoneCode,
            countryCode,
            mobile,
            dob,
            gender,
            countryOfResidence
        )
    }

    suspend fun login(email: String): Response<SigninResponse> {
        return myFolioApi.signin(email)
    }

    suspend fun loginVerifyOtp(email: String?, otp: String?): Response<SignupVerifyOtpResponse> {
        return myFolioApi.signinVerifyOtp(email, otp)
    }

    suspend fun addBankAccount(
        id: Int?,
        token: String?,
        accountType: Int,
        accountHolderName: String,
        bankName: String,
        branchName: String,
        accountNumber: String,
        ifscCode: String,
        customerId: String,
        relationshipMgr: String,
        comment: String,
        share: Int
    ): Response<AddBankAccountResponse> {
        if (id != null)
            return myFolioApi.updateBankAccount(
                id,
                token,
                accountType,
                accountHolderName,
                bankName,
                branchName,
                accountNumber,
                ifscCode,
                customerId,
                relationshipMgr,
                comment,
                share
            )
        else
            return myFolioApi.addBankAccount(
                token,
                accountType,
                accountHolderName,
                bankName,
                branchName,
                accountNumber,
                ifscCode,
                customerId,
                relationshipMgr,
                comment,
                share
            )
    }

    suspend fun getBankAccounts(
        token: String?,
        accountType: String?
    ): Response<GetBankAccountsResponse> {
        return myFolioApi.getBankAccounts(token, accountType)
    }

    suspend fun getPious(
        token: String?,
        accountType: String?
    ): Response<GetPersonalIousResponse> {
        return myFolioApi.getPious(token, accountType)
    }

    suspend fun getLoans(
        token: String?,
        loanType: String?
    ): Response<GetLoansResponse> {
        return myFolioApi.getLoans(token, loanType)
    }

    suspend fun deleteBankAccount(
        token: String?,
        accountId: String?
    ): Response<DeleteBankAccountResponse> {
        return myFolioApi.deleteBankAccount(token, accountId)
    }

    suspend fun deleteLoan(
        token: String?,
        id: String?
    ): Response<DeleteLoanResponse> {
        return myFolioApi.deleteLoan(token, id)
    }

    suspend fun deletePiou(
        token: String?,
        accountId: String?
    ): Response<DeletePersonalIouResponse> {
        return myFolioApi.deletePiou(token, accountId)
    }

    suspend fun addPersonalIou(
        id: Int?,
        token: String?,
        type: Int,
        reason: String,
        lenderName: String,
        borrowerName: String,
        totalAmount: String,
        currencyType: Int,
        amountPending: String,
        amountReturned: String,
        deadline: String,
        comment: String,
        share: Int,
    ): Response<AddPersonalIouResponse> {

        if (id != null)
            return myFolioApi.updatePersonalIou(
                id,
                token,
                type,
                reason,
                lenderName,
                borrowerName,
                totalAmount,
                currencyType,
                amountPending,
                amountReturned,
                deadline,
                comment,
                share
            )
        else
            return myFolioApi.addPersonalIou(
                token,
                type,
                reason,
                lenderName,
                borrowerName,
                totalAmount,
                currencyType,
                amountPending,
                amountReturned,
                deadline,
                comment,
                share
            )
    }

    suspend fun addUpdateLoan(
        id: Int?,
        token: String?,
        type: Int?,
        purpose: String,
        accountNo: String,
        accHolderName: String,
        loanAmount: String,
        loanInstituteName: String,
        emiValue: String,
        startDate: String,
        endDate: String,
        status: Int,
        comment: String,
        share: Int
    ): Response<AddLoanResponse> {

        if (id != null)
            return myFolioApi.updateLoan(
                id,
                token,
                type,
                purpose,
                accountNo,
                accHolderName,
                loanAmount,
                loanInstituteName,
                emiValue,
                startDate,
                endDate,
                status,
                comment,
                share
            )
        else
            return myFolioApi.addLoan(
                token,
                type,
                purpose,
                accountNo,
                accHolderName,
                loanAmount,
                loanInstituteName,
                emiValue,
                startDate,
                endDate,
                status,
                comment,
                share
            )
    }

    suspend fun getCategories(
        category: String?,
        token: String?,
    ): Response<GetCategoriesResponse> {
        return myFolioApi.getCategories(category, token)
    }

    suspend fun getFinInvestments(
        token: String?,
        type: String?
    ): Response<GetFinInvestmentsResponse> {
        return myFolioApi.getFinInvestments(token, type)
    }

    suspend fun deleteFinInvestment(
        token: String?,
        id: String?
    ): Response<DeleteFinInvestmentResponse> {
        return myFolioApi.deleteFinInvestment(token, id)
    }

    suspend fun addUpdateFinInvestment(
        id: Int?,
        auth: String?,
        finance_type_id: Int?,
        acc_holder_name: String,
        broker_name: String,
        equity_name: String,
        customer_id: String,
        no_of_shares: String,
        amount_invested: String,
        fund_type: String,
        fund_name: String,
        sip_name: String,
        start_date: String,
        end_date: String,
        bank_name: String,
        branch_name: String,
        fixed_deposit_no: String,
        amount_deposit: String,
        maturity_amount: String,
        maturity_date: String,
        recurring_deposit_no: String,
        montly_installment: String,
        crypto_name: String,
        quantity: String,
        nps_type: String,
        retirement_account_no: String,
        nps_amount: String,
        ppf_account_no: String,
        bond_name: String,
        bond_no: String,
        principle_amount: String,
        uan_number: String,
        pf_amount: String,
        employee_number: String,
        certificate_name: String,
        certificate_no: String,
        investment_type: String,
        folio_no: String,
        investment_amount: String,
        comment: String,
        share: Int?
    ): Response<AddUpdateFinInvestmentResponse> {

        if (id != null)
            return myFolioApi.updateFinInvestment(
                id,
                auth,
                finance_type_id,
                acc_holder_name,
                broker_name,
                equity_name,
                customer_id,
                no_of_shares,
                amount_invested,
                fund_type,
                fund_name,
                sip_name,
                start_date,
                end_date,
                bank_name,
                branch_name,
                fixed_deposit_no,
                amount_deposit,
                maturity_amount,
                maturity_date,
                recurring_deposit_no,
                montly_installment,
                crypto_name,
                quantity,
                nps_type,
                retirement_account_no,
                nps_amount,
                ppf_account_no,
                bond_name,
                bond_no,
                principle_amount,
                uan_number,
                pf_amount,
                employee_number,
                certificate_name,
                certificate_no,
                investment_type,
                folio_no,
                investment_amount,
                comment,
                share
        )
        else
        return myFolioApi.addFinInvestment(
            auth,
            finance_type_id,
            acc_holder_name,
            broker_name,
            equity_name,
            customer_id,
            no_of_shares,
            amount_invested,
            fund_type,
            fund_name,
            sip_name,
            start_date,
            end_date,
            bank_name,
            branch_name,
            fixed_deposit_no,
            amount_deposit,
            maturity_amount,
            maturity_date,
            recurring_deposit_no,
            montly_installment,
            crypto_name,
            quantity,
            nps_type,
            retirement_account_no,
            nps_amount,
            ppf_account_no,
            bond_name,
            bond_no,
            principle_amount,
            uan_number,
            pf_amount,
            employee_number,
            certificate_name,
            certificate_no,
            investment_type,
            folio_no,
            investment_amount,
            comment,
            share
        )
    }

    suspend fun getContacts(
        token: String?
    ): Response<GetContactsResponse> {
        return myFolioApi.getContacts(token)
    }

    suspend fun deleteContact(
        token: String?,
        id: String?
    ): Response<DeleteContactResponse> {
        return myFolioApi.deleteContact(token, id)
    }

    suspend fun addUpdateContact(
        id: Int?,
        token: String?,
        typeId: Int?,
        name: String,
        number: String,
        email: String,
        share: Int
    ): Response<AddUpdateContactResponse> {

        if (id != null)
            return myFolioApi.updateContact(
                id,
                token,
                typeId,
                name,
                number,
                email,
                share
            )
        else
            return myFolioApi.addContact(
                token,
                typeId,
                name,
                number,
                email,
                share
            )
    }

    suspend fun getDocuments(
        token: String?,
        type: String?
    ): Response<GetDocumentsResponse> {
        return myFolioApi.getDocuments(token, type)
    }

    suspend fun deleteDocument(
        token: String?,
        id: String?
    ): Response<DeleteDocumentResponse> {
        return myFolioApi.deleteDocument(token, id)
    }

    suspend fun addUpdateDocument(
        id: Int?,
        token: String?,
        typeId: Int?,
        docHolderName: String,
        docNumber: String?,
        medType: String,
        medDate: String,
        presType: String,
        presDate: String,
        comment: String,
        share: Int
    ): Response<AddUpdateDocumentResponse> {

        if (id != null)
            return myFolioApi.updateDocument(
                id,
                token,
                typeId,
                docHolderName,
                docNumber,
                medType,
                medDate,
                presType,
                presDate,
                comment,
                share
            )
        else
            return myFolioApi.addDocument(
                token,
                typeId,
                docHolderName,
                docNumber,
                medType,
                medDate,
                presType,
                presDate,
                comment,
                share
            )
    }


    suspend fun getCards(
        token: String?,
        type: String?
    ): Response<GetCardsResponse> {
        return myFolioApi.getCards(token, type)
    }

    suspend fun deleteCard(
        token: String?,
        id: String?
    ): Response<DeleteCardResponse> {
        return myFolioApi.deleteCard(token, id)
    }

    suspend fun addUpdateCard(
        id: Int?,
        token: String?,
        typeId: Int?,
        cardBankName: String,
        cardNumber: String,
        expiryDate: String,
        cardLimit: String,
        comment: String,
        share: Int
    ): Response<AddUpdateCardResponse> {

        if (id != null)
            return myFolioApi.updateCard(
                id,
                token,
                typeId,
                cardBankName,
                cardNumber,
                expiryDate,
                cardLimit,
                comment,
                share
            )
        else
            return myFolioApi.addCard(
                token,
                typeId,
                cardBankName,
                cardNumber,
                expiryDate,
                cardLimit,
                comment,
                share
            )
    }

    suspend fun getAssets(
        token: String?,
        type: String?
    ): Response<GetAssetsResponse> {
        return myFolioApi.getAsset(token, type)
    }

    suspend fun deleteAsset(
        token: String?,
        id: String?
    ): Response<DeleteAssetResponse> {
        return myFolioApi.deleteAsset(token, id)
    }

    suspend fun addUpdateAsset(
        id: Int?,
        token: String?,
        typeId: Int?,
        ownerName: String,
        propertyType: String,
        propertyName: String,
        propertyAddress: String,
        propertyValue: String,
        propertyStatus: String,
        vehicleType: String,
        vehicleBrand: String,
        vehicleRegNo: String,
        vehicleChassisNo: String,
        vehicleInsValidity: String,
        assetType: String,
        assetName: String,
        assetWeight: String,
        assetQty: String,
        comment: String,
        share: Int
    ): Response<AddUpdateAssetResponse> {

        if (id != null)
            return myFolioApi.updateAsset(
                id,
                token,
                typeId,
                ownerName,
                propertyType,
                propertyName,
                propertyAddress,
                propertyValue,
                propertyStatus,
                vehicleType,
                vehicleBrand,
                vehicleRegNo,
                vehicleChassisNo,
                vehicleInsValidity,
                assetType,
                assetName,
                assetWeight,
                assetQty,
                comment,
                share
            )
        else
            return myFolioApi.addAsset(
                token,
                typeId,
                ownerName,
                propertyType,
                propertyName,
                propertyAddress,
                propertyValue,
                propertyStatus,
                vehicleType,
                vehicleBrand,
                vehicleRegNo,
                vehicleChassisNo,
                vehicleInsValidity,
                assetType,
                assetName,
                assetWeight,
                assetQty,
                comment,
                share
            )
    }

    suspend fun getNomineeNominators(
        token: String?
    ): Response<GetNomineeNominatorsResponse> {
        return myFolioApi.getNomineeNominators(token)
    }

    suspend fun deleteNominee(
        token: String?,
        id: String?
    ): Response<DeleteNomineeResponse> {
        return myFolioApi.deleteNominee(token, id)
    }

    suspend fun addNominee(
        id: Int?,
        token: String?,
        nomineeName: String,
        nomineeDOB: String,
        nomineeGender: String,
        nomineeMobile: String,
        nomineeEmail: String,
        nomineeAadhar: String,
        nomineeRelation: String
    ): Response<AddNomineeResponse> {

        if (id != null){
            return myFolioApi.updateNominee(
                id,
                token,
                nomineeName,
                nomineeDOB,
                nomineeGender,
                nomineeMobile,
                nomineeEmail,
                nomineeAadhar,
                nomineeRelation
            )
        }else{
            return myFolioApi.addNominee(
                token,
                nomineeName,
                nomineeDOB,
                nomineeGender,
                nomineeMobile,
                nomineeEmail,
                nomineeAadhar,
                nomineeRelation
            )
        }
    }

    suspend fun nomineeVerifyOtp(
        token: String?,
        id: String,
        otp: String
    ): Response<NomineeVerifyOtpResponse> {
        return myFolioApi.nomineeVerifyOtp(token, id, otp)
    }

    suspend fun nomineeResend(token: String?, id: String): Response<ResendUserOtpResponse> {
        return myFolioApi.nomineeResendOtp(token, id)
    }

    suspend fun logout(token: String?): Response<LogoutResponse> {
        return myFolioApi.logout(token)
    }

    suspend fun getInsurance(
        token: String?,
        type: String?
    ): Response<GetInsuranceResponse> {
        return myFolioApi.getInsurance(token, type)
    }

    suspend fun deleteInsurance(
        token: String?,
        id: String?
    ): Response<DeleteInsuranceResponse> {
        return myFolioApi.deleteInsurance(token, id)
    }

    suspend fun addUpdateInsurance(
        id: Int?,
        token: String?,
        typeId: Int?,
        type: String,
        insHolderName: String,
        insCompanyName: String,
        insNumber: String,
        insCoverAmt: String,
        insPremiumAmt: String,
        comment: String,
        share: Int?,
        insCategory: String
    ): Response<AddInsuranceResponse> {

        if (id != null)
            return myFolioApi.updateInsurance(
                id,
                token,
                typeId,
                type,
                insHolderName,
                insCompanyName,
                insNumber,
                insCoverAmt,
                insPremiumAmt,
                comment,
                share,
                insCategory
            )
        else
            return myFolioApi.addInsurance(
                token,
                typeId,
                type,
                insHolderName,
                insCompanyName,
                insNumber,
                insCoverAmt,
                insPremiumAmt,
                comment,
                share,
                insCategory
            )
    }

    suspend fun getSubscriptionInfo(
        token: String?
    ): Response<GetSubscriptionInfoResponse> {
        return myFolioApi.getSubscriptionInfo(token)
    }

    suspend fun registerFCMToken(
        deviceType: String?,
        deviceId: String?,
        fcmToken: String?
    ): Response<PushNotiTokenRegistrationResponse> {
        return myFolioApi.registerFCMToken(deviceType, deviceId, fcmToken)
    }

    suspend fun getNotifications(
        token: String?
    ): Response<GetNotificationsResponse> {
        return myFolioApi.getNotifications(token)
    }

    suspend fun getDidYouKnow(
        token: String?
    ): Response<GetDidYouKnowResponse> {
        return myFolioApi.getDidYouKnow(token)
    }

    suspend fun deleteAccountRequest(
        token: String?
    ): Response<DeleteAccountReqResponse> {
        return myFolioApi.deleteAccountRequest(token)
    }

    suspend fun deleteAccount(
        token: String?,
        code: String?
    ): Response<DeleteAccountReqResponse> {
        return myFolioApi.deleteAccount(token, code)
    }

}