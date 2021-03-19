package com.example.tictactoeapp
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_third_page.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess
import kotlinx.android.synthetic.main.activity_code.*
var isMyMove = isCodeMaker;

class ThirdPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_page)
        button110.setOnClickListener {
            reset()
        }
        FirebaseDatabase.getInstance().reference.child("data").child(code).addChildEventListener(object : ChildEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var data = snapshot.value
                if(isMyMove==true){
                    isMyMove = false
                    moveonline(data.toString() , isMyMove)
                }
                else{
                    isMyMove = true
                    moveonline(data.toString() , isMyMove)
                }



            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                reset()
                errorMsg("Game Reset")
            }

        })
    }

    var player1Count = 0
    var player2Count = 0
    fun clickfun(view:View)
    {
        if(isMyMove) {
            val but = view as Button
            var cellOnline = 0
            when (but.id) {
                R.id.button11 -> cellOnline = 1
                R.id.button12 -> cellOnline = 2
                R.id.button13 -> cellOnline = 3
                R.id.button14 -> cellOnline = 4
                R.id.button15 -> cellOnline = 5
                R.id.button16 -> cellOnline = 6
                R.id.button17 -> cellOnline = 7
                R.id.button18 -> cellOnline = 8
                R.id.button19 -> cellOnline = 9
                else -> {cellOnline=0}

            }
            playerTurn = false;
            Handler().postDelayed(Runnable { playerTurn = true } , 600)
            playnow(but, cellOnline)
            updateDatabase(cellOnline);

        }
        else{
            Toast.makeText(this , "Wait for your turn" , Toast.LENGTH_LONG).show()
        }
    }
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1
    fun playnow(buttonSelected:Button , currCell:Int)
    {   val audio = MediaPlayer.create(this , R.raw.poutch)

            buttonSelected.text = "X"
            emptyCells.remove(currCell)
            textView3.text = "Turn : Player 2"
            buttonSelected.setTextColor(Color.parseColor("#EC0C0C"))
            player1.add(currCell)
            emptyCells.add(currCell)
            audio.start()
            //Handler().postDelayed(Runnable { audio.pause() } , 500)
            buttonSelected.isEnabled = false
            Handler().postDelayed(Runnable { audio.release() } , 200)
            checkwinner()
            /*val checkWinner = checkwinner()
            if(checkWinner == 1) {
                Handler().postDelayed(Runnable { reset() }, 2000)
            }*/

        /*else
        {
            buttonSelected.text = "O"
            audio.start()
            buttonSelected.setTextColor(Color.parseColor("#D22BB804"))
            //Handler().postDelayed(Runnable { audio.pause() } , 500)
            activeUser = 1
            player2.add(currCell)
            emptyCells.add(currCell)
            Handler().postDelayed(Runnable { audio.release() } , 200)
            buttonSelected.isEnabled = false
            val checkWinner  = checkwinner()
            if(checkWinner == 1)
                Handler().postDelayed(Runnable { reset() } , 4000)
        }*/

    }

    fun moveonline(data : String , move : Boolean){
        val audio = MediaPlayer.create(this , R.raw.poutch)

        if(move) {
            var buttonselected: Button?
            buttonselected = when (data.toInt()) {
                1 -> button11
                2 -> button12
                3 -> button13
                4 -> button14
                5 -> button15
                6 -> button16
                7 -> button17
                8 -> button18
                9 -> button19
                else -> {
                    button11
                }
            }
            buttonselected.text = "O"
            textView3.text = "Turn : Player 1"
            buttonselected.setTextColor(Color.parseColor("#D22BB804"))
            player2.add(data.toInt())
            emptyCells.add(data.toInt())
            audio.start()
            //Handler().postDelayed(Runnable { audio.pause() } , 500)
            Handler().postDelayed(Runnable { audio.release() } , 200)
            buttonselected.isEnabled = false
            checkwinner()
        }
    }

    fun updateDatabase(cellId : Int)
    {
        FirebaseDatabase.getInstance().reference.child("data").child(code).push().setValue(cellId);
    }

    fun checkwinner():Int
    {
        val audio = MediaPlayer.create(this , R.raw.success)
        if((player1.contains(1) && player1.contains(2) && player1.contains(3) ) || (player1.contains(1) && player1.contains(4) && player1.contains(7))||
            (player1.contains(3) && player1.contains(6) && player1.contains(9)) || (player1.contains(7) && player1.contains(8) && player1.contains(9))||
            (player1.contains(4)&&player1.contains(5)&&player1.contains(6)) || (player1.contains(1)&&player1.contains(5) && player1.contains(9))||
            player1.contains(3)&&player1.contains(5)&&player1.contains(7) || (player1.contains(2)&&player1.contains(5) && player1.contains(8))) {
            player1Count+=1
            buttonDisable()
            audio.start()
            disableReset()
            Handler().postDelayed(Runnable { audio.release() } , 4000)
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 1 Wins!!" + "\n\n" + "Do you want to play again")
            build.setPositiveButton("Ok") { dialog, which ->
                reset()
                audio.release()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                audio.release()
                removeCode()
                exitProcess(1)

            }
            Handler().postDelayed(Runnable { build.show() } , 2000)
            return 1


        }
        else if((player2.contains(1) && player2.contains(2) && player2.contains(3) ) || (player2.contains(1) && player2.contains(4) && player2.contains(7))||
            (player2.contains(3) && player2.contains(6) && player2.contains(9)) || (player2.contains(7) && player2.contains(8) && player2.contains(9))||
            (player2.contains(4)&&player2.contains(5)&&player2.contains(6)) || (player2.contains(1)&&player2.contains(5) && player2.contains(9))||
            player2.contains(3)&&player2.contains(5)&&player2.contains(7) || (player2.contains(2)&&player2.contains(5) && player2.contains(8))){
            player2Count+=1
            audio.start()
            buttonDisable()
            disableReset()
            Handler().postDelayed(Runnable { audio.release() } , 4000)
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 2 Wins!!" + "\n\n" + "Do you want to play again")
            build.setPositiveButton("Ok"){dialog, which ->
                reset()
                audio.release()
            }
            build.setNegativeButton("Exit"){dialog, which ->
                audio.release()
                removeCode()
                exitProcess(1)
            }
            Handler().postDelayed(Runnable { build.show() } , 2000)
            return 1
        }
        else if(emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) && emptyCells.contains(4) && emptyCells.contains(5) && emptyCells.contains(6) && emptyCells.contains(7) &&
            emptyCells.contains(8) && emptyCells.contains(9) ) {

            val build = AlertDialog.Builder(this)
            build.setTitle("Game Draw")
            build.setMessage("Nobody Wins" + "\n\n" + "Do you want to play again")
            build.setPositiveButton("Ok"){dialog, which ->
                audio.release()
                reset()
            }
            build.setNegativeButton("Exit"){dialog, which ->
                audio.release()
                exitProcess(1)
                removeCode()
            }
            build.show()
            return 1

        }
        return 0
    }

    fun reset()
    {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1;
        for(i in 1..9)
        {
            var buttonselected : Button?
            buttonselected = when(i){
                1 -> button11
                2 -> button12
                3 -> button13
                4 -> button14
                5 -> button15
                6 -> button16
                7 -> button17
                8 -> button18
                9 -> button19
                else -> {button11}
            }
            buttonselected.isEnabled = true
            buttonselected.text = ""
            textView.text = "Player1 : $player1Count"
            textView2.text = "Player2 : $player2Count"
            isMyMove = isCodeMaker
            //startActivity(Intent(this,ThirdPage::class.java))
            if(isCodeMaker){
                FirebaseDatabase.getInstance().reference.child("data").child(code).removeValue()
            }


        }
    }

    fun buttonDisable()
    {
        for(i in 1..9)
        {
            val buttonSelected = when(i)
            {
                1 -> button11
                2 -> button12
                3 -> button13
                4 -> button14
                5 -> button15
                6 -> button16
                7 -> button17
                8 -> button18
                9 -> button19
                else -> {button11}

            }
            if(buttonSelected.isEnabled == true)
                buttonSelected.isEnabled = false
        }
    }
    fun buttoncelldisable(){
        emptyCells.forEach{
            val buttonSelected = when(it)
            {
                1 -> button11
                2 -> button12
                3 -> button13
                4 -> button14
                5 -> button15
                6 -> button16
                7 -> button17
                8 -> button18
                9 -> button19
                else -> {button11}

            }
            if(buttonSelected.isEnabled == true)
                    buttonSelected.isEnabled = false;
        }
    }

    fun removeCode(){
        if(isCodeMaker){
            FirebaseDatabase.getInstance().reference.child("codes").child(keyValue).removeValue()
        }
    }

    fun errorMsg(value : String){
        Toast.makeText(this , value  , Toast.LENGTH_SHORT).show()
    }
    fun disableReset()
    {
        button110.isEnabled = false
        Handler().postDelayed(Runnable { button110.isEnabled = true } , 2200)
    }

    override fun onBackPressed() {
        removeCode()
        if(isCodeMaker){
            FirebaseDatabase.getInstance().reference.child("data").child(code).removeValue()
        }
        exitProcess(0)
    }
}
