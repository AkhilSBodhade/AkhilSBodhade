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
class ContactHomeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _listData = MutableLiveData<DataHandler<GetContactsResponse>>()
    val listData: LiveData<DataHandler<GetContactsResponse>> = _listData

    fun getContacts(
        token: String?
    ) {
        _listData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getContacts(
                token
            )
            _listData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<GetContactsResponse>): DataHandler<GetContactsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

    private val _deleteData = MutableLiveData<DataHandler<DeleteContactResponse>>()
    val deleteData: LiveData<DataHandler<DeleteContactResponse>> = _deleteData

    fun deleteContact(
        token: String?,
        id: String?
    ) {
        _deleteData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.deleteContact(
                token,
                id
            )
            _deleteData.postValue(handleDeleteResponse(response))
        }
    }

    private fun handleDeleteResponse(response: Response<DeleteContactResponse>):
            DataHandler<DeleteContactResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}