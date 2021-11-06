package com.example.tryradio.ui.main

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.tryradio.R
import com.example.tryradio.databinding.MainFragmentBinding
import com.example.tryradio.databinding.MoreInfoFragBinding

class MoreInfoFrag: Fragment() {
    private  var _binding: MoreInfoFragBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoreInfoFragBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set the textview field
        val seasonObserver = Observer<String> {
                season->binding.textView.text = season.toString()
        }
        viewModel.getSeason().observe(viewLifecycleOwner,seasonObserver)




        //Click listener for backButton
        binding.backButton.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_moreInfoFrag_to_mainFragment)
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    interface OnFragmentInteractionListener{
        fun onFragmentInteraction(uri: Uri)
    }
}