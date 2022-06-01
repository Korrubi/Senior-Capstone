package com.jjcc.dishdiscovery.activities.ui.profile

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler
import com.jjcc.dishdiscovery.activities.CognitoSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Time
import java.time.LocalDateTime

class ProfileViewModel() : ViewModel() {

    //Method 1 of making a variable
    //"Applying value to the variable"
    private val _textBlabla = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }

    //Make variables, we're assigning it to the guy above
    val textBlabla: LiveData<String> = _textBlabla

    //Method 2 - setting a value then using a getter below
    private val _someValue = MutableLiveData("123")

    val someValue: LiveData<String>  //Variable
        get() = _someValue


    private val _userSub = MutableLiveData("")

    val userSub: LiveData<String>
        get() = _userSub

    //May 26th - Testing to Log current time
    private val _currentTime = MutableLiveData(LocalDateTime.now())

    val currentTime: LiveData<LocalDateTime>
        get() = _currentTime




    //Make cognitoSettings and CognitoUser object to grab info using tokens
//    val cognitoSettings: CognitoSettings = CognitoSettings(application.applicationContext)
//    val thisUser : CognitoUser = cognitoSettings.userPool.currentUser
//
//    //callback Handler to perform getAttributes() call from cognitoUser
//    val detailsHandler = object: GetDetailsHandler {
//        override fun onSuccess(cognitoUserDetails: CognitoUserDetails?) {
//            Log.i(ContentValues.TAG, "GetDetailsHandler succeeded!")
//            Log.i(ContentValues.TAG, "Attributes Returned: " + cognitoUserDetails?.attributes?.attributes)
//
//            val userName = MutableLiveData<String>().apply { postValue(cognitoUserDetails?.attributes?.attributes?.getValue("name").toString())}
//            val userEmail = MutableLiveData<String>().apply { postValue(cognitoUserDetails?.attributes?.attributes?.getValue("email").toString())}
//            val userSub = MutableLiveData<String>().apply { postValue(cognitoUserDetails?.attributes?.attributes?.getValue("sub").toString())}
//
//            //Testing May 18th - add userSub
////            userSub = cognitoUserDetails?.attributes?.attributes?.getValue("sub").toString()
//
//            Log.i(ContentValues.TAG, "Username: $userName")
//            Log.i(ContentValues.TAG, "Email: $userEmail")
//            Log.i(ContentValues.TAG, "Sub: $userSub")
//
//
//        }
//
//        override fun onFailure(exception: Exception?) {
//            Log.i(ContentValues.TAG, "GetDetailsHandler Failed!")
//            Log.i(ContentValues.TAG, "Error: " + exception?.localizedMessage)
//        }
//    }


//    CoroutineScope(IO).launch {
//        val result = async { thisUser.getDetailsInBackground(detailsHandler)}.await()
//
//        delay(1000)
//        Log.i(ContentValues.TAG, "userName: $userName")
//        Log.i(ContentValues.TAG, "userEmail: $userEmail")
//        Log.i(ContentValues.TAG, "userSub: $userSub")
//        intent.putExtra("User Name", userName)
//        intent.putExtra("User Email", userEmail)
//        intent.putExtra("User Sub", userSub)
//        Log.i(ContentValues.TAG, "Scope done")
//    }



}