package com.example.fragmentexample;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.content.Context;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToolbarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToolbarFragment extends Fragment implements OnSeekBarChangeListener {

    private static int seekvalue = 10;
    private static EditText edittext;

    public ToolbarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToolbarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToolbarFragment newInstance(String param1, String param2) {
        ToolbarFragment fragment = new ToolbarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toolbar,container,false);
        edittext = view.findViewById(R.id.editText1);
        final SeekBar seekbar = view.findViewById(R.id.seekBar1);
        seekbar.setOnSeekBarChangeListener(this);
        final Button button = view.findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                buttonClicked(v);
            }
        });
        return view;
    }

    public void buttonClicked(View view) {
        activityCallback.onButtonClick(seekvalue,edittext.getText().toString());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        seekvalue = progress;
    }
    @Override
    public void onStartTrackingTouch(SeekBar arg0) {
    }
    @Override
    public void onStopTrackingTouch(SeekBar arg0) {
    }

    ToolbarListener activityCallback;
    public interface ToolbarListener {
        public void onButtonClick(int position, String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCallback = (ToolbarListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement TollbarListener");
        }
    }

}