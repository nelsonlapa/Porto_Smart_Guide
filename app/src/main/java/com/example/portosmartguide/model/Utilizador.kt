package com.example.portosmartguide.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_utilizadores")
data class Utilizador(
    // Aqui não usamos o autoGenerate porque este ID virá da autenticação do Google/Firebase
    @PrimaryKey
    val userID: String,

    val nome: String,
    val email: String,
    val fotoUrl: String,
    val dataRegisto: Long // Guardamos a data em milissegundos (Long) porque é mais fácil para o SQLite processar
)