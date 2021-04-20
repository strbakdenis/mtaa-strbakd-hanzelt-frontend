package com.example.mtaa_strbakd_hanzelt

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class Search : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val keyList = mutableMapOf("Vybrať" to 190191991)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        jsonik()

        val imageButton: ImageButton = findViewById(R.id.back)
        imageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val imageButton1: ImageButton = findViewById(R.id.profile_icon)
        imageButton1.setOnClickListener {

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
            startActivity(intent)
        }
    }

    private fun jsonik() {
        val url = "https://e95a4d3de6d8.ngrok.io/cities/"
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                var spinner:Spinner ? = null
                var arrayAdapter:ArrayAdapter<String> ? = null
                val itemList: MutableList<String> = ArrayList()
                itemList.add("Vybrať")

                for (i in 0 until response.length()) {
                    val city: JSONObject = response.getJSONObject(i)
                    val name = city.getString("name")
                    val idcity = city.getInt("id")
                    itemList.add(name)
                    keyList[name] = idcity
                }
                spinner = findViewById(R.id.spinner_choose_city)
                arrayAdapter =
                        ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, itemList)
                spinner?.adapter = arrayAdapter
                spinner?.onItemSelectedListener = this
            },
            Response.ErrorListener { error ->
                println("kdesi je chybička")
            }
        )

        queue.add(jsonObjectRequest)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(applicationContext, "Nothing Select", Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        var items:String = parent?.getItemAtPosition(position) as String
        if ("$items" != "Vybrať") {
            Toast.makeText(applicationContext, "$items", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, types_activity::class.java)
            intent.putExtra("city", "$items")
            intent.putExtra("id", keyList["$items"])
            startActivity(intent)
        }
    }
}