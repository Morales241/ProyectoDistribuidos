package morales.jesus.appconsumidormovil

import android.content.Intent
import android.os.Bundle
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
import org.mindrot.jbcrypt.BCrypt
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class Register : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var correoEditText: EditText
    private lateinit var contrasenaEditText: EditText
    private lateinit var registrarseButton: Button
    private lateinit var volverLoginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nombreEditText = findViewById(R.id.nombreEditText)
        correoEditText = findViewById(R.id.correoEditText)
        contrasenaEditText = findViewById(R.id.contrasenaEditText)
        registrarseButton = findViewById(R.id.registrarseButton)
        volverLoginButton = findViewById(R.id.volverLoginButton)

        registrarseButton.setOnClickListener {
            registrarUsuario(nombreEditText.text.toString(), correoEditText.text.toString(), correoEditText.text.toString())
        }

        volverLoginButton.setOnClickListener {
            finish()
        }
    }

    fun registrarUsuario(nombre:String, correo:String, contraseña:String) {
        val json = JSONObject().apply {
            put("id", JSONObject.NULL)
            put("nombre", nombre)
            put("correo", correo)
            put("fechaRegistro", JSONObject.NULL)
            put("contrasena", BCrypt.hashpw(contraseña, BCrypt.gensalt()))
        }

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("http://10.0.2.2:8082/consumidores/guardar")
            .post(requestBody)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // error
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful ){
                    val json = JSONObject(response.body?.string() ?: "")
                    val nombre = json.getString("nombre")
                    val id = json.getInt("id")
                    val prefs = getSharedPreferences("datos_usuario", MODE_PRIVATE)
                    prefs.edit().apply {
                        putInt("consumidorId", id)
                        putString("consumidorNombre", nombre)
                        apply()
                    }

                    var intent = Intent(this@Register, HomeFragment::class.java)
                    startActivity(intent)
                }else {
                    Toast.makeText(
                        this@Register,
                        "no se pudo registrar usuario, intelo nuevamente.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}