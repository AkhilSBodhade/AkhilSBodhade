package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.AddBankAccountResponse
import com.mutefrog.myfolio.model.AddNomineeResponse
import com.mutefrog.myfolio.model.AddUpdateCardResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddNomineeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _addData = MutableLiveData<DataHandler<AddNomineeResponse>>()
    val addData: LiveData<DataHandler<AddNomineeResponse>> = _addData

    fun addNominee(
        id: Int?,
        token: String?,
        nomineeName: String,
        nomineeDOB: String,
        nomineeGender: String,
        nomineeMobile: String,
        nomineeEmail: String,
        nomineeAadhar: String,
        nomineeRelation: String
    ) {
        _addData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.addNominee(
                id,
                token,
                nomineeName,
                nomineeDOB,
                nomineeGender,
                nomineeMobile,
                nomineeEmail,
                nomineeAadhar,
                nomineeRelation
            )
            _addData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<AddNomineeResponse>): DataHandler<AddNomineeResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}