package com.mutefrog.myfolio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutefrog.myfolio.di.NetworkRepository
import com.mutefrog.myfolio.model.AddBankAccountResponse
import com.mutefrog.myfolio.model.GetCategoriesResponse
import com.mutefrog.myfolio.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _getCategoryData = MutableLiveData<DataHandler<GetCategoriesResponse>>()
    val getCategoryData: LiveData<DataHandler<GetCategoriesResponse>> = _getCategoryData

    fun getCategories(
        category: String?,
        token: String?
    ) {
        _getCategoryData.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getCategories(
                category,
                token
            )
            _getCategoryData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<GetCategoriesResponse>): DataHandler<GetCategoriesResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it -> return DataHandler.SUCCESS(it) }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}