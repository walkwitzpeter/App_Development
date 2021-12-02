package com.example.myadventuregame.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myadventuregame.R
import com.example.myadventuregame.databinding.WestSecretRoomBinding

class SecretRoomFrag : Fragment() {
    companion object {
        fun newInstance() = SecretRoomFrag()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var secretRoomBinding: WestSecretRoomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        secretRoomBinding = WestSecretRoomBinding.inflate(layoutInflater)

        return secretRoomBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val unavailableText: String = "You look but see nowhere to go that way."
        val deathString : String = "You died to the skeleton!"

        // Navigation Buttons
        secretRoomBinding.northButton.setOnClickListener {
            activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }
        secretRoomBinding.westButton.setOnClickListener {
            activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }
        secretRoomBinding.eastButton.setOnClickListener {
            activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }
        secretRoomBinding.southButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.westBedroomFrag)
            viewModel.lastRoomFragment = "westBedroom"
        }

        // Interaction Buttons
        secretRoomBinding.searchButton.setOnClickListener {
            secretRoomBinding.westSecretLayout.background = resources.getDrawable(R.drawable.secret_room)
            secretRoomBinding.deathText.text = deathString
            secretRoomBinding.toolbar3.visibility = View.INVISIBLE
            // Restarting the game after 5 seconds
            Handler().postDelayed(Runnable {
                context?.let { it1 -> viewModel.triggerRebirth(it1, nextIntent = null) }
            }, 5000)
        }
        secretRoomBinding.inventoryButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.inventoryFrag)
        }

    }
}