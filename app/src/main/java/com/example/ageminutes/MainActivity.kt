package com.example.ageminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var displayDate:TextView?=null
    private var displayMinutes:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayMinutes=findViewById(R.id.minutes)
        displayDate=findViewById(R.id.date)
        val datePickerButton=findViewById<Button>(R.id.button)
        datePickerButton.setOnClickListener {
            dateDisplay()
        }
    }
    private fun dateDisplay()
    {
        val calendar=Calendar.getInstance()
        val year=calendar.get(Calendar.YEAR)
        val month=calendar.get(Calendar.MONTH)
        val day=calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog=DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener{view, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate="${selectedDayOfMonth}/${selectedMonth+1}/${selectedYear}" //this data is of type String
                displayDate?.text = selectedDate
                //calculating the age
                val dateFormatObject=SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)//create the object of type dateFormat
                //convert the dateString to dateFormat
                val actualDate=dateFormatObject.parse(selectedDate)
                //-----to convert the date in some other format see the android documentation :DateFormat-----
                actualDate?.let {
                    val dateInMinutes= actualDate.time / 60000   //this will convert the date from milliSec to minutes
                    // how much time has passed from the past to the selected date
                    val currentDate=dateFormatObject.parse(dateFormatObject.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes= currentDate.time /60000
                        // how much time has passed from the past to the current date
                        val ageInMinutes=currentDateInMinutes-dateInMinutes  //this is of type long
                        displayMinutes?.text=ageInMinutes.toString()
                    }
                }
            }, year, month, day
        )
        datePickerDialog.datePicker.maxDate=System.currentTimeMillis()-86400000 //3.6X24 million sec in one day
        datePickerDialog.show()
    }

}