package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.SignupResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _signupData = MutableLiveData<DataHandler<SignupResponse>>()
    val signupData: LiveData<DataHandler<SignupResponse>> = _signupData

    fun signup(emailId: String) {
        _signupData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.signup(emailId)
            _signupData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<SignupResponse>): DataHandler<SignupResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}