package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.SigninResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _loginData = MutableLiveData<DataHandler<SigninResponse>>()
    val loginData: LiveData<DataHandler<SigninResponse>> = _loginData

    fun login(emailId: String) {
        _loginData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.login(emailId)
            _loginData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<SigninResponse>): DataHandler<SigninResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}