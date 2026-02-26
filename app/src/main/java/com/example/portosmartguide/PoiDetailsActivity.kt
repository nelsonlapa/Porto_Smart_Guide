package com.example.portosmartguide

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PoiDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poi_details)

        // 1. Ir buscar a informação que foi enviada pelo clique no mapa
        val nomePoi = intent.getStringExtra("NOME_POI") ?: "Local Desconhecido"
        val categoriaPoi = intent.getStringExtra("CATEGORIA_POI") ?: "Sem Categoria"

        // 2. Encontrar os elementos de texto no ecrã (XML)
        val tvName = findViewById<TextView>(R.id.tvPoiName)
        val tvCategory = findViewById<TextView>(R.id.tvPoiCategory)

        // 3. Substituir o texto estático pelos dados reais
        tvName.text = nomePoi
        tvCategory.text = categoriaPoi

        // 4. Preparar o botão de direções (Requisito RF10 - que programaremos no futuro)
        val btnNavigate = findViewById<Button>(R.id.btnNavigate)
        btnNavigate.setOnClickListener {
            Toast.makeText(this, "A abrir rota para $nomePoi...", Toast.LENGTH_SHORT).show()
        }
    }
}