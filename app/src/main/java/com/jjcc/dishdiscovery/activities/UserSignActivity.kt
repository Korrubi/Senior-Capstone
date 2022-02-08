package com.jjcc.dishdiscovery.activities


import android.app.Dialog
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.jjcc.dishdiscovery.R
import java.lang.Exception
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class UserSignActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_sign)
        registerUser()
    }


    private fun registerUser() {
        val inputName = findViewById<EditText>(R.id.name_edit)
        val inputEmail = findViewById<EditText>(R.id.email_edit)

        val inputPassword = findViewById<EditText>(R.id.create_pw_edit)

        val userAttributes = CognitoUserAttributes()


        val signupCallback = object : SignUpHandler {

            override fun onSuccess(
                user: CognitoUser,
                signUpConfirmationState: Boolean,
                cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails
            ) {
                Log.i(TAG, "sign up success...is confirmed: " + signUpConfirmationState)

                //shows Creation Success Dialog Prompt (function below)

                if (!signUpConfirmationState) {
                    Log.i(
                        TAG,
                        "sign up success...not confirmed, verification code sent to: " + cognitoUserCodeDeliveryDetails.getDestination()
                    )

                } else {
                    Log.i(TAG, "sign up success...confirmed")
                }
                confirmVerify(cognitoUserCodeDeliveryDetails.getDestination())
            }

            override fun onFailure(exception: Exception) {

                //logs to LogCat
                Log.i(TAG, "Sign-up Failure: " + exception.localizedMessage)

                //shows Creation Failed Dialog Prompt (function below)
                signUpFail(exception.localizedMessage)
            }
        }

        val buttonRegister = findViewById<Button>(R.id.done_button)
        buttonRegister.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {

                //passes to Cognito
                userAttributes.addAttribute("name", inputName.text.toString())
                userAttributes.addAttribute("email", inputEmail.text.toString())

                val cognitoSettings: CognitoSettings = CognitoSettings(this@UserSignActivity)
//                cognitoSettings.userPool.signUpInBackground(inputUserName.text.toString(), inputPassword.text.toString(), userAttributes, null, signupCallback)
                cognitoSettings.userPool.signUpInBackground(
                    inputEmail.text.toString(),
                    inputPassword.text.toString(),
                    userAttributes,
                    null,
                    signupCallback
                )

            }
        })

    }

    fun signUpFail(errorMsg: String) {


        val pattern = Regex("^[^\\(]+")

        val result: MatchResult? = pattern.find(errorMsg)
        var output = result?.value

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_fail_title)
            .setMessage(output)
            .setPositiveButton(R.string.ok) { dialog, which ->
                showMsg("Good boy, now try again")
            }
            .setNegativeButton(R.string.cancel) { dialog, which ->
                showMsg("wtf sir")
            }
            .show()
    }

    fun confirmVerify(successMsg: String) {

        val pattern = Regex("^[^\\(]+")

        val result: MatchResult? = pattern.find(successMsg)
        var output = result?.value

        val intent = Intent(this, UserVerifyActivity::class.java)

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_success_title)
            .setMessage("Verification Code sent to $output. Please verify to continue")
//            .setMessage(R.string.dialog_success_message)
            .setPositiveButton(R.string.ok) { dialog, which ->
                startActivity(intent)
                showMsg("Good boy")
            }
            .setNegativeButton(R.string.cancel) { dialog, which ->
                showMsg("wtf sir")
            }
            .show()
    }

    private fun showMsg(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }
}