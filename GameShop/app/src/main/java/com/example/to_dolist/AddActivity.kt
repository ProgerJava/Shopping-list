package com.example.to_dolist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.*
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.ExpandableListView.OnGroupClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.NestedScrollingChild
import androidx.core.view.get
import androidx.core.view.size
import kotlin.math.abs


class AddActivity : AppCompatActivity() {

    private lateinit var listOfDepartments : List <String>
    private lateinit var shoppingList : HashMap <String, List<String>>
    private lateinit var listView: MyExpandableListAdapter
    lateinit var expandableListAdapter : ExpandableListView
    var backPressedTime : Long = 0
    var gDetector: GestureDetectorCompat? = null
    lateinit var home : ImageButton
    lateinit var setting : ImageButton
    lateinit var sharedPreferences : SharedPreferences
    lateinit var backgroundMain : ImageView
    ////Работа с текстовыми полями активити:
    lateinit var nameActivity : TextView
    lateinit var relativeLayout: RelativeLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        overridePendingTransition(0, 0)
        this.gDetector = GestureDetectorCompat(this, SwipeListener())
        home = findViewById(R.id.home)
        setting = findViewById(R.id.setting)
        expandableListAdapter = findViewById(R.id.expListView)
        //Инициализация фона активити:
        backgroundMain = findViewById(R.id.backgroundAdd)
        sharedPreferences = getSharedPreferences("SaveBackground", Context.MODE_PRIVATE)
        //Инициализация текстовых полей активити:
        nameActivity = findViewById(R.id.nameActivity)
        relativeLayout = findViewById(R.id.relative)


        showList()

        listView = MyExpandableListAdapter (this, listOfDepartments, shoppingList, sharedPreferences, AddActivity())
        expandableListAdapter.setAdapter(listView)

        home.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        setting.setOnClickListener() {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }
        expandableListAdapter.setOnGroupClickListener(OnGroupClickListener { parent, v, groupPosition, id ->
            false
        })
        expandableListAdapter.setOnChildClickListener(OnChildClickListener { parent, v, groupPosition, childPosition, id ->
            true
        })
    }
    private fun showList () {
        expandableListAdapter.setIndicatorBoundsRelative(100, 300)
        listOfDepartments = ArrayList ()
        shoppingList = HashMap()

        (listOfDepartments as ArrayList <String>).add("Овощи")
        (listOfDepartments as ArrayList <String>).add("Фрукты")
        (listOfDepartments as ArrayList <String>).add("Орехи")
        (listOfDepartments as ArrayList <String>).add("Молочные продукты")
        (listOfDepartments as ArrayList <String>).add("Мясо")
        (listOfDepartments as ArrayList <String>).add("Морепродукты, рыба")
        (listOfDepartments as ArrayList <String>).add("Выпечка")
        (listOfDepartments as ArrayList <String>).add("Крупы")
        (listOfDepartments as ArrayList <String>).add("Напитки")


        val shopping1 : MutableList <String> = ArrayList()
        shopping1.add("Картофель")
        shopping1.add("Огурцы")
        shopping1.add("Помидоры")
        shopping1.add("Лук")
        shopping1.add("Морковь")
        shopping1.add("Капуста")

        val shopping2 : MutableList <String> = ArrayList()
        shopping2.add("Яблоки")
        shopping2.add("Бананы")
        shopping2.add("Апельсины")
        shopping2.add("Груши")
        shopping2.add("Ананасы")
        shopping2.add("Помелло")

        val shopping3 : MutableList <String> = ArrayList()
        shopping3.add("Грецкий")
        shopping3.add("Миндаль")
        shopping3.add("Кешью")
        shopping3.add("Фисташки")
        shopping3.add("Арахис")
        shopping3.add("Кедровые орехи")

        val shopping4 : MutableList <String> = ArrayList()
        shopping4.add("Кефир")
        shopping4.add("Сметана")
        shopping4.add("Ряженка")
        shopping4.add("Сыр")
        shopping4.add("Простокваша")
        shopping4.add("Сливки")

        val shopping5 : MutableList <String> = ArrayList()
        shopping5.add("Курица")
        shopping5.add("Свинина")
        shopping5.add("Телятина")
        shopping5.add("Баранина")
        shopping5.add("Индейка")
        shopping5.add("Кролик")

        val shopping6 : MutableList <String> = ArrayList()
        shopping6.add("Икра")
        shopping6.add("Кальмар")
        shopping6.add("Морской еж")
        shopping6.add("Рыба")
        shopping6.add("Крабовые палочки")
        shopping6.add("Креветки")

        val shopping7 : MutableList <String> = ArrayList()
        shopping7.add("Хлеб черный")
        shopping7.add("Батон")
        shopping7.add("Лепешка")
        shopping7.add("Бублики")
        shopping7.add("Багет")
        shopping7.add("Пряники")

        val shopping8 : MutableList <String> = ArrayList()
        shopping8.add("Гречневая крупа")
        shopping8.add("Ячменная крупа")
        shopping8.add("Манная крупа")
        shopping8.add("Булгур")
        shopping8.add("Рис")
        shopping8.add("Овсяная крупа")

        val shopping9 : MutableList <String> = ArrayList()
        shopping9.add("Вода")
        shopping9.add("Сок")
        shopping9.add("Лимонад")
        shopping9.add("Пиво")
        shopping9.add("Сироп")
        shopping9.add("Чай")

        shoppingList [listOfDepartments[0]] = shopping1
        shoppingList [listOfDepartments[1]] = shopping2
        shoppingList [listOfDepartments[2]] = shopping3
        shoppingList [listOfDepartments[3]] = shopping4
        shoppingList [listOfDepartments[4]] = shopping5
        shoppingList [listOfDepartments[5]] = shopping6
        shoppingList [listOfDepartments[6]] = shopping7
        shoppingList [listOfDepartments[7]] = shopping8
        shoppingList [listOfDepartments[8]] = shopping9

    }
    override fun onResume() {
        super.onResume()
        var id = sharedPreferences.getInt("Background", R.drawable.background1)
        backgroundMain.setBackgroundResource(id)
        var idTextColor = sharedPreferences.getInt("TextColor", R.color.black)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nameActivity.setTextColor(getColor(idTextColor))
        }
    }
    fun getColorChild (idTextColor: Int) : Int {
        var idLayoutText = 0
        when (idTextColor) {
            R.color.boxFace -> {
                idLayoutText = R.layout.child_list1
            }
            R.color.nameApp -> {
                idLayoutText = R.layout.child_list2
            }
            R.color.purple_200 -> {
                idLayoutText = R.layout.child_list3
            }
            R.color.black -> {
                idLayoutText = R.layout.child_list4
            }
            R.color.textColor1 -> {
                idLayoutText = R.layout.child_list5
            }
            R.color.textColor2 -> {
                idLayoutText = R.layout.child_list6
            }
            R.color.textColor3 -> {
                idLayoutText = R.layout.child_list7
            }
            R.color.textColor4 -> {
                idLayoutText = R.layout.child_list8
            }
            R.color.white -> {
                idLayoutText = R.layout.child_list9
            }
            R.color.textColor5 -> {
                idLayoutText = R.layout.child_list10
            }
        }
        return idLayoutText
    }
    fun getColorGroup (idTextColor: Int) : Int {
        var idLayoutText = 0
        when (idTextColor) {
            R.color.boxFace -> {
                idLayoutText = R.layout.parent_list1
            }
            R.color.nameApp -> {
                idLayoutText = R.layout.parent_list2
            }
            R.color.purple_200 -> {
                idLayoutText = R.layout.parent_list3
            }
            R.color.black -> {
                idLayoutText = R.layout.parent_list4
            }
            R.color.textColor1 -> {
                idLayoutText = R.layout.parent_list5
            }
            R.color.textColor2 -> {
                idLayoutText = R.layout.parent_list6
            }
            R.color.textColor3 -> {
                idLayoutText = R.layout.parent_list7
            }
            R.color.textColor4 -> {
                idLayoutText = R.layout.parent_list8
            }
            R.color.white -> {
                idLayoutText = R.layout.parent_list9
            }
            R.color.textColor5 -> {
                idLayoutText = R.layout.parent_list10
            }
        }
        return idLayoutText
    }

    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(enterAnim, exitAnim)
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (gDetector!!.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }
    fun startMain () {
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }
    fun startSetting () {
        val intent = Intent (this, Setting::class.java)
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
    inner class SwipeListener : GestureDetector.SimpleOnGestureListener() {
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
                        this@AddActivity.startMain()
                    } else {
                        this@AddActivity.startSetting()
                    }
                }
            }
            return true
        }

    }
}