package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.CompleteSignupResponse
import com.mutefrog.myfolio.model.SignupResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CompleteSignupViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _completeSignupData = MutableLiveData<DataHandler<CompleteSignupResponse>>()
    val completeSignupData: LiveData<DataHandler<CompleteSignupResponse>> = _completeSignupData

    fun completeSignup(
        token: String?, fullname: String, phoneCode: String, countryCode: String,
        mobile: String, dob: String, gender: String, countryOfResidence: String
    ) {
        _completeSignupData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.completeSignup(
                token,
                fullname,
                phoneCode,
                countryCode,
                mobile,
                dob,
                gender,
                countryOfResidence
            )
            _completeSignupData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<CompleteSignupResponse>): DataHandler<CompleteSignupResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }


    private val _updatePersonalInfoData = MutableLiveData<DataHandler<CompleteSignupResponse>>()
    val updatePersonalInfoData: LiveData<DataHandler<CompleteSignupResponse>> =
        _updatePersonalInfoData

    fun updatePersonalInformation(
        token: String?, fullname: String, phoneCode: String, countryCode: String,
        mobile: String, dob: String, gender: String, countryOfResidence: String
    ) {
        _updatePersonalInfoData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.updatePersonalInformation(
                token,
                fullname,
                phoneCode,
                countryCode,
                mobile,
                dob,
                gender,
                countryOfResidence
            )
            _updatePersonalInfoData.postValue(handleUpdatePersonalInfoResponse(response))
        }
    }

    private fun handleUpdatePersonalInfoResponse(response: Response<CompleteSignupResponse>): DataHandler<CompleteSignupResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }
}