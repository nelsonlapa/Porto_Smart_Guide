package com.example.portosmartguide.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.portosmartguide.model.POI
import kotlinx.coroutines.flow.Flow

@Dao
interface PoiDao {

    // Vai buscar TODOS os POIs para mostrar no Mapa ou na Lista
    // Usamos Flow para que a interface atualize automaticamente se a base de dados mudar
    @Query("SELECT * FROM tabela_pois")
    fun obterTodosPOIs(): Flow<List<POI>>

    // Vai buscar apenas os POIs de uma determinada categoria (ex: "Monumentos")
    @Query("SELECT * FROM tabela_pois WHERE categoria = :categoria")
    fun obterPOIsPorCategoria(categoria: String): Flow<List<POI>>

    // Vai buscar os detalhes de apenas um POI quando o utilizador clica nele
    @Query("SELECT * FROM tabela_pois WHERE poiID = :id")
    fun obterPOIPorId(id: Long): Flow<POI>

    // Guarda uma lista de POIs na base de dados.
    // O comando "suspend" significa que isto vai correr numa thread secundária para não bloquear o telemóvel
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirPOIs(pois: List<POI>)
}