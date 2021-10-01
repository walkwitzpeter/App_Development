package com.example.myviewmodeldemo.ui.main;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private static final Float USD_TO_EU_RATE = 0.74F;
    public MutableLiveData<String> dollarValue = new MutableLiveData<>();
    public MutableLiveData<Float> result = new MutableLiveData<>();

    public void convertValue() {
        if ((dollarValue.getValue() != null) &&
                (!dollarValue.getValue().equals(""))) {
            result.setValue(Float.parseFloat(dollarValue.getValue()) * USD_TO_EU_RATE);
        } else {
            result.setValue(0F);
        }
    }


//    public void setAmount(String value) {
//        dollarValue = value;
//        result.setValue( Float.valueOf(dollarText) * USD_TO_EU_RATE);
//    }
//
//    public MutableLiveData<Float> getResult() {
//        return result;
//    }

    // TODO: Implement the ViewModel
}