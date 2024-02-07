package edu.uw.ischool.dpham722.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.text.Editable
import android.widget.*
import java.text.NumberFormat
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var inputPrice: EditText
    private lateinit var submitTip: Button
    private lateinit var tipspinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputPrice = findViewById(R.id.input_price)
        submitTip = findViewById(R.id.submit_tip)
        tipspinner = findViewById(R.id.tip_spinner)

        inputPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                validate()
            }
        })
        submitTip.setOnClickListener {
            tipCalculate()
        }
    }

    private fun validate() {
        val input = inputPrice.toString()
        submitTip.isEnabled = input.isNotEmpty()
    }

    private fun tipCalculate() {
        val inputText = inputPrice.text.toString().replace("$", "").toDoubleOrNull()
        if (inputText != null) {
            val roundedInput = DecimalFormat("#.##").format(inputText).toDouble()
            val roundedString = DecimalFormat("$#.##").format(roundedInput)
            inputPrice.setText(roundedString)
            inputPrice.setSelection(roundedString.length)
            tipspinner.selectedItem.toString().dropLast(1).toDoubleOrNull()?.let { percentage ->
                val tipAmount = roundedInput * (percentage / 100)
                val formattedTip = NumberFormat.getCurrencyInstance().format(tipAmount)
                Toast.makeText(applicationContext, formattedTip, Toast.LENGTH_LONG).show()
            }
        }
    }
}