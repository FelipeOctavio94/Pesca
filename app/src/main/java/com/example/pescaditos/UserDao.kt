package com.example.pescaditos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    // Método de inicio de sesión: devuelve un User? o null si no se encuentra coincidencia
    @Query("SELECT * FROM users WHERE correo = :correo AND contrasena = :contrasena")
    fun login(correo: String, contrasena: String): User?

    @Query("SELECT * FROM users WHERE role = :role AND correo = :correo")
    fun getUserByRole(correo: String, role: String): User?

    @Query("UPDATE users SET role = :newRole WHERE correo = :correo")
    fun updateUserRole(correo: String, newRole: String)

    @Query("SELECT * FROM users WHERE correo = :email LIMIT 1")
    fun findUserByEmail(email: String): User?

    @Query("UPDATE users SET role = :role WHERE id = :userId")
    fun updateUserRole(userId: Int, role: String)
}
