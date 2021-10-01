package com.example.eventexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import java.util.Random;

import com.example.eventexample.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Handler mHandler;

    // Function for Random Colors when button is pushed
    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256),
                            rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Changing Text Color
        binding.myButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        binding.statusText.setText("Button Clicked");
                        binding.statusText.setTextColor(getRandomColor());
                        binding.myButton.setText("hi");
                    }
                }
        );

        // Changing Text Color Continually
        binding.myButton.setOnLongClickListener(
            new Button.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    binding.statusText.setText("Long Button Clicked");

                    // To get the colors to keep changing
                    // (although they don't stop changing I couldn't figure that part out)
                    if (mHandler == null) {
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 500);
                    }
                    return false;
                }
            }
        );

        // Changing the Background Color
        binding.backgroundButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        binding.background.setBackgroundColor(getRandomColor());
                    }
                }
        );

        //Changing the Button Color
        binding.buttonButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        v.setBackgroundColor(getRandomColor());
                        binding.backgroundButton.setBackgroundColor(getRandomColor());
                        binding.myButton.setBackgroundColor(getRandomColor());
                    }
                }
        );

    }

    //A "runnable" action that lets something repeat its action
    Runnable mAction = new Runnable() {
        @Override public void run() {
            binding.statusText.setTextColor(getRandomColor());
            mHandler.postDelayed(this, 500);
        }
    };

}