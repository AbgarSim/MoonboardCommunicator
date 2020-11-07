package com.example.moonboardcommunicator.utils


val woodenHoldsSet : ArrayList<String> = arrayListOf(
    "A7",
    "A10",
    "B5",
    "B12",
    "B15",
    "C6",
    "C9",
    "C11",
    "C18",
    "D7",
    "D13",
    "D16",
    "E5",
    "E9",
    "E12",
    "E15",
    "G5",
    "G9",
    "G12",
    "G15",
    "H7",
    "H13",
    "H16",
    "I6",
    "I9",
    "I11",
    "I18",
    "J5",
    "J12",
    "J15",
    "K7",
    "K10"
)

fun isHoldWooden(holdRC : String) : Boolean{
    return woodenHoldsSet.contains(holdRC)
}