package com.example.reproductormusical

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

// Pantalla que permite seleccionar una nueva canción desde el almacenamiento del dispositivo.
class AddSongActivity : AppCompatActivity() {

    // Launcher que abre el explorador de archivos del sistema para elegir un audio.
    private val pickAudio = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let {
            contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            // Devuelve la URI seleccionada a ListActivity, si el usuario cancela,
            // se cierra la pantalla sin añadir nada.
            val data = Intent().apply {
                putExtra("uri", it.toString())
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        } ?: run {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_song)

        // Referencias a la imagen y botones de la pantalla.
        val imgAddSong = findViewById<ImageView>(R.id.imgAddSong)
        val btnSelect = findViewById<Button>(R.id.btnSelectAudio)
        val btnCancel = findViewById<Button>(R.id.btnCancel)

        imgAddSong.setImageResource(R.drawable.auriculares)

        // Abre el explorador para seleccionar un archivo de audio.
        btnSelect.setOnClickListener {
            pickAudio.launch(arrayOf("audio/*"))
        }

        // Cancela la operación y vuelve a la pantalla anterior.
        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}