package com.example.workoutapp

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment1_metric.*
import kotlinx.android.synthetic.main.fragment2_standard.*
import kotlinx.android.synthetic.main.fragment2_standard.view.*
import java.util.*

class standard: Fragment(R.layout.fragment2_standard) , View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(R.layout.fragment2_standard, container, false)
        val btn = myView.btn1 as Button

        btn.setOnClickListener {
            metricbmi()
        }
        return myView
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    private fun metricbmi() {
        try {
            val height_ft: Float = height_standard_ft.text.toString().toFloat()
            val height_inch: Float = height_standard_inch.text.toString().toFloat()
            val weightinkgs: Float = weight_standard.text.toString().toFloat()

            val inchCovertor = height_ft * 12 + height_inch
            val data: Float = "${(weightinkgs / inchCovertor / inchCovertor) * 703}".toFloat()

            //covert double to 0.1 value
            BMI_value_tv1.text = String.format(Locale.US, "%.2f", data)
            bmiResult(BMI_value_tv1.text.toString().toFloat())
            bmi_container1.visibility = View.VISIBLE
            Log.d("pp", "calculate bmi")
        }catch (e:Exception){
            e.printStackTrace()
            Toast.makeText(context,"Enter value",Toast.LENGTH_SHORT).show()
        }
    }

    private fun bmiResult(bmi: Float) {
        try {
//source https://www.calculator.net/bmi-calculator.html
            val bmiLabel = category_tv1 as TextView
            if (bmi.compareTo(16.00f) <= 0) {
                bmiLabel.text = "Severe Thinness"
            } else if (bmi.compareTo(16.00f) > 0 && bmi.compareTo(17.00f) <= 0) {
                bmiLabel.text = "Moderate Thinness"
            } else if (bmi.compareTo(17.00f) > 0 && bmi.compareTo(18.50f) <= 0) {
                bmiLabel.text = "Mild Thinness"
            } else if (bmi.compareTo(18.50f) > 0 && bmi.compareTo(25.00f) <= 0) {
                bmiLabel.text = "Normal"
            } else if (bmi.compareTo(25.00f) > 0 && bmi.compareTo(30.00f) <= 0) {
                bmiLabel.text = "Overweight"
            } else if (bmi.compareTo(30.00f) > 0 && bmi.compareTo(35.00f) <= 0) {
                bmiLabel.text = "Obese Class I"
            } else if (bmi.compareTo(35.00f) > 0 && bmi.compareTo(40.00f) <= 0) {
                bmiLabel.text = "Obese Class II"
            } else if (bmi.compareTo(40.00f) > 0 && bmi.compareTo(60.00f) <= 0) {
                bmiLabel.text = "Obese Class III"
            } else {
                bmiLabel.text = "No data available"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("category", e.toString())
        }
    }

}