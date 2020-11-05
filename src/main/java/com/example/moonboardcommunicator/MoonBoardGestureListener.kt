package com.example.moonboardcommunicator

import android.view.GestureDetector
import android.view.MotionEvent
import com.example.moonboardcommunicator.utils.BLUE_COLOR
import com.example.moonboardcommunicator.utils.GestureParser
import com.example.moonboardcommunicator.utils.HoldsCanvas
import com.example.moonboardcommunicator.utils.getLedColor

open class MoonBoardGestureListener(isDrawerOpen: Boolean) : GestureDetector.SimpleOnGestureListener() {

    private val drawerOpen = isDrawerOpen

    override fun onSingleTapUp(e: MotionEvent?): Boolean {

        val rc = GestureParser.onDown(e?.rawX, e?.rawY)

        if (rc == "") return super.onSingleTapUp(e)

        if (drawerOpen) return super.onSingleTapUp(e)

        HoldsCanvas.addHold(rc, getLedColor())

        return super.onSingleTapUp(e)
    }

}
