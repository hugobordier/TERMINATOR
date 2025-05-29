package com.terminator.repository

import com.terminator.model.Product

object PanierRepository {
    private val panier = mutableListOf<Product>()

    suspend fun add_product(product: Product) {
        panier.add(product)
    }

    suspend fun get_panier(): List<Product> = panier.toList()

    suspend fun vider_panier() {
        panier.clear()
    }

    suspend fun enleverproduct(product: Product) {
        panier.remove(product)
    }
}
