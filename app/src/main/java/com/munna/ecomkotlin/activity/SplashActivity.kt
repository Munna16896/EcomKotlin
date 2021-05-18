package com.munna.ecomkotlin.activity

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.munna.ecomkotlin.R

class SplashActivity : AppCompatActivity() {
    lateinit var sharedpreference: SharedPreferences
    lateinit var userid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedpreference = getSharedPreferences("login", MODE_PRIVATE)
        userid = sharedpreference.getString("userid", "").toString()
        println("check8700="+userid)

        requestPermissionMethod()
    }

    private fun requestPermissionMethod() {
        try {
            Dexter.withContext(SplashActivity@ this)
                .withPermissions(
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_WIFI_STATE
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                        if (p0?.areAllPermissionsGranted() == true) {
                            openNextPage()
                        } else if (p0?.isAnyPermissionPermanentlyDenied == true) {

                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        TODO("Not yet implemented")
                    }
                })
                .onSameThread().check()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openNextPage() {
        try {
            println("check0001")
            if (userid.equals("")) {
                println("check0002")
                Handler().postDelayed(Runnable {
                    startActivity(Intent(SplashActivity@ this, LoginActivity::class.java))
                    finish()
                },2000)

            } else {
                println("check0003")
                Handler().postDelayed(Runnable {
                    startActivity(Intent(SplashActivity@ this, MainActivity::class.java))
                    finish()
                },2000)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
