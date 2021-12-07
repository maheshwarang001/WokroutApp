package com.example.workoutapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment1_metric.*
import kotlinx.android.synthetic.main.fragment1_metric.view.*
import java.util.*

class metric : Fragment(R.layout.fragment1_metric), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(R.layout.fragment1_metric, container, false)
        val btn = myView.btn as Button

        btn.setOnClickListener {
            metricbmi()
        }
        return myView
    }

    override fun onClick(v: View?) {
    }

    private fun metricbmi() {

        try {
            val heightcms: Float = height_tv.text.toString().toFloat()
            val weightinkgs: Float = weight_tv.text.toString().toFloat()
            val count: Float = "${(weightinkgs / heightcms / heightcms) * 10000}".toFloat()
            //covert double to 0.1 value
            BMI_value_tv.text = String.format(Locale.US, "%.2f", count)
            bmiResult(BMI_value_tv.text.toString().toFloat())
            bmi_container.visibility = View.VISIBLE
            Log.d("pp", "calculate bmi")

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Enter value", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bmiResult(bmi: Float) {
        try {
//source https://www.calculator.net/bmi-calculator.html
            val bmiLabel = category_tv as TextView
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