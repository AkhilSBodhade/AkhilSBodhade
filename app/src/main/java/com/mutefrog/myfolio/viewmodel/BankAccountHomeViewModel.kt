package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.DeleteBankAccountResponse
import com.mutefrog.myfolio.model.GetBankAccountsResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BankAccountHomeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _bankAccountsData = MutableLiveData<DataHandler<GetBankAccountsResponse>>()
    val bankAccountsData: LiveData<DataHandler<GetBankAccountsResponse>> = _bankAccountsData

    fun getBankAccounts(
        token: String?,
        accountType: String?
    ) {
        _bankAccountsData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getBankAccounts(
                token,
                accountType
            )
            _bankAccountsData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<GetBankAccountsResponse>): DataHandler<GetBankAccountsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }


    private val _bankAccountsDeleteData = MutableLiveData<DataHandler<DeleteBankAccountResponse>>()
    val bankAccountsDeleteData: LiveData<DataHandler<DeleteBankAccountResponse>> = _bankAccountsDeleteData

    fun deleteBankAccount(
        token: String?,
        accountId: String?
    ) {
        _bankAccountsDeleteData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.deleteBankAccount(
                token,
                accountId
            )
            _bankAccountsDeleteData.postValue(handleDeleteBankAccountResponse(response))
        }
    }

    private fun handleDeleteBankAccountResponse(response: Response<DeleteBankAccountResponse>): DataHandler<DeleteBankAccountResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}