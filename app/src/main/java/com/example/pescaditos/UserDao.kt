package com.example.pescaditos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    // Método de inicio de sesión: devuelve un User? o null si no se encuentra coincidencia
    @Query("SELECT * FROM users WHERE correo = :correo AND contrasena = :contrasena LIMIT 1")
    fun login(correo: String, contrasena: String): User?

}
