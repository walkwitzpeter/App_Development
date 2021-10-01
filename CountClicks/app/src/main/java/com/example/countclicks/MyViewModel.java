package com.example.countclicks;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private int numberCount = 0;

    public void countClick() {
        numberCount++;
    }

    public int getNumberCount() {
        return numberCount;
    }
}
