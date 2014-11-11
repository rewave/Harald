package com.harald;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.btwiz.library.BTSocket;
import com.btwiz.library.BTWiz;
import com.harald.fragments.DeviceConnected;
import com.harald.fragments.DeviceListing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class Main extends Activity {

    BTSocket socket;
    ProgressBar progressBar;
    List<Integer> commandMap = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) return;

        //Setup the command mapper
        commandMap.add(R.id.c0);
        commandMap.add(R.id.c1);
        commandMap.add(R.id.c2);
        commandMap.add(R.id.c3);
        commandMap.add(R.id.c4);
        commandMap.add(R.id.c5);

        getFragmentManager()
            .beginTransaction()
            .add(R.id.container, new DeviceListing())
            .commit()
        ;
    }

    public void setSocket(BTSocket s) {
        socket = s;
    }

    public BTSocket getSocket() {
        return socket;
    }

    ////////////////////
    public void commandButtonPressed(View v) {
        int command;
        int viewId = v.getId();

        command = commandMap.indexOf(viewId);

        if (command >= 0) try {
            socket.write(String.valueOf(command).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        else {
            try {
                socket.write("exit");
            } catch (IOException e) {
                e.printStackTrace();
            }

            BTWiz.closeAllOpenSockets();
            BTWiz.cleanup(Main.this);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new DeviceListing())
                    .commit();
        }

        Log.i("Command", String.valueOf(command));
    }

}
