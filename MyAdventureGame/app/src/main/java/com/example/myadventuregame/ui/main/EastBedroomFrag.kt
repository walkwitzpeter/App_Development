package com.example.myadventuregame.ui.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myadventuregame.MainActivity
import com.example.myadventuregame.R
import com.example.myadventuregame.databinding.FragEastBedroomBinding
import com.example.myadventuregame.databinding.MainFragmentBinding
import android.view.WindowManager
import com.example.myadventuregame.databinding.EastBedroomSearchBinding
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer


class EastBedroomFrag : Fragment() {

    companion object {
        fun newInstance() = EastBedroomFrag()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var eastBedroomBinding: FragEastBedroomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        eastBedroomBinding = FragEastBedroomBinding.inflate(layoutInflater)

        return eastBedroomBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val unavailableText : String = "You look but see nowhere to go that way."

        // Navigation Buttons
        eastBedroomBinding.northButton.setOnClickListener {
            activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }
        eastBedroomBinding.westButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.mainFragment)
        }
        eastBedroomBinding.eastButton.setOnClickListener {
            activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }
        eastBedroomBinding.southButton.setOnClickListener {
            activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }

        // Search Room Button
        eastBedroomBinding.searchButton.setOnClickListener {
            val popupView: View = LayoutInflater.from(activity)
                .inflate(com.example.myadventuregame.R.layout.east_bedroom_search, null)
            val popupWindow = PopupWindow(popupView, 500, 800)
            popupWindow.isOutsideTouchable = true
            popupWindow.isFocusable = true
            popupWindow.width = 800
            popupWindow.height = 1300
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

            val yesString : String = "As you start to pry up the floorboard a flood of spiders rushes " +
                    "at you making you scream and jump onto the bed. However it turns out the spiders " +
                    "weren't coming for you, they just wanted to escape. Finally looking down you see " +
                    "a crowbar which you take."
            val noString : String = "Spiders can only mean bad news, if there are constantly a few " +
                    "trickling out imagine how many are truly in there they likely would have killed " +
                    "you with their poison. Wise choice."

            // PopUp Window Buttons
            val yesButton : Button = popupView.findViewById<View>(R.id.yes_button) as Button
            val noButton : Button = popupView.findViewById<View>(R.id.no_button) as Button
            yesButton.setOnClickListener {
                popupView.findViewById<TextView>(R.id.result_text).text = yesString
                viewModel.crowbar = true
            }
            noButton.setOnClickListener {
                popupView.findViewById<TextView>(R.id.result_text).text = noString
            }
        }
        
    }
}