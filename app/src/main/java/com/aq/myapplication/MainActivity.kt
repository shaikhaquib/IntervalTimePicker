package com.aq.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aq.myapplication.DateDialog.DateDialogListener

class MainActivity : AppCompatActivity(), DateDialogListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab = findViewById<View>(R.id.HW) as Button
        fab.setOnClickListener {
            val dateDialog = DateDialog(this@MainActivity)
            dateDialog.setDateDialogListener(this@MainActivity)
            dateDialog.show()
        }
    }

    override fun OnDateValidate(hour: String,minutes: String) {
        Toast.makeText(this, "$hour : $minutes", Toast.LENGTH_SHORT).show()
    }
}