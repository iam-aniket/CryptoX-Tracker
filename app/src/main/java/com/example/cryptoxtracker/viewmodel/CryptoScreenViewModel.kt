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
import kotlinx.coroutines.launch

class CryptoScreenViewModel : ViewModel() {

    private val repository: DataRepository = DataRepository(RetrofitClient.apiService)

    private val APIKEY = "CG-cADMVJyJqucmmmNkLoPimPiL"

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _coinListData = MutableLiveData<Result<List<CryptoCurrency>>>(Result.success(emptyList()))
    val coinListData: LiveData<Result<List<CryptoCurrency>>> = _coinListData

    init {
        fetchItems()
    }

    private fun fetchItems(){
        viewModelScope.launch {
            try{
                val result = repository.getDataFromApi()
                _coinListData.value = result
            } catch(ex : Exception){
                Log.e("Exception", "Exception occurred $ex")
            }
        }
    }
}