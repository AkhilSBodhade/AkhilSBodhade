package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.ResendUserOtpResponse
import com.mutefrog.myfolio.model.SignupVerifyOtpResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginOTPViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _resendOTPData = MutableLiveData<DataHandler<ResendUserOtpResponse>>()
    val resendOTPData: LiveData<DataHandler<ResendUserOtpResponse>> = _resendOTPData

    fun resendOTP(emailId: String) {
        _resendOTPData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.resend(emailId)
            _resendOTPData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<ResendUserOtpResponse>): DataHandler<ResendUserOtpResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }


    private val _verifyLoginOTPData = MutableLiveData<DataHandler<SignupVerifyOtpResponse>>()
    val verifyOTPData: LiveData<DataHandler<SignupVerifyOtpResponse>> = _verifyLoginOTPData

    fun verifyLoginOTP(emailId: String?, otp: String?) {
        _verifyLoginOTPData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.loginVerifyOtp(emailId.toString(), otp.toString())
            _verifyLoginOTPData.postValue(handleLoginVerifyOTPResponse(response))
        }
    }

    private fun handleLoginVerifyOTPResponse(response: Response<SignupVerifyOtpResponse>):
            DataHandler<SignupVerifyOtpResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }


}