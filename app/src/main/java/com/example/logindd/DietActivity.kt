package com.example.logindd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class DietActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet)

        val dietBackButton = findViewById<ImageButton>(R.id.dietBackButton)
        dietBackButton.setOnClickListener{
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }

        val dietForwardButton = findViewById<ImageButton>(R.id.dietForwardButton)
        dietForwardButton.setOnClickListener{
            val intent = Intent(this, CuisineActivity::class.java)
            startActivity(intent)
        }
    }

}