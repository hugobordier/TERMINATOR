package com.terminator

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.terminator.model.Product

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view)
public const val PRODUCT_DATA = "product.data"
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

        itemView.setOnClickListener() {
            with(itemView.context) {
                val intent = Intent(this, DetailArticle::class.java).apply {
                    putExtra(PRODUCT_DATA, product)
                }
                startActivity(intent)
            }

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

        itemView.findViewById<ImageView>(R.id.imageView_article).let {
            if (product.image.isBlank()) {
                Glide.with(itemView).load(product.image).into(it)
            } else {
                Glide.with(itemView).load(product.image).into(it)
            }
        }
    }
}
