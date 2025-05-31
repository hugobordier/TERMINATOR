package com.terminator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.terminator.model.Product
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import android.graphics.Bitmap

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view)

public const val PRODUCT_DATA = "product_data"
public const val PANIER = "frompanier"

class ProductAdapter(
    private var products: MutableList<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    private var originalProducts: List<Product> = products.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_view, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        val itemView = holder.itemView

        // Configurer le click listener
        itemView.setOnClickListener {
            onItemClick(product)
        }

        itemView.findViewById<TextView>(R.id.textView_title).apply {
            text = product.title
        }
        itemView.findViewById<TextView>(R.id.textView_price).apply {
            text = "${product.price} €"
        }
        itemView.findViewById<TextView>(R.id.textView_rating).apply {
            text = product.rating.toString()
        }

        itemView.findViewById<ImageView>(R.id.imageView_article).let { imageView ->
            if (product.image.isNotBlank()) {
                Glide.with(itemView)
                    .load(product.image)
                    .into(imageView)
            }
        }

        // Génération du QR Code
        generateQRCode(product, itemView)
    }

    private fun generateQRCode(product: Product, itemView: View) {
        try {
            val barcodeEncoder = BarcodeEncoder()
            val qrContent = """
                {
                  "title": "${product.title}",
                  "description": "${product.description}",
                  "category": "${product.category}",
                  "price": ${product.price},
                  "rating": ${product.rating},
                  "image": "${product.image}"
                }
            """.trimIndent()

            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(
                qrContent,
                BarcodeFormat.QR_CODE,
                300,
                300
            )
            itemView.findViewById<ImageView>(R.id.imageView_qrcode).setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Méthode pour mettre à jour les produits (utile pour le filtrage)
    fun updateProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    // Méthode pour filtrer les produits
    fun filter(query: String) {
        products.clear()
        if (query.isEmpty()) {
            products.addAll(originalProducts)
        } else {
            val filteredList = originalProducts.filter { product ->
                product.title.contains(query, ignoreCase = true) ||
                        product.description.contains(query, ignoreCase = true) ||
                        product.category.contains(query, ignoreCase = true)
            }
            products.addAll(filteredList)
        }
        notifyDataSetChanged()
    }

    // Méthode pour réinitialiser les produits originaux
    fun setOriginalProducts(newProducts: List<Product>) {
        originalProducts = newProducts
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }
}