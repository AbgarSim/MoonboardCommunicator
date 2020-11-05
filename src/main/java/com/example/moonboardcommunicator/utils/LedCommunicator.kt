package com.example.moonboardcommunicator.utils

import android.util.Log

object LedCommunicator {



    fun lightUpLED(hold: String, color: Int){
        Log.i("Tag","Hold ${hold} lighting up with color ${color} ")
    }
}