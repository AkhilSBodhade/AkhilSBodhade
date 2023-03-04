package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.*
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignupOTPViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
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

    fun resendNomineeOTP(token: String?, id: String) {
        _resendOTPData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.nomineeResend(token, id)
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


    private val _verifySignupOTPData = MutableLiveData<DataHandler<SignupVerifyOtpResponse>>()
    val verifyOTPData: LiveData<DataHandler<SignupVerifyOtpResponse>> = _verifySignupOTPData

    fun verifySignupOTP(emailId: String?, otp: String?) {
        _verifySignupOTPData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.signupVerifyOtp(emailId.toString(), otp.toString())
            _verifySignupOTPData.postValue(handleSingupVerifyOTPResponse(response))
        }
    }

    private fun handleSingupVerifyOTPResponse(response: Response<SignupVerifyOtpResponse>):
            DataHandler<SignupVerifyOtpResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

    private val _verifyNomineeOTPData = MutableLiveData<DataHandler<NomineeVerifyOtpResponse>>()
    val verifyNomineeOTPData: LiveData<DataHandler<NomineeVerifyOtpResponse>> = _verifyNomineeOTPData

    fun verifyNomineeOTP(token: String?, id: String?, otp: String?) {
        _verifyNomineeOTPData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.nomineeVerifyOtp(token, id.toString(), otp.toString())
            _verifyNomineeOTPData.postValue(handleNomineeVerifyOTPResponse(response))
        }
    }

    private fun handleNomineeVerifyOTPResponse(response: Response<NomineeVerifyOtpResponse>):
            DataHandler<NomineeVerifyOtpResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }


    private val _deleteAccountRequest = MutableLiveData<DataHandler<DeleteAccountReqResponse>>()
    val deleteAccountReqData: LiveData<DataHandler<DeleteAccountReqResponse>> = _deleteAccountRequest

    fun deleteAccountRequest(
        token: String?
    ) {
        _deleteAccountRequest.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.deleteAccountRequest(
                token
            )
            _deleteAccountRequest.postValue(handleDeleteAccReqResponse(response))
        }
    }

    private fun handleDeleteAccReqResponse(response: Response<DeleteAccountReqResponse>): DataHandler<DeleteAccountReqResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

    private val _deleteAccount = MutableLiveData<DataHandler<DeleteAccountReqResponse>>()
    val deleteAccountData: LiveData<DataHandler<DeleteAccountReqResponse>> = _deleteAccount

    fun deleteAccount(
        token: String?,
        code: String?
    ) {
        _deleteAccount.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.deleteAccount(
                token,
                code
            )
            _deleteAccount.postValue(handleDeleteAccResponse(response))
        }
    }

    private fun handleDeleteAccResponse(response: Response<DeleteAccountReqResponse>): DataHandler<DeleteAccountReqResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }


}