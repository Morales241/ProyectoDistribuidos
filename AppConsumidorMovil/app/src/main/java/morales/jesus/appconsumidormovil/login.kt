package morales.jesus.appconsumidormovil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import morales.jesus.appconsumidormovil.ui.home.HomeFragment
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class login : AppCompatActivity() {

    private lateinit var correoEditText: EditText
    private lateinit var contrasenaEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var irRegistroButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        correoEditText = findViewById(R.id.correoEditText)
        contrasenaEditText = findViewById(R.id.contrasenaEditText)
        loginButton = findViewById(R.id.loginButton)
        irRegistroButton = findViewById(R.id.irRegistroButton)

        loginButton.setOnClickListener {

            iniciarSesion(correoEditText.text.toString(), contrasenaEditText.text.toString())
        }

        irRegistroButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    fun iniciarSesion(correo: String, contrasena: String) {
        val formBody = FormBody.Builder()
            .add("correo", correo)
            .add("contrasena", contrasena)
            .build()

        val request = Request.Builder()
            .url("http://192.168.0.101:8082/consumidores/inicioSesion")
            .post(formBody)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@login, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.d("error de red", e.message.toString())
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val json = JSONObject(response.body?.string() ?: "")
                    val nombre = json.getString("nombre")
                    val id = json.getInt("id")
                    val prefs = getSharedPreferences("datos_usuario", MODE_PRIVATE)
                    prefs.edit().apply {
                        putInt("consumidorId", id)
                        putString("consumidorNombre", nombre)
                        apply()
                    }

                    val intent = Intent(this@login, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    runOnUiThread {
                        Log.d("error de inicio", response.message.toString())
                        Toast.makeText(
                            this@login,
                            "Correo o contraseña incorrectos. Intenta nuevamente.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }
}