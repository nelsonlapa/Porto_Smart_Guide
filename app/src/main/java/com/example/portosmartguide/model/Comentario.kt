package com.example.portosmartguide.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_comentarios")
data class Comentario(
    @PrimaryKey(autoGenerate = true)
    val reviewId: Long = 0,

    val userId: String, // Para sabermos QUEM escreveu
    val poiId: Long,    // Para sabermos ONDE escreveu (ligação ao POI)
    val texto: String,
    val classificacao: Int, // De 1 a 5 estrelas, como definiste no RF14
    val data: Long
)