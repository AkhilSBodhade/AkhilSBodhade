package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.DeleteBankAccountResponse
import com.mutefrog.myfolio.model.DeletePersonalIouResponse
import com.mutefrog.myfolio.model.GetBankAccountsResponse
import com.mutefrog.myfolio.model.GetPersonalIousResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PersonalIouHomeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _listData = MutableLiveData<DataHandler<GetPersonalIousResponse>>()
    val listData: LiveData<DataHandler<GetPersonalIousResponse>> = _listData

    fun getPious(
        token: String?,
        accountType: String?
    ) {
        _listData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getPious(
                token,
                accountType
            )
            _listData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<GetPersonalIousResponse>): DataHandler<GetPersonalIousResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }


    private val _deleteData = MutableLiveData<DataHandler<DeletePersonalIouResponse>>()
    val deleteData: LiveData<DataHandler<DeletePersonalIouResponse>> = _deleteData

    fun deletePiou(
        token: String?,
        accountId: String?
    ) {
        _deleteData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.deletePiou(
                token,
                accountId
            )
            _deleteData.postValue(handleDeleteResponse(response))
        }
    }

    private fun handleDeleteResponse(response: Response<DeletePersonalIouResponse>):
            DataHandler<DeletePersonalIouResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}