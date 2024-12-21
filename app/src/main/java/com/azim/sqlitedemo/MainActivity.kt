package com.azim.sqlitedemo

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.azim.sqlitedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var mUserData: EventDataSqlHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the Database Helper
        mUserData = EventDataSqlHelper(this )

        // binding.retrieveDataBtn.isEnabled = false

        binding.saveBtn.setOnClickListener {
            enterData(
                binding.nameEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.phoneEditText.text.toString(),)
            binding.retrieveDataBtn.isEnabled = true
        }

        binding.retrieveDataBtn.setOnClickListener {
            val cursor = getEvents()
            if (cursor != null) {
                binding.addressTextView.text = showData(cursor)
            } else {
                binding.addressTextView.text = "No Data found"
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mUserData?.close()
    }

    private fun enterData(name:String,email:String,phone:String) {
        // get the database on Mode Write
        val db = mUserData?.writableDatabase
        val values = ContentValues()
        values.put(EventDataSqlHelper.COL_NAME, name)
        values.put(EventDataSqlHelper.COL_EMAIL, email)
        values.put(EventDataSqlHelper.COL_PHONE, phone)
        db?.insert(EventDataSqlHelper.TABLE_NAME,null,values)
    }

    private fun getEvents():Cursor? {
        val db = mUserData?.readableDatabase
        // SELECT * FROM users
        // columns = which column you want SELECT name, email, phone
        // selection = WHERE id
        // selectionArgs = 1
        val cursor = db?.query(EventDataSqlHelper.TABLE_NAME,
            null,null,null,
            null,null,null)

        return cursor
    }

    private fun showData(cursor: Cursor?):String {
        val ret = StringBuilder("")
        // cursor - hasil query dari getEvents()
        // selagi ada data,kita akan dapatkan nama, email, phone

        while(cursor!!.moveToNext()) {
            val id = cursor.getLong(0)
            val name = cursor.getString(1)
            val email = cursor.getString(2)
            val phone = cursor.getString(3)
            ret.append("ID: $id, Name: $name, Email: $email, Phone: $phone")
            ret.append("\n")
        }
        return ret.toString()
    }
}