package com.harald.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.harald.R;

public class DeviceConnected extends Fragment {
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_device_connected, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
    }

    public void commandButtonPressed(View v) {
        int command;

        switch (v.getId()){
            case R.id.c1:
                command = 1;
                break;

            case R.id.c2:
                command = 2;
                break;

            case R.id.c3:
                command = 3;
                break;

            case R.id.c4:
                command = 4;
                break;

            case R.id.c5:
                command = 5;
                break;

            case R.id.close_connection:
                command = -1;
                break;

            default:
                command = 0;
        }

        ((OnCommandIssuedListener) v.getContext()).onCommandIssued(command);
        Log.i("Command", String.valueOf(command));
    }

    public interface OnCommandIssuedListener {
        public void onCommandIssued(int command);
    }
}
