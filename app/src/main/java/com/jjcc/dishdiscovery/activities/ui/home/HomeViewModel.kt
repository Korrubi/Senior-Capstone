package com.jjcc.dishdiscovery.activities.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.LocalTime

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
    private val _NoonHour = MutableLiveData(LocalTime.NOON)

    val NoonHour: LiveData<LocalTime>
        get() = _NoonHour

}