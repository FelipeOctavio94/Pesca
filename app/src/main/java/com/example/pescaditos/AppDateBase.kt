package com.example.pescaditos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.room.migration.Migration
import android.util.Log

// Define la base de datos y especifica la entidad y la versión
@Database(entities = [User::class, FishingRecord::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    // Define una función abstracta para obtener el DAO de usuario
    abstract fun userDao(): UserDao
    // Define una función abstracta para obtener el DAO de FishingRecord
    abstract fun fishingRecordDao(): FishingRecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Definir la migración de la versión 1 a la versión 2
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Agregar la nueva columna 'rol' con un valor predeterminado
                Log.d("Migration", "Applying migration from 1 to 2")
                database.execSQL("ALTER TABLE users ADD COLUMN role TEXT NOT NULL DEFAULT 'participante'")
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Aquí puedes agregar cualquier instrucción para cambiar la estructura de la tabla si es necesario
                // Como FishingRecord es una nueva entidad, normalmente no necesitas hacer cambios aquí
            }
        }


        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database_v3"
                ) .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                    .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Crear el usuario administrador
                        CoroutineScope(Dispatchers.IO).launch {
                            getDatabase(context).userDao().insertUser(
                                User(
                                    nombre = "Admin",
                                    apellido = "Admin",
                                    rut = "00000000-0",
                                    correo = "admin",
                                    celular = "000000000",
                                    clubPesca = "Admin Club",
                                    contrasena = "1020",
                                    role = "admin"
                                )
                            )
                        }
                    }
                })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
