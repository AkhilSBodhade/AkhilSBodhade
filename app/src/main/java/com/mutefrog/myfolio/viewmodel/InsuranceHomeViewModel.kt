package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.DeleteAssetResponse
import com.mutefrog.myfolio.model.DeleteInsuranceResponse
import com.mutefrog.myfolio.model.GetInsuranceResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class InsuranceHomeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _insuranceData = MutableLiveData<DataHandler<GetInsuranceResponse>>()
    val assetsData: LiveData<DataHandler<GetInsuranceResponse>> = _insuranceData

    fun getInsurance(
        token: String?,
        accountType: String?
    ) {
        _insuranceData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getInsurance(
                token,
                accountType
            )
            _insuranceData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<GetInsuranceResponse>): DataHandler<GetInsuranceResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }


    private val _deleteData = MutableLiveData<DataHandler<DeleteInsuranceResponse>>()
    val assetsDeleteData: LiveData<DataHandler<DeleteInsuranceResponse>> = _deleteData

    fun deleteAsset(
        token: String?,
        accountId: String?
    ) {
        _deleteData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.deleteInsurance(
                token,
                accountId
            )
            _deleteData.postValue(handleDeleteResponse(response))
        }
    }

    private fun handleDeleteResponse(response: Response<DeleteInsuranceResponse>): DataHandler<DeleteInsuranceResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}