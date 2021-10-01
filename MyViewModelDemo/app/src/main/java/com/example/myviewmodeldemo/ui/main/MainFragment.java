package com.example.myviewmodeldemo.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myviewmodeldemo.R;
import com.example.myviewmodeldemo.databinding.MainFragmentBinding;
import static com.example.myviewmodeldemo.BR.myViewModel;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private MainFragmentBinding binding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment,
                container,false);
        binding.setLifecycleOwner(this);
        binding.setVariable(myViewModel,mViewModel);

        View view = binding.getRoot();
        //move from deprecated onActivityCreated
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        return view;
    }

}

