package com.example.biggernumbergame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.biggernumbergame.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NumberManager numberManager; // = new NumberManager(1, 2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        numberManager = new ViewModelProvider(this).get(NumberManager.class);
        binding.scoreNumber.setText(String.valueOf(numberManager.getScore()));

        if (numberManager.getNumber1() == 1 && numberManager.getNumber2() == 2){
            numberManager.newValues();
        }

        binding.leftButton.setText(String.valueOf(numberManager.getNumber1()));
        binding.rightButton.setText(String.valueOf(numberManager.getNumber2()));

        binding.leftButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if (numberManager.testResponse(numberManager.getNumber1())) {
                            binding.scoreNumber.setText(String.valueOf(numberManager.getScore()));
                            changeNums();
                        }
                        else {
                            changeNums();
                        }
                    }
                }
        );

        binding.rightButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if (numberManager.testResponse(numberManager.getNumber2())) {
                            binding.scoreNumber.setText(String.valueOf(numberManager.getScore()));
                            changeNums();
                        }
                        else {
                            changeNums();
                        }
                    }
                }
        );
    }

    public void changeNums () {
        numberManager.newValues();
        binding.leftButton.setText(String.valueOf(numberManager.getNumber1()));
        binding.rightButton.setText(String.valueOf(numberManager.getNumber2()));
    }

}