package com.example.portosmartguide.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.portosmartguide.data.repository.PoiRepository
import com.example.portosmartguide.model.POI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel(private val repository: PoiRepository) : ViewModel() {

    private val _pois = MutableStateFlow<List<POI>>(emptyList())
    val pois: StateFlow<List<POI>> = _pois.asStateFlow()

    init {
        carregarTodosPOIs()
    }

    private fun carregarTodosPOIs() {
        viewModelScope.launch {
            repository.todosOsPOIs.collect { listaDaBaseDeDados ->
                _pois.value = listaDaBaseDeDados
            }
        }
    }

    fun filtrarPorCategoria(categoria: String) {
        viewModelScope.launch {
            repository.obterPOIsPorCategoria(categoria).collect { listaFiltrada ->
                _pois.value = listaFiltrada
            }
        }
    }

    // NOVA FUNÇÃO: Cria os nossos primeiros pontos de interesse na Base de Dados
    fun inserirPOIsIniciais() {
        viewModelScope.launch {
            val lista = listOf(
                POI(nome = "Torre dos Clérigos", categoria = "Monumentos", latitude = 41.1458, longitude = -8.6139, descricao = "O símbolo da cidade do Porto.", imagemUrl = ""),
                POI(nome = "Livraria Lello", categoria = "Monumentos", latitude = 41.1468, longitude = -8.6148, descricao = "Uma das livrarias mais belas do mundo.", imagemUrl = ""),
                POI(nome = "Jardins do Palácio de Cristal", categoria = "Jardins", latitude = 41.1481, longitude = -8.6258, descricao = "Jardins românticos com pavões e miradouros.", imagemUrl = "")
            )
            repository.inserirPOIs(lista)
        }
    }
}

class MapViewModelFactory(private val repository: PoiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapViewModel(repository) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}