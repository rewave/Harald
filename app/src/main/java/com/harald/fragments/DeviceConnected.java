package com.harald.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btwiz.library.BTSocket;
import com.btwiz.library.BTWiz;
import com.harald.Main;
import com.harald.R;

import java.io.IOException;

public class DeviceConnected extends Fragment {
    Activity activity;
    BTSocket socket;

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
        socket = ((Main) activity).getSocket();
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

        sendCommand(command);
        Log.i("Command", String.valueOf(command));
    }

    public void sendCommand(int command) {
        if (command > 0) writeToSocket(String.valueOf(command));
        else {
            writeToSocket("exit");

            BTWiz.closeAllOpenSockets();
            BTWiz.cleanup(activity);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new DeviceListing())
                    .commit();
        }
    }

    public void writeToSocket(String s) {
        try {
            socket.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
