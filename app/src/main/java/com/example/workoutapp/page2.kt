package com.example.workoutapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_page2.*
import kotlinx.android.synthetic.main.customdialogue.*
import java.util.*

class page2 : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var resttime: CountDownTimer? = null
    private var restprogress: Int = 0
    private var resttimeduration: Int = 10

    //for next
    private var restprogress1: Int = 0
    private var resttimeduration1: Int = 0
    //mediaplayer

    private var player: MediaPlayer? = null

    //declaring TextToSpeech  null

    private var tts: TextToSpeech? = null

    private var exceriseShow: ArrayList<excercise>? = null
    private var mCurrentexcercise = -1

    //recycler view
    lateinit var exAdapter: Exerciseadapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page2)

        //setting toolbar
        setSupportActionBar(tool_bar)
        val action = supportActionBar

        action?.setDisplayHomeAsUpEnabled(true)

        //navigation bar
        tool_bar.setNavigationOnClickListener {
            customebox()
        }
        //set time in seconds
        time_tv.text = resttimeduration.toString()
        setUpRest()

        //setting excercise list
        exceriseShow = constants.defaultExcercise()

        //textspeech
        tts = TextToSpeech(this, this)

        //setting bottom number list
        setupNumberbar()
    }
    
    //setting custom dailogbox
    private fun customebox(){
        val customBox = Dialog(this)
        customBox.setContentView(R.layout.customdialogue)

        customBox.Yes.setOnClickListener {

            if (resttime != null) {
                resttime!!.cancel()
                restprogress = 0
            }
            onBackPressed()
            customBox.dismiss()
        }
        customBox.No.setOnClickListener {
            customBox.dismiss()
        }

        customBox.create()
        customBox.setCancelable(false)
        customBox.show()
    }


    private fun restprogressbar() {
        progress.max = 10
        progress.progress = restprogress

        try {
            player = MediaPlayer.create(applicationContext, R.raw.notification)
            player!!.isLooping = false
            player!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("player", e.printStackTrace().toString())
        }

        resttime = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restprogress++
                progress.progress = resttimeduration - restprogress
                time_tv.text = (resttimeduration - restprogress).toString()
                upcoming1_tv.text = exceriseShow!![mCurrentexcercise + 1].getName()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                mCurrentexcercise++

                exceriseShow!![mCurrentexcercise].setisSelected(true)
                exAdapter.notifyDataSetChanged()

                nextActivityOnFinish()
            }
        }.start()
    }


    private fun nextActivityOnFinish() {

        restview.visibility = View.GONE
        view2.visibility = View.VISIBLE
        progress_view2.progress = restprogress
        restprogress1 = 0
        resttimeduration1 = 30

        speakout(exceriseShow!![mCurrentexcercise].getName())

        resttime = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                restprogress1++
                progress_view2.progress = resttimeduration1 - restprogress1
                time_tv_view2.text = (resttimeduration1 - restprogress1).toString()


                setuplist()

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                if (mCurrentexcercise < exceriseShow!!.size - 1) {
                    setUpVoid()
                } else {
                    //going to result page
                    val intent = Intent(this@page2,result::class.java)
                    startActivity(intent)
                }
                //selected view set false
                exceriseShow!![mCurrentexcercise].setisSelected(false)
                exceriseShow!![mCurrentexcercise].setisCompleted(true)
                exAdapter.notifyDataSetChanged().also { Log.d("dpts","notified") }

            }
        }.start()
    }


    private fun setUpRest() {
        if (resttime != null) {
            resttime!!.cancel()
            restprogress = 0
        }
        restprogressbar()
    }

    private fun setUpVoid() {

        // to avoid delay
        restview.visibility = View.VISIBLE
        view2.visibility = View.GONE

        if (resttime != null) {
            resttime!!.cancel()
            restprogress = 0
        }
        restprogressbar()
    }

    //adding exercise list to the view

    private fun setuplist() {

        image_tv.setImageResource(exceriseShow!![mCurrentexcercise].getImage())
        text_tv.text = exceriseShow!![mCurrentexcercise].getName()


        if (mCurrentexcercise == exceriseShow!!.size - 1) {
            upcoming_tv.visibility = View.GONE
            notify.text = getString(R.string.amlost_done)
        } else {
            upcoming_tv.text = exceriseShow!![mCurrentexcercise + 1].getName()
        }

    }

    private fun speakout(s: String) {
        tts!!.speak(s, TextToSpeech.QUEUE_FLUSH, null, " ")
        tts!!.setSpeechRate(0.5F)
    }


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.UK)

            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                Log.e("tts", "Language not supported")
            }

        } else {
            Log.e("tts", "Initialization failed")
        }
    }


    override fun onDestroy() {
        if (resttime != null) {
            resttime!!.cancel()
            restprogress = 0
        }

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    //adapter
    private fun setupNumberbar() {
        recyler_view.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        exAdapter = Exerciseadapter(exceriseShow!!, this)
        recyler_view.adapter = exAdapter
    }

}