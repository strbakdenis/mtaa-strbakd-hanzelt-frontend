package com.example.mtaa_strbakd_hanzelt

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit

class Register : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val imageButton: ImageButton = findViewById(R.id.back)
        imageButton.setOnClickListener {
            val intent = Intent(this, login_registr::class.java)
            startActivity(intent)
        }
        val imageButton2: ImageButton = findViewById(R.id.home_icon)
        imageButton2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val email: EditText = findViewById(R.id.email_registration_input)
        val password: EditText = findViewById(R.id.password_registration_input)

        val Button: Button = findViewById(R.id.register_registration)
        Button.setOnClickListener{
            var email_string = email.text.toString()
            var password_string = password.text.toString()
            val intent = Intent(this, Login::class.java)
            val intent1 = Intent(this, Register::class.java)

            val retrofit = Retrofit.Builder()
                    .baseUrl("https://e95a4d3de6d8.ngrok.io/")
                    .build()

            val service = retrofit.create(APIService::class.java)

            val jsonObject = JSONObject()
            jsonObject.put("email_address", email_string)
            jsonObject.put("password", password_string)

            val jsonObjectString = jsonObject.toString()

            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

            CoroutineScope(Dispatchers.IO).launch {

                val response = service.register(requestBody)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {

                        Toast.makeText(this@Register, "Zaregistrovaný",Toast.LENGTH_LONG).show()
                        startActivity(intent)

                    } else {

                        if (response.code() == 409) {
                            Toast.makeText(this@Register, "E-mail sa už používa",Toast.LENGTH_LONG).show()
                            startActivity(intent1)
                        }

                        if (response.code() == 400) {
                            Toast.makeText(this@Register, "Neplatný e-mail",Toast.LENGTH_LONG).show()
                            startActivity(intent1)
                        }

                    }
                }
            }
        }

    }


}