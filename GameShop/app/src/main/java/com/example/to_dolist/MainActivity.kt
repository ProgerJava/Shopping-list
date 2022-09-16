package com.example.to_dolist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.provider.BaseColumns
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.example.to_dolist.DB.FeedReaderContract
import com.example.to_dolist.DB.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE
import com.example.to_dolist.DB.FeedReaderContract.FeedEntry.TABLE_NAME
import com.example.to_dolist.DB.FeedReaderDbHelper
import kotlin.math.abs


class MainActivity() : AppCompatActivity() {
    var backPressedTime : Long = 0
    lateinit var lSwipeDetector : GestureDetectorCompat
    lateinit var add : ImageButton
    lateinit var setting : ImageButton
    var dbHelper : FeedReaderDbHelper? = null
    lateinit var listWithProducts : ArrayList <String>
    lateinit var listView : ListView
    var db: SQLiteDatabase? = null
    var projection: Array<String>? = null
    var array: ArrayAdapter<String>? = null
    lateinit var sharedPreferences : SharedPreferences
    lateinit var backgroundMain : ImageView
    lateinit var nameApp : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(0, 0)
        add = findViewById(R.id.add)
        setting = findViewById(R.id.setting)
        listView = findViewById(R.id.listView)
        backgroundMain = findViewById(R.id.backgroundMainAct)
        sharedPreferences = getSharedPreferences("SaveBackground", Context.MODE_PRIVATE)
        listWithProducts = ArrayList()
        nameApp = findViewById(R.id.nameAct)

        //Инициализация базы данных:
        dbHelper = FeedReaderDbHelper(this)
        db = dbHelper!!.readableDatabase

        projection = arrayOf(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE)
        //Чтение из базы данных:
        readDB(db, projection!!)
        //Обработка свайпа:
        lSwipeDetector = GestureDetectorCompat(this, MyGestureListener())
        //Клик по кнопке "Добавить новый список":
        add.setOnClickListener() {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        //Клик по кнопке "Настройки":
        setting.setOnClickListener() {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            var a = listWithProducts[position]
            array!!.remove(a);
            //Удаление из базы данных:
            db = dbHelper!!.writableDatabase;
            val selection = "$COLUMN_NAME_TITLE LIKE ?"
            val selectionArgs = arrayOf(a)
            val delCount: Int = db!!.delete(
                TABLE_NAME, selection, selectionArgs)
        }


    }

    override fun onResume() {
        super.onResume()
        var id = sharedPreferences.getInt("Background", R.drawable.background1)
        backgroundMain.setBackgroundResource(id)
        var idTextColor = sharedPreferences.getInt("TextColor", R.color.black)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nameApp.setTextColor(getColor(idTextColor))
        }
        var idText = 0
        var idLayoutText = 0
        when (idTextColor) {
            R.color.boxFace -> {
                idText = R.id.sdk1
                idLayoutText = R.layout.activity_list_text_style1
            }
            R.color.nameApp -> {
                idText = R.id.sdk2
                idLayoutText = R.layout.activity_list_text_style2
            }
            R.color.purple_200 -> {
                idText = R.id.sdk3
                idLayoutText = R.layout.activity_list_text_style3
            }
            R.color.black -> {
                idText = R.id.sdk4
                idLayoutText = R.layout.activity_list_text_style4
            }
            R.color.textColor1 -> {
                idText = R.id.sdk5
                idLayoutText = R.layout.activity_list_text_style5
            }
            R.color.textColor2 -> {
                idText = R.id.sdk6
                idLayoutText = R.layout.activity_list_text_style6
            }
            R.color.textColor3 -> {
                idText = R.id.sdk7
                idLayoutText = R.layout.activity_list_text_style7
            }
            R.color.textColor4 -> {
                idText = R.id.sdk8
                idLayoutText = R.layout.activity_list_text_style8
            }
            R.color.white -> {
                idText = R.id.sdk9
                idLayoutText = R.layout.activity_list_text_style9
            }
            R.color.textColor5 -> {
                idText = R.id.sdk10
                idLayoutText = R.layout.activity_list_text_style10
            }
        }
        readDB(db, projection!!)
        array = ArrayAdapter(this, idLayoutText, idText, listWithProducts)
        listView.adapter = array
    }

    private fun readDB(db: SQLiteDatabase?, projection: Array<String>) {
        val cursor = db?.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        with(cursor) {
            while (this?.moveToNext() == true) {
                val nameProduct = cursor!!.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE))
                if (!listWithProducts.contains(nameProduct)) {
                    listWithProducts.add(nameProduct)
                }
            }
        }
        cursor?.close()
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(this, "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(enterAnim, exitAnim)
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (lSwipeDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }
    fun startNext () {
        val intent = Intent (this, AddActivity::class.java)
        startActivity(intent)
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

                    } else {
                        this@MainActivity.startNext()
                    }
                }
            }
            return true
        }

    }

}

