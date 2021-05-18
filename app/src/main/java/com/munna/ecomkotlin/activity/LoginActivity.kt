package com.munna.ecomkotlin.activity

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.munna.ecomkotlin.R
import org.json.JSONObject
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    lateinit var login_username: EditText
    lateinit var login_password: EditText
    lateinit var login_login: Button
    lateinit var sharedpreference: SharedPreferences
    lateinit var progressDialog: ProgressDialog
    lateinit var tv_regsiter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedpreference = getSharedPreferences("login", MODE_PRIVATE)
        login_username = findViewById(R.id.login_username)
        login_password = findViewById(R.id.login_password)
        login_login = findViewById(R.id.login_login)
        tv_regsiter = findViewById(R.id.tv_regsiter)

        progressDialog = ProgressDialog(LoginActivity@ this)
        progressDialog.setCancelable(false)
        progressDialog.setTitle("Please Wait...")

        login_login.setOnClickListener({
            if (login_username.text.length > 0) {
                if (login_password.text.length > 0) {
                    loginMethod()
                } else {
                    login_password.setError("Please Enter Password")
                    login_password.requestFocus()
                    login_password.isFocusable = true
                }
            } else {
                login_username.setError("Please Enter UserName")
                login_username.requestFocus()
                login_username.isFocusable = true
            }
        })

        tv_regsiter.setOnClickListener({
            startActivity(Intent(LoginActivity@ this, RegistrationActivity::class.java))
            finish()
        })
    }

    fun loginMethod() {
        var stringReuqest = object : StringRequest(
            Request.Method.POST,
            getString(R.string.baseurl) + "UserLogin",
            Response.Listener { response ->
                try {
                    progressDialog.dismiss()
                    var jsonObject = JSONObject(response)
                    println("checklogin001=" + jsonObject.getString("userid"))
                    println("checklogin002=" + jsonObject.getString("username"))
                    println("checklogin003=" + jsonObject.getString("mobileno"))

                    var intent = Intent(LoginActivity@ this, MainActivity::class.java)
                    val sharedPreferences: SharedPreferences.Editor = sharedpreference.edit()
                    sharedPreferences.putString("userid", jsonObject.getString("userid"))
                    sharedPreferences.putString("username", jsonObject.getString("username"))
                    sharedPreferences.putString("usermobileno", jsonObject.getString("mobileno"))
                    sharedPreferences.apply()
                    sharedPreferences.commit()
                    startActivity(intent)
                    finish()

//                    var jsonArray = jsonObject.getJSONArray("Data")
//                    for (i in 0 until jsonArray.length()) {
//                        var jsonObject2 = jsonArray.getJSONObject(i)
//
//                    }
                } catch (e: Exception) {
                    progressDialog.dismiss()
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                progressDialog.dismiss()
                Toast.makeText(this, "NetworkError:-" + error.toString(), Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                var map = HashMap<String, String>();
                map.put("mobileno", login_username.text.toString().trim())
                map.put("password", login_password.text.toString().trim())
                return map
            }
        }
        var requestQueue = Volley.newRequestQueue(LoginActivity@ this)
        requestQueue.add(stringReuqest)
        progressDialog.show()
    }
}