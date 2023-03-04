package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.DeleteCardResponse
import com.mutefrog.myfolio.model.GetCardsResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CardHomeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _cardsData = MutableLiveData<DataHandler<GetCardsResponse>>()
    val cardsData: LiveData<DataHandler<GetCardsResponse>> = _cardsData

    fun getCards(
        token: String?,
        accountType: String?
    ) {
        _cardsData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getCards(
                token,
                accountType
            )
            _cardsData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<GetCardsResponse>): DataHandler<GetCardsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

    private val _cardsDeleteData = MutableLiveData<DataHandler<DeleteCardResponse>>()
    val cardsDeleteData: LiveData<DataHandler<DeleteCardResponse>> = _cardsDeleteData

    fun deleteCard(
        token: String?,
        accountId: String?
    ) {
        _cardsDeleteData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.deleteCard(
                token,
                accountId
            )
            _cardsDeleteData.postValue(handleDeleteCardResponse(response))
        }
    }

    private fun handleDeleteCardResponse(response: Response<DeleteCardResponse>): DataHandler<DeleteCardResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}