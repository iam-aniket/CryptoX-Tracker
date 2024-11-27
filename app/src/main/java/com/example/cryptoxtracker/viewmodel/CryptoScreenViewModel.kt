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
import androidx.lifecycle.viewModelScope
import com.example.cryptoxtracker.model.CryptoValues
import com.example.cryptoxtracker.repository.DataStoreManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class CryptoScreenViewModel(private val repository1: DataStoreManager) : ViewModel() {

    private val repository: DataRepository = DataRepository(RetrofitClient.apiService)

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _coinListData =
        MutableStateFlow<Result<List<CryptoCurrency>>>(Result.success(emptyList()))
    val coinListData: StateFlow<Result<List<CryptoCurrency>>> = _coinListData

    //Quantity Variable - Map
    private val _coinQuantityList = MutableStateFlow<MutableMap<String, Double>>(mutableMapOf())
    val coinQuantityList: StateFlow<MutableMap<String, Double>> = _coinQuantityList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Filtered coin list wrapped in a Result
    val filteredCoinList: StateFlow<Result<List<CryptoCurrency>>> = combine(
        _coinListData,
        _searchQuery
    ) { result, query ->
        result.mapCatching { coinList ->
            if (query.isBlank()) {
                coinList // Return full list if no search query
            } else {
                coinList.filter { it.name.contains(query, ignoreCase = true) } // Filter coins
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = Result.success(emptyList())
    )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            try {
                Log.e("Preference", "Starting ---> Data Store Value Starting")

                val details = repository1.getCoinDetails().first()
                Log.e("ZZ", "$details")
                _coinQuantityList.value = details

                Log.e("Preference", "Pref Loaded ---> Data Store Value Loaded")
                val result = repository.getDataFromApi()
                _coinListData.value = result
                _isLoading.value = false
            } catch (ex: Exception) {
                Log.e("Exception", "Exception occurred $ex")
            }
        }
    }

    fun updateCoinDetails(newDetails: MutableMap<String, Double>) {
        viewModelScope.launch {
            repository1.updateCoinDetails(newDetails)
            _coinQuantityList.value = newDetails
        }
    }
}