package com.example.pescaditos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val apellido: String,
    val rut: String,
    val correo: String,
    val celular: String,
    val clubPesca: String,
    val contrasena: String
)

