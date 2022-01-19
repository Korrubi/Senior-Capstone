package com.example.dishdiscovery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val passwordBackButton = findViewById<Button>(R.id.butPasswordBackArrow)
        passwordBackButton.setOnClickListener{
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }

    }

}