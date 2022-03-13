package com.jjcc.dishdiscovery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.jjcc.dishdiscovery.R
import com.jjcc.dishdiscovery.activities.ui.profile.ProfileFragment

class PasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val passwordBackButton = findViewById<Button>(R.id.butPasswordBackArrow)
        passwordBackButton.setOnClickListener{
            val intent = Intent(this, ProfileFragment::class.java)
            startActivity(intent)
        }

    }

}