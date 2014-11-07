package com.harald.listners.DeviceListing;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.btwiz.library.BTSocket;
import com.btwiz.library.BTWiz;
import com.btwiz.library.IDeviceConnectionListener;
import com.btwiz.library.SecureMode;
import com.harald.Main;
import com.harald.R;
import com.harald.fragments.DeviceConnected;

import java.util.List;
import java.util.UUID;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by shivekkhurana on 18/10/14.
 */
public class DeviceSelector implements AdapterView.OnItemClickListener {

    List<BluetoothDevice> devices;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;

    public DeviceSelector(Context c, List<BluetoothDevice> d, SwipeRefreshLayout s) {
        context = c;
        devices = d;
        swipeRefreshLayout = s;
    }

    @Override
    public void onItemClick(final AdapterView parent, View v, int position, long id) {

        BTWiz.cancelDiscovery(context);
        swipeRefreshLayout.setRefreshing(false);

        final BluetoothDevice device = devices.get(position);
        //((Main) context).loading(true);
        Log.i("Selector", "Device Clicked");
        ((TextView) v).setText("•••");

        IDeviceConnectionListener connectionListener = new IDeviceConnectionListener() {
            @Override
            public void onConnectSuccess(BTSocket btSocket) {
                Log.i("DeviceSelector", "Connected to " + device.getName());
                //((Main) context).loading(false);
                ((Main) context).setSocket(btSocket);
                ((Main) context).getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new DeviceConnected())
                        .commit();
            }

            @Override
            public void onConnectionError(Exception e, String s) {
                Log.i("Device Selector", "Connection error");
                //e.printStackTrace();
                connectionError();
            }
        };

        //BTWiz.connectAsClientAsync(context, device, connectionListener); //HC05 and other devices without UUIDS

        BTWiz.connectAsClientAsync(context, device, connectionListener, SecureMode.INSECURE, UUID.fromString("a1a738e0-c3b3-11e3-9c1a-0800200c9a66"));
    }

    public void connectionError(){
        Crouton.makeText((Activity)context, "Could not connect :(", Style.ALERT);
    }

}
