package com.example.logindd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class AllergyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allergy)

        val allergyBackButton = findViewById<ImageButton>(R.id.allergyBackButton)
        allergyBackButton.setOnClickListener{
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }

        val allergyForwardButton = findViewById<ImageButton>(R.id.allergyForwardButton)
        allergyForwardButton.setOnClickListener{
            val intent = Intent(this, DietActivity::class.java)
            startActivity(intent)
        }
    }
}