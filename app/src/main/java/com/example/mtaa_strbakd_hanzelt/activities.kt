package com.example.mtaa_strbakd_hanzelt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class activities : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities)

        val city=intent.getStringExtra("city")
        val name: TextView = findViewById(R.id.mesto)
        name.text = city

        val type=intent.getStringExtra("type")
        val name1: TextView = findViewById(R.id.type_activity)
        name1.text = type

        val imageButton: ImageButton = findViewById(R.id.back)
        imageButton.setOnClickListener {
            val intent = Intent(this, types_activity::class.java)
            intent.putExtra("city", city)
            startActivity(intent)
        }
        val imageButton1: ImageButton = findViewById(R.id.profile_icon)
        imageButton1.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        val imageButton2: ImageButton = findViewById(R.id.home_icon)
        imageButton2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val button: Button = findViewById(R.id.button6)
        button.setOnClickListener {
            val intent = Intent(this, Activita::class.java)
            intent.putExtra("city", city)
            intent.putExtra("type", type)
            startActivity(intent)
        }
    }
}