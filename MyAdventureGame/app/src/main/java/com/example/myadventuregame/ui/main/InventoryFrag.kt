package com.example.myadventuregame.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myadventuregame.MainActivity
import com.example.myadventuregame.databinding.InventoryBinding
import com.example.myadventuregame.databinding.MainActivityBinding
import com.example.myadventuregame.databinding.MainFragmentBinding

class InventoryFrag : Fragment() {
    companion object {
        fun newInstance() = InventoryFrag()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var inventoryBinding: InventoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inventoryBinding = InventoryBinding.inflate(layoutInflater)

        return inventoryBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val mainAct : MainActivity = MainActivity()

        inventoryBinding.closeInventory.setOnClickListener {
            viewModel.findLastRoom(view)
        }

        // Seeing if the user has obtained items
        val obtained : String = "Obtained"
        if (viewModel.crowbar) {
            inventoryBinding.crowbarObtained.text = obtained
        }

    }

}