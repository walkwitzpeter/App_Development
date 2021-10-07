package com.example.myclicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myclicker.databinding.ActivityDiamondMineBinding;
import com.example.myclicker.databinding.ActivityUpgradesBinding;

public class DiamondMine extends AppCompatActivity {

    private ActivityDiamondMineBinding diamondBinding;
    private ActivityUpgradesBinding upgradesBinding;
    private MechanicDataManager mechanicDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diamondBinding = ActivityDiamondMineBinding.inflate(getLayoutInflater());
        upgradesBinding = ActivityUpgradesBinding.inflate(getLayoutInflater());

        View view = diamondBinding.getRoot();
        setContentView(view);

        mechanicDataManager = new ViewModelProvider(this).get(MechanicDataManager.class);
        mechanicDataManager.diamondInitializer();

        final Observer<Integer> timeObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer diamondValue) {
                diamondBinding.diamondNumber.setText(diamondValue.toString());
            }
        };

        mechanicDataManager.getDiamonds().observe(this, timeObserver);

        diamondBinding.diamondMine.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mechanicDataManager.addDiamonds(1);
                    }
                }
        );

        diamondBinding.worldButton2.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
//                        finishActivity(1);
                        Intent myIntent = new Intent(DiamondMine.this, MainActivity.class);
                        DiamondMine.this.startActivity(myIntent);
                    }
                }
        );
    }
}