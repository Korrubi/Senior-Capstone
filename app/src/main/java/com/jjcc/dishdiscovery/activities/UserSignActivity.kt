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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class UserSignActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_sign)
        registerUser()
    }


    private fun registerUser() {
        val inputName = findViewById<EditText>(R.id.name_edit)
        val inputEmail = findViewById<EditText>(R.id.email_edit)

        val textInputLayout = findViewById<TextInputLayout>(R.id.create_password)
        val inputPassword = textInputLayout.editText

        val userAttributes = CognitoUserAttributes()


        val signupCallback = object : SignUpHandler {

            override fun onSuccess(user: CognitoUser, signUpConfirmationState: Boolean, cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails) {
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

                //Checks if passwords match, if they do not, break.
                if(validatePassword()){
                    return
                }
                //passes to Cognito
                userAttributes.addAttribute("name", inputName.text.toString())
                userAttributes.addAttribute("email", inputEmail.text.toString())

                val cognitoSettings: CognitoSettings = CognitoSettings(this@UserSignActivity)
//                cognitoSettings.userPool.signUpInBackground(inputUserName.text.toString(), inputPassword.text.toString(), userAttributes, null, signupCallback)
                cognitoSettings.userPool.signUpInBackground(inputEmail.text.toString(), inputPassword?.text.toString(), userAttributes, null, signupCallback)
            }
        })

    }

    private fun validatePassword(): Boolean {
        val textInputLayout = findViewById<TextInputLayout>(R.id.create_password)
        val inputPassword = textInputLayout.editText

        val confirmNewPassword = findViewById<TextInputLayout>(R.id.confirm_create_password)
        val inputConfirmPassword = confirmNewPassword.editText

        //Checks if passwords DO NOT match, throws error message if true
        if(!inputPassword?.text.toString().equals(inputConfirmPassword?.text.toString())){
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_LONG).show()
            return true
        }
        return false
    }

    // Shows AlertDialog when signUp fails, uses regex to parse error msg to display
    fun signUpFail(errorMsg: String) {

        val pattern = Regex("^[^\\(]+")

        val result: MatchResult? = pattern.find(errorMsg)
        var output = result?.value

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_fail_title)
            .setMessage(output)
            .setPositiveButton(R.string.ok) { dialog, which ->
                showMsg("Check inputs and try again")
            }
//            .setNegativeButton(R.string.cancel) { dialog, which ->
//                showMsg("wtf sir")
//            }
            .show()
    }

    // Shows AlertDialog when signUp success, use regex to parse confirm msg
    fun confirmVerify(successMsg: String) {

        val pattern = Regex("^[^\\(]+")

        val result: MatchResult? = pattern.find(successMsg)
        var output = result?.value

        val intent = Intent(this, UserVerifyActivity::class.java)

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_success_title)
            .setMessage("Verification Code sent to $output. Please verify to continue")
//            .setMessage(R.string.dialog_success_message)
            .setPositiveButton(R.string.dialog_success_continue) { dialog, which ->
                startActivity(intent)
//                showMsg("Good boy")
            }
//            .setNegativeButton(R.string.cancel) { dialog, which ->
//                showMsg("wtf sir")
//            }
            .show()
    }

    private fun showMsg(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }
}