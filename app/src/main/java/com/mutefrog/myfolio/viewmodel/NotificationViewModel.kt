package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.DeleteAssetResponse
import com.mutefrog.myfolio.model.GetNotificationsResponse
import com.mutefrog.myfolio.model.PushNotiTokenRegistrationResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _listData = MutableLiveData<DataHandler<GetNotificationsResponse>>()
    val listData: LiveData<DataHandler<GetNotificationsResponse>> = _listData

    fun getNotifications(
        token: String?
    ) {
        _listData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getNotifications(
                token
            )
            _listData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<GetNotificationsResponse>): DataHandler<GetNotificationsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }


    private val _tokenRegiData = MutableLiveData<DataHandler<PushNotiTokenRegistrationResponse>>()
    val tokenRegiData: LiveData<DataHandler<PushNotiTokenRegistrationResponse>> = _tokenRegiData

    fun regFCMToken(
        deviceType: String?,
        deviceId: String?,
        fcmToken: String?
    ) {
        _tokenRegiData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.registerFCMToken(
                deviceType,
                deviceId,
                fcmToken
            )
            _tokenRegiData.postValue(handleTokenRegResponse(response))
        }
    }

    private fun handleTokenRegResponse(response: Response<PushNotiTokenRegistrationResponse>)
            : DataHandler<PushNotiTokenRegistrationResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}