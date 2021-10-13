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
//    private ActivityUpgradesBinding upgradesBinding;
    private MechanicDataManager mechanicDataManager;
    private final String DIAMONDS = new String("diamonds");

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("MyDiamonds", mechanicDataManager.getDiamonds().getValue());
    }

//    @Override
//    public void onResumeInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        int savedDiamonds = savedInstanceState.getInt("MyDiamonds");
//        mechanicDataManager.setDiamonds(savedDiamonds);
//
//        // These are used to restart/set the things needed at startup
//        diamondBinding.diamondNumber.setText(mechanicDataManager.getDiamonds().getValue().toString());
//    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int savedDiamonds = savedInstanceState.getInt("MyDiamonds");
        mechanicDataManager.setDiamonds(savedDiamonds);

        // These are used to restart/set the things needed at startup
        diamondBinding.diamondNumber.setText(mechanicDataManager.getDiamonds().getValue().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diamondBinding = ActivityDiamondMineBinding.inflate(getLayoutInflater());
//        upgradesBinding = ActivityUpgradesBinding.inflate(getLayoutInflater());

        // TODO trying to pass mechanic manager to this class from Main
//        Intent myIntent = getIntent();
//        mechanicDataManager = MainActivity.getMechanicDataManager();

        View view = diamondBinding.getRoot();
        setContentView(view);

//        mechanicDataManager = new ViewModelProvider(this).get(MechanicDataManager.class);
        mechanicDataManager.diamondInitializer();
        Log.i("TAG", "crystals" + mechanicDataManager.getCrystals().getValue());

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
                        Intent intent = new Intent();
                        intent.putExtra(DIAMONDS, mechanicDataManager.getDiamonds().getValue());
                        setResult(RESULT_OK, intent);
                        Log.i("TAG", DIAMONDS + " " + RESULT_OK);
                        finish();

//                        finishActivity(1);
//                        Intent myIntent = new Intent(DiamondMine.this, MainActivity.class);
//                        DiamondMine.this.startActivity(myIntent);
//                        Intent openMainActivity = new Intent(DiamondMine.this, MainActivity.class);
//                        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
//                        startActivityIfNeeded(openMainActivity, 0);
                    }
                }
        );
    }
}