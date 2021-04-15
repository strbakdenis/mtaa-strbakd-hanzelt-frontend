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

        val button1: Button = findViewById(R.id.delete_acc_btn)
        button1.setOnClickListener{

            val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.popup_delete,null)

            // Initialize a new instance of popup window
            val popupWindow = PopupWindow(
                    view, // Custom view to show in popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT // Window height
            )

            // Get the widgets reference from custom view
            val tv = view.findViewById<Button>(R.id.delete_sure_btn)
            val buttonPopup = view.findViewById<Button>(R.id.delete_notsure_btn)
            val xko = view.findViewById<TextView>(R.id.xko)

            // Set click listener for popup window's text view
            tv.setOnClickListener{
                // Change the text color of popup window's text view
                popupWindow.dismiss()
                Toast.makeText(applicationContext,"Zmazané",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            xko.setOnClickListener{
                popupWindow.dismiss()
                Toast.makeText(applicationContext,"Zrušené",Toast.LENGTH_SHORT).show()
            }

            // Set a click listener for popup's button widget
            buttonPopup.setOnClickListener{
                // Dismiss the popup window
                popupWindow.dismiss()
                Toast.makeText(applicationContext,"Zrušené",Toast.LENGTH_SHORT).show()
            }

            // Finally, show the popup window on app
            TransitionManager.beginDelayedTransition(findViewById(R.id.my_profile))
            popupWindow.showAtLocation(
                    findViewById(R.id.my_profile), // Location to display popup window
                    Gravity.CENTER, // Exact position of layout to display popup
                    0, // X offset
                    0 // Y offset
            )
        }

    }


}