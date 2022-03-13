package com.jjcc.dishdiscovery.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.jjcc.dishdiscovery.R


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Grab EditText Fields for Username and Password
        val inputUsername = findViewById<EditText>(R.id.signInUsername)
        val textInputLayout = findViewById<TextInputLayout>(R.id.signInPassword)
        val inputPassword = textInputLayout.editText


        //Callback handler to authenticate User
        val authenticationHandler = object: AuthenticationHandler {

            //if success
            override fun onSuccess(userSession: CognitoUserSession, newDevice: CognitoDevice?) {

                Log.i(TAG, "Login successful, can get tokens here!")

                //Grab tokens
                val accessToken = userSession.accessToken.jwtToken
                val idToken = userSession.idToken.jwtToken

                val usernameToken = userSession.username

                //Sanity check area ..
                Log.i(TAG, "Access Token: " + userSession.accessToken.jwtToken)
                Log.i(TAG, "Id Token: " + userSession.idToken.jwtToken)

                Log.i(TAG, "Username: " + userSession.username )
                Log.i(TAG, "is Valid? : " + userSession.isValid)

                //Toast to let users know we redirected
                Toast.makeText(this@LoginActivity, "Login success. Redirecting..", Toast.LENGTH_SHORT).show()

                //Pass tokens through intent to next Activity
                val intent = Intent(this@LoginActivity, HomeActivity_New::class.java)

//                intent.putExtra("Access Token", accessToken)
//                intent.putExtra("Username", usernameToken)
                startActivity(intent)
            }

            override fun getAuthenticationDetails(authenticationContinuation: AuthenticationContinuation?, userId: String)
            {
                Log.i(TAG, "in getAuthenticationDetails() ...")

                //need to get userID and password to continue
                val authenticationDetails: AuthenticationDetails = AuthenticationDetails(userId, inputPassword?.text.toString(), null)

                //Pass user sign-in credentials to the continuation
                authenticationContinuation?.setAuthenticationDetails(authenticationDetails)

                //Allow the sign in to continue
                authenticationContinuation?.continueTask()
            }

            //can add extra overrides such as override fun getMFACode for multi-factor authentication
            override fun getMFACode(continuation: MultiFactorAuthenticationContinuation?) {

                Log.i(TAG, "in getMFACode() ...")
            }

            //this will always trigger when username and password is verified
            //or authenticationChallenge
            override fun authenticationChallenge(continuation: ChallengeContinuation?) {
                Log.i(TAG, "in authenticationChallenge() function ...")
                continuation?.continueTask()

            }

            override fun onFailure(exception: Exception?) {

                //Logcat if login fail
                Log.i(TAG, "Login failed: " + exception?.localizedMessage)

                val pattern = Regex("^[^\\(]+")

                val result: MatchResult? = exception?.localizedMessage?.let { pattern.find(it) }
                var output = result?.value


                val toast = Toast.makeText(this@LoginActivity, "Login failed: $output", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

//                val toast = Toast.makeText(this@LoginActivity, "Login failed: Incorrect username or password. ", Toast.LENGTH_SHORT)
//                toast.setGravity(Gravity.CENTER, 0, 0)
//                toast.show()
            }

        }

        //clickListener for Login Button
        val buttonLogin = findViewById<Button>(R.id.signInButton)
        buttonLogin.setOnClickListener(object: View.OnClickListener {

            override fun onClick(v: View?) {

                if(inputUsername.text.toString().isNullOrEmpty()){
                    val toast = Toast.makeText(this@LoginActivity, "Please enter Username", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0,0)
                    toast.show()
                    return
                }

                //Make cognitoSettings object
                val cognitoSettings: CognitoSettings = CognitoSettings(this@LoginActivity)

                //Make CognitoUser object
                val thisUser: CognitoUser = cognitoSettings.userPool.getUser(inputUsername.text.toString())

                Log.i(TAG, "sign in button was clicked . . .")

                //Run authenticateUser in background
                thisUser.getSessionInBackground(authenticationHandler)
            }

        })

    }

    fun loginFail(failMsg: String) {

        val pattern = Regex("^[^\\(]+")

        val result: MatchResult? = pattern.find(failMsg)

        //process the message
        var output = result?.value

        val intent = Intent(this, LoginActivity::class.java)

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_login_fail_title)
            .setMessage(output)
            .setPositiveButton(R.string.ok) { dialog, which ->
                showMsg("Check inputs and try again")
            }
            .show()
    }
    private fun showMsg(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }
}