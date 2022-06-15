package com.jjcc.dishdiscovery.activities


import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jjcc.dishdiscovery.R
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception

class UserVerifyActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_verify)

        val verifyCode = findViewById<EditText>(R.id.enterVerifyCode)
        val verifyEmail = findViewById<EditText>(R.id.enterEmailAddress)
        val buttonVerify = findViewById<Button>(R.id.verifyButton)
//        val intent = Intent(this, LoginActivity::class.java )

        buttonVerify.setOnClickListener{

            //There's IO, Main, Default
            CoroutineScope(IO).launch {

                //Run in asynchronously
                val task1 = async {
                    doInBackground(verifyCode.text.toString(), verifyEmail.text.toString())
                }.await()

                if (task1.contains("Succeeded")) {
                    println("Debug: Task 1 is done: $task1")

                    verifySuccess(task1)
                }
                if (task1.contains("Failed")){
                    println("Debug: Task 1 is done: $task1")
                    verifyFail(task1)
                }
//                doInBackground(verifyCode.text.toString(), verifyEmail.text.toString())
            }
        }
    }

    private suspend fun doInBackground(verifyCode: String, verifyEmail: String): String {
        val result = Array(1) { "" }

        val confirmationCallback = object : GenericHandler {
            override fun onSuccess() {
                result[0] = "Succeeded!"
            }
            override fun onFailure(exception: Exception) {
                result[0] = "Failed " + exception.message
            }
        }

        val cognitoSettings: CognitoSettings = CognitoSettings(this@UserVerifyActivity)
        val thisUser: CognitoUser = cognitoSettings.userPool.getUser(verifyEmail)
        thisUser.confirmSignUp(verifyCode, false, confirmationCallback)
        Log.i(TAG, "Confirmation Result: " + result[0])
        return result[0]
    }

    private suspend fun verifySuccess(successMsg: String) {
        withContext(Main) {

            val pattern = Regex("^[^\\(]+")

            val result: MatchResult? = pattern.find(successMsg)
            var output = result?.value

            val intent = Intent(this@UserVerifyActivity, LoginActivity::class.java)

            MaterialAlertDialogBuilder(this@UserVerifyActivity)
                .setTitle(R.string.verify_success_title)
                .setMessage(R.string.verify_success_message)
                .setPositiveButton(R.string.verify_success_continue) { dialog, which ->
                    startActivity(intent)
                }
                .show()
        }
    }

    private suspend fun verifyFail(errorMsg: String) {
        withContext(Main) {

            val pattern = Regex("^[^\\(]+")

            val result: MatchResult? = pattern.find(errorMsg)
            var output = result?.value

            MaterialAlertDialogBuilder(this@UserVerifyActivity)
                .setTitle(R.string.verify_fail_title)
                .setMessage(output)
                .setPositiveButton(R.string.ok) { dialog, which ->
                }
                .show()
        }
    }
}