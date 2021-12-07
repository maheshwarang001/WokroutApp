package com.example.workoutapp

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentViewHolder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.fragment1_metric.*

class bmi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setSupportActionBar(tool_bar_bmi)
        val action = supportActionBar
        action?.setDisplayHomeAsUpEnabled(true)
        tool_bar_bmi.setNavigationOnClickListener {
            onBackPressed()
        }

        //setting fragment
        //default fragment metric

        supportFragmentManager.beginTransaction().apply {
          replaceaFragment(metric())
            metric_tv.background = ContextCompat.getDrawable(this@bmi,R.color.grey)
            standard_tv.background = ContextCompat.getDrawable(this@bmi,R.color.blue)
        }
        metric_tv.setOnClickListener {
            vibrate()
            replaceaFragment(metric())
            metric_tv.background = ContextCompat.getDrawable(this,R.color.grey)
            standard_tv.background = ContextCompat.getDrawable(this,R.color.blue)

        }
        standard_tv.setOnClickListener {
            vibrate()
            replaceaFragment(standard())
            metric_tv.background = ContextCompat.getDrawable(this,R.color.blue)
            standard_tv.background = ContextCompat.getDrawable(this,R.color.grey)
        }

    }

    private fun replaceaFragment(f : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,f)
        fragmentTransaction.commit()
    }
    fun vibrate(){
        val v = (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(
                VibrationEffect.createOneShot(100,
                    VibrationEffect.DEFAULT_AMPLITUDE))
        }
        else {
            v.vibrate(500)
        }
    }


}