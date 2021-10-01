package com.example.countclicks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.countclicks.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        binding.count.setText(Integer.toString(myViewModel.getNumberCount()));

        binding.countButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        myViewModel.countClick();
                        binding.count.setText(Integer.toString(myViewModel.getNumberCount()));
                    }});
    }
}