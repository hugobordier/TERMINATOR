package com.terminator.repository

import com.terminator.model.Product
import com.terminator.network.RetrofitInstance

class ProductRepository {

    suspend fun getAllProducts(): List<Product> {
        return RetrofitInstance.api.getAllProducts()
    }

    suspend fun getProductById(id: Int): Product {
        return RetrofitInstance.api.getProduct(id)
    }

    suspend fun getCategories(): List<String> {
        return RetrofitInstance.api.getAllCategories()
    }

    suspend fun getProductsByCategory(category: String): List<Product> {
        return RetrofitInstance.api.getProductsByCategory(category)
    }

    suspend fun createProduct(product: Product): Product {
        return RetrofitInstance.api.createProduct(product)
    }
}
