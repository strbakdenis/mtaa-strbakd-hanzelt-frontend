package com.example.mtaa_strbakd_hanzelt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

var token = ""
var login_email = ""

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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


        val email: EditText = findViewById(R.id.input_email_login)
        val password: EditText = findViewById(R.id.input_password_login)

        val Button: Button = findViewById(R.id.button_login)
        Button.setOnClickListener{
            var emailstring = email.text.toString()
            var passwordstring = password.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            val intent1 = Intent(this, Login::class.java)

            val retrofit = Retrofit.Builder()
                .baseUrl("https://e95a4d3de6d8.ngrok.io/")
                .build()

            val service = retrofit.create(APIService::class.java)

            val jsonObject = JSONObject()
            jsonObject.put("email_address", emailstring)
            jsonObject.put("password", passwordstring)

            val jsonObjectString = jsonObject.toString()

            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

            CoroutineScope(Dispatchers.IO).launch {

                val response = service.login(requestBody)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {


                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(
                            JsonParser.parseString(
                                response.body()
                                    ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                            )
                        )
                        token = prettyJson.substring(14, prettyJson.length - 3)
                        login_email = emailstring
                        Toast.makeText(this@Login, "Úspešné prihlásenie", Toast.LENGTH_LONG).show()
                        startActivity(intent)

                    } else {

                        if (response.code() == 404) {
                            Toast.makeText(this@Login, "Zlé prihlasovacie údaje", Toast.LENGTH_LONG).show()
                            startActivity(intent1)
                        }


                    }
                }
            }
        }

    }
}