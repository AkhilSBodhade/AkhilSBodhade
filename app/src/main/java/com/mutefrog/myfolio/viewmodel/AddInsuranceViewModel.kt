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
class AddInsuranceViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _addData = MutableLiveData<DataHandler<AddInsuranceResponse>>()
    val addData: LiveData<DataHandler<AddInsuranceResponse>> = _addData

    fun addUpdateInsurance(
        id: Int?,
        token: String?,
        typeId: Int?,
        type: String,
        insHolderName: String,
        insCompanyName: String,
        insNumber: String,
        insCoverAmt: String,
        insPremiumAmt: String,
        comment: String,
        share: Int?,
        insCategory: String
    ) {
        _addData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.addUpdateInsurance(
                id,
                token,
                typeId,
                type,
                insHolderName,
                insCompanyName,
                insNumber,
                insCoverAmt,
                insPremiumAmt,
                comment,
                share,
                insCategory
            )
            _addData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<AddInsuranceResponse>): DataHandler<AddInsuranceResponse> {
        if (response.isSuccessful){
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}