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

// Used for saving state after closed
import androidx.lifecycle.SavedStateHandle;

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
    private final int minerCostExponentiation = 2;
    final private long timeToWait = 5;

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
    public LiveData<Integer> getMiners() {return miners;}
    public LiveData<Integer> getMinecartCost() {return minecartCost;}

    // Setters for restoring the Saved Instance State
    public void setCrystals(int amount) {crystals.setValue(amount);}
    public void setCrystalsPerSwing(int amount) {crystalsPerSwing.setValue(amount);}
    public void setPickCost(int amount) {pickUpgradeCost.setValue(amount);}
    public void setMinerCost(int amount) {minerCost.setValue(amount);}
    public void setMiners(int amount) {miners.setValue(amount);}
    public void setMinecartCost(int amount) {minecartCost.setValue(amount);}



    // For keeping data after app closes
//    private static final String CRYSTAL_KEY = "Crystal Value";
//    private SavedStateHandle savedStateHandle;
////    public MutableLiveData<Float> result = new MutableLiveData<>();
//    public MutableLiveData<Float> result ;
//
//    public MechanicDataManager (SavedStateHandle savedStateHandle) {
//        this.savedStateHandle = savedStateHandle;
//        result = savedStateHandle.getLiveData(CRYSTAL_KEY);
//    }
//
//    public void setAmount(String value) {
//        String dollarText = value;
////        result.setValue( Float.valueOf(dollarText) * USD_TO_EU_RATE);
//        Float convertedValue = Float.valueOf(dollarText) * 5;
//        result.setValue(convertedValue);
//        savedStateHandle.set(CRYSTAL_KEY, convertedValue);
//    }



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
