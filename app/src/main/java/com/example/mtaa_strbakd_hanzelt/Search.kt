package com.example.mtaa_strbakd_hanzelt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView

class Search : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val imageButton: ImageButton = findViewById(R.id.back)
        imageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
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

        val button: Button = findViewById(R.id.button3)
        button.setOnClickListener {
            val intent = Intent(this, login_registr::class.java)
            startActivity(intent)
        }

        val buttonAdd: Button = findViewById(R.id.choose_city)
        val popupMenu = PopupMenu(this, buttonAdd)

        popupMenu.menuInflater.inflate(R.menu.menu_cities, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            buttonAdd.text = menuItem.title
            val intent = Intent(this, types_activity::class.java)
            intent.putExtra("city", menuItem.title)
            startActivity(intent)

            false
        }

        buttonAdd.setOnClickListener {
            popupMenu.show()
        }
    }


}