package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.AddBankAccountResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddBankAccountViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _addBankAccountData = MutableLiveData<DataHandler<AddBankAccountResponse>>()
    val addBankAccountData: LiveData<DataHandler<AddBankAccountResponse>> = _addBankAccountData

    fun addBankAccount(
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
        share: Int,
    ) {
        _addBankAccountData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.addBankAccount(
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
            _addBankAccountData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<AddBankAccountResponse>): DataHandler<AddBankAccountResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}