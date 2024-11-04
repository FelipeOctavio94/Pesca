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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val inputCorreo = findViewById<EditText>(R.id.et_correo_login)
        val inputContrasena = findViewById<EditText>(R.id.et_contrasena_login)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        btnLogin.setOnClickListener {
            val correo = inputCorreo.text.toString()
            val contrasena = inputContrasena.text.toString()

            val db = AppDatabase.getDatabase(this)
            CoroutineScope(Dispatchers.IO).launch {
                val user = db.userDao().login(correo, contrasena)
                runOnUiThread {
                    if (user != null) {
                        // Inicio de sesión exitoso
                        Toast.makeText(this@LoginActivity, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        // Redirige al usuario a la pantalla principal de la aplicación
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    } else {
                        // Credenciales incorrectas
                        Toast.makeText(this@LoginActivity, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    PescaditosTheme {
        Greeting3("Android")
    }
}