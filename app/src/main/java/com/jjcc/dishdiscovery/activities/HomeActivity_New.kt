package com.jjcc.dishdiscovery.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler
import com.jjcc.dishdiscovery.R
import com.jjcc.dishdiscovery.activities.ui.profile.ProfileFragment
import com.jjcc.dishdiscovery.databinding.ActivityHomeNewBinding
import java.lang.Exception

class HomeActivity_New : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHomeActivityNew.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
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

        //Initialize name and email of a given user
        var userName = ""
        var userEmail = ""

        //Make cognitoSettings and CognitoUser object to grab info using tokens
        val cognitoSettings: CognitoSettings = CognitoSettings(this@HomeActivity_New)
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

        //use CognitoUser thisUser to call getDetailsInBackground to retrieve information
        thisUser.getDetailsInBackground(detailsHandler)

        //links up profile from home
        //pass 3 intent string field
//        val profileButton = findViewById<Button>(R.id.nav_profile)
//        profileButton.setOnClickListener{
//            val intent = Intent(this, ProfileFragment::class.java)
//            intent.putExtra("Access Token", accessToken)
//            intent.putExtra("User Name", userName)
//            intent.putExtra("User Email", userEmail)
//            startActivity(intent)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_activity__new, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home_activity_new)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}