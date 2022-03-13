//From the initial start up page of DD
package com.jjcc.dishdiscovery.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.ImageView
import com.jjcc.dishdiscovery.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.login)
        loginButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //Links view as guest with the home page
        //w/o profile functionality
        val guestButton = findViewById<Button>(R.id.guest)
        guestButton.setOnClickListener{
                val intent = Intent(this, HomeActivity_New::class.java)
                startActivity(intent)
        }

        //Links account creation button on startup page
        //to the user sign up page
        val createAccButton = findViewById<Button>(R.id.createAcc)
        createAccButton.setOnClickListener{
            val intent = Intent(this, UserSignActivity::class.java)
            startActivity(intent)
        }

    }

}