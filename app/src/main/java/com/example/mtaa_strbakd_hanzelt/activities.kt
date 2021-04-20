package com.example.mtaa_strbakd_hanzelt

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.io.FileOutputStream


val nameList: MutableList<String> = ArrayList()
val descList: MutableList<String> = ArrayList()
val imageList: MutableList<String> = ArrayList()

class activities : AppCompatActivity() {

    private var idcity = 0
    private var typeid = 0
    private var city = ""
    private var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        idcity=intent.getIntExtra("id", 0)
        typeid=intent.getIntExtra("type_id", 0)

        callActivities()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities)

        city= intent.getStringExtra("city").toString()
        val name: TextView = findViewById(R.id.mesto)
        name.text = city

        type= intent.getStringExtra("type").toString()
        val name1: TextView = findViewById(R.id.type_activity)
        name1.text = type

        val imageButton: ImageButton = findViewById(R.id.back)
        imageButton.setOnClickListener {
            val intent = Intent(this, types_activity::class.java)
            intent.putExtra("city", city)
            intent.putExtra("id", idcity)
            nameList.clear()
            descList.clear()
            imageList.clear()
            startActivity(intent)
        }
        val imageButton1: ImageButton = findViewById(R.id.profile_icon)
        imageButton1.setOnClickListener {
            nameList.clear()
            descList.clear()
            imageList.clear()
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
            nameList.clear()
            descList.clear()
            imageList.clear()
            startActivity(intent)
        }
    }

    private fun callActivities() {
        val url = "https://e95a4d3de6d8.ngrok.io/activities/?city=$idcity&activity_type=$typeid"
        val queue = Volley.newRequestQueue(this)
        val idList: MutableList<Int> = ArrayList()

        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                        for (i in 0 until response.length()) {
                            val activity: JSONObject = response.getJSONObject(i)
                            val name = activity.getString("name")
                            val tdescription = activity.getString("thumbnail_description")
                            val descpic = activity.getString("thumbnail_image")
                            val idactivity = activity.getInt("id")
                            idList.add(idactivity)
                            nameList.add(name)
                            descList.add(tdescription)
                            imageList.add(descpic)
                            var listView = findViewById<ListView>(R.id.listView);
                            val customAdptor = CustomAdptor(this)
                            listView.adapter=customAdptor
                            listView.setOnItemClickListener{ adapterView, view, i, l ->
                                Toast.makeText(this, nameList[i],Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, Activita::class.java)
                                intent.putExtra("city", city)
                                intent.putExtra("type", type)
                                intent.putExtra("type_id", typeid)
                                intent.putExtra("id", idcity)
                                intent.putExtra("name", nameList[i])
                                intent.putExtra("activity_id", idList[i])
                                startActivity(intent)
                                nameList.clear()
                                descList.clear()
                                imageList.clear()
                            }
                        }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Nič sa tu nenachádza",Toast.LENGTH_SHORT).show()
                }
        )
        queue.add(jsonObjectRequest)
    }
}

class CustomAdptor(private val context: Activity): BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.layoutInflater
        val view1 = inflater.inflate(R.layout.row_data,null)
        val fimage = view1.findViewById<ImageView>(R.id.fImage)
        var fName = view1.findViewById<TextView>(R.id.fname)
        var fDesc = view1.findViewById<TextView>(R.id.fdesc)

        val string = imageList[p0].substring(2, imageList[p0].length - 1)
        val imageBytes = Base64.decode(string, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        fimage.setImageBitmap(decodedImage)

        fName.setText(nameList[p0])
        fDesc.setText(descList[p0])
        return view1
    }

    override fun getItem(p0: Int): Any {
        val string = imageList[p0].substring(2, imageList[p0].length - 1)
        val imageBytes = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return imageList.size
    }

}