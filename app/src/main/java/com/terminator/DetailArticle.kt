    package com.terminator

    import android.os.Build
    import android.os.Bundle
    import android.util.Log
    import android.view.View
    import android.widget.Button
    import android.widget.ImageView
    import android.widget.TextView
    import android.widget.Toast
    import androidx.annotation.RequiresApi
    import androidx.appcompat.app.AppCompatActivity
    import androidx.lifecycle.lifecycleScope
    import com.bumptech.glide.Glide
    import com.terminator.model.Product
    import com.terminator.repository.PanierRepository
    import kotlinx.coroutines.launch

    class DetailArticle : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_detail_article)

            val titleview = findViewById<TextView>(R.id.title)
            val descriptionview = findViewById<TextView>(R.id.description)
            val priceview = findViewById<TextView>(R.id.price)
            val ratingview = findViewById<TextView>(R.id.rating)
            val categoryview = findViewById<TextView>(R.id.category)
            val imageView = findViewById<ImageView>(R.id.imageView)

            val extras = intent.extras

            // Récupération compatible avec toutes les versions Android
            val product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                extras?.getParcelable(PRODUCT_DATA, Product::class.java)
            } else {
                @Suppress("DEPRECATION")
                extras?.getParcelable<Product>(PRODUCT_DATA)
            }

            product?.let {
                titleview.text = it.title
                descriptionview.text = it.description
                priceview.text = it.price.toString()
                ratingview.text = it.rating.toString()
                categoryview.text = it.category
                Glide.with(this).load(it.image).into(imageView)
            } ?: run {
                // Gérer le cas où le produit est null
                Log.e("DetailArticle", "Produit non trouvé dans l'intent")
                Toast.makeText(this, "Erreur: produit non trouvé", Toast.LENGTH_SHORT).show()
                finish()
                return
            }

            val addbutton = findViewById<Button>(R.id.button_acheter_article)
            val fromPanier = intent.getStringExtra(PANIER) == "frompanier"
            val suppbutton = findViewById<Button>(R.id.button_supp_article)
            val panierrepo = PanierRepository

            if (fromPanier) {
                suppbutton.visibility = View.VISIBLE
                addbutton.visibility = View.GONE

                suppbutton.setOnClickListener {
                    lifecycleScope.launch {
                        panierrepo.enleverproduct(product)
                        Toast.makeText(this@DetailArticle, R.string.sup_client_message_add, Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            } else {
                suppbutton.visibility = View.GONE
                addbutton.visibility = View.VISIBLE

                addbutton.setOnClickListener {
                    lifecycleScope.launch {
                        panierrepo.add_product(product)
                        Toast.makeText(this@DetailArticle, R.string.add_client_message_add, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }