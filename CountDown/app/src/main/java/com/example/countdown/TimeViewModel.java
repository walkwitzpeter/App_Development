package com.example.countdown;

import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimeViewModel extends ViewModel {
    private CountDownTimer timer = null;
    private MutableLiveData<Integer> seconds = new MutableLiveData<>();
    private MutableLiveData<Boolean> finished = new MutableLiveData<>();
    private MutableLiveData<Long> timeToWait = new MutableLiveData<>();

    public LiveData<Integer> getSeconds() {
        return seconds;
    }

    public LiveData<Boolean> getFinished() {
        return finished;
    }

    public void setWaitTime(Long value) {
        timeToWait.setValue(value);
    }

    // Times are in milliseconds
    public void startTimer() {
        timer = new CountDownTimer(timeToWait.getValue(), 1000) {

            public void onTick (long milliToFinish) {
                long timeLeft = milliToFinish / 1000;
                seconds.setValue((int)timeLeft);
            }

            public void onFinish () {
                finished.setValue(true);
            }
        }.start();

    }

//    public void resumeTimer() {
//        timer.wait();
//    }

    public void stopTimer() {
        timer.cancel();
    }


}
