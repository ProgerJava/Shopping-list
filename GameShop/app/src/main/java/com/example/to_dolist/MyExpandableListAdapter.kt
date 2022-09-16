package com.example.to_dolist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.to_dolist.DB.FeedReaderContract
import com.example.to_dolist.DB.FeedReaderDbHelper

class MyExpandableListAdapter(private val context: Context, private val listOfDepartments: List<String>, private val shoppingList: HashMap<String, List<String>>,
    private val sharedPreferences: SharedPreferences, private val addActivity: AddActivity
) : BaseExpandableListAdapter () {



    override fun getGroupCount(): Int {
        return listOfDepartments.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return this.shoppingList[this.listOfDepartments[groupPosition]]!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return listOfDepartments[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
         return this.shoppingList[this.listOfDepartments[groupPosition]]!![childPosition]

    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()

    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()

    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        var listOfDepartmentsTitle = getGroup(groupPosition) as String

        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var idTextColor = sharedPreferences.getInt("TextColor", R.color.black)
            convertView = layoutInflater.inflate(addActivity.getColorGroup(idTextColor), null)
        }
        val parentTv = convertView!!.findViewById<TextView>(R.id.parent)
        parentTv!!.text = listOfDepartmentsTitle
        return convertView
    }

    @SuppressLint("ResourceAsColor")
    override fun getChildView(
        groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        var listOfShoppingTitle = getChild(groupPosition, childPosition) as String

        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var idTextColor = sharedPreferences.getInt("TextColor", R.color.black)
            convertView = layoutInflater.inflate(addActivity.getColorChild(idTextColor), null)
        }
        var checkBox : CheckBox? = convertView?.findViewById<CheckBox>(R.id.checkBoxBold)
        checkBox?.setOnClickListener() {
            if (checkBox.isChecked) {
                addToDB(listOfShoppingTitle)
            }
        }
        val parentTv = convertView?.findViewById<TextView>(R.id.child)
        parentTv?.text = listOfShoppingTitle

        return convertView
    }

    private fun addToDB(product : String) {
        val dbHelper = FeedReaderDbHelper(context)
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, product)
        }
        val newRowId = db?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}