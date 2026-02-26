package com.example.portosmartguide.data.repository

import com.example.portosmartguide.data.local.PoiDao
import com.example.portosmartguide.model.POI
import kotlinx.coroutines.flow.Flow

// O Repositório recebe o PoiDao (as instruções da base de dados) no construtor.
class PoiRepository(private val poiDao: PoiDao) {

    // 1. Expor a lista completa de POIs.
    // Usamos Flow para que qualquer alteração na base de dados atualize o ecrã automaticamente.
    val todosOsPOIs: Flow<List<POI>> = poiDao.obterTodosPOIs()

    // 2. Cumprir o RF11 (Filtrar POIs por categoria)
    fun obterPOIsPorCategoria(categoria: String): Flow<List<POI>> {
        return poiDao.obterPOIsPorCategoria(categoria)
    }

    // 3. Cumprir o RF09 (Visualizar os detalhes completos do POI)
    fun obterPOIPorId(id: Long): Flow<POI> {
        return poiDao.obterPOIPorId(id)
    }

    // 4. Inserir POIs (vamos precisar disto para criar a nossa lista inicial de monumentos)
    // O "suspend" garante que a escrita na base de dados é feita sem congelar a aplicação.
    suspend fun inserirPOIs(pois: List<POI>) {
        poiDao.inserirPOIs(pois)
    }
}