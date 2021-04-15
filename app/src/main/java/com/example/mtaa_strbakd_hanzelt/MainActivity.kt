package com.example.mtaa_strbakd_hanzelt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.search)
        button.setOnClickListener {
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }
        val button1: Button = findViewById(R.id.add_activitka)
        button1.setOnClickListener {
            val intent = Intent(this, Add_activity1::class.java)
            startActivity(intent)
        }

    }
}