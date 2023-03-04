package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.DeleteAssetResponse
import com.mutefrog.myfolio.model.DeleteCardResponse
import com.mutefrog.myfolio.model.GetAssetsResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AssetHomeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _assetsData = MutableLiveData<DataHandler<GetAssetsResponse>>()
    val assetsData: LiveData<DataHandler<GetAssetsResponse>> = _assetsData

    fun getAssets(
        token: String?,
        accountType: String?
    ) {
        _assetsData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getAssets(
                token,
                accountType
            )
            _assetsData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<GetAssetsResponse>): DataHandler<GetAssetsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

    private val _assetsDeleteData = MutableLiveData<DataHandler<DeleteAssetResponse>>()
    val assetsDeleteData: LiveData<DataHandler<DeleteAssetResponse>> = _assetsDeleteData

    fun deleteAsset(
        token: String?,
        accountId: String?
    ) {
        _assetsDeleteData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.deleteAsset(
                token,
                accountId
            )
            _assetsDeleteData.postValue(handleDeleteCardResponse(response))
        }
    }

    private fun handleDeleteCardResponse(response: Response<DeleteAssetResponse>): DataHandler<DeleteAssetResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}