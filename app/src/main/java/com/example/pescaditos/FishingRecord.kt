package com.example.pescaditos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FishingRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int, // ID del usuario asociado a este registro de pesca
    val fishSize: Double, // Tamaño del pescado
    val timestamp: Long = System.currentTimeMillis() // Fecha y hora del registro
)
