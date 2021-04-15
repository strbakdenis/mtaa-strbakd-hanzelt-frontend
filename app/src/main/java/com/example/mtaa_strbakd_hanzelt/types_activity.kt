package com.example.mtaa_strbakd_hanzelt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class types_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_types_activity)

        val city=intent.getStringExtra("city")
        val name: TextView = findViewById(R.id.mesto)
        name.text = city

        val imageButton: ImageButton = findViewById(R.id.back)
        imageButton.setOnClickListener {
            val intent = Intent(this, Search::class.java)
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


        val imageButton3: ImageButton = findViewById(R.id.sport_button)
        imageButton3.setOnClickListener {
            val intent = Intent(this, activities::class.java)
            intent.putExtra("city", city)
            intent.putExtra("type", "Šport")
            startActivity(intent)
        }
        val imageButton4: ImageButton = findViewById(R.id.history_button)
        imageButton4.setOnClickListener {
            val intent = Intent(this, activities::class.java)
            intent.putExtra("city", city)
            intent.putExtra("type", "História")
            startActivity(intent)
        }
        val imageButton5: ImageButton = findViewById(R.id.hiking_button)
        imageButton5.setOnClickListener {
            val intent = Intent(this, activities::class.java)
            intent.putExtra("city", city)
            intent.putExtra("type", "Turistika")
            startActivity(intent)
        }
        val imageButton6: ImageButton = findViewById(R.id.funny_button)
        imageButton6.setOnClickListener {
            val intent = Intent(this, activities::class.java)
            intent.putExtra("city", city)
            intent.putExtra("type", "Zábava")
            startActivity(intent)
        }
        val imageButton7: ImageButton = findViewById(R.id.relax_button)
        imageButton7.setOnClickListener {
            val intent = Intent(this, activities::class.java)
            intent.putExtra("city", city)
            intent.putExtra("type", "Relax")
            startActivity(intent)
        }
        val imageButton8: ImageButton = findViewById(R.id.culture_button)
        imageButton8.setOnClickListener {
            val intent = Intent(this, activities::class.java)
            intent.putExtra("city", city)
            intent.putExtra("type", "Kultúra")
            startActivity(intent)
        }
    }

}