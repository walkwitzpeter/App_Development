package com.example.myadventuregame.ui.main

import android.R
import android.app.Activity
import android.app.Notification
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


class MainViewModel : ViewModel() {
    var crowbar : Boolean = false
    var westSearched : Boolean = false

    // Displaying a toast message (Overloaded)
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

}