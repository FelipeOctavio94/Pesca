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
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botón para Crear Cuenta
        val buttonCrearCuenta = findViewById<Button>(R.id.button_crear_cuenta)
        buttonCrearCuenta.setOnClickListener {
            // Inicia la actividad FormularioActivity
            val intent = Intent(this, FormularioActivity::class.java)
            startActivity(intent)
        }

        // Botón para Iniciar Sesión
        val buttonIniciarSesion = findViewById<Button>(R.id.button_iniciar_sesion)
        buttonIniciarSesion.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PescaditosTheme {
        Greeting("Android")
    }
}