package com.terminator

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.terminator.model.Product
import com.terminator.repository.ProductRepository

class DetailArticle : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)



        val titleview = findViewById<TextView>(R.id.title)
        val descriptionview = findViewById<TextView>(R.id.description)
        val priceview=findViewById<TextView>(R.id.price)
        val ratingview=findViewById<TextView>(R.id.rating)
        val categoryview=findViewById<TextView>(R.id.category)
        val imageView = findViewById<ImageView>(R.id.imageView)

        val extras = intent.extras

        val product = extras?.getParcelable(PRODUCT_DATA, Product::class.java)?.let {
            titleview.text = it.title
            descriptionview.text =it.description
            priceview.text= it.price.toString()
            ratingview.text= it.rating.toString()
            categoryview.text=it.category
            Glide.with(this).load(it.image).into(imageView)

        }
        val button = findViewById<Button>(R.id.button_acheter_article)
        button.setOnClickListener {
            var lastName = lastNameEdittext.text.toString()
            Log.d("epf", "Nom : ${lastName}")

            val gender =
                if (genderRadioGroup.checkedRadioButtonId == R.id.client_genre_woman_bt) {
                    Gender.WOMAN
                } else {
                    Gender.MAN
                }
            Log.d(tag,"genre: $gender")
            val client = Client("",lastName,gender)
            finish()
            Toast.makeText(this,R.string.add_client_message, Toast.LENGTH_LONG).show()
        }
    }
}