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

class gallery : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)


        val city=intent.getStringExtra("city")
        val name: TextView = findViewById(R.id.mesto_gallery)
        name.text = city

        val nameAktivity=intent.getStringExtra("name")
        val nameAkt: TextView = findViewById(R.id.konkretna_activita)
        nameAkt.text = nameAktivity

        val type=intent.getStringExtra("type")
        val typeid=intent.getIntExtra("type_id", 0)
        val idcity=intent.getIntExtra("id", 0)
        idactivity=intent.getIntExtra("activity_id", 0)


        val imageButton: ImageButton = findViewById(R.id.back)
        imageButton.setOnClickListener {
            val intent = Intent(this, Activita::class.java)
            intent.putExtra("city", city)
            intent.putExtra("type", type)
            intent.putExtra("type_id", typeid)
            intent.putExtra("id", idcity)
            intent.putExtra("name", nameAktivity)
            intent.putExtra("activity_id", idactivity)
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

        var photosView = findViewById<ListView>(R.id.photosView);
        val customAdaptor = CustomAdaptor(this)
        photosView.adapter=customAdaptor
    }
}

class CustomAdaptor(private val context: Activity): BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.layoutInflater
        val view1 = inflater.inflate(R.layout.pictures,null)
        val fimage = view1.findViewById<ImageView>(R.id.fPicture)
        val string = pictureList[p0].substring(2, pictureList[p0].length - 1)
        val imageBytes = Base64.decode(string, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        fimage.setImageBitmap(decodedImage)
        return view1
    }

    override fun getItem(p0: Int): Any {
        val string = pictureList[p0].substring(2, pictureList[p0].length - 1)
        val imageBytes = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)


    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return pictureList.size
    }

}

