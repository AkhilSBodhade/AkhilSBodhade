package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.GetDidYouKnowResponse
import com.mutefrog.myfolio.model.GetSubscriptionInfoResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _subscriptionInfoData = MutableLiveData<DataHandler<GetSubscriptionInfoResponse>>()
    val subscriptionInfoData: LiveData<DataHandler<GetSubscriptionInfoResponse>> =
        _subscriptionInfoData

    fun getSubscriptionInfo(
        token: String?
    ) {
        _subscriptionInfoData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getSubscriptionInfo(
                token
            )
            _subscriptionInfoData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<GetSubscriptionInfoResponse>)
            : DataHandler<GetSubscriptionInfoResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }


    private val _didYouKnowData = MutableLiveData<DataHandler<GetDidYouKnowResponse>>()
    val didYouKnowData: LiveData<DataHandler<GetDidYouKnowResponse>> =
        _didYouKnowData

    fun getDidYouKnow(
        token: String?
    ) {
        _subscriptionInfoData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getDidYouKnow(
                token
            )
            _didYouKnowData.postValue(handleDidYouKnowResponse(response))
        }
    }

    private fun handleDidYouKnowResponse(response: Response<GetDidYouKnowResponse>)
            : DataHandler<GetDidYouKnowResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}