package com.harald;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.btwiz.library.BTSocket;
import com.harald.fragments.DeviceConnected;
import com.harald.fragments.DeviceListing;



public class Main extends Activity {

    BTSocket socket;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) return;

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

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
        //called directly from view hence needs to be handled by main
        (new DeviceConnected()).commandButtonPressed(v);
    }

    ////////////////////
    public void loading(boolean show) {
        if (show) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.INVISIBLE);
    }


}
