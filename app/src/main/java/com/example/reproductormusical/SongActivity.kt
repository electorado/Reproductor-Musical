package com.example.reproductormusical

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Pantalla encargada de reproducir la canción seleccionada.
// Muestra la imagen, el título y los controles básicos del reproductor.
class SongActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var rawId: Int = -1
    private var uriString: String? = null
    private var currentUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        // Referencias a los elementos visuales y botones de control.
        val txtTitulo = findViewById<TextView>(R.id.txtTitulo)
        val imgSong = findViewById<ImageView>(R.id.imgSong)
        val btnPlay = findViewById<ImageButton>(R.id.btnPlay)
        val btnPause = findViewById<ImageButton>(R.id.btnPause)
        val btnStop = findViewById<ImageButton>(R.id.btnStop)

        // Datos recibidos desde la pantalla de lista.
        rawId = intent.getIntExtra("rawId", -1)
        uriString = intent.getStringExtra("uri")
        val titulo = intent.getStringExtra("titulo") ?: "Canción"
        val imagen = intent.getIntExtra("imagen", 0)

        txtTitulo.text = titulo

        if (imagen != 0) {
            imgSong.setImageResource(imagen)
        }

        // Se prepara la URI del audio, ya sea desde res/raw o desde un archivo externo.
        currentUri = when {
            rawId != -1 -> Uri.parse("android.resource://$packageName/$rawId")
            uriString != null -> Uri.parse(uriString)
            else -> null
        }

        prepararMediaPlayer()

        //Botones de reproducción, pausa y stop.
        btnPlay.setOnClickListener {
            mediaPlayer?.start()
        }

        btnPause.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }

        btnStop.setOnClickListener {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            }
            mediaPlayer = null
            prepararMediaPlayer()
        }
    }

    // Inicializa el reproductor con la canción seleccionada.
    private fun prepararMediaPlayer() {
        val uri = currentUri ?: return

        mediaPlayer = MediaPlayer().apply {
            setDataSource(this@SongActivity, uri)
            prepare()
        }
    }

    // Libera los recursos del reproductor al salir de la pantalla.
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}