//For the home page of DD
package com.example.logindd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //links up profile from home
        val profileButton = findViewById<Button>(R.id.profile)
        profileButton.setOnClickListener{
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }
    }
}
