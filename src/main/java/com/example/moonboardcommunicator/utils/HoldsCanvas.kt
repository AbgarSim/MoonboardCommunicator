package com.example.moonboardcommunicator.utils

import android.annotation.SuppressLint
import android.graphics.*
import android.widget.ImageView
//import com.squareup.moshi.JsonReader
import com.example.moonboardcommunicator.utils.GestureParser.height
import com.example.moonboardcommunicator.utils.LedCommunicator.lightUpLED

//import java.io.InputStreamReader


@SuppressLint("StaticFieldLeak")
object HoldsCanvas {
    var canvasPaint = Paint()
    var mainCanvas = Canvas()

    lateinit var canvasView: ImageView
    var holdsData: HashMap<String, Int> = hashMapOf()


    fun addHold(hold: String, color: String, update: Boolean = true) {
        if (isHoldWooden(hold))
            return

        val c = convertColorString(color)
        if (holdsData[hold] == c) {
            removeHold(hold)
            return
        }

        holdsData[hold] = c
        if (update) updateCanvas()
    }

    fun removeHold(hold: String) {
        if (isHoldWooden(hold))
            return

        if (holdsData[hold] == Color.TRANSPARENT) return
        holdsData.remove(hold)
        updateCanvas()
    }

    fun init(screenSize: IntArray) {
        height = screenSize[0]
        val width = screenSize[1]
        val top = screenSize[2]

        canvasPaint = Paint()
        canvasPaint.isAntiAlias = true
        canvasPaint.style = Paint.Style.STROKE
        canvasPaint.strokeWidth = STROKE_MULTIPLIER * height

        val bitmap = Bitmap.createBitmap(width, height - top, Bitmap.Config.ARGB_8888)
        mainCanvas = Canvas(bitmap)
        canvasView.setImageBitmap(bitmap)
    }

    fun setCanvasImage(image: ImageView) {
        canvasView = image
    }

    fun drawCircle(x: Float, y: Float, color: Int, updating: Boolean = false) {
        canvasPaint.color = color
        mainCanvas.drawCircle(x, y, RADIUS_MULTIPLIER * height, canvasPaint)
        if (!updating) canvasView.invalidate()
    }

    fun drawHoldCircle(hold: String, color: Int, updating: Boolean = false) {
        val coords = GestureParser.convertRCtoCoord(hold)
        val x = coords[0]
        val y = coords[1]

        drawCircle(x, y, color, updating)
    }

    fun convertColorString(c: String): Int {
        return when (c) {
            NO_COLOR -> Color.TRANSPARENT
            RED_COLOR -> Color.RED
            BLUE_COLOR -> Color.BLUE
            GREEN_COLOR -> Color.GREEN
            else -> Color.BLUE
        }
    }

    fun clearCanvas() {
        clear(true)
        for ((hold) in holdsData) {
            drawHoldCircle(hold, Color.TRANSPARENT, true)
        }

        holdsData.clear()
        canvasView.invalidate()
    }

    fun updateCanvas() {
        clear(false)

        for ((hold, color) in holdsData) {
            drawHoldCircle(hold, color, true)
            lightUpLED(hold, color)
        }
        canvasView.invalidate()
    }

    fun clear(clearData: Boolean = true) {
        if (clearData) holdsData.clear()
        mainCanvas.drawColor(0, PorterDuff.Mode.CLEAR)
        canvasView.invalidate()
    }
}