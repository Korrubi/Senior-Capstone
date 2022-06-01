package com.jjcc.dishdiscovery.activities.ui.diet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue

class DietViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    // Create a LiveData with a String
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val currentBox : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    //Method 2 - setting a value then using a getter below
    // private var _dietMap = mutableMapOf<String, AttributeValue>()

//    var dietMap: MutableMap<String, AttributeValue> = mutableMapOf()  //Variable
//        get() = _dietMap
}