package org.hyperskill.calculator

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, "I'm a Toast", duration)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()

        var text = "0"
        val setText = { i: String ->
            if (text.startsWith("0") && !text.contains(".")) {
                text = i
                editText.setText(text)
            } else {
                text += i
                editText.setText(text)
            }
        }

        button0.setOnClickListener { if (text != "-") setText("0") }
        button1.setOnClickListener { setText("1") }
        button2.setOnClickListener { setText("2") }
        button3.setOnClickListener { setText("3") }
        button4.setOnClickListener { setText("4") }
        button5.setOnClickListener { setText("5") }
        button6.setOnClickListener { setText("6") }
        button7.setOnClickListener { setText("7") }
        button8.setOnClickListener { setText("8") }
        button9.setOnClickListener { setText("9") }
        dotButton.setOnClickListener {
            if (!text.contains(".")) {
                if (text == "-") {
                    text = "-0."
                } else text += "."
                editText.setText(text)
            }
        }

        var temp = 0.0
        var buffer = ' '
        val reset = {
            temp = 0.0
            buffer = ' '
            editText.hint = "0"
            editText.setText("0")
        }
        clearButton.setOnClickListener { reset() }

        val calc = {
            val value = text.toDouble()
            when (buffer) {
                '*' -> temp *= value
                else -> if (value != 0.0) {
                    when (buffer) {
                        '+' -> temp += value
                        '-' -> temp -= value
                        '/' -> temp /= value
                    }
                }
            }
        }

        val apply = { c: Char ->
            if (buffer != ' ') {
                calc()
            } else temp = text.toDouble()
            buffer = c
            editText.hint=text
            text = ""
            editText.setText(text)
        }

        equalButton.setOnClickListener {
            calc()
            buffer = ' '
            if (temp%temp.toInt().toDouble()==0.0) {
                editText.setText(temp.toInt().toString())
            }
            else editText.setText(temp.toString())
        }

        addButton.setOnClickListener { apply('+') }
        subtractButton.setOnClickListener {
            if (text != "0") {
                apply('-')
            } else {
                text = "-"
                editText.setText(text)
            }
        }
        multiplyButton.setOnClickListener { apply('*') }
        divideButton.setOnClickListener { apply('/') }
    }


    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmation")
            setMessage("Are you sure you want to exit the app?")

            setPositiveButton("Yes") { _, _ ->
                // if user press yes, then exit from app
                super.onBackPressed()
            }

            setNegativeButton("No") { _, _ ->
                // if user press no, then return the activity
                Toast.makeText(
                    applicationContext, "^~^",
                    Toast.LENGTH_LONG
                ).show()
            }
        }.show()
    }
}