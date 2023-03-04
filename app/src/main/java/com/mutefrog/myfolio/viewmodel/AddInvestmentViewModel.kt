package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.*
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddInvestmentViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _addData = MutableLiveData<DataHandler<AddUpdateFinInvestmentResponse>>()
    val addData: LiveData<DataHandler<AddUpdateFinInvestmentResponse>> = _addData

    fun addUpdateInvestment(
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
    ) {
        _addData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.addUpdateFinInvestment(
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
            _addData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<AddUpdateFinInvestmentResponse>): DataHandler<AddUpdateFinInvestmentResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}