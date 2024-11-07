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
import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminActivity : AppCompatActivity() {

    private val userDao by lazy { AppDatabase.getDatabase(this).userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val etSearchEmail = findViewById<EditText>(R.id.et_search_email)
        val btnSearch = findViewById<Button>(R.id.btn_search)
        val rgRoles = findViewById<RadioGroup>(R.id.rg_roles)
        val btnAssignRole = findViewById<Button>(R.id.btn_assign_role)

        var foundUser: User? = null

        btnSearch.setOnClickListener {
            val email = etSearchEmail.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                foundUser = userDao.findUserByEmail(email)
                runOnUiThread {
                    if (foundUser != null) {
                        Toast.makeText(this@AdminActivity, "Usuario encontrado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AdminActivity, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btnAssignRole.setOnClickListener {
            if (foundUser != null) {
                val selectedRole = when (rgRoles.checkedRadioButtonId) {
                    R.id.rb_supervisor -> "Supervisor"
                    R.id.rb_participante -> "Participante"
                    else -> null
                }
                if (selectedRole != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        userDao.updateUserRole(foundUser!!.id, selectedRole)
                        runOnUiThread {
                            Toast.makeText(this@AdminActivity, "Rol asignado: $selectedRole", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting5(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    PescaditosTheme {
        Greeting5("Android")
    }
}