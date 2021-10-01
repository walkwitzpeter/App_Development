package com.example.myclicker;

import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myclicker.databinding.ActivityMainBinding;
import com.example.myclicker.databinding.ActivityUpgradesBinding;

public class MechanicDataManager extends ViewModel {
    // Constants
    private final int initialCrystals = 0;
    private final int initialCrystalsPerSwing = 1;
    private final int initialPickCost = 1;
    private final int initialMinerCost = 1;
    private final int initialMiners = 0;
    private final int crystalsPerMiner = 1;
    private final int pickCostExponentiation = 4;
    private final int crystalsPerSwingExponentiation = 2;
    private final int minerCostExponentiation = 4;
    final private long timeToWait = 10;

    // My resources to keep track of
    private MutableLiveData<Integer> crystals = new MutableLiveData<>();
    private MutableLiveData<Integer> crystalsPerSwing = new MutableLiveData<>();
    private MutableLiveData<Integer> pickUpgradeCost = new MutableLiveData<>();
    private MutableLiveData<Integer> minerCost = new MutableLiveData<>();
    private MutableLiveData<Integer> miners = new MutableLiveData<>();
    private MutableLiveData<Integer> minecartCost = new MutableLiveData<>();

    // Getters for needed items
    public LiveData<Integer> getCrystals() {return crystals;}
    public LiveData<Integer> getCrystalsPerSwing() {return crystalsPerSwing;}
    public LiveData<Integer> getPickUpgradeCost() {return pickUpgradeCost;}
    public LiveData<Integer> getMinerCost() {return minerCost;}
    public LiveData<Integer> getMinecartCost() {return minecartCost;}

    //Timer to keep track of the resource mining
    private CountDownTimer timer = null;

    public void initializer() {
        if (crystals.getValue() == null) {
            crystals.setValue(initialCrystals);
            crystalsPerSwing.setValue(initialCrystalsPerSwing);
            pickUpgradeCost.setValue(initialPickCost);
            minerCost.setValue(initialMinerCost);
            miners.setValue(initialMiners);
        }
//        Log.i("TAG", "called init");
    }

    public void startMinerTimer() {
        timer = new CountDownTimer(timeToWait*1000, 1000) {

            public void onTick (long milliToFinish) {
            }

            public void onFinish () {
                crystals.setValue(crystals.getValue() + miners.getValue() * crystalsPerMiner);
//                Log.i("TAG", "finish timer");
                timer.start();
            }
        }.start();

    }

    public void addCrystals(int crystalsToAdd) {
//        Log.i("TAG", Integer.toString(crystalsToAdd));
        crystals.setValue(crystals.getValue() + crystalsToAdd);
    }

    public void pickUpgrade() {
        if (crystals.getValue() >= pickUpgradeCost.getValue()) {
            crystals.setValue(crystals.getValue() - pickUpgradeCost.getValue());
            pickUpgradeCost.setValue(pickUpgradeCost.getValue() * pickCostExponentiation);
            crystalsPerSwing.setValue(crystalsPerSwing.getValue() * crystalsPerSwingExponentiation);
        }
    }

    public void addMiner() {
        if (crystals.getValue() >= minerCost.getValue()) {
            crystals.setValue(crystals.getValue() - minerCost.getValue());
            minerCost.setValue(minerCost.getValue() * minerCostExponentiation);
            miners.setValue(miners.getValue() + 1);
        }
    }



}
