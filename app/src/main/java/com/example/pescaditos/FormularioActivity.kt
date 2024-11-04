package com.example.pescaditos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
class FormularioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        val etNombre = findViewById<EditText>(R.id.et_nombre)
        val etApellido = findViewById<EditText>(R.id.et_apellido)
        val etRut = findViewById<EditText>(R.id.et_rut)
        val etCorreo = findViewById<EditText>(R.id.et_correo)
        val etCelular = findViewById<EditText>(R.id.et_celular)
        val etClubPesca = findViewById<EditText>(R.id.et_club_pesca)
        val btnEnviar  = findViewById<Button>(R.id.btn_enviar)

        btnEnviar.setOnClickListener {
            // Captura los valores de los campos de texto
            val nombre = etNombre.text.toString()
            val apellido = etApellido.text.toString()
            val rut = etRut.text.toString()
            val correo = etCorreo.text.toString()
            val celular = etCelular.text.toString()
            val clubPesca = etClubPesca.text.toString()

            val intent = Intent(this, PasswordActivity::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("apellido", apellido)
            intent.putExtra("rut", rut)
            intent.putExtra("correo", correo)
            intent.putExtra("celular", celular)
            intent.putExtra("club_pesca", clubPesca)
            startActivity(intent)

            // Muestra los datos en un Toast
            Toast.makeText(
                this,
                "Datos: Nombre: $nombre, Apellido: $apellido, RUT: $rut, Correo: $correo, Celular: $celular, Club de Pesca: $clubPesca",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}