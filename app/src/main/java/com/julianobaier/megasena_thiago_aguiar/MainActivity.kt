package com.julianobaier.megasena_thiago_aguiar

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Range
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var pres:         SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val edit = findViewById<EditText>(R.id.editTextNumber)
        val result = findViewById<TextView>(R.id.txt_r)
        val generation = findViewById<Button>(R.id.btn_generation)

        pres = getSharedPreferences("db", Context.MODE_PRIVATE)
        val resultado = pres.getString("result",null)



        //CONCEITO DE LET: Se for diferente de nulo
        resultado?.let {
            result.text ="Ultima aposta: $it"
        }
        //Substitui o if perguntando se é diferente de nulo
        if (resultado!=null){
            result.text ="Ultima aposta: $resultado"
        }


        generation.setOnClickListener {
            val text = edit.text.toString()

            numberGeneration(text, result)
        }

    }


    private fun numberGeneration(text: String, result: TextView) {
        if (text.isEmpty()) {
            //deu falha nº1
            Toast.makeText(this, "Digite um numero entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }
        val qtd = text.toInt()

        if (qtd < 6 || qtd > 15) {
            //deu falha nº2
            Toast.makeText(this, "Digite um numero entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }



        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }
        result.text = numbers.joinToString(" - ")

        val editor = pres.edit()
        editor.putString("result",result.text.toString())
        editor.apply()

        //alternativa 2 :Avançado  aplica modificações dentro de um objeto
       /* pres.edit().apply(){
            putString("result",result.text.toString())
            apply()
        }
        */

    }


}