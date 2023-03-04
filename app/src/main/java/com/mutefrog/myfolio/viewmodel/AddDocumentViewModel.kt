package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.AddUpdateContactResponse
import com.mutefrog.myfolio.model.AddUpdateDocumentResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddDocumentViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _addData = MutableLiveData<DataHandler<AddUpdateDocumentResponse>>()
    val addData: LiveData<DataHandler<AddUpdateDocumentResponse>> = _addData

    fun addUpdateDocument(
        id: Int?,
        token: String?,
        typeId: Int?,
        docHolderName: String,
        docNumber: String?,
        medType: String,
        medDate: String,
        presType: String,
        presDate: String,
        comment: String,
        share: Int
    ) {
        _addData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.addUpdateDocument(
                id,
                token,
                typeId,
                docHolderName,
                docNumber,
                medType,
                medDate,
                presType,
                presDate,
                comment,
                share
            )
            _addData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<AddUpdateDocumentResponse>): DataHandler<AddUpdateDocumentResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}