package com.example.reproductormusical

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SongActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var songId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        val txtTitulo = findViewById<TextView>(R.id.txtTitulo)
        val imgSong = findViewById<ImageView>(R.id.imgSong)
        val btnPlay = findViewById<ImageButton>(R.id.btnPlay)
        val btnPause = findViewById<ImageButton>(R.id.btnPause)
        val btnStop = findViewById<ImageButton>(R.id.btnStop)

        val rawId = intent.getIntExtra("rawId", -1)
        val uriString = intent.getStringExtra("uri")
        val titulo = intent.getStringExtra("titulo") ?: "Canción"
        val imagen = intent.getIntExtra("imagen", 0)

        txtTitulo.text = titulo
        imgSong.setImageResource(imagen)

        val uri = Uri.parse("android.resource://$packageName/$songId")

        mediaPlayer = MediaPlayer().apply {
            setDataSource(this@SongActivity, uri)
            prepare()
        }

        btnPlay.setOnClickListener {
            mediaPlayer?.start()
        }

        btnPause.setOnClickListener {
            mediaPlayer?.pause()
        }

        btnStop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.release()

            mediaPlayer = MediaPlayer().apply {
                setDataSource(this@SongActivity, uri)
                prepare()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}