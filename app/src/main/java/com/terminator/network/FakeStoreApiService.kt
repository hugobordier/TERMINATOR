package com.terminator.network

import com.terminator.model.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body

interface FakeStoreApiService {

    @GET("products")
    suspend fun getAllProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product

    @GET("products/categories")
    suspend fun getAllCategories(): List<String>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<Product>

    @POST("products")
    suspend fun createProduct(@Body product: Product): Product
}
