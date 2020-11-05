package com.example.moonboardcommunicator.utils

object DataParser {


    fun convertHoldTypeToColor(isStart: Boolean, isEnd: Boolean) : String{
        return when {
            isStart -> "0,255,0"
            isEnd -> "255,0,0"
            else -> return "0,0,255"
        }
    }
}