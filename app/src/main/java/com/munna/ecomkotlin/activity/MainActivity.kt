package com.munna.ecomkotlin.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.munna.ecomkotlin.R

class MainActivity : AppCompatActivity() {
    lateinit var main_btnlogout:Button
    lateinit var sharedpreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_btnlogout=findViewById(R.id.main_btnlogout)
        sharedpreference = getSharedPreferences("login", MODE_PRIVATE)

        main_btnlogout.setOnClickListener({
            var intent=Intent(MainActivity@this,LoginActivity::class.java)
            val sharedPreferences: SharedPreferences.Editor = sharedpreference.edit()
            sharedPreferences.putString("userid", "")
            sharedPreferences.putString("username", "")
            sharedPreferences.putString("usermobileno", "")
            sharedPreferences.apply()
            sharedPreferences.commit()
            startActivity(intent)
            finish()
        })
    }
}