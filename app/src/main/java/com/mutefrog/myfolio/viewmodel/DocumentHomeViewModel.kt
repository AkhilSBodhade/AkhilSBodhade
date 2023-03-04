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
class DocumentHomeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _listData = MutableLiveData<DataHandler<GetDocumentsResponse>>()
    val listData: LiveData<DataHandler<GetDocumentsResponse>> = _listData

    fun getDocuments(
        token: String?,
        typeId: String?
    ) {
        _listData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getDocuments(
                token,
                typeId
            )
            _listData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<GetDocumentsResponse>): DataHandler<GetDocumentsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

    private val _deleteData = MutableLiveData<DataHandler<DeleteDocumentResponse>>()
    val deleteData: LiveData<DataHandler<DeleteDocumentResponse>> = _deleteData

    fun deleteDocument(
        token: String?,
        id: String?
    ) {
        _deleteData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.deleteDocument(
                token,
                id
            )
            _deleteData.postValue(handleDeleteResponse(response))
        }
    }

    private fun handleDeleteResponse(response: Response<DeleteDocumentResponse>):
            DataHandler<DeleteDocumentResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}