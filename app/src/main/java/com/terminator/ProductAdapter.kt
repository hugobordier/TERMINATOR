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
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_view, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        val itemView = holder.itemView
        itemView.findViewById<TextView>(R.id.textView_title).apply {
            text = product.title
        }
        itemView.findViewById<TextView>(R.id.textView_price).apply {
            text = "${product.price} €"
        }
        itemView.findViewById<TextView>(R.id.textView_rating).apply {
            text = product.rating.toString()
        }
        itemView.findViewById<ImageView>(R.id.imageView_article).let {
            if (product.image.isBlank()) {
                Glide.with(itemView).load(product.image).into(it)
            } else {
                Glide.with(itemView).load(product.image).into(it)
            }

        }
        try {
            val barcodeEncoder = BarcodeEncoder()
            val qrContent = """
        {
          "title": "${product.title}",
          "description": "${product.description}",
          "category": "${product.category}",
          "price": ${product.price},
          "rating":${product.rating}
          "image": "${product.image}"
        }
    """.trimIndent()

            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(qrContent, BarcodeFormat.QR_CODE, 300, 300)
            itemView.findViewById<ImageView>(R.id.imageView_qrcode).setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        holder.itemView.setOnClickListener{
            onItemClick(product)
        }



    }
}