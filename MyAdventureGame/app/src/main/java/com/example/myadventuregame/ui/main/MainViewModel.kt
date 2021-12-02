package com.example.myadventuregame.ui.main

import android.R
import android.app.Activity
import android.app.Notification
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import android.widget.TextView
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import androidx.lifecycle.viewModelScope
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK

import android.content.Intent
import android.os.CountDownTimer
import androidx.navigation.Navigation
import com.example.myadventuregame.MainActivity
import android.content.DialogInterface



class MainViewModel : ViewModel() {
    var crowbar : Boolean = false
    var westSecretFound : Boolean = false
    var lastRoomFragment : String = "entrance"
    var upstairsKey : Boolean = false

    var timerDeath : Boolean = true
    var timerStarted : Boolean = false

    // Displaying a toast message (Overloaded) (just in case)
    fun displayMessage(text: String, activity: Activity) {
        val myToast : Toast = Toast.makeText(activity, text, Toast.LENGTH_LONG)
        myToast.setGravity(Gravity.CENTER, 0, 0)
        myToast.show()
    }
    fun displayMessage(text: String, activity: FragmentActivity) {
        val myToast : Toast = Toast.makeText(activity, text, Toast.LENGTH_LONG)
        myToast.setGravity(Gravity.CENTER, 0, 0)
        myToast.show()
    }

    // Restarting function
    fun triggerRebirth(context: Context, nextIntent: Intent?) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        if (context is Activity) {
            (context as Activity).finish()
        }
        Runtime.getRuntime().exit(0)
    }

    // Finding the last fragment for the inventory "back" button
    fun findLastRoom(view : View) {
        when (lastRoomFragment) {
            "entrance" -> {
                Navigation.findNavController(view).navigate(com.example.myadventuregame.R.id.mainFragment)
            }
            "eastBedroom" -> {
                Navigation.findNavController(view).navigate(com.example.myadventuregame.R.id.eastBedroomFrag)
            }
            "westBedroom" -> {
                Navigation.findNavController(view).navigate(com.example.myadventuregame.R.id.westBedroomFrag)
            }
            "secretRoom" -> {
                Navigation.findNavController(view).navigate(com.example.myadventuregame.R.id.secretRoomFragment)
            }
            "stairwell" -> {
                Navigation.findNavController(view).navigate(com.example.myadventuregame.R.id.northStairwellFrag)
            }
        }
    }


}


