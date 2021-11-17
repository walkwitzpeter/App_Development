package com.example.myadventuregame.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myadventuregame.R
import com.example.myadventuregame.databinding.FragWestBedroomBinding
import com.example.myadventuregame.databinding.WestSecretRoomBinding

class SecretRoomFragment : Fragment() {
    companion object {
        fun newInstance() = SecretRoomFragment()
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
        }

    }
}