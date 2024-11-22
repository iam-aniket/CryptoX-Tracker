package com.example.cryptoxtracker.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoxtracker.model.CryptoCurrency
import com.example.cryptoxtracker.network.RetrofitClient
import com.example.cryptoxtracker.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CryptoScreenViewModel : ViewModel() {

    private val repository: DataRepository = DataRepository(RetrofitClient.apiService)

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _coinListData = MutableStateFlow<Result<List<CryptoCurrency>>>(Result.success(emptyList()))
    val coinListData: StateFlow<Result<List<CryptoCurrency>>> = _coinListData

    init {
        fetchItems()
    }

    private fun fetchItems(){
        viewModelScope.launch {
            try{
                val result = repository.getDataFromApi()
                _coinListData.value = result
                _isLoading.value = false
            } catch(ex : Exception){
                Log.e("Exception", "Exception occurred $ex")
            }
        }
    }
}