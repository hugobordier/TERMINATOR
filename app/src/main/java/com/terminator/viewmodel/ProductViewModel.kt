package com.terminator.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terminator.model.Product
import com.terminator.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    val products = MutableLiveData<List<Product>>()
    val categories = MutableLiveData<List<String>>()
    val singleProduct = MutableLiveData<Product>()

    fun loadAllProducts() {
        viewModelScope.launch {
            val result = repository.getAllProducts()
            products.postValue(result)
        }
    }

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            val result = repository.getProductById(id)
            singleProduct.postValue(result)
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            val result = repository.getCategories()
            categories.postValue(result)
        }
    }

    fun loadProductsByCategory(category: String) {
        viewModelScope.launch {
            val result = repository.getProductsByCategory(category)
            products.postValue(result)
        }
    }

    fun createProduct(product: Product) {
        viewModelScope.launch {
            val result = repository.createProduct(product)
            // par exemple, tu peux rafraîchir la liste après création
            loadAllProducts()
        }
    }
}
