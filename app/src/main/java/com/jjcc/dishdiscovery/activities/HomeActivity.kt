package com.jjcc.dishdiscovery.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler
import com.google.android.material.navigation.NavigationView
import com.jjcc.dishdiscovery.R
import com.jjcc.dishdiscovery.activities.spoonacular.Spoonacular
import com.jjcc.dishdiscovery.databinding.ActivityHomeNewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeNewBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    //Initialize name and email of a given user
    var userName = ""
    var userEmail = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHomeActivityNew.toolbar)

        drawerLayout = binding.drawerLayout
        navView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_content_home_activity_new)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_profile, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Make cognitoSettings and CognitoUser object to grab info using tokens
        val cognitoSettings: CognitoSettings = CognitoSettings(this@HomeActivity)
        val thisUser : CognitoUser = cognitoSettings.userPool.currentUser

        //callback Handler to perform getAttributes() call from cognitoUser
        val detailsHandler = object: GetDetailsHandler {
            override fun onSuccess(cognitoUserDetails: CognitoUserDetails?) {
                Log.i(ContentValues.TAG, "GetDetailsHandler succeeded!")
                Log.i(ContentValues.TAG, "Attributes Returned: " + cognitoUserDetails?.attributes?.attributes)

                userName = cognitoUserDetails?.attributes?.attributes?.getValue("name").toString()
                userEmail = cognitoUserDetails?.attributes?.attributes?.getValue("email").toString()
                Log.i(ContentValues.TAG, "Username: $userName")
                Log.i(ContentValues.TAG, "Email: $userEmail")
            }

            override fun onFailure(exception: Exception?) {
                Log.i(ContentValues.TAG, "GetDetailsHandler Failed!")
                Log.i(ContentValues.TAG, "Error: " + exception?.localizedMessage)
            }
        }

        CoroutineScope(IO).launch {
            val result = async { thisUser.getDetailsInBackground(detailsHandler)}.await()

            delay(1000)
            intent.putExtra("User Name", userName)
            intent.putExtra("User Email", userEmail)
        }

        val thaiButton = findViewById<Button>(R.id.thai)
        thaiButton.setOnClickListener {
            var intent = Intent (this, Spoonacular::class.java)
            intent.putExtra("cuisine", "thai")
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home_activity_new)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}