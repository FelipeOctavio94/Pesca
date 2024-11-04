package com.example.pescaditos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Define la base de datos y especifica la entidad y la versión
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // Define una función abstracta para obtener el DAO de usuario
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Usa el patrón Singleton para evitar múltiples instancias de la base de datos
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database" // Nombre de la base de datos
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
