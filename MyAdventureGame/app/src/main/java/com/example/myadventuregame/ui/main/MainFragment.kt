package com.example.myadventuregame.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myadventuregame.R
import com.example.myadventuregame.databinding.MainActivityBinding
import com.example.myadventuregame.databinding.MainFragmentBinding

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Initializing my view-model and binding variables
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        // TODO: Use the ViewModel
        entranceFragmentBinding.eastButton.setOnClickListener {
            entranceFragmentBinding.eastButton.text = "Test text"
            Log.i("TAG", "east click")
        }

    }

}
