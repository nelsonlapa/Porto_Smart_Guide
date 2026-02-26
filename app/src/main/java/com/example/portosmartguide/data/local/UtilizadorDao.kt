package com.example.portosmartguide.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.portosmartguide.model.Utilizador
import kotlinx.coroutines.flow.Flow

@Dao
interface UtilizadorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarUtilizador(utilizador: Utilizador)

    @Query("SELECT * FROM tabela_utilizadores WHERE userID = :id")
    fun obterUtilizador(id: String): Flow<Utilizador>
}