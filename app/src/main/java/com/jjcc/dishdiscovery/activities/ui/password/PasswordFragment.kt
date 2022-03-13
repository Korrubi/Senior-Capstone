package com.jjcc.dishdiscovery.activities.ui.password

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.google.android.material.textfield.TextInputLayout
import com.jjcc.dishdiscovery.R
import com.jjcc.dishdiscovery.activities.CognitoSettings
import java.lang.Exception

class PasswordFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =  inflater.inflate(R.layout.fragment_password, container, false)

        val editOldPassword = view.findViewById<TextInputLayout>(R.id.oldPassword)
        val oldPassword = editOldPassword.editText

        val editNewPassword = view.findViewById<TextInputLayout>(R.id.newPassword)
        val newPassword = editNewPassword.editText

        val userNameEmailTest = view.findViewById<TextInputLayout>(R.id.emailPassword)
        val usernameEmail = userNameEmailTest.editText

        val confirmButtonNewPassword = view.findViewById<Button>(R.id.changePassword)

        val handler = object : GenericHandler {
            override fun onSuccess() {
                Log.i(ContentValues.TAG, "Password Changed Successfully")
                showToast("Password changed successfully")
                view.findNavController().popBackStack()
            }

            override fun onFailure(exception: Exception?) {
                Log.i(ContentValues.TAG, "Failed to change Password: " + exception?.localizedMessage)
                showToast("Failed to change password: " + exception?.localizedMessage)
            }
        }

        confirmButtonNewPassword.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val cognitoSettings: CognitoSettings = CognitoSettings(activity)
                val thisUser: CognitoUser = cognitoSettings.userPool.getUser(usernameEmail?.text.toString())

                cognitoSettings.userPool.getUser(usernameEmail?.text.toString())
                    .changePasswordInBackground(oldPassword?.text.toString(), newPassword?.text.toString(), handler)
            }
        })
        return view
    }

    private fun showToast(value:String){
        val toast = Toast.makeText(activity, value, Toast.LENGTH_SHORT)
        toast.show()
    }
}
