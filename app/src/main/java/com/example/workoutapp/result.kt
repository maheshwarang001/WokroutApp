package com.example.workoutapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.android.synthetic.main.activity_result.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class result : AppCompatActivity() {
    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        playVictorySound()
        Handler(Looper.getMainLooper()).postDelayed({
            getTranfer()
        }, 20000)

        end_button.setOnClickListener {
            getTranfer()
        }
        addTimeToDB()
    }

    private fun addTimeToDB() {

        try {
            val calendar = Calendar.getInstance()
            val time = calendar.time
            Log.d("date", "date process to db")

            val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
            val date = sdf.format(time)

            //passing data to db
            val databaseHandler: db = db(this,null)
            databaseHandler.addDate(historyModelClass(0,date))
        }catch (e:Exception){
            e.printStackTrace()
            Log.e("dbs",e.toString())
        }
    }

    private fun getTranfer() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun playVictorySound() {
        try {
            player = MediaPlayer.create(applicationContext, R.raw.victory5)
            player!!.isLooping = false
            player!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("player", "media player error,activity result")
        }
    }
}