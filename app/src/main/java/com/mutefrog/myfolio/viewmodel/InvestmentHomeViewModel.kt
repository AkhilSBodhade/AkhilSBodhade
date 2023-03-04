package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.DeleteFinInvestmentResponse
import com.mutefrog.myfolio.model.DeletePersonalIouResponse
import com.mutefrog.myfolio.model.GetFinInvestmentsResponse
import com.mutefrog.myfolio.model.GetPersonalIousResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class InvestmentHomeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _listData = MutableLiveData<DataHandler<GetFinInvestmentsResponse>>()
    val listData: LiveData<DataHandler<GetFinInvestmentsResponse>> = _listData

    fun getFinInvestments(
        token: String?,
        accountType: String?
    ) {
        _listData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getFinInvestments(
                token,
                accountType
            )
            _listData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<GetFinInvestmentsResponse>): DataHandler<GetFinInvestmentsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

    private val _deleteData = MutableLiveData<DataHandler<DeleteFinInvestmentResponse>>()
    val deleteData: LiveData<DataHandler<DeleteFinInvestmentResponse>> = _deleteData

    fun deleteFinInvestments(
        token: String?,
        id: String?
    ) {
        _deleteData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.deleteFinInvestment(
                token,
                id
            )
            _deleteData.postValue(handleDeleteResponse(response))
        }
    }

    private fun handleDeleteResponse(response: Response<DeleteFinInvestmentResponse>):
            DataHandler<DeleteFinInvestmentResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}