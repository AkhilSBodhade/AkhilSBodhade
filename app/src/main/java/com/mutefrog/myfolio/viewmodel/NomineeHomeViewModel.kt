package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.DeleteCardResponse
import com.mutefrog.myfolio.model.DeleteNomineeResponse
import com.mutefrog.myfolio.model.GetNomineeNominatorsResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NomineeHomeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _listData = MutableLiveData<DataHandler<GetNomineeNominatorsResponse>>()
    val listData: LiveData<DataHandler<GetNomineeNominatorsResponse>> = _listData

    fun getNomineeNominators(
        token: String?,
    ) {
        _listData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getNomineeNominators(
                token
            )
            _listData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<GetNomineeNominatorsResponse>): DataHandler<GetNomineeNominatorsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }


    private val _deleteData = MutableLiveData<DataHandler<DeleteNomineeResponse>>()
    val deleteData: LiveData<DataHandler<DeleteNomineeResponse>> = _deleteData

    fun deleteNominee(
        token: String?,
        accountId: String?
    ) {
        _deleteData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.deleteNominee(
                token,
                accountId
            )
            _deleteData.postValue(handleDeleteCardResponse(response))
        }
    }

    private fun handleDeleteCardResponse(response: Response<DeleteNomineeResponse>): DataHandler<DeleteNomineeResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}