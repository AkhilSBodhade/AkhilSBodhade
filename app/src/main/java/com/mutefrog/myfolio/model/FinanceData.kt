package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName
import com.mutefrog.myfolio.utils.decrypt


data class FinanceData(

    @SerializedName("investment_type") var investmentType: String? = null,
    @SerializedName("folio_no") var folioNo: String? = null,
    @SerializedName("investment_amount") var investmentAmount: String? = null,
    @SerializedName("comment") var comment: String? = null,
    @SerializedName("acc_holder_name") var accHolderName: String? = null,
    @SerializedName("broker_name") var brokerName: String? = null,
    @SerializedName("equity_name") var equityName: String? = null,
    @SerializedName("customer_id") var customerId: String? = null,
    @SerializedName("no_of_shares") var noOfShares: String? = null,
    @SerializedName("amount_invested") var amountInvested: String? = null,
    @SerializedName("fund_type") var fundType: String? = null,
    @SerializedName("fund_name") var fundName: String? = null,
    @SerializedName("sip_name") var sipName: String? = null,
    @SerializedName("start_date") var startDate: String? = null,
    @SerializedName("end_date") var endDate: String? = null,
    @SerializedName("bank_name") var bankName: String? = null,
    @SerializedName("branch_name") var branchName: String? = null,
    @SerializedName("fixed_deposit_no") var fixedDepositNo: String? = null,
    @SerializedName("amount_deposit") var amountDeposit: String? = null,
    @SerializedName("maturity_amount") var maturityAmount: String? = null,
    @SerializedName("maturity_date") var maturityDate: String? = null,
    @SerializedName("recurring_deposit_no") var recurringDepositNo: String? = null,
    @SerializedName("montly_installment") var montlyInstallment: String? = null,
    @SerializedName("crypto_name") var cryptoName: String? = null,
    @SerializedName("quantity") var quantity: String? = null,
    @SerializedName("nps_type") var npsType: String? = null,
    @SerializedName("retirement_account_no") var retirementAccountNo: String? = null,
    @SerializedName("nps_amount") var npsAmount: String? = null,
    @SerializedName("ppf_account_no") var ppfAccountNo: String? = null,
    @SerializedName("bond_name") var bondName: String? = null,
    @SerializedName("bond_no") var bondNo: String? = null,
    @SerializedName("principle_amount") var principleAmount: String? = null,
    @SerializedName("uan_number") var uanNumber: String? = null,
    @SerializedName("pf_amount") var pfAmount: String? = null,
    @SerializedName("employee_number") var employeeNumber: String? = null,
    @SerializedName("certificate_name") var certificateName: String? = null,
    @SerializedName("certificate_no") var certificateNo: String? = null
){
    fun getEquityNameValue() = equityName?.decrypt()
    fun getAccountHolderNameValue() = accHolderName?.decrypt()
    fun getBrokerNameValue() = brokerName?.decrypt()
    fun getCustomerIdValue() = customerId?.decrypt()
    fun getNoOfSharesValue() = noOfShares?.decrypt()
    fun getAmountInvestedValue() = amountInvested?.decrypt()
    fun getBankNameValue() = bankName?.decrypt()
    fun getFixedDepositNumberValue() = fixedDepositNo?.decrypt()
    fun getAmountDepositedValue() = amountDeposit?.decrypt()
    fun getMaturityAmountValue() = maturityAmount?.decrypt()
    fun getRecurringDepositNumberValue() = recurringDepositNo?.decrypt()
    fun getMonthlyInstallmentValue() = montlyInstallment?.decrypt()
    fun getCryptoNameValue() = cryptoName?.decrypt()
    fun getQuantityValue() = quantity?.decrypt()
    fun getNPSTypeValue() = npsType?.decrypt()
    fun getPermanentRetirementAccNoValue() = retirementAccountNo?.decrypt()
    fun getNPSAmountValue() = npsAmount?.decrypt()
    fun getPPFAccountNumberValue() = ppfAccountNo?.decrypt()
    fun getBondNameValue() = bondName?.decrypt()
    fun getBondNumberValue() = bondNo?.decrypt()
    fun getPrincipalAmountValue() = principleAmount?.decrypt()
    fun getUANValue() = uanNumber?.decrypt()
    fun getPfAmountValue() = pfAmount?.decrypt()
    fun getEmployeeNumberValue() = employeeNumber?.decrypt()
    fun getCertificateNameValue() = certificateName?.decrypt()
    fun getCertificateNumberValue() = certificateNo?.decrypt()
    fun getInvestmentTypeValue() = investmentType?.decrypt()
    fun getFolioNumberValue() = folioNo?.decrypt()
    fun getInvestedAmountValue() = investmentAmount?.decrypt()
    fun getFundNameValue() = fundName?.decrypt()


}