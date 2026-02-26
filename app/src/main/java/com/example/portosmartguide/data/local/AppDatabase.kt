package com.example.portosmartguide.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.portosmartguide.model.Comentario
import com.example.portosmartguide.model.POI
import com.example.portosmartguide.model.Utilizador

// Aqui declaramos as entidades (tabelas) que pertencem a esta base de dados
@Database(entities = [POI::class, Utilizador::class, Comentario::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Ligações aos teus DAOs
    abstract fun poiDao(): PoiDao
    abstract fun utilizadorDao(): UtilizadorDao
    abstract fun comentarioDao(): ComentarioDao

    // O companion object funciona como um "static" em Java.
    // Serve para acedermos à base de dados de qualquer lado da app.
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Se a INSTANCE já existir, devolvemos essa. Se não, criamos uma nova.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smart_porto_guide_database"
                )
                    // Usamos fallbackToDestructiveMigration durante o desenvolvimento.
                    // Se mudares alguma tabela (ex: adicionar uma coluna), ele apaga a base de dados antiga e cria uma nova sem dar erro (crash).
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}