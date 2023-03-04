package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.DeleteLoanResponse
import com.mutefrog.myfolio.model.GetLoansResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoanHomeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _loansData = MutableLiveData<DataHandler<GetLoansResponse>>()
    val loansData: LiveData<DataHandler<GetLoansResponse>> = _loansData

    fun getLoans(
        token: String?,
        loanType: String?
    ) {
        _loansData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getLoans(
                token,
                loanType
            )
            _loansData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<GetLoansResponse>): DataHandler<GetLoansResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }


    private val _loansDeleteData = MutableLiveData<DataHandler<DeleteLoanResponse>>()
    val loansDeleteData: LiveData<DataHandler<DeleteLoanResponse>> = _loansDeleteData

    fun deleteLoan(
        token: String?,
        accountId: String?
    ) {
        _loansDeleteData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.deleteLoan(
                token,
                accountId
            )
            _loansDeleteData.postValue(handleDeleteLoantResponse(response))
        }
    }

    private fun handleDeleteLoantResponse(response: Response<DeleteLoanResponse>): DataHandler<DeleteLoanResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}