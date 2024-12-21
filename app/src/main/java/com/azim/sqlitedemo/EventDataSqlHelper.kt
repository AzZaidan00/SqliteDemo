package com.azim.sqlitedemo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class EventDataSqlHelper(context: Context?) : SQLiteOpenHelper(context,
    EventDataSqlHelper.DATABASE_NAME,null,
    EventDataSqlHelper.DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "events.db"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "users"
        val COL_NAME = "name"
        val COL_EMAIL = "email"
        val COL_PHONE = "phone"
    }

    // When the DB is created, this method will be called
    // CREATE TABLE users (_id integer PRIMARY KEY AUTOINCREMENT, name text, email text, phone text
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME (${BaseColumns._ID} integer " +
                "PRIMARY KEY AUTOINCREMENT," +
                "$COL_NAME text, $COL_EMAIL text," +
                "$COL_PHONE text)"
        db?.execSQL(query)
    }

    // When the DB is upgrade, (DB Version Changed) this method will be called
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        return
    }

}