package com.example.reproductormusical

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SongActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var songId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        val txtTitulo = findViewById<TextView>(R.id.txtTitulo)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnPause = findViewById<Button>(R.id.btnPause)
        val btnStop = findViewById<Button>(R.id.btnStop)

        songId = intent.getIntExtra("songId", 0)
        val titulo = intent.getStringExtra("titulo") ?: "Canción"

        txtTitulo.text = titulo

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