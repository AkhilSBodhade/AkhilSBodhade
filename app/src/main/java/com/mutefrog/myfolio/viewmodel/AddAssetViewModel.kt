package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.AddBankAccountResponse
import com.mutefrog.myfolio.model.AddLoanResponse
import com.mutefrog.myfolio.model.AddPersonalIouResponse
import com.mutefrog.myfolio.model.AddUpdateAssetResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddAssetViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _addData = MutableLiveData<DataHandler<AddUpdateAssetResponse>>()
    val addData: LiveData<DataHandler<AddUpdateAssetResponse>> = _addData

    fun addUpdateAsset(
        id: Int?,
        token: String?,
        typeId: Int?,
        ownerName: String,
        propertyType: String,
        propertyName: String,
        propertyAddress: String,
        propertyValue: String,
        propertyStatus: String,
        vehicleType: String,
        vehicleBrand: String,
        vehicleRegNo: String,
        vehicleChassisNo: String,
        vehicleInsValidity: String,
        assetType: String,
        assetName: String,
        assetWeight: String,
        assetQty: String,
        comment: String,
        share: Int
    ) {
        _addData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.addUpdateAsset(
                id,
                token,
                typeId,
                ownerName,
                propertyType,
                propertyName,
                propertyAddress,
                propertyValue,
                propertyStatus,
                vehicleType,
                vehicleBrand,
                vehicleRegNo,
                vehicleChassisNo,
                vehicleInsValidity,
                assetType,
                assetName,
                assetWeight,
                assetQty,
                comment,
                share
            )
            _addData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<AddUpdateAssetResponse>): DataHandler<AddUpdateAssetResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}