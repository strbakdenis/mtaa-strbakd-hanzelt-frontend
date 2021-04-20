package com.example.mtaa_strbakd_hanzelt

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit


private var number = 0
var addedPictureList: JSONArray = JSONArray()

class Add_activity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_activity1)

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

        val buttonChoose: Button = findViewById(R.id.choose_type)
        val popupMenu = PopupMenu(this, buttonChoose)
        var typaktivity = 0

        popupMenu.menuInflater.inflate(R.menu.menu_popup, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            if (id == R.id.menu_sport) {
                buttonChoose.text = "Šport"
                typaktivity = 1
            }
            else if (id == R.id.menu_historia) {
                buttonChoose.text = "História"
                typaktivity = 2
            }
            else if (id == R.id.menu_turistika) {
                buttonChoose.text = "Turistika"
                typaktivity = 3
            }
            else if (id == R.id.menu_zabava) {
                buttonChoose.text = "Zábava"
                typaktivity = 4
            }
            else if (id == R.id.menu_relax) {
                buttonChoose.text = "Relax"
                typaktivity = 5
            }
            else if (id == R.id.menu_kultura) {
                buttonChoose.text = "Kultúra"
                typaktivity = 6
            }

            false
        }

        buttonChoose.setOnClickListener {
            popupMenu.show()
        }

        val buttonAdd: ImageButton = findViewById(R.id.add_button)
        buttonAdd.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                number = 1
                pickImageFromGallery()
            }
        }
        val buttonAdd1: ImageButton = findViewById(R.id.add_button1)
        buttonAdd1.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                number = 2
                pickImageFromGallery()
            }
        }
        val buttonAdd2: ImageButton = findViewById(R.id.add_button2)
        buttonAdd2.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                number = 3
                pickImageFromGallery()
            }
        }
        val buttonAdd3: ImageButton = findViewById(R.id.add_button3)
        buttonAdd3.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                number = 4
                pickImageFromGallery()
            }
        }

        val city: EditText = findViewById(R.id.city_add_input)
        //  typaktivity = cislo, ktore chceme
        val name: EditText = findViewById(R.id.name_add_input)
        val address: EditText = findViewById(R.id.address_add_input)
        val thumbdesc : EditText = findViewById(R.id.thumbnail_desc_add_input)
        val description: EditText = findViewById(R.id.description_add_input)

        val ButtonAddActivity: Button = findViewById(R.id.add_activity_btn)
        ButtonAddActivity.setOnClickListener{
            var city_string = city.text.toString()
            var name_string = name.text.toString()
            var address_string = address.text.toString()
            var thumbdesc_string = thumbdesc.text.toString()
            var description_string = description.text.toString()

            val intent = Intent(this, MainActivity::class.java)
            val intent1 = Intent(this, Add_activity1::class.java)

            val httpClient = OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)

            val retrofit = Retrofit.Builder()
                .baseUrl("https://e95a4d3de6d8.ngrok.io/")
                .client(httpClient.build())
                .build()

            val service = retrofit.create(APIService::class.java)

            val jsonObject = JSONObject()
            jsonObject.put("city", city_string)
            jsonObject.put("name", name_string)
            jsonObject.put("activity_type", typaktivity)
            jsonObject.put("address", address_string)
            jsonObject.put("thumbnail_description", thumbdesc_string)
            jsonObject.put("description", description_string)
            jsonObject.put("photos", addedPictureList)

            val jsonObjectString = jsonObject.toString()

            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

            CoroutineScope(Dispatchers.IO).launch {

                val response = service.add(token, requestBody)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {

                        Toast.makeText(applicationContext, "Aktivita pridaná",Toast.LENGTH_LONG).show()
                        startActivity(intent)
                    } else {

                        if (response.code() == 409) {
                            Toast.makeText(applicationContext, "Aktivita už existuje",Toast.LENGTH_LONG).show()
                            startActivity(intent1)
                        }

                        if (response.code() == 400) {
                            Toast.makeText(applicationContext, "Aktivitu sa nepodarilo pridať",Toast.LENGTH_LONG).show()
                            startActivity(intent1)
                        }
                    }
                }
            }
        }
    }
    private fun pickImageFromGallery() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val picture: ImageView = findViewById(R.id.add_button)
            val picture1: ImageView = findViewById(R.id.add_button1)
            val picture2: ImageView = findViewById(R.id.add_button2)
            val picture3: ImageView = findViewById(R.id.add_button3)

            if(number == 1) {
                picture.setImageURI(data?.data)
                val selectedfile: Uri? = data?.data

                if (selectedfile != null) {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedfile)
                    val outputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    val byteArray = outputStream.toByteArray()

                    val encodedString = Base64.encodeToString(byteArray, Base64.NO_WRAP)
                    addedPictureList.put(encodedString)

                }
            }
            else if(number == 2) {
                picture1.setImageURI(data?.data)
                val selectedfile: Uri? = data?.data

                if (selectedfile != null) {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, selectedfile)
                    val outputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    val byteArray = outputStream.toByteArray()

                    val encodedString = Base64.encodeToString(byteArray, Base64.NO_WRAP)
                    addedPictureList.put(encodedString)
                }
            }
            else if(number == 3) {
                picture2.setImageURI(data?.data)
                val selectedfile: Uri? = data?.data

                if (selectedfile != null) {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, selectedfile)
                    val outputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    val byteArray = outputStream.toByteArray()

                    val encodedString = Base64.encodeToString(byteArray, Base64.NO_WRAP)
                    addedPictureList.put(encodedString)
                }
            }
            else if(number == 4) {
                picture3.setImageURI(data?.data)
                    val selectedfile: Uri? = data?.data

                    if (selectedfile != null) {
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, selectedfile)
                        val outputStream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        val byteArray = outputStream.toByteArray()

                        val encodedString = Base64.encodeToString(byteArray, Base64.NO_WRAP)
                        addedPictureList.put(encodedString)
                    }
            }
        }
    }
}