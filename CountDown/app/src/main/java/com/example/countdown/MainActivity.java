package com.example.countdown;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.countdown.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TimeViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.countText.setText("0");

        model = new ViewModelProvider(this).get(TimeViewModel.class);

        final Observer<Integer> timeObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer timeValue) {
                binding.countText.setText(timeValue.toString());
            }
        };

        final Observer<Boolean> finishObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.countText.setText("Complete");
            }
        };

        model.getSeconds().observe(this,timeObserver);
        model.getFinished().observe(this, finishObserver);

        binding.startButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if (binding.timerText.getText().equals("")) {
                            binding.countText.setText("Enter a Time");
                        } else {
                            Long timeMilli = Long.parseLong(
                                    binding.timerText.getText().toString()) * 1000;
                            model.setWaitTime(timeMilli);
                            model.startTimer();
                        }
                    }
                }
        );

        binding.stopButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick (View v) {
                        model.stopTimer();
                    }
                }
        );

    }

}