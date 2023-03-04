package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.AddBankAccountResponse
import com.mutefrog.myfolio.model.AddUpdateCardResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _addCardData = MutableLiveData<DataHandler<AddUpdateCardResponse>>()
    val addCardData: LiveData<DataHandler<AddUpdateCardResponse>> = _addCardData

    fun addUpdateCard(
        id: Int?,
        token: String?,
        typeId: Int?,
        cardBankName: String,
        cardNumber: String,
        expiryDate: String,
        cardLimit: String,
        comment: String,
        share: Int
    ) {
        _addCardData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.addUpdateCard(
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
            _addCardData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<AddUpdateCardResponse>): DataHandler<AddUpdateCardResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}