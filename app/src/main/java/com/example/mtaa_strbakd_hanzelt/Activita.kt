package com.example.mtaa_strbakd_hanzelt

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

var idactivity = 0
val pictureList: MutableList<String> = ArrayList()

class Activita : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activita)

        val city=intent.getStringExtra("city")
        val name: TextView = findViewById(R.id.mesto)
        name.text = city

        val type=intent.getStringExtra("type")
        val typeid=intent.getIntExtra("type_id", 0)
        val idcity=intent.getIntExtra("id", 0)
        idactivity=intent.getIntExtra("activity_id", 0)

        callInformations()

        val nameAktivity = intent.getStringExtra("name")
        val nameAktivita:TextView = findViewById(R.id.type_activity)
        nameAktivita.text = nameAktivity

        val imageButton: ImageButton = findViewById(R.id.back)
        imageButton.setOnClickListener {
            val intent = Intent(this, activities::class.java)
            intent.putExtra("city", city)
            intent.putExtra("type", type)
            intent.putExtra("type_id", typeid)
            intent.putExtra("id", idcity)
            pictureList.clear()
            startActivity(intent)
        }
        val imageButton1: ImageButton = findViewById(R.id.profile_icon)
        imageButton1.setOnClickListener {
            pictureList.clear()
            if(token.isNotEmpty()) {
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
            }
            else {
                val intent = Intent(this, login_registr::class.java)
                startActivity(intent)
            }
        }

        val imageButton2: ImageButton = findViewById(R.id.home_icon)
        imageButton2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            pictureList.clear()
            startActivity(intent)
        }

        val Button: Button = findViewById(R.id.gallery_btn)
        Button.setOnClickListener{
            val intent = Intent(this, gallery::class.java)
            intent.putExtra("city", city)
            intent.putExtra("name", nameAktivity)
            intent.putExtra("type", type)
            intent.putExtra("type_id", typeid)
            intent.putExtra("id", idcity)
            intent.putExtra("activity_id", idactivity)

            startActivity(intent)
        }
    }

    private fun callInformations() {
        val url = "https://e95a4d3de6d8.ngrok.io/activities/activity/?id=$idactivity"
        val queue = Volley.newRequestQueue(this)
        val description_activity: TextView = findViewById(R.id.descriptionView)
        val address_activity: TextView = findViewById(R.id.address_activity)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                        val description = response.getString("text")
                        description_activity.text = description
                        val address = response.getString("address")
                        address_activity.text = address
                        val photos = response.getJSONArray("photos")
                                for (i in 0 until photos.length()) {
                               pictureList.add(photos[i] as String)
                            }
                },
                Response.ErrorListener { error ->
                    println("kdesi je chybička")
                }
        )
        queue.add(jsonObjectRequest)
    }
}
