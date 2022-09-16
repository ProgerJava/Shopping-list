package com.example.to_dolist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.TintableBackgroundView
import kotlin.math.abs

class Setting : AppCompatActivity() {
    var backPressedTime : Long = 0
    lateinit var lSwipeDetector : GestureDetectorCompat
    lateinit var home : ImageButton
    lateinit var add : ImageButton
    //Работа с цветом фона активити:
    lateinit var backgroundMain : ImageView
    lateinit var background1 : ImageView
    lateinit var background2 : ImageView
    lateinit var background3 : ImageView
    lateinit var background4 : ImageView
    lateinit var background5 : ImageView
    lateinit var background6 : ImageView
    lateinit var background7 : ImageView
    lateinit var sharedPreferences : SharedPreferences
    lateinit var sharedPreferencesEditor : SharedPreferences.Editor
    //Работа с цветом текста:
    lateinit var textColor1 : TextView
    lateinit var textColor2 : TextView
    lateinit var textColor3 : TextView
    lateinit var textColor4 : TextView
    lateinit var textColor5 : TextView
    lateinit var textColor6 : TextView
    lateinit var textColor7 : TextView
    lateinit var textColor8 : TextView
    lateinit var textColor9 : TextView
    lateinit var textColor10 : TextView
    //Работа с текстовыми полями активити:
    lateinit var nameActivity : TextView
    lateinit var titleBackground : TextView
    lateinit var titleTextColor : TextView


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        overridePendingTransition(0, 0)
        home = findViewById(R.id.home)
        add = findViewById(R.id.add)
        sharedPreferences = getSharedPreferences("SaveBackground", Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
        //Инициализация полей по смене цвета активити
        backgroundMain = findViewById(R.id.backgroundSetting)
        background1 = findViewById(R.id.background1)
        background2 = findViewById(R.id.background2)
        background3 = findViewById(R.id.background3)
        background4 = findViewById(R.id.background4)
        background5 = findViewById(R.id.background5)
        background6 = findViewById(R.id.background6)
        background7 = findViewById(R.id.background7)
        //Инициализация полей по смене цвета текста
        textColor1 = findViewById(R.id.textColor1)
        textColor2 = findViewById(R.id.textColor2)
        textColor3 = findViewById(R.id.textColor3)
        textColor4 = findViewById(R.id.textColor4)
        textColor5 = findViewById(R.id.textColor5)
        textColor6 = findViewById(R.id.textColor6)
        textColor7 = findViewById(R.id.textColor7)
        textColor8 = findViewById(R.id.textColor8)
        textColor9 = findViewById(R.id.textColor9)
        textColor10 = findViewById(R.id.textColor10)
        //Инициализация текстовых полей активити:
        nameActivity = findViewById(R.id.nameActivity)
        titleBackground = findViewById(R.id.titleBackground)
        titleTextColor = findViewById(R.id.titleTextColor)


        lSwipeDetector = GestureDetectorCompat(this, MyGestureListener())

        home.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        add.setOnClickListener() {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        background1.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.background1)
            sharedPreferencesEditor.putInt("Background", R.drawable.background1).apply()
        }
        background2.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.background2)
            sharedPreferencesEditor.putInt("Background", R.drawable.background2).apply()
        }
        background3.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.background3)
            sharedPreferencesEditor.putInt("Background", R.drawable.background3).apply()
        }
        background4.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.background4)
            sharedPreferencesEditor.putInt("Background", R.drawable.background4).apply()
        }
        background5.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.background5)
            sharedPreferencesEditor.putInt("Background", R.drawable.background5).apply()
        }
        background6.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.background6)
            sharedPreferencesEditor.putInt("Background", R.drawable.background6).apply()
        }
        background7.setOnClickListener() {
            backgroundMain.setBackgroundResource(R.drawable.background7)
            sharedPreferencesEditor.putInt("Background", R.drawable.background7).apply()
        }
        textColor1.setOnClickListener() {
            setColorText(R.color.boxFace)
        }
        textColor2.setOnClickListener() {
            setColorText(R.color.nameApp)
        }
        textColor3.setOnClickListener() {
            setColorText(R.color.purple_200)
        }
        textColor4.setOnClickListener() {
            setColorText(R.color.black)
        }
        textColor5.setOnClickListener() {
            setColorText(R.color.textColor1)
        }
        textColor6.setOnClickListener() {
            setColorText(R.color.textColor2)
        }
        textColor7.setOnClickListener() {
            setColorText(R.color.textColor3)
        }
        textColor8.setOnClickListener() {
            setColorText(R.color.textColor4)
        }
        textColor9.setOnClickListener() {
            setColorText(R.color.white)
        }
        textColor10.setOnClickListener() {
            setColorText(R.color.textColor5)
        }
    }
    fun setColorText(idColor :Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sharedPreferencesEditor.putInt("TextColor", idColor).apply()
            nameActivity.setTextColor(getColor(idColor))
            titleBackground.setTextColor(getColor(idColor))
            titleTextColor.setTextColor(getColor(idColor))
        }
    }
    override fun onResume() {
        super.onResume()
        var id = sharedPreferences.getInt("Background", R.drawable.background1)
        backgroundMain.setBackgroundResource(id)
        var idTextColor = sharedPreferences.getInt("TextColor", R.color.black)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nameActivity.setTextColor(getColor(idTextColor))
            titleBackground.setTextColor(getColor(idTextColor))
            titleTextColor.setTextColor(getColor(idTextColor))
        }
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (lSwipeDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }
    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(enterAnim, exitAnim)
    }
    fun startAdd () {
        val intent = Intent (this, AddActivity::class.java)
        startActivity(intent)
    }
    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            return
        } else {
            Toast.makeText(this, "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
    inner class MyGestureListener () : GestureDetector.SimpleOnGestureListener() {
        private val  SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            downEvent: MotionEvent?,
            moveEvent: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var diffX = moveEvent?.x?.minus(downEvent!!.x) ?: 0.0F
            var diffY = moveEvent?.y?.minus(downEvent!!.y) ?: 0.0F

            if (abs(diffX) > abs(diffY)) {

                if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0 ) {
                        this@Setting.startAdd()
                    } else {
                    }
                }
            }
            return true
        }

    }
}