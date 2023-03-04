package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.AddBankAccountResponse
import com.mutefrog.myfolio.model.AddLoanResponse
import com.mutefrog.myfolio.model.AddPersonalIouResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddLoanViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _addData = MutableLiveData<DataHandler<AddLoanResponse>>()
    val addData: LiveData<DataHandler<AddLoanResponse>> = _addData

    fun addUpdateLoan(
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
    ) {
        _addData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.addUpdateLoan(
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
            _addData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<AddLoanResponse>): DataHandler<AddLoanResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}