package com.example.myadventuregame.ui.main

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myadventuregame.R
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
            if (viewModel.westSecretFound && viewModel.crowbar) {
                Navigation.findNavController(view).navigate(R.id.action_westBedroomFrag_to_secretRoomFragment)
                viewModel.lastRoomFragment = "secretRoom"
            }
            else
                activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }
        westBedroomBinding.westButton.setOnClickListener {
            activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }
        westBedroomBinding.eastButton.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.mainFragment)
            viewModel.lastRoomFragment = "entrance"
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
            val alreadySearchedString : String = "You have already searched this room and found a " +
                    "secret room to the north."
            val yesString : String = "After prying open the hole a bit more with the crowbar you see " +
                    "a room inside. You have discovered a secret room to the north!"
            val noString : String = "A shame I guess your curiosity won't be satisfied today."
            val yesButton : Button = popupView.findViewById<View>(R.id.search_again_button) as Button
            val noButton : Button = popupView.findViewById<View>(R.id.fight_button) as Button

            if (viewModel.westSecretFound) {
                popupView.findViewById<TextView>(R.id.intro_text).text = alreadySearchedString
            }
            else if (viewModel.crowbar) {
                popupView.findViewById<TextView>(R.id.intro_text).text = crowbarSearchString
                yesButton.visibility = View.VISIBLE
                noButton.visibility = View.VISIBLE

                // Popup Button Listeners
                yesButton.setOnClickListener {
                    viewModel.westSecretFound = true
                    yesButton.visibility = View.INVISIBLE
                    noButton.visibility = View.INVISIBLE
                    popupView.findViewById<TextView>(R.id.result_text).text = yesString
                }
                noButton.setOnClickListener {
                    yesButton.visibility = View.INVISIBLE
                    noButton.visibility = View.INVISIBLE
                    popupView.findViewById<TextView>(R.id.result_text).text = noString
                }
            }
            else
                popupView.findViewById<TextView>(R.id.intro_text).text = searchString
        }
        westBedroomBinding.inventoryButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.inventoryFrag)
        }

    }
}