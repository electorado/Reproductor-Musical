package com.example.reproductormusical

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

//Pantalla de entrada a la aplicación, tiene un botón para entrar a la lista de canciones


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialización de vistas y configuración del evento click
        val imgMain = findViewById<ImageView>(R.id.imgMain)
        val boton = findViewById<Button>(R.id.btnLista)

        imgMain.setImageResource(R.drawable.auriculares)

        boton.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
    }
}