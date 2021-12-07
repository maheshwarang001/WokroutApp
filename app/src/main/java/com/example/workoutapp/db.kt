package com.example.workoutapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class db(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_V) {

    companion object {
        private const val DATABASE_NAME = "workout.db"
        private const val DATABASE_V = 2
        private const val TABLE_DB = "history"
        private const val KEY_ID = "_id"
        private const val COMPLETE_DATE = "completeDate"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = ("CREATE TABLE " + TABLE_DB + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, " + COMPLETE_DATE + " TEXT" + ")")
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_DB)
        onCreate(db)
    }

    fun addDate(emp: historyModelClass):Long {
        val db = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(COMPLETE_DATE, emp.date)
        val sucess = db.insert(TABLE_DB, null, contentValue)
        db.close()

        return sucess
    }

    @SuppressLint("Recycle", "Range")
    fun viewData(): ArrayList<historyModelClass> {
        val dbList: ArrayList<historyModelClass> = ArrayList()

        val select = "SELECT * FROM $TABLE_DB"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(select, null)
        } catch (e: SQLException) {
            db.execSQL(select)
            return ArrayList()
        }

        var id: Int
        var text: String
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                text = cursor.getString(cursor.getColumnIndex(COMPLETE_DATE))

                val emp = historyModelClass(id, text)
                dbList.add(emp)
            } while (cursor.moveToNext())
        }
        return dbList
    }
    fun delete (dbList: historyModelClass):Int{
        val db = this.writableDatabase
        val content = ContentValues()
        content.put(KEY_ID,dbList.id)
        val success = db.delete(TABLE_DB, KEY_ID + "=" + dbList.id , null)
        db.close()
        return success
    }
}