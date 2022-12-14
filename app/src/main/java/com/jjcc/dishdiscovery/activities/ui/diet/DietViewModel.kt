package com.jjcc.dishdiscovery.activities.ui.diet

import android.database.Observable
import android.text.Spannable
import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableField
import androidx.databinding.ObservableMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import org.w3c.dom.Attr

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
     private var _dietMap = mutableMapOf<String, AttributeValue>()

    var dietMap: MutableMap<String, AttributeValue> = mutableMapOf()  //Variable
        get() = newMap

    var newMap = ObservableArrayMap<String, AttributeValue>()



//    var dietMap1: LiveData<Spannable>

//    var dietMap1 : Observable<MutableMap<String, AttributeValue>> = mutableMapOf()
//            get() = dietMap1
//    val dietMap : MutableMap<String, AttributeValue> by lazy {
//        MutableMap<String, AttributeValue>()
//    }
}