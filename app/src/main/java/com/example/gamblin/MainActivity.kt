package com.example.gamblin

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var rg: RadioGroup
    private lateinit var pares: RadioButton
    private lateinit var siete: RadioButton
    private lateinit var adapter:ArrayAdapter<String>
    private var par = mutableListOf<String>("PAR","IMPAR")
    private var seven = mutableListOf<String>("Mayor","Menor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinner = findViewById(R.id.spinner)
        pares = findViewById(R.id.pares)
        siete = findViewById(R.id.siete)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val dato: String = parent?.getItemAtPosition(position).toString()
            }

        }

    }
    fun radioTapped(view: View) {
        if(view is RadioButton) {
            val selectedID: Int = view.id
            val option = view.isChecked

            when (selectedID) {
                R.id.pares -> if(option) {
                    spinner.adapter = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,par)
                    pares.setBackgroundColor(Color.GRAY)
                    siete.setBackgroundColor(getColor(R.color.nearblack))
                }

                R.id.siete -> if(option) {
                    spinner.adapter = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,seven)
                    siete.setBackgroundColor(Color.GRAY)
                    pares.setBackgroundColor(getColor(R.color.nearblack))
                }
            }
        }
    }



}