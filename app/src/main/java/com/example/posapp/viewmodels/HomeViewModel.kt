package com.example.posapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.posapp.models.Product
import com.example.posapp.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel : ViewModel() {
    private val repository = ProductRepository()

    val productLiveData: MutableLiveData<List<Product>> = MutableLiveData()
    val searchLiveData: MutableLiveData<List<Product>> = MutableLiveData()

    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun getProducts(skip: Int, take: Int) {
        viewModelScope.launch {
            try {
                val product = repository.getProducts(skip, take)
                Log.v("product" , product.toString())
                productLiveData.value = product
            } catch (e: Exception) {
                errorLiveData.postValue("Failed to load products: ${e.message}")
            }
        }
    }

    fun searchProducts(searchString: String) {
        viewModelScope.launch {
            try {
                val product = repository.searchProducts(searchString)
                Log.v("product" , product.toString())
                searchLiveData.value = product
            } catch (e: Exception) {
                errorLiveData.postValue("Failed to search products: ${e.message}")
            }
        }
    }
    // If we wanted to do local search in live data without calling api
    fun searchProductsInLiveData(searchString: String) {
        val lowerCaseSearchString = searchString.lowercase(Locale.ROOT)
        val filteredProducts = productLiveData.value?.filter {
            it.name.en.lowercase(Locale.ROOT).contains(lowerCaseSearchString)
        }
        productLiveData.value = filteredProducts
    }
}

