package com.jjcc.dishdiscovery.activities


import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.jjcc.dishdiscovery.R
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.lang.Exception


class UserVerifyActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_verify)

        val verifyCode = findViewById<EditText>(R.id.verifyCode_edit)
        val verifyEmail = findViewById<EditText>(R.id.email_edit)
        val buttonVerify = findViewById<Button>(R.id.verify_button)

        buttonVerify.setOnClickListener{
            CoroutineScope(IO).launch {
                doInBackground(verifyCode.text.toString(), verifyEmail.text.toString())
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
                result[0] = "Failed" + exception.message
            }
        }

        val cognitoSettings: CognitoSettings = CognitoSettings(this@UserVerifyActivity)
        val thisUser: CognitoUser = cognitoSettings.userPool.getUser(verifyEmail)
        thisUser.confirmSignUp(verifyCode, false, confirmationCallback)
        Log.i(TAG, "Confirmation Result: " + result[0])
        return result[0]
    }
}
