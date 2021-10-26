package com.example.introtokotlin.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.example.introtokotlin.R
import com.example.introtokotlin.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(  			// Constructor between()
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {  					// It will return a view
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroup.setOnCheckedChangeListener { group: RadioGroup, checkedId ->
            if (checkedId == R.id.radioSpring) {
                Log.i("SEASON", binding.radioSpring.text.toString())
                binding.selectionView.text = binding.radioSpring.text.toString()
            } else if (checkedId == R.id.radioSummer) {
                Log.i("SEASON", binding.radioSummer.text.toString())
                binding.selectionView.text = binding.radioSummer.text.toString()
            } else if (checkedId == R.id.radioFall) {
                Log.i("SEASON", binding.radioWinter.text.toString())
                binding.selectionView.text = binding.radioFall.text.toString()
            } else if (checkedId == R.id.radioWinter) {
                Log.i("SEASON", binding.radioWinter.text.toString())
                binding.selectionView.text = binding.radioWinter.text.toString()
            }
        }
    }

}