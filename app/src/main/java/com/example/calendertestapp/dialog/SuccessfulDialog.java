package com.example.calendertestapp.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.calendertestapp.MoreActivity;
import com.example.calendertestapp.R;
import com.example.calendertestapp.WelcomeActivity;

public class SuccessfulDialog extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.dialog_successful, container, false);
        ImageView success = view.findViewById(R.id.success);
        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WelcomeActivity.class));
            }
        });

        return view;
    }
}