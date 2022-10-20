package com.aq.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.Button
import com.aq.myapplication.DateDialog.DateDialogListener
import android.widget.TimePicker
import android.widget.NumberPicker
import com.aq.myapplication.DateDialog
import com.aq.myapplication.R
import java.lang.Exception
import java.util.*

class DateDialog(private val mActivity: Activity) : View.OnClickListener {
    private val TAG = this@DateDialog.javaClass.simpleName
    private val mDateDialog: Dialog
    private var dListener: DateDialogListener? = null
    private val timePicker: TimePicker

    interface DateDialogListener {
        fun OnDateValidate(hour: String,minutes: String)
    }

    fun setDateDialogListener(listener: DateDialogListener?) {
        dListener = listener
    }

    fun show() {
        mDateDialog.show()
    }

    /**
     * Set TimePicker interval by adding a custom minutes list
     *
     * @param timePicker
     */
    private fun setTimePickerInterval(timePicker: TimePicker) {
        try {
            val minutePicker = timePicker.findViewById<View>(
                Resources.getSystem().getIdentifier(
                    "minute", "id", "android"
                )
            ) as NumberPicker
            minutePicker.minValue = 0
            minutePicker.maxValue = 60 / TIME_PICKER_INTERVAL - 1
            val displayedValues: MutableList<String> = ArrayList()
            var i = 0
            while (i < 60) {
                displayedValues.add(String.format("%02d", i))
                i += TIME_PICKER_INTERVAL
            }
            minutePicker.displayedValues = displayedValues.toTypedArray()
        } catch (e: Exception) {
            Log.e(TAG, "Exception: $e")
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.validate_btn -> {
                val hour = String.format("%02d", timePicker.currentHour)
                val minute = String.format("%02d", timePicker.currentMinute * TIME_PICKER_INTERVAL)
                if (dListener != null) {
                    dListener!!.OnDateValidate(hour,minute)
                }
                mDateDialog.cancel()
            }
            R.id.cancel_btn -> mDateDialog.cancel()
            else -> {}
        }
    }

    companion object {
        private const val TIME_PICKER_INTERVAL = 30
    }

    init {
        val rootView = mActivity.layoutInflater.inflate(R.layout.date_dialog, null, false)
        timePicker = rootView.findViewById<View>(R.id.timePicker) as TimePicker
        val validateButton = rootView.findViewById<View>(R.id.validate_btn) as Button
        val cancelButton = rootView.findViewById<View>(R.id.cancel_btn) as Button
        validateButton.setOnClickListener(this)
        cancelButton.setOnClickListener(this)

        // Set timer picker
        timePicker.setIs24HourView(false)
        val calendar: Calendar = GregorianCalendar()
        calendar.timeInMillis = System.currentTimeMillis()
        var hour = calendar[Calendar.HOUR_OF_DAY]
        var minute = calendar[Calendar.MINUTE]
        setTimePickerInterval(timePicker)

        // Configure displayed time
        if (minute % TIME_PICKER_INTERVAL != 0) {
            val minuteFloor = minute + TIME_PICKER_INTERVAL - minute % TIME_PICKER_INTERVAL
            minute = minuteFloor + if (minute == minuteFloor + 1) TIME_PICKER_INTERVAL else 0
            if (minute >= 60) {
                minute = minute % 60
                hour++
            }
            timePicker.currentHour = hour
            timePicker.currentMinute = minute / TIME_PICKER_INTERVAL
        }

        // Implement dialog box
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(rootView)
        mDateDialog = builder.create()
    }
}