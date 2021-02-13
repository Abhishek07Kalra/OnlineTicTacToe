package com.example.tictactoeapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_code.*

var isCodeMaker = true;
var code = "null";
class CodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)
        Create.setOnClickListener{
            code = GameCode.text.toString()
            if(code != "null" ) {

                isCodeMaker = true;
                startActivity(Intent(this, ThirdPage::class.java));
            }
            else
            {
                Toast.makeText(this , "Enter Code Properly" , Toast.LENGTH_SHORT).show();
            }
        }
        Join.setOnClickListener{
            code = GameCode.text.toString()
            if(code != "null" ) {
                isCodeMaker = false;
                startActivity(Intent(this, ThirdPage::class.java));
            }
            else
            {
                Toast.makeText(this , "Enter Code Properly" , Toast.LENGTH_SHORT).show();
            }
        }

    }
}