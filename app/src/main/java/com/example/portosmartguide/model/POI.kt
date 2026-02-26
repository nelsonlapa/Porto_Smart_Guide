package com.example.portosmartguide.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// A anotação @Entity diz ao Android que esta classe vai ser uma tabela na base de dados SQLite
@Entity(tableName = "tabela_pois")
data class POI(
    // O @PrimaryKey diz que este é o identificador único.
    // autoGenerate = true significa que a base de dados cria o ID (1, 2, 3...) sozinha.
    @PrimaryKey(autoGenerate = true)
    val poiID: Long = 0,

    val nome: String,
    val categoria: String,
    val latitude: Double,
    val longitude: Double,
    val descricao: String,
    val imagemUrl: String
) {
    // Nota: O método "+distanciaAte" que tens no diagrama do relatório
    // será implementado mais tarde usando as ferramentas de localização nativas do Android!
}