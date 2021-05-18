package com.munna.ecomkotlin.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.munna.ecomkotlin.R
import com.munna.ecomkotlin.model.CityModel
import com.munna.ecomkotlin.model.StateModel
import org.json.JSONObject


class RegistrationActivity : AppCompatActivity() {
    lateinit var register_username: EditText
    lateinit var register_mobileno: EditText
    lateinit var register_address: EditText
    lateinit var register_emailid: EditText
    lateinit var register_password: EditText
    lateinit var register_spin_stateid: Spinner
    lateinit var register_spin_cityid: Spinner
    lateinit var register_register: Button
    lateinit var tv_login: TextView
    lateinit var progressDialog: ProgressDialog
    var statelist = ArrayList<StateModel>()
    var citylist = ArrayList<CityModel>()
    var stateid_str: String? = "0"
    var statename_str: String? = "Select"
    var cityid_str: String? = "0"
    var cityname_str: String? = "Select"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_registration)
            register_username = findViewById(R.id.register_username)
            register_mobileno = findViewById(R.id.register_mobileno)
            register_address = findViewById(R.id.register_address)
            register_emailid = findViewById(R.id.register_emailid)
            register_password = findViewById(R.id.register_password)
            register_spin_stateid = findViewById(R.id.register_spin_stateid)
            register_spin_cityid = findViewById(R.id.register_spin_cityid)
            register_register = findViewById(R.id.register_register)
            tv_login = findViewById(R.id.tv_login)

            progressDialog = ProgressDialog(RegistrationActivity@ this)
            progressDialog.setCancelable(false)
            progressDialog.setTitle("Please Wait...")

            getStateMethod()
            getCityMethod()

            tv_login.setOnClickListener({
                try {
                    startActivity(Intent(RegistrationActivity@ this, LoginActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })

            register_register.setOnClickListener({
                if (register_username.text.toString().trim().length > 0) {
                    if (register_mobileno.text.toString().trim().length > 0) {
                        if (register_address.text.toString().trim().length > 0) {
                            if (register_emailid.text.toString().trim().length > 0) {
                                if (register_password.text.toString().trim().length > 0) {
                                    registerUserMethod()
                                } else {
                                    register_password.setError("")
                                    register_password.requestFocus()
                                    register_password.isFocusable = true
                                }
                            } else {
                                register_emailid.setError("")
                                register_emailid.requestFocus()
                                register_emailid.isFocusable = true
                            }
                        } else {
                            register_address.setError("")
                            register_address.requestFocus()
                            register_address.isFocusable = true
                        }
                    } else {
                        register_mobileno.setError("")
                        register_mobileno.requestFocus()
                        register_mobileno.isFocusable = true
                    }
                } else {
                    register_username.setError("")
                    register_username.requestFocus()
                    register_username.isFocusable = true
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getStateMethod() {
        try {
            statelist.clear()
            var stringRequest =
                object : StringRequest(Request.Method.POST,
                    getString(R.string.baseurl) + "GetState",
                    Response.Listener { response ->
                        progressDialog.dismiss()
                        var jsonObject = JSONObject(response)
                        if (jsonObject.getString("Status").equals("Success")) {
                            var jsonArray = jsonObject.getJSONArray("Data")
                            for (i in 0 until jsonArray.length()) {
                                var jsonObject2 = jsonArray.getJSONObject(i)
                                statelist.add(
                                    StateModel(
                                        jsonObject2.getString("stateid"), jsonObject2.getString(
                                            "statename"
                                        )
                                    )
                                )
                            }
                            val adapter: ArrayAdapter<StateModel> = ArrayAdapter<StateModel>(
                                this,
                                android.R.layout.simple_spinner_item,
                                statelist
                            )
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            register_spin_stateid.setAdapter(adapter)

                            register_spin_stateid.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>,
                                        view: View,
                                        position: Int,
                                        id: Long
                                    ) {
                                        stateid_str = statelist.get(position).stateid
                                        statename_str = statelist.get(position).statename
                                        println("Stateid001=" + stateid_str)
                                        println("Stateid002=" + statename_str)
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>) {
                                        // write code to perform some action
                                    }
                                }
                        } else {
                            Toast.makeText(
                                RegistrationActivity@ this,
                                jsonObject.getString("Message"),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    },
                    Response.ErrorListener { error ->
                        progressDialog.dismiss()
                        Toast.makeText(
                            RegistrationActivity@ this,
                            "Network Error:-" + error.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        var map = HashMap<String, String>();
                        return map
                    }
                }
            var requestQueue = Volley.newRequestQueue(RegistrationActivity@ this)
            requestQueue.add(stringRequest)
            progressDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getCityMethod() {
        try {
            citylist.clear()
            var stringRequest =
                object : StringRequest(Request.Method.POST,
                    getString(R.string.baseurl) + "GetCity",
                    Response.Listener { response ->
                        progressDialog.dismiss()
                        var jsonObject = JSONObject(response)
                        if (jsonObject.getString("Status").equals("Success")) {
                            var jsonArray = jsonObject.getJSONArray("Data")
                            for (i in 0 until jsonArray.length()) {
                                var jsonObject2 = jsonArray.getJSONObject(i)
                                citylist.add(
                                    CityModel(
                                        jsonObject2.getString("stateid"), jsonObject2.getString(
                                            "statename"
                                        )
                                    )
                                )
                            }
                            val adapter: ArrayAdapter<CityModel> = ArrayAdapter<CityModel>(
                                this,
                                android.R.layout.simple_spinner_item,
                                citylist
                            )
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            register_spin_cityid.setAdapter(adapter)

                            register_spin_cityid.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>,
                                        view: View,
                                        position: Int,
                                        id: Long
                                    ) {
                                        cityid_str = citylist.get(position).cityid
                                        cityname_str = citylist.get(position).cityname
                                        println("Stateid003=" + cityid_str)
                                        println("Stateid004" + cityname_str)
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>) {
                                        // write code to perform some action
                                    }
                                }
                        } else {
                            Toast.makeText(
                                RegistrationActivity@ this,
                                jsonObject.getString("Message"),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    },
                    Response.ErrorListener { error ->
                        progressDialog.dismiss()
                        Toast.makeText(
                            RegistrationActivity@ this,
                            "Network Error:-" + error.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        var map = HashMap<String, String>();
                        return map
                    }
                }
            var requestQueue = Volley.newRequestQueue(RegistrationActivity@ this)
            requestQueue.add(stringRequest)
            progressDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun registerUserMethod() {
        try {
            var stringRequest =
                object : StringRequest(Request.Method.POST,
                    getString(R.string.baseurl) + "UserRegistration",
                    Response.Listener { response ->
                        progressDialog.dismiss()
                        var jsonObject = JSONObject(response)
                        if (jsonObject.getString("Status").equals("Success")) {
                            Toast.makeText(
                                RegistrationActivity@ this,
                                "Registration Successfully",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                RegistrationActivity@ this,
                                jsonObject.getString("Message"),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    },
                    Response.ErrorListener { error ->
                        progressDialog.dismiss()
                        Toast.makeText(
                            RegistrationActivity@ this,
                            "Network Error:-" + error.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        var map = HashMap<String, String>();
                        map.put("username", register_username.text.toString().trim())
                        map.put("mobileno", register_mobileno.text.toString().trim())
                        map.put("address", register_address.text.toString().trim())
                        map.put("emailid", register_emailid.text.toString().trim())
                        map.put("password", register_password.text.toString().trim())
                        map.put("stateid", "33")
                        map.put("cityid", "3306")
                        map.put("userstatus", "1")
                        map.put("userid", "1")
                        return map
                    }
                }
            var requestQueue = Volley.newRequestQueue(RegistrationActivity@ this)
            requestQueue.add(stringRequest)
            progressDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}