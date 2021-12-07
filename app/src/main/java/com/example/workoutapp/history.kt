package com.example.workoutapp

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_page2.*

class history : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(tool_bar_history)
        val action2 = supportActionBar
        action2?.setDisplayHomeAsUpEnabled(true)
        //navigation bar
        tool_bar_history.setNavigationOnClickListener {
            onBackPressed()
        }
        setRecyclerView()
    }

// reading data from sql (db)
    private fun viewData(): ArrayList<historyModelClass> {
        val databaseHandler: db = db(this, null)
        return databaseHandler.viewData()
    }

// Setting recyclerview adapter and linearlayoutmanager
    fun setRecyclerView() {
        if (viewData().size > 0) {
            visible_text.visibility = View.GONE
            recycler_view_container.visibility = View.VISIBLE

            recycler_view_container.layoutManager = LinearLayoutManager(this)
            recycler_view_container.adapter = historyAdapter(this, viewData())
        } else {
            visible_text.visibility = View.VISIBLE
            recycler_view_container.visibility = View.GONE
        }
    }

//delete record from database
    fun deleteRecord(emp: historyModelClass) {

    //to make delete safe, alert box has been used

        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.ic_baseline_warning_24)
        builder.setTitle("Delete Record")
        builder.setMessage("Are you sure you want to delete ${emp.date}")

        builder.setPositiveButton("Yes") { DialogInterface, _ ->
            val databasehandler: db = db(this, null)

            val status = databasehandler.delete(historyModelClass(emp.id, ""))

            if (status > -1) {
                Toast.makeText(
                    applicationContext,
                    "Record deleted successfully.",
                    Toast.LENGTH_SHORT
                ).show()
                setRecyclerView()
            }
            DialogInterface.dismiss()
        }
        builder.setNegativeButton("No") { DialogInterface, which ->
            DialogInterface.dismiss()
        }

        //create alert box
        val alertbox: AlertDialog = builder.create()
        alertbox.setCancelable(false)
        alertbox.show()
    }
}