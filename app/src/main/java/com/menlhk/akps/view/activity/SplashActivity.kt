package com.menlhk.akps.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.menlhk.akps.R
import java.lang.Exception

class SplashActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        val background = object : Thread(){
            override fun run() {
                try {
                    Thread.sleep(5000)
                    val intent = Intent(baseContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }

        background.start()
    }
}