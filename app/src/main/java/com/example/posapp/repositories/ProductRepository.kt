package com.example.posapp.repositories

import com.example.posapp.models.Product
import com.example.posapp.utils.ApiUtils

class ProductRepository {


    suspend fun getProducts(skip: Int, take: Int): List<Product> {
        return ApiUtils.instanceTwo.getProducts(skip, take)
    }

    suspend fun searchProducts(searchString: String): List<Product> {
        return ApiUtils.instanceTwo.searchProducts(searchString)
    }
}
