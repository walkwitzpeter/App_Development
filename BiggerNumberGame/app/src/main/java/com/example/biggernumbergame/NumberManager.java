package com.example.biggernumbergame;

import androidx.lifecycle.ViewModel;

import java.util.Random;

public class NumberManager extends ViewModel {

    private int number1 = 1;
    private int number2 = 2;
    private int score = 0;

//    public NumberManager(int num1, int num2) {
//        number1 = num1;
//        number2 = num2;
//        score = 0;
//    }

    public void newValues() {
        Random rand = new Random();
        number1 = rand.nextInt(100);
        number2 = rand.nextInt(100);
    }

    public boolean testResponse(int chosen) {
        System.out.println("chosen:" + chosen + " num1:" + number1 + " num2:" + number2);
        if (chosen == number1 && number1 > number2) {
            score += 1;
            return true;
        } else if (chosen == number2 && number2 > number1) {
            score += 1;
            return true;
        } else {
            return false;
        }
    }

    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }

    public int getScore() { return score;}
}

