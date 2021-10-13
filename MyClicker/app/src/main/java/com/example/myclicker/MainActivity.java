package com.example.myclicker;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.myclicker.databinding.ActivityHardResetBinding;
import com.example.myclicker.databinding.ActivityMainBinding;
import com.example.myclicker.databinding.ActivityUpgradesBinding;
// For saving state after App Closes
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    // CONSTANTS
    private final String pickUpgradeString = "Pick Upgrade: ";
    private final String minerUpgradeString = "Buy a Miner: ";
    private final String cartUpgradeString = "Buy a Minecart: ";
    private final String softResetString = "Mystery Reset: ";
    private final int popUpWidth = 800;
    private final int popUpHeight = 1100;

    private ActivityMainBinding binding;
    private ActivityUpgradesBinding upgradesBinding;
    private ActivityHardResetBinding hardResetBinding;
    private MechanicDataManager mechanicDataManager;

    private final String TAG = "Debug";


    // Saving state after app closes (Methods/ideas taken from the following website)
    // https://stackoverflow.com/questions/151777/how-can-i-save-an-activity-state-using-the-save-instance-state
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("MyCrystals", mechanicDataManager.getCrystals().getValue());
        savedInstanceState.putInt("MyCrystalsPerSwing", mechanicDataManager.getCrystalsPerSwing().getValue());
        savedInstanceState.putInt("MyPickCost", mechanicDataManager.getPickUpgradeCost().getValue());
        savedInstanceState.putInt("MyMinerCost", mechanicDataManager.getMinerCost().getValue());
        savedInstanceState.putInt("MyMiners", mechanicDataManager.getMiners().getValue());
        savedInstanceState.putInt("MyMinecartCost", mechanicDataManager.getMinecartCost().getValue());
        savedInstanceState.putInt("MyCrystalsPerMiner", mechanicDataManager.crystalsPerMiner);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)   {
        super.onRestoreInstanceState(savedInstanceState);
        int savedCrystals = savedInstanceState.getInt("MyCrystals");
        int savedCrystalsPerSwing = savedInstanceState.getInt("MyCrystalsPerSwing");
        int savedPickCost = savedInstanceState.getInt("MyPickCost");
        int savedMinerCost = savedInstanceState.getInt("MyMinerCost");
        int savedMiners = savedInstanceState.getInt("MyMiners");
        int savedMinecartCost = savedInstanceState.getInt("MyMinecartCost");
        int savedCrystalsPerMiner = savedInstanceState.getInt("MyCrystalsPerMiner");
        mechanicDataManager.setCrystals(savedCrystals);
        mechanicDataManager.setCrystalsPerSwing(savedCrystalsPerSwing);
        mechanicDataManager.setPickCost(savedPickCost);
        mechanicDataManager.setMinerCost(savedMinerCost);
        mechanicDataManager.setMiners(savedMiners);
        mechanicDataManager.setMinecartCost(savedMinecartCost);
        mechanicDataManager.crystalsPerMiner = savedCrystalsPerMiner;

        // These are used to restart/set the things needed at startup
        binding.crystalNumber.setText(mechanicDataManager.getCrystals().getValue().toString());
        mechanicDataManager.startMinerTimer();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        upgradesBinding = ActivityUpgradesBinding.inflate(getLayoutInflater());
        hardResetBinding = ActivityHardResetBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        mechanicDataManager = new ViewModelProvider(this).get(MechanicDataManager.class);
        mechanicDataManager.initializer();
        mechanicDataManager.startMinerTimer();
        binding.crystalNumber.setText(mechanicDataManager.getCrystals().getValue().toString());
        mechanicDataManager.startIdleTimer();

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
                        mechanicDataManager.addCrystals(mechanicDataManager.getCrystalsPerSwing().getValue());
                    }
                }
        );

        // Dealing with all of the different upgrade Buttons
        binding.upgradeButton.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    displayUpgradeWindow();
                    upgradesBinding.pickUpgrade.setText(pickUpgradeString +
                            mechanicDataManager.getPickUpgradeCost().getValue().toString());
                    upgradesBinding.minerButton.setText(minerUpgradeString +
                            mechanicDataManager.getMinerCost().getValue().toString());
                    upgradesBinding.minecartUpgrade.setText(cartUpgradeString +
                            mechanicDataManager.getMinecartCost().getValue().toString());
                    upgradesBinding.softResetButton.setText(softResetString +
                            mechanicDataManager.softResetCost);
                }
            }
        );

        binding.hardResetButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        displayResetWarning();
                    }
                }
        );

        // Idea for link to other activity (other mine) taken from this website
        // https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        binding.worldButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent myIntent = new Intent(MainActivity.this, DiamondMine.class);
//                        myIntent.putExtra("dataManager", mechanicDataManager.getDiamonds().getValue());
                        // see if I can pass in my mechanic data manager
//                        myIntent.putExtra("mechanicDataManager", mechanicDataManager);
//                        MainActivity.this.startActivity(myIntent);
                    }
                }
        );

    }


    // This controls the upgrades window (Taken from the website below)
    // https://stackoverflow.com/questions/41277504/darken-background-after-popup-window-opened-android
    public void displayUpgradeWindow() {
        View view = upgradesBinding.getRoot();
        final PopupWindow popup = new PopupWindow(this);
        popup.setContentView(view);
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setWidth(popUpWidth);
        popup.setHeight(popUpHeight);
        popup.showAtLocation(view, Gravity.CENTER, 0, 0);
        popup.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        upgradesBinding.pickUpgrade.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        mechanicDataManager.pickUpgrade();
                        upgradesBinding.pickUpgrade.setText(pickUpgradeString +
                                mechanicDataManager.getPickUpgradeCost().getValue().toString());
                    }
                }
        );

        upgradesBinding.minerButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        mechanicDataManager.addMiner();
                        upgradesBinding.minerButton.setText(minerUpgradeString +
                                mechanicDataManager.getMinerCost().getValue().toString());
                    }
                }
        );

        upgradesBinding.minecartUpgrade.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        mechanicDataManager.minecartUpgrade();
                        upgradesBinding.minecartUpgrade.setText(cartUpgradeString +
                                mechanicDataManager.getMinecartCost().getValue().toString());
                    }
                }
        );

        upgradesBinding.softResetButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        mechanicDataManager.softReset();
                        upgradesBinding.softResetButton.setText(softResetString +
                                mechanicDataManager.softResetCost);
                        upgradesBinding.softResetButton.destroyDrawingCache();
                    }
                }
        );

    }

    // Website for matching parent parameters below
    // https://stackoverflow.com/questions/43637608/how-to-android-popup-window-match-sreen-size-width
    public void displayResetWarning() {
        View view = hardResetBinding.getRoot();
        final PopupWindow popup = new PopupWindow(view);
        popup.setContentView(view);
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setWidth(MATCH_PARENT);
        popup.setHeight(popUpHeight);
        popup.showAtLocation(view, Gravity.CENTER, 0, 0);
        popup.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        hardResetBinding.cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        popup.dismiss();
                    }
                }
        );

        hardResetBinding.hardResetButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        // Set crystalsPerSwing to 0 in order to fulfill condition in initializer
                        mechanicDataManager.setCrystalsPerSwing(0);
                        mechanicDataManager.initializer();
                        popup.dismiss();
                    }
                }
        );

    }

    // Used to detect a tap anywhere on the screen
    // https://stackoverflow.com/questions/32068562/how-to-detect-user-interaction-in-android
    @Override
    public void onUserInteraction()
    {
        mechanicDataManager.resetIdleTimer();
    }

}