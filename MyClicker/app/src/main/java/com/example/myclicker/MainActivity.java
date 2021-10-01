package com.example.myclicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.example.myclicker.databinding.ActivityMainBinding;
import com.example.myclicker.databinding.ActivityUpgradesBinding;

public class MainActivity extends AppCompatActivity {
    // CONSTANTS
    private final String pickUpgradeString = "Pick Upgrade: ";
    private final String minerUpgradeString = "Buy a Miner: ";
    private final int popUpWidth = 800;
    private final int popUpHeight = 1100;

    private ActivityMainBinding binding;
    private ActivityUpgradesBinding upgradesBinding;
    private MechanicDataManager mechanicDataManager;

    private final String TAG = "Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        upgradesBinding = ActivityUpgradesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mechanicDataManager = new ViewModelProvider(this).get(MechanicDataManager.class);
        mechanicDataManager.initializer();
        mechanicDataManager.startMinerTimer();
        binding.crystalNumber.setText(mechanicDataManager.getCrystals().getValue().toString());
//        Log.i(TAG, mechanicDataManager.getCrystals().getValue().toString());

        final Observer<Integer> timeObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer crystalValue) {
                binding.crystalNumber.setText(crystalValue.toString());
            }
        };

        mechanicDataManager.getCrystals().observe(this, timeObserver);

        binding.crystalMine.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
//                        Log.i(TAG, mechanicDataManager.getCrystals().getValue().toString());
                        mechanicDataManager.addCrystals(mechanicDataManager.getCrystalsPerSwing().getValue());
                        binding.crystalNumber.setText(String.valueOf(mechanicDataManager.getCrystals()
                                                                        .getValue().toString()));
                    }
                }
        );

        // Dealing with all of the different upgrade Buttons
        binding.upgradeButton.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    displayPopupWindow();
                    upgradesBinding.pickUpgrade.setText(pickUpgradeString +
                            mechanicDataManager.getPickUpgradeCost().getValue().toString());
                    upgradesBinding.minerButton.setText(minerUpgradeString +
                            mechanicDataManager.getMinerCost().getValue().toString());
                }
            }
        );

        upgradesBinding.pickUpgrade.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        mechanicDataManager.pickUpgrade();
                        upgradesBinding.pickUpgrade.setText(pickUpgradeString +
                                mechanicDataManager.getPickUpgradeCost().getValue().toString());
                        binding.crystalNumber.setText(String.valueOf(mechanicDataManager.getCrystals()
                                .getValue().toString()));
                    }
                }
        );

        upgradesBinding.minerButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        mechanicDataManager.addMiner();
                        upgradesBinding.minerButton.setText(minerUpgradeString +
                                mechanicDataManager.getMinerCost().getValue().toString());
                        binding.crystalNumber.setText(String.valueOf(mechanicDataManager.getCrystals()
                                .getValue().toString()));
                    }
                }
        );


    }

    // This controls the upgrades window
    public void displayPopupWindow() {
//        upgradesBinding = ActivityUpgradesBinding.inflate(getLayoutInflater());
        View view = upgradesBinding.getRoot();
        final PopupWindow popup = new PopupWindow(this);
        popup.setContentView(view);
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setWidth(popUpWidth);
        popup.setHeight(popUpHeight);
        popup.showAtLocation(view, Gravity.CENTER, 0, 0);
        popup.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

//        mechanicDataManager = new ViewModelProvider(this).get(MechanicDataManager.class);

        // This section controls all the upgrade mechanics
//        upgradesBinding.minerButton.setText("Buy a Miner: " + minerCost);
//        upgradesBinding.minerButton.setOnClickListener(
//            new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    if (crystals >= minerCost) {
////                        crystals -= minerCost;
////                        minerCost += 1; //TODO get rid of hardcoded values both here, above, and below
////                        miners += 1;
////
////                        mechanicDataManager.startTimer();
////
////                        binding2.minerButton.setText("Buy a Miner: " + minerCost);
////                        binding.crystalNumber.setText(String.valueOf(crystals));
////                    }
////                    else {
////                        // Somehow print that they r dumb
//////                        crystals = 0;
//////                        binding.crystalNumber.setText(String.valueOf(crystals));
////                    }
//                }
//        });
//        // The Popupwindow code was taken and edited from
//        //https://stackoverflow.com/questions/41277504/darken-background-after-popup-window-opened-android
//        // This is for the pick upgrade
//        upgradesBinding.pickUpgrade.setText("Upgrade Pick : " + pickCost);
//        upgradesBinding.pickUpgrade.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        if (crystals >= pickCost) {
////                            crystals -= pickCost;
////                            pickCost += 1;
////                            crystalsPerSwing += 1;
////                            binding2.pickUpgrade.setText("Upgrade Pick: " + pickCost);
////                            binding.crystalNumber.setText(String.valueOf(crystals));
////                        }
////                        else {
////                            // Somehow print that they r dumb
//////                            crystals = 0;
//////                            binding.crystalNumber.setText(String.valueOf(crystals));
////                        }
//                    }
//                });

    }

}