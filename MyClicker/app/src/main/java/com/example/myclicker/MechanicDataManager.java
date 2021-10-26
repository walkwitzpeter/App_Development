package com.example.myclicker;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;
import java.util.Timer;


public class MechanicDataManager extends ViewModel {
    // Constants
    public MutableLiveData<Integer> denseCrystalWait = new MutableLiveData<>(10);
    private final int initialCrystals = 99999;
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
    public int pickCostExponentiation = 10;
    public int crystalsPerSwingExponentiation = 2;
    public int minerCostExponentiation = 5;
    public int minecartCostExponentiation = 10;

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
//    private MutableLiveData<CountDownTimer> denseCrystalTimer = null;
    private CountDownTimer denseCrystalTimer = null;
    private Random random = new Random();

    public void initializer() {
        if (crystals.getValue() == null || crystalsPerSwing.getValue() == 0) {
            crystals.setValue(initialCrystals);
            crystalsPerSwing.setValue(initialCrystalsPerSwing);
            pickUpgradeCost.setValue(initialPickCost);
            minerCost.setValue(initialMinerCost);
            miners.setValue(initialMiners);
            minecartCost.setValue(initialMinecartCost);

            // For the hard-reset
            softResetCost = 10000;
            pickCostExponentiation = 10;
            crystalsPerSwingExponentiation = 2;
            minerCostExponentiation = 5;
            minecartCostExponentiation = 10;
        }
    }

//    public void diamondInitializer() {
//        if (diamonds.getValue() == null) {
//            diamonds.setValue(initialCrystals);
////            crystalsPerSwing.setValue(initialCrystalsPerSwing);
////            pickUpgradeCost.setValue(initialPickCost);
////            minerCost.setValue(initialMinerCost);
////            miners.setValue(initialMiners);
//        }
//    }

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
        if (idle) {
            crystalsPerMiner /= 2;
        }
        idleTimer.cancel();
        idle = false;
        idleTimer.start();
    }

    public void startDenseCrystalTimer() {
        denseCrystalTimer = new CountDownTimer(denseCrystalWait.getValue() * 1000, 1000) {

            public void onTick (long milliToFinish) {
                denseCrystalWait.setValue((int)milliToFinish);
            }

            public void onFinish () {
                denseCrystalWait.setValue(1);
                denseCrystalTimer.start();
            }
        }.start();
    }

    // test thing to observe
//    MutableLiveData<CountDownTimer> test =
    public MutableLiveData<Integer> getDenseWait () {
        return denseCrystalWait;
    }

    public void addCrystals(int crystalsToAdd) {
        crystals.setValue(crystals.getValue() + crystalsToAdd);
    }

//    public void addDiamonds(int diamondsToAdd) {
//        diamonds.setValue(diamonds.getValue() + diamondsToAdd);
//    }

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


    // This is a test popup to see what happens with my "DENSE CRYSTALS"
//    public void displayResetWarning() {
//        View view = hardResetBinding.getRoot();
//        final PopupWindow popup = new PopupWindow(view);
//        popup.setContentView(view);
//        popup.setOutsideTouchable(true);
//        popup.setFocusable(true);
//        popup.setWidth(MATCH_PARENT);
//        popup.setHeight(popUpHeight);
//        popup.showAtLocation(view, Gravity.CENTER, 0, 0);
//        popup.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//        hardResetBinding.cancelButton.setOnClickListener(
//                new View.OnClickListener() {
//                    public void onClick(View view) {
//                        popup.dismiss();
//                    }
//                }
//        );
//
//        hardResetBinding.hardResetButton.setOnClickListener(
//                new View.OnClickListener() {
//                    public void onClick(View view) {
//                        // Set crystalsPerSwing to 0 in order to fulfill condition in initializer
//                        mechanicDataManager.setCrystalsPerSwing(0);
//                        mechanicDataManager.initializer();
//                        popup.dismiss();
//                    }
//                }
//        );
//
//    }


}
