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
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Intent

class PasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        val etContrasena = findViewById<EditText>(R.id.et_contrasena)
        val etRepiteContrasena = findViewById<EditText>(R.id.et_repite_contrasena)
        val btnGuardar = findViewById<Button>(R.id.btn_guardar)

        // Recupera los datos del Intent
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val rut = intent.getStringExtra("rut")
        val correo = intent.getStringExtra("correo")
        val celular = intent.getStringExtra("celular")
        val clubPesca = intent.getStringExtra("club_pesca")

        btnGuardar.setOnClickListener {
            val contrasena = etContrasena.text.toString()
            val repiteContrasena = etRepiteContrasena.text.toString()

            if (contrasena == repiteContrasena && contrasena.isNotEmpty()) {
                // Crea el usuario y lo guarda en la base de datos
                val user = User(
                    nombre = nombre ?: "",
                    apellido = apellido ?: "",
                    rut = rut ?: "",
                    correo = correo ?: "",
                    celular = celular ?: "",
                    clubPesca = clubPesca ?: "",
                    contrasena = contrasena
                )

                // Inserta el usuario en la base de datos usando coroutines
                val db = AppDatabase.getDatabase(this)
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().insertUser(user)
                    runOnUiThread {
                        Toast.makeText(this@PasswordActivity, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                        // Redirige a la actividad principal o login
                        startActivity(Intent(this@PasswordActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            } else {
                etContrasena.setTextColor(Color.RED)
                etRepiteContrasena.setTextColor(Color.RED)
                Toast.makeText(this, "Las contraseñas no coinciden. Por favor, revisa.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    PescaditosTheme {
        Greeting2("Android")
    }
}