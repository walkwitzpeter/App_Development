package com.example.myclicker;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Timer;

// Used for saving state after closed


public class MechanicDataManager extends ViewModel {
    // Constants
    private final int initialCrystals = 9999999;
    private final int initialCrystalsPerSwing = 1;
    private final int initialPickCost = 1;
    private final int initialMinerCost = 1;
    private final int initialMiners = 0;
    private final int initialMinecartCost = 1;
    public int crystalsPerMiner = 1;    // This is changed by an upgrade
    public int softResetCost = 10000;  // This is changed by an upgrade
    public boolean idle = false;  // This is changed by a timer
    private final int softResetExponentiation = 2;
    // Exponentiation variables
    private int pickCostExponentiation = 10;
    private int crystalsPerSwingExponentiation = 2;
    private int minerCostExponentiation = 5;
    private int minecartCostExponentiation = 10;

    private final long minerTimeToWait = 5;
    private final long idleTimeToWait = 10;

    // My resources to keep track of
    private MutableLiveData<Integer> crystals = new MutableLiveData<>();
    private MutableLiveData<Integer> crystalsPerSwing = new MutableLiveData<>();
    private MutableLiveData<Integer> pickUpgradeCost = new MutableLiveData<>();
    private MutableLiveData<Integer> minerCost = new MutableLiveData<>();
    private MutableLiveData<Integer> miners = new MutableLiveData<>();
    private MutableLiveData<Integer> minecartCost = new MutableLiveData<>();

    private MutableLiveData<Integer> diamonds = new MutableLiveData<>();

    // Getters for needed items
    public LiveData<Integer> getCrystals() {return crystals;}
    public LiveData<Integer> getCrystalsPerSwing() {return crystalsPerSwing;}
    public LiveData<Integer> getPickUpgradeCost() {return pickUpgradeCost;}
    public LiveData<Integer> getMinerCost() {return minerCost;}
    public LiveData<Integer> getMiners() {return miners;}
    public LiveData<Integer> getMinecartCost() {return minecartCost;}

    public LiveData<Integer> getDiamonds() {return diamonds;}

    // Setters for restoring the Saved Instance State
    public void setCrystals(int amount) {crystals.setValue(amount);}
    public void setCrystalsPerSwing(int amount) {crystalsPerSwing.setValue(amount);}
    public void setPickCost(int amount) {pickUpgradeCost.setValue(amount);}
    public void setMinerCost(int amount) {minerCost.setValue(amount);}
    public void setMiners(int amount) {miners.setValue(amount);}
    public void setMinecartCost(int amount) {minecartCost.setValue(amount);}

    public void setDiamonds(int amount) {diamonds.setValue(amount);}


    //Timer to keep track of the resource mining
    private CountDownTimer minerTimer = null;
    private CountDownTimer idleTimer = null;

    public void initializer() {
        if (crystals.getValue() == null || crystalsPerSwing.getValue() == 0) {
            crystals.setValue(initialCrystals);
            crystalsPerSwing.setValue(initialCrystalsPerSwing);
            pickUpgradeCost.setValue(initialPickCost);
            minerCost.setValue(initialMinerCost);
            miners.setValue(initialMiners);
            minecartCost.setValue(initialMinecartCost);
        }
    }

    public void diamondInitializer() {
        if (diamonds.getValue() == null) {
            diamonds.setValue(initialCrystals);
//            crystalsPerSwing.setValue(initialCrystalsPerSwing);
//            pickUpgradeCost.setValue(initialPickCost);
//            minerCost.setValue(initialMinerCost);
//            miners.setValue(initialMiners);
        }
    }

    public void startMinerTimer() {
        minerTimer = new CountDownTimer(minerTimeToWait * 1000, 1000) {

            public void onTick (long milliToFinish) {
            }

            public void onFinish () {
                crystals.setValue(crystals.getValue() + miners.getValue() * crystalsPerMiner);
                minerTimer.start();
            }
        }.start();

    }

    public void startIdleTimer() {
        idleTimer = new CountDownTimer(idleTimeToWait * 1000, 1000) {

            public void onTick (long milliToFinish) {
            }

            public void onFinish () {
                crystalsPerMiner *= 2;
                idle = true;
            }
        }.start();
    }

    public void resetIdleTimer() {
        if (idle = true && crystalsPerMiner > 1) {
            crystalsPerMiner /= 2;
        }
        idle = false;
        idleTimer.cancel();
        idleTimer.start();
    }

    public void addCrystals(int crystalsToAdd) {
        crystals.setValue(crystals.getValue() + crystalsToAdd);
    }

    public void addDiamonds(int diamondsToAdd) {
        diamonds.setValue(diamonds.getValue() + diamondsToAdd);
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

    public void minecartUpgrade() {
        if (crystals.getValue() >= minecartCost.getValue()) {
            crystals.setValue(crystals.getValue() - minecartCost.getValue());
            crystalsPerMiner *= 2;
            minecartCost.setValue(minecartCost.getValue() * minecartCostExponentiation);
        }
    }

    public void softReset () {
        if (pickCostExponentiation > 2) {
            if (crystals.getValue() >= softResetCost) {
                crystals.setValue(initialCrystals);
                pickUpgradeCost.setValue(initialPickCost);
                crystalsPerSwing.setValue(initialCrystalsPerSwing);
                miners.setValue(initialMiners);
                minerCost.setValue(initialMinerCost);
                crystalsPerMiner = 1;
                minecartCost.setValue(initialMinecartCost);
                softResetCost *= softResetExponentiation;

                // Making the exponentiation smaller
                pickCostExponentiation -= 1;
                minecartCostExponentiation -= 1;
                if (minerCostExponentiation > 2) {
                    minerCostExponentiation -= 1;
                }
            }
        }
        else {
            Log.i("TAG", "already max upgraded");
        }
    }


}
