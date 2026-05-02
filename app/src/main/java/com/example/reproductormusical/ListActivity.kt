package com.example.reproductormusical

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val recycler = findViewById<RecyclerView>(R.id.recyclerSongs)
        recycler.layoutManager = LinearLayoutManager(this)

        val lista = mutableListOf(
            Song("Bilal - Levels (Live @ KEXP)",
                R.drawable.bilal,
                R.raw.bilal_levels_live_kexp),
            Song("JC Brooks & The Uptown Sound - Progress",
                R.drawable.jcbrooks,
                R.raw.jc_brooks_the_uptown_sound_progress),
            Song("Jasmine Jordan - Best I Can",
                R.drawable.jasjordan,
                R.raw.jasmine_jordan_best_i_can_feat_habit_blcx),
            Song("Pierce Murphy - Galilee",
                R.drawable.piercemurphy,
                R.raw.pierce_murphy_galilee),
            Song("Serge Quadrado - Honey",
                R.drawable.sergequadrado,
                R.raw.serge_quadrado_honey),
            Song("The Immaculates - I Guess You Found Out",
                R.drawable.immaculates,
                R.raw.the_immaculates_i_guess_you_found_out)
        )

        val adapter = SongAdapter(lista) { song ->
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("titulo", song.titulo)
            intent.putExtra("imagen", song.imagen)
            song.rawId?.let {
                intent.putExtra("rawId", it)
            }

            song.uri?.let {
                intent.putExtra("uri", it)
            }
            startActivity(intent)
        }

        recycler.adapter = adapter
    }
}