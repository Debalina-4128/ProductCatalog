package com.example.productcalatlog.data.api

import ApiProduct
import com.example.productcalatlog.data.api.model.ApiProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface ProductApi {
    @GET("products")
    suspend fun getProducts(): ApiProductsResponse

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ApiProduct
}

