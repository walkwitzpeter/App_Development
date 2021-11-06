package com.example.tryradio.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.navigation.Navigation
import com.example.tryradio.R
import com.example.tryradio.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val TAG = "SEASON"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        if (viewModel.getSeason().value.toString() == "Spring")
            binding.radioGroup.check(R.id.radioSpring)
        else if (viewModel.getSeason().value.toString() == "Summer")
            binding.radioGroup.check(R.id.radioSummer)
        else if (viewModel.getSeason().value.toString() == "Fall")
            binding.radioGroup.check(R.id.radioFall)
        else if (viewModel.getSeason().value.toString() == "Winter")
            binding.radioGroup.check(R.id.radioWinter)

        //Set from view model
        binding.checkBox1.isChecked = viewModel.getCheckValue("checkBox1")
        binding.checkBox2.isChecked = viewModel.getCheckValue("checkBox2")
        binding.checkBox3.isChecked = viewModel.getCheckValue("checkBox3")
        binding.checkBox4.isChecked = viewModel.getCheckValue("checkBox4")


        //Notice that it is checked
        binding.checkBox1.setOnClickListener { view ->
            viewModel.setCheck(binding.checkBox1.isChecked(), binding.checkBox1.text.toString())
        }
        binding.checkBox2.setOnClickListener { view ->
            viewModel.setCheck(binding.checkBox2.isChecked(), binding.checkBox2.text.toString())
        }
        binding.checkBox3.setOnClickListener { view ->
            viewModel.setCheck(binding.checkBox3.isChecked(), binding.checkBox3.text.toString())
        }
        binding.checkBox4.setOnClickListener { view ->
            viewModel.setCheck(binding.checkBox4.isChecked(), binding.checkBox4.text.toString())
        }

        
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioSpring) {
                Log.i("SEASON", binding.radioSpring.text.toString())
                //binding.selectionView.text = binding.radioSpring.text.toString()
                viewModel.setSeason(binding.radioSpring.text.toString())
                Navigation.findNavController(view)
                    .navigate(R.id.action_mainFragment_to_moreInfoFrag)
            } else if (checkedId == R.id.radioSummer) {
                Log.i("SEASON", binding.radioSummer.text.toString())
                viewModel.setSeason(binding.radioSummer.text.toString())
                Navigation.findNavController(view)
                    .navigate(R.id.action_mainFragment_to_moreInfoFrag)
            } else if (checkedId == R.id.radioFall) {
                Log.i("SEASON", binding.radioFall.text.toString())
                viewModel.setSeason(binding.radioFall.text.toString())
                Navigation.findNavController(view)
                    .navigate(R.id.action_mainFragment_to_moreInfoFrag)
            } else {
                Log.i("SEASON", binding.radioWinter.text.toString())
                viewModel.setSeason(binding.radioWinter.text.toString())
                Navigation.findNavController(view)
                    .navigate(R.id.action_mainFragment_to_moreInfoFrag)
            }

            //debug statements
            Log.i(TAG +"3", binding.checkBox1.isChecked().toString())
            Log.i(TAG + "4", binding.checkBox2.isChecked().toString())
            Log.i(TAG + "5 ", binding.checkBox3.isChecked().toString())
            Log.i(TAG + "6", binding.checkBox4.isChecked().toString())

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}