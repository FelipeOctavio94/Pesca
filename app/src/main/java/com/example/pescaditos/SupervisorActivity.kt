package com.example.pescaditos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pescaditos.ui.theme.PescaditosTheme
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import android.text.InputType
import android.widget.Button
import android.widget.TextView
import android.widget.LinearLayout
import kotlinx.coroutines.launch
import android.view.View


class SupervisorActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private var selectedUser: User? = null
    private val fishSizeInputs = mutableListOf<EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supervisor)

        db = AppDatabase.getDatabase(this)

        val etSearchParticipant = findViewById<EditText>(R.id.et_search_participant)
        val btnSearch = findViewById<Button>(R.id.btn_search)
        val tvParticipantInfo = findViewById<TextView>(R.id.tv_participant_info)
        val llFishRecords = findViewById<LinearLayout>(R.id.ll_fish_records)
        val btnAddFishSize = findViewById<Button>(R.id.btn_add_fish_size)
        val btnSaveRecords = findViewById<Button>(R.id.btn_save_records)

        // Buscar participante
        btnSearch.setOnClickListener {
            val email = etSearchParticipant.text.toString()
            if (email.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val user = db.userDao().findUserByEmail(email)
                    runOnUiThread {
                        if (user != null) {
                            selectedUser = user
                            tvParticipantInfo.text = "Nombre: ${user.nombre} ${user.apellido}"
                            tvParticipantInfo.visibility = View.VISIBLE
                            updateFishRecords()
                        } else {
                            tvParticipantInfo.text = "Participante no encontrado"
                            tvParticipantInfo.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        // Agregar campo de tamaño de pescado
        btnAddFishSize.setOnClickListener {
            if (fishSizeInputs.size < 5) {
                val newFishSizeInput = EditText(this)
                newFishSizeInput.hint = "Tamaño del pescado"
                newFishSizeInput.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                llFishRecords.addView(newFishSizeInput)
                fishSizeInputs.add(newFishSizeInput)
            } else {
                Toast.makeText(this, "No puedes agregar más de 5 registros", Toast.LENGTH_SHORT).show()
            }
        }

        // Guardar registros de pesca
        btnSaveRecords.setOnClickListener {
            if (selectedUser != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    val recordCount = db.fishingRecordDao().getFishRecordCount(selectedUser!!.id)
                    if (recordCount + fishSizeInputs.size > 5) {
                        runOnUiThread {
                            Toast.makeText(this@SupervisorActivity, "Excediste el límite de 5 registros", Toast.LENGTH_SHORT).show()
                        }
                        return@launch
                    }

                    // Guardar cada registro en la base de datos
                    fishSizeInputs.forEach { input ->
                        val fishSize = input.text.toString().toDoubleOrNull()
                        if (fishSize != null) {
                            val record = FishingRecord(
                                userId = selectedUser!!.id,
                                fishSize = fishSize
                            )
                            db.fishingRecordDao().insertFishingRecord(record)
                        }
                    }

                    runOnUiThread {
                        Toast.makeText(this@SupervisorActivity, "Registros guardados exitosamente", Toast.LENGTH_SHORT).show()
                        llFishRecords.removeAllViews()
                        fishSizeInputs.clear()
                    }
                }
            } else {
                Toast.makeText(this, "Selecciona un participante antes de guardar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Actualizar los registros existentes en la interfaz (si es necesario)
    private fun updateFishRecords() {
        // Implementa lógica aquí para mostrar los registros existentes si lo deseas
    }
}

@Composable
fun Greeting8(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview8() {
    PescaditosTheme {
        Greeting8("Android")
    }
}