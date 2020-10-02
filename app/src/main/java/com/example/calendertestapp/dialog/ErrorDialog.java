package com.example.calendertestapp.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.calendertestapp.MoreActivity;
import com.example.calendertestapp.R;


public class ErrorDialog extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_error, container, false);

        ImageView success = view.findViewById(R.id.error);
        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), MoreActivity.class));
            }
        });

        ConstraintLayout errorContainer = view.findViewById(R.id.error_container);
        errorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().findViewById(R.id.text_about_app_title).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.text_about_app).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.text_feedback_title).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.text_feedback).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.submit_button).setVisibility(View.VISIBLE);

                getFragmentManager().beginTransaction().
                        remove(getFragmentManager().findFragmentByTag("dialog")).commit();
            }
        });

        return view;
    }
}