package com.example.reproductormusical

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.jvm.java

// Pantalla que muestra la lista de canciones disponibles.
// Desde aquí el usuario puede reproducir una canción, añadir una nueva o eliminar una existente.
class ListActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: SongAdapter
    // Lista mutable porque las canciones se pueden añadir y eliminar dinámicamente.
    private val lista = mutableListOf<Song>()
    // Launcher que recibe el resultado de AddSongActivity.
    // Si el usuario selecciona un audio, se añade a la lista.
    private val addSongLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uriString = result.data?.getStringExtra("uri")
            uriString?.let {
                val uri = Uri.parse(it)
                val nombre = obtenerNombreArchivo(uri)

                val nuevaCancion = Song(
                    titulo = nombre,
                    imagen = R.drawable.ic_launcher_foreground,
                    uri = it
                )

                lista.add(nuevaCancion)
                adapter.notifyItemInserted(lista.size - 1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        // Referencias a los elementos de la interfaz.
        recycler = findViewById(R.id.recyclerSongs)
        val btnAdd = findViewById<Button>(R.id.btnAddSong)
        // Configuración del RecyclerView en formato de lista vertical.
        recycler.layoutManager = LinearLayoutManager(this)
        // Canciones iniciales cargadas desde recursos locales de la app.
        lista.addAll(
            listOf(
                Song("Bilal - Levels (Live @ KEXP)", R.drawable.bilal, rawId = R.raw.bilal_levels_live_kexp),
                Song("JC Brooks & The Uptown Sound - Progress", R.drawable.jcbrooks, rawId = R.raw.jc_brooks_the_uptown_sound_progress),
                Song("Jasmine Jordan - Best I Can", R.drawable.jasjordan, rawId = R.raw.jasmine_jordan_best_i_can_feat_habit_blcx),
                Song("Pierce Murphy - Galilee", R.drawable.piercemurphy, rawId = R.raw.pierce_murphy_galilee),
                Song("Serge Quadrado - Honey", R.drawable.sergequadrado, rawId = R.raw.serge_quadrado_honey),
                Song("The Immaculates - I Guess You Found Out", R.drawable.immaculates, rawId = R.raw.the_immaculates_i_guess_you_found_out)
            )
        )
        // Adapter que gestiona la visualización de la lista y las acciones de pulsar o borrar.
        adapter = SongAdapter(
            songs = lista,
        // Abre la pantalla de reproducción de la canción seleccionada.
            onClick = { song ->
                val intent = Intent(this, SongActivity::class.java)
                intent.putExtra("titulo", song.titulo)
                intent.putExtra("imagen", song.imagen)

                song.rawId?.let { intent.putExtra("rawId", it) }
                song.uri?.let { intent.putExtra("uri", it) }

                startActivity(intent)
            },
            // Muestra un diálogo de confirmación antes de eliminar una canción.
            onDeleteClick = { position ->
                if (position != RecyclerView.NO_POSITION) {
                    val titulo = lista[position].titulo
                    AlertDialog.Builder(this)
                        .setTitle("Confirmar borrado")
                        .setMessage("¿Seguro que quieres eliminar la canción \"$titulo\"?")
                        .setPositiveButton("Sí") { _, _ ->
                            lista.removeAt(position)
                            adapter.notifyItemRemoved(position)
                        }
                        .setNegativeButton("No", null)
                        .show()
                }
            }
        )

        recycler.adapter = adapter

        // Abre la pantalla para añadir una nueva canción.
        btnAdd.setOnClickListener {
            addSongLauncher.launch(Intent(this, AddSongActivity::class.java))
        }
    }

    // Obtiene el nombre del archivo seleccionado desde el explorador del sistema.
    private fun obtenerNombreArchivo(uri: Uri): String {
        var nombre = "Nueva canción"

        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && index != -1) {
                nombre = cursor.getString(index)
            }
        }

        return nombre
    }
}