package com.example.statechange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.statechange.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "StateChange";
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"onCreate");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        final EditText editText = findViewById(R.id.statusText);
        CharSequence userText = editText.getText();
        outState.putCharSequence("savedText", userText);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");

        final EditText editText = findViewById(R.id.statusText);
        CharSequence userText = savedInstanceState.getCharSequence("savedText");
        editText.setText(userText);
    }




//    @Override
//    protected void onStart() {
//        super.onStart();
////        binding.statusText2.setText("Start");
//        Log.i(TAG,"onStart");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
////        binding.statusText2.setText("Resume");
//        Log.i(TAG,"onResume");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        binding.statusText2.setText("Destroy");
//        Log.i(TAG,"onDestroy");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
////        binding.statusText2.setText("Stop");
//        Log.i(TAG,"onStop");
//    }

}