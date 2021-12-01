package com.example.logindd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class UserProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val allergyButton = findViewById<Button>(R.id.allergyPref)
        allergyButton.setOnClickListener{
            val intent = Intent(this, AllergyActivity::class.java)
            startActivity(intent)
        }

    }

}