package com.example.myadventuregame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myadventuregame.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }
    }

    public fun removeView(){
        removeView()
    }
}