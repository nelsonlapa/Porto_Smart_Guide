package com.example.portosmartguide.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.portosmartguide.model.Comentario
import kotlinx.coroutines.flow.Flow

@Dao
interface ComentarioDao {

    // Requisito RF14: Escrever o seu próprio comentário
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirComentario(comentario: Comentario)

    // Requisito RF14: Ler comentários de outros utilizadores sobre um POI específico
    @Query("SELECT * FROM tabela_comentarios WHERE poiId = :poiId ORDER BY data DESC")
    fun obterComentariosDoPOI(poiId: Long): Flow<List<Comentario>>
}