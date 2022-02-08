package com.jjcc.dishdiscovery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.jjcc.dishdiscovery.R

class UserProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val userBackButton = findViewById<Button>(R.id.butBackArrow)
        userBackButton.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val emailButton = findViewById<Button>(R.id.butChange_Email)
        emailButton.setOnClickListener{
            val intent = Intent(this, EmailActivity::class.java)
            startActivity(intent)
        }

        val passwordButton = findViewById<Button>(R.id.butChange_Password)
        passwordButton.setOnClickListener{
            val intent = Intent(this, PasswordActivity::class.java)
            startActivity(intent)
        }

        val dietButton = findViewById<Button>(R.id.butChange_Diet_Pref)
        dietButton.setOnClickListener{
            val intent = Intent(this, DietActivity::class.java)
            startActivity(intent)
        }

        val allergyButton = findViewById<Button>(R.id.butChange_Allergy_Pref)
        allergyButton.setOnClickListener{
            val intent = Intent(this, AllergyActivity::class.java)
            startActivity(intent)
        }

        val cuisineButton = findViewById<Button>(R.id.butChange_Cuisine_Pref)
        cuisineButton.setOnClickListener{
            val intent = Intent(this, CuisineActivity::class.java)
            startActivity(intent)
        }

    }

}