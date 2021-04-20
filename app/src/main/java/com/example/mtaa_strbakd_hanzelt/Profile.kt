package com.example.mtaa_strbakd_hanzelt

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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


class Profile : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val imageButton1: ImageButton = findViewById(R.id.home_icon)
        imageButton1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val button: Button = findViewById(R.id.change_data_btn)
        button.setOnClickListener {
            val intent = Intent(this, change_account_data::class.java)
            startActivity(intent)
        }

        val logoutbtn: Button = findViewById(R.id.logout_btn)
        logoutbtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            token = ""
            Toast.makeText(applicationContext,"Odhlásený",Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }


        val email: TextView = findViewById(R.id.email_profile)
        email.text = login_email

        val button1: Button = findViewById(R.id.delete_acc_btn)
        button1.setOnClickListener{

            val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = inflater.inflate(R.layout.popup_delete,null)

            val popupWindow = PopupWindow(
                    view,
                    LinearLayout.LayoutParams.WRAP_CONTENT, //
                    LinearLayout.LayoutParams.WRAP_CONTENT //
            )

            val tv = view.findViewById<Button>(R.id.delete_sure_btn)
            val buttonPopup = view.findViewById<Button>(R.id.delete_notsure_btn)
            val xko = view.findViewById<TextView>(R.id.xko)

            tv.setOnClickListener{
                popupWindow.dismiss()
                val intent = Intent(this, MainActivity::class.java)
                val intent1 = Intent(this, Profile::class.java)

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://e95a4d3de6d8.ngrok.io/")
                    .build()

                val service = retrofit.create(APIService::class.java)

                CoroutineScope(Dispatchers.IO).launch {

                    val response = service.delete(token)

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {

                            Toast.makeText(applicationContext,"Účet bol zmazaný",Toast.LENGTH_SHORT).show()
                            token = ""
                            login_email = ""
                            startActivity(intent)
                        } else {

                            if (response.code() == 404) {
                                Toast.makeText(applicationContext, "Nepodarilo sa zmazať účet", Toast.LENGTH_LONG).show()
                                startActivity(intent1)
                            }
                        }
                    }
                }
            }

            xko.setOnClickListener{
                popupWindow.dismiss()
                Toast.makeText(applicationContext,"Zrušené",Toast.LENGTH_SHORT).show()
            }

            buttonPopup.setOnClickListener{
                popupWindow.dismiss()
                Toast.makeText(applicationContext,"Zrušené",Toast.LENGTH_SHORT).show()
            }

            TransitionManager.beginDelayedTransition(findViewById(R.id.my_profile))
            popupWindow.showAtLocation(
                    findViewById(R.id.my_profile),
                    Gravity.CENTER,
                    0,
                    0
            )
        }
    }
}