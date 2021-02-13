package com.example.tictactoeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_firstpage.*
var singleUser = false
class Firstpage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstpage)

        button11.setOnClickListener {
            startActivity(Intent(this , MainActivity::class.java))
            singleUser = true;
        }
        button12.setOnClickListener {
            startActivity(Intent(this , SecondPage::class.java))
            singleUser = false;
        }
    }

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)
    }
}
