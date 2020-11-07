package com.example.moonboardcommunicator

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.GestureDetector
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.moonboardcommunicator.utils.*
import kotlinx.android.synthetic.main.activity_moon_board.*

class MoonBoardActivity : AppCompatActivity() {

    private lateinit var gestureDetector: GestureDetector

    private var uIInitialized = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moon_board)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        gestureDetector = GestureDetector(this, MoonBoardGestureListener(drawer_layout.isDrawerOpen(GravityCompat.START)))
        drawer_layout.setOnTouchListener { v, event ->
            v.performClick()
            gestureDetector.onTouchEvent(event)
        }


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, 0, 0
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        HoldsCanvas.setCanvasImage(canvasImage)


        //Set content dimensions after content layout has loaded
        content_layout.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            initializeUI()
        }

        HoldsCanvas.updateCanvas()

        btn_blue_led.setOnClickListener {
            setLedColor(BLUE_COLOR)
        }

        btn_green_led.setOnClickListener {
            setLedColor(GREEN_COLOR)
        }

        btn_red_led.setOnClickListener {
            setLedColor(RED_COLOR)
        }

        btn_clear_led.setOnClickListener {
            HoldsCanvas.clearCanvas()
        }

    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            val contentCoordinates = IntArray(2)
            mainImage.getLocationOnScreen(contentCoordinates)
            GestureParser.height = mainImage.height + contentCoordinates[1]
            println("new height: ${mainImage.height + contentCoordinates[1]}")
        }
    }

    private fun initializeUI() {
        if (uIInitialized) return
            uIInitialized = true

        val contentCoordinates = IntArray(2)
        mainImage.getLocationOnScreen(contentCoordinates)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        GestureParser.setScreenDimensions(
            displayMetrics.heightPixels,
            displayMetrics.widthPixels,
            contentCoordinates[1]
        )

        println("screen. height: ${displayMetrics.heightPixels}, width: ${displayMetrics.widthPixels}")

        HoldsCanvas.init(GestureParser.getScreenDimensions())
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}