package com.example.myadventuregame.ui.main

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.provider.Settings
import android.util.Log
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
import com.example.myadventuregame.databinding.NorthStairwellBinding
import android.media.AudioManager




class NorthStairwellFrag : Fragment() {
    companion object {
        fun newInstance() = NorthStairwellFrag()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var stairwellBinding: NorthStairwellBinding
    private lateinit var player: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        stairwellBinding = NorthStairwellBinding.inflate(layoutInflater)

        return stairwellBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting up sounds
        player = MediaPlayer.create(activity, R.raw.squeaky_floor)
        player.isLooping = false

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val unavailableText: String = "You look but see nowhere to go that way."
        val northString: String = "The door is locked!"

        // Navigation Buttons
        stairwellBinding.northButton.setOnClickListener {
            if (viewModel.upstairsKey) {
                Navigation.findNavController(view).navigate(R.id.mainFragment)
            }
            else {
                activity?.let { it1 -> viewModel.displayMessage(northString, it1) }
//                player.start()
            }
        }
        stairwellBinding.westButton.setOnClickListener {
            activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }
        stairwellBinding.eastButton.setOnClickListener {
            activity?.let { it1 -> viewModel.displayMessage(unavailableText, it1) }
        }
        stairwellBinding.southButton.setOnClickListener {
            if (! viewModel.timerStarted) {
                Navigation.findNavController(view).navigate(R.id.mainFragment)
                viewModel.lastRoomFragment = "entrance"
            }
        }

        // Interaction Buttons
        stairwellBinding.inventoryButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.inventoryFrag)
        }

        stairwellBinding.searchButton.setOnClickListener {

            val searchString : String = "You frantically search for a way into the room hoping to slam " +
                    "the door on whatever is coming up the stairs behind you! After a few very long " +
                    "seconds you find a key which fits into the lock. You turn and hear a click, you " +
                    "can now go through, you better hurry!"
            val fightString : String = "The ominous creaking scares you too much, you'd rather trust " +
                    "your fists than trying to find a key. Unfortunately when you turn around you see " +
                    "a banshee and you realize there is no way you can fight something that is incorporeal." +
                    " Goodbye."
            val deathString : String = "You have died to the Banshee"

            val popupView: View = LayoutInflater.from(activity)
                .inflate(com.example.myadventuregame.R.layout.stairwell_search, null)
            val popupWindow = PopupWindow(popupView, 500, 800)
            popupWindow.isOutsideTouchable = true
            popupWindow.isFocusable = true
            popupWindow.width = 800
            popupWindow.height = 1300
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)
            player.start()

            val searchButton : Button = popupView.findViewById<View>(R.id.search_again_button) as Button
            val fightButton : Button = popupView.findViewById<View>(R.id.fight_button) as Button

            // Timer if the user doesn't get to the north in time
            val timer = object: CountDownTimer(25000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    viewModel.timerStarted = true
                    player.start()
                    Log.i("TAG", "Tick")
                }

                override fun onFinish() {
                    Log.i("TAG", "Finish")
                    player.stop()
                    if (viewModel.timerDeath) {
                        stairwellBinding.backgroundLayout.background = resources.getDrawable(R.drawable.banshee)
                        stairwellBinding.deathText2.text = deathString
                        stairwellBinding.toolbar.visibility = View.INVISIBLE
                        popupView.visibility = View.INVISIBLE
                        viewModel.timerStarted = false
                        Handler().postDelayed(Runnable {
                            context?.let { it1 -> viewModel.triggerRebirth(it1, nextIntent = null) }
                        }, 5000)
                    }
                }
            }.start()

            searchButton.setOnClickListener {
                fightButton.visibility = View.INVISIBLE
                searchButton.visibility = View.INVISIBLE
                viewModel.upstairsKey = true
                popupView.findViewById<TextView>(R.id.result_text).text = searchString
            }

            fightButton.setOnClickListener {
                fightButton.visibility = View.INVISIBLE
                searchButton.visibility = View.INVISIBLE
                popupView.findViewById<TextView>(R.id.result_text).text = fightString
            }

        }

    }
}