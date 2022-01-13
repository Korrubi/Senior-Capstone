package com.example.logindd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class CuisineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuisine)

        val cuisineBackButton = findViewById<ImageButton>(R.id.cuisineBackButton)
        cuisineBackButton.setOnClickListener{
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }

        val cuisineForwardButton = findViewById<ImageButton>(R.id.cuisineForwardButton)
        cuisineForwardButton.setOnClickListener{
            val intent = Intent(this, AllergyActivity::class.java)
            startActivity(intent)
        }

    }
}