package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.AddBankAccountResponse
import com.mutefrog.myfolio.model.AddPersonalIouResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddPersonalIouViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _addData = MutableLiveData<DataHandler<AddPersonalIouResponse>>()
    val addData: LiveData<DataHandler<AddPersonalIouResponse>> = _addData

    fun addPersonalIou(
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
    ) {
        _addData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.addPersonalIou(
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
            _addData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<AddPersonalIouResponse>): DataHandler<AddPersonalIouResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}