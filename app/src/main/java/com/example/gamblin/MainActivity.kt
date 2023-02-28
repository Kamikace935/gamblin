package com.example.gamblin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var dinero: TextView
    private lateinit var seleccion: Spinner
    private lateinit var apuesta: EditText
    private lateinit var pares: RadioButton
    private lateinit var siete: RadioButton
    private lateinit var imagenes: ImageView
    private lateinit var dado1: TextView
    private lateinit var dado2: TextView
    private lateinit var builder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dinero = findViewById(R.id.dinero)
        dinero.text = 100.toString()
        seleccion = findViewById(R.id.seleccion)
        apuesta = findViewById(R.id.apuesta)
        pares = findViewById(R.id.pares)
        siete = findViewById(R.id.siete)
        imagenes = findViewById(R.id.imagen)
        imagenes.setImageResource(0)
        builder = AlertDialog.Builder(this)
        dado1 = findViewById(R.id.dado1)
        dado2 = findViewById(R.id.dado2)

        seleccion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
            val par = resources.getStringArray(R.array.par).toList()
            val seven = resources.getStringArray(R.array.siete).toList()

            when (selectedID) {
                R.id.pares -> if(option) {
                    seleccion.adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,par)
                    pares.setBackgroundColor(Color.GRAY)
                    siete.setBackgroundColor(getColor(R.color.nearblack))
                }

                R.id.siete -> if(option) {
                    seleccion.adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,seven)
                    siete.setBackgroundColor(Color.GRAY)
                    pares.setBackgroundColor(getColor(R.color.nearblack))
                }
            }
        }
    }

    fun jugar(view: View) {
        val saldo = dinero.text.toString().toInt()
        val jugando: Int = apuesta.text.toString().toInt()
        val quiebra: Boolean = final(saldo)

        if( !quiebra && (pares.isChecked || siete.isChecked) ) {
            if (saldo >= jugando) {
                Toast.makeText(
                    this@MainActivity, "Lanzando los dados...", Toast.LENGTH_LONG
                ).show()

                Thread.sleep(1_000)

                //lanzamiento()
                dinero.text = calculo(saldo, jugando)

                alerta()
            } else if (jugando == 0) {
                Toast.makeText(
                    this@MainActivity, "Tienes que apostar algo para jugar", Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                this@MainActivity, "Has apostado mas de lo que tienes", Toast.LENGTH_LONG            ).show()
            }
        }
    }

    fun final(saldo: Int): Boolean {
        if(saldo <= 0) {
            Toast.makeText(
                this@MainActivity, "Perdiste", Toast.LENGTH_SHORT
            ).show()
            return true
        }else {
            return false
        }
    }

    fun calculo(saldo: Int, jugado: Int): String {
        val fdice = (1..6).random()
        val sdice = (1..6).random()
        val arcas: Int

        dado1.text = fdice.toString()
        dado2.text = sdice.toString()

        when (seleccion.selectedItem.toString()) {

            "Par" -> {

                if((fdice+sdice)%2 != 0) {
                    arcas = saldo + jugado
                    imagenes.setImageResource(R.drawable.ganar_dados)
                    return arcas.toString()
                }else {
                    arcas = saldo - jugado
                    imagenes.setImageResource(R.drawable.perder_dados)
                    return arcas.toString()
                }
            }

            "Impar" -> {
                if((fdice+sdice)%2 != 0) {
                    arcas = saldo + jugado
                    imagenes.setImageResource(R.drawable.ganar_dados)
                    return arcas.toString()
                }else {
                    arcas = saldo - jugado
                    imagenes.setImageResource(R.drawable.perder_dados)
                    return arcas.toString()
                }
            }

            "Mayor" -> {
                if((fdice+sdice) > 7) {
                    arcas = saldo + jugado
                    imagenes.setImageResource(R.drawable.ganar_dados)
                    return arcas.toString()
                }else {
                    arcas = saldo - jugado
                    imagenes.setImageResource(R.drawable.perder_dados)
                    return arcas.toString()
                }
            }

            "Igual" -> {
                if((fdice+sdice) == 7) {
                    arcas = saldo + jugado
                    imagenes.setImageResource(R.drawable.ganar_dados)
                    return arcas.toString()
                }else {
                    arcas = saldo - jugado
                    imagenes.setImageResource(R.drawable.perder_dados)
                    return arcas.toString()
                }
            }

            "Menor" -> {
                if((fdice+sdice) < 7) {
                    arcas = saldo + jugado
                    imagenes.setImageResource(R.drawable.ganar_dados)
                    return arcas.toString()
                }else {
                    arcas = saldo - jugado
                    imagenes.setImageResource(R.drawable.perder_dados)
                    return arcas.toString()
                }
            }

            else -> {
                return saldo.toString()
            }
        }
    }

    fun alerta() {
        builder.setTitle("Jugando a los dados")
        .setMessage("¿Desea seguir jugando?")
        .setPositiveButton("Seguir jugando"){DialogInterface, it ->
            Toast.makeText(                this@MainActivity, "Papa necesita una polainas nuevas", Toast.LENGTH_LONG
            ).show()        }
        .setNegativeButton("Salir del juego"){DialogInterface,it ->
            finish()
        }.show()
    }

    fun lanzamiento() {

        Toast(this).apply {
            val dados = layoutInflater.inflate(R.layout.animacion, null)
            duration = Toast.LENGTH_LONG
            setGravity(Gravity.CENTER,0,0)
            view = dados
        }.show()

    }
}