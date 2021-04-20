package com.example.mtaa_strbakd_hanzelt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

class change_account_data : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_account_data)

        val imageButton1: ImageButton = findViewById(R.id.home_icon)
        imageButton1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val imageButton: ImageButton = findViewById(R.id.back)
        imageButton.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        val email: EditText = findViewById(R.id.email_change_input)
        val password: EditText = findViewById(R.id.password_change_input)

        val Button: Button = findViewById(R.id.button_change_data)
        Button.setOnClickListener {
            var email_string = email.text.toString()
            var password_string = password.text.toString()
            val intent = Intent(this, Profile::class.java)
            val intent1 = Intent(this, change_account_data::class.java)


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

                val response = service.update(token, requestBody)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {

                        Toast.makeText(this@change_account_data, "Zmenené", Toast.LENGTH_LONG).show()
                        startActivity(intent)

                    } else {
                        Toast.makeText(this@change_account_data, "E-mail je obsadený", Toast.LENGTH_LONG).show()
                        startActivity(intent1)
                    }
                }
            }

        }
    }
}