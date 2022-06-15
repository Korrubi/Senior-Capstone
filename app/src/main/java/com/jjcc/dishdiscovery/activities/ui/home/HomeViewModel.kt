package com.jjcc.dishdiscovery.activities.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.LocalTime
import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    //May 26th - Testing to Log current time
    private val _currentTime = MutableLiveData(LocalDateTime.now())

    val currentTime: LiveData<LocalDateTime>
        get() = _currentTime

    //Set current Time in Hours:Minutes:Seconds
    private val _currentHour = MutableLiveData(LocalTime.now())

    //Get current Time in Hours:Minutes:Seconds
    val currentHour: LiveData<LocalTime>
        get() = _currentHour

    //This uses the fixed 12:00 Noon
    private val _noonHour = MutableLiveData(LocalTime.NOON)

    val noonHour: LiveData<LocalTime>
        get() = _noonHour

    private val _midnightHour = MutableLiveData(LocalTime.MIDNIGHT)

    val midnightHour : LiveData<LocalTime>
        get() = _midnightHour

    //Set morning time as 06:00
    private val _morningHour = MutableLiveData(LocalTime.of(6,0,0))

    val morningHour : LiveData<LocalTime>
        get() = _morningHour
//    private val _DinnerHour = MutableLiveData(LocalTime.)


}