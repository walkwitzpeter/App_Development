package com.example.myadventuregame.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.myadventuregame.R
import com.example.myadventuregame.databinding.MainActivityBinding
import com.example.myadventuregame.databinding.MainFragmentBinding
import androidx.fragment.app.FragmentTransaction


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var entranceFragmentBinding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        entranceFragmentBinding = MainFragmentBinding.inflate(layoutInflater)

        return entranceFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val unavailableText: String = "You look but see nowhere to go that way."

        // Navigation Buttons
        entranceFragmentBinding.northButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.northStairwellFrag)
            viewModel.lastRoomFragment = "stairwell"
        }
        entranceFragmentBinding.westButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.westBedroomFrag)
            viewModel.lastRoomFragment = "westBedroom"
        }
        entranceFragmentBinding.eastButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.eastBedroomFrag)
            viewModel.lastRoomFragment = "eastBedroom"
        }
        entranceFragmentBinding.southButton.setOnClickListener {
            activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }

        // Interaction Buttons
        entranceFragmentBinding.searchButton.setOnClickListener {
            activity?.let { it1 -> viewModel.displayMessage("Nothing interesting here", it1) }
        }
        entranceFragmentBinding.inventoryButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.inventoryFrag)
        }
    }

}
