package com.example.myadventuregame.ui.main

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myadventuregame.R
import com.example.myadventuregame.databinding.FragEastBedroomBinding
import com.example.myadventuregame.databinding.FragWestBedroomBinding

class WestBedroomFrag : Fragment() {

    companion object {
        fun newInstance() = WestBedroomFrag()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var westBedroomBinding: FragWestBedroomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        westBedroomBinding = FragWestBedroomBinding.inflate(layoutInflater)

        return westBedroomBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val unavailableText : String = "You look but see nowhere to go that way."

        // Navigation Buttons
        westBedroomBinding.northButton.setOnClickListener {
            if (viewModel.westSearched && viewModel.crowbar) {
                Navigation.findNavController(view).navigate(R.id.action_westBedroomFrag_to_secretRoomFragment)
            }
            else
                activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }
        westBedroomBinding.westButton.setOnClickListener {
            activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }
        westBedroomBinding.eastButton.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.mainFragment)
        }
        westBedroomBinding.southButton.setOnClickListener {
            activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }

        //Activity Buttons
        westBedroomBinding.searchButton.setOnClickListener {
            val popupView: View = LayoutInflater.from(activity)
                .inflate(com.example.myadventuregame.R.layout.west_bedroom_search, null)
            val popupWindow = PopupWindow(popupView, 500, 800)
            popupWindow.isOutsideTouchable = true
            popupWindow.isFocusable = true
            popupWindow.width = 800
            popupWindow.height = 1300
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

            // PopUp Window Variations
            val crowbarSearchString : String = "You search every nook and cranny and find a tiny hole " +
                    "on the north wall that you can probably pry open with your crowbar, do you widen it?"
            val searchString : String = "You search everywhere, but see nothing you could further " +
                    "explore with what you have. It's possible this room just holds danger. Maybe " +
                    "you should just leave."
            val yesButton : Button = popupView.findViewById<View>(R.id.yes_button) as Button
            val noButton : Button = popupView.findViewById<View>(R.id.no_button) as Button

            if (viewModel.crowbar) {
                popupView.findViewById<TextView>(R.id.intro_text).text = crowbarSearchString
                yesButton.visibility = View.VISIBLE
                noButton.visibility = View.VISIBLE
                viewModel.westSearched = true
            }
            else
                popupView.findViewById<TextView>(R.id.intro_text).text = searchString
        }

    }
}