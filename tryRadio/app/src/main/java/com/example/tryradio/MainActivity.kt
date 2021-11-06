package com.example.tryradio

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import com.example.tryradio.ui.main.MainFragment
import com.example.tryradio.databinding.MainActivityBinding
import com.example.tryradio.ui.main.MoreInfoFrag

class MainActivity : AppCompatActivity(),
               MoreInfoFrag.OnFragmentInteractionListener {

    private lateinit var binding: MainActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}