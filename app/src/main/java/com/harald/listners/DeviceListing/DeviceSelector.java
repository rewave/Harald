package com.harald.listners.DeviceListing;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.btwiz.library.BTSocket;
import com.btwiz.library.BTWiz;
import com.btwiz.library.IDeviceConnectionListener;
import com.harald.Main;
import com.harald.R;
import com.harald.fragments.DeviceConnected;

import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by shivekkhurana on 18/10/14.
 */
public class DeviceSelector implements AdapterView.OnItemClickListener {

    List<BluetoothDevice> devices;
    Context context;

    public DeviceSelector(Context c, List<BluetoothDevice> d) {
        context = c;
        devices = d;
    }

    @Override
    public void onItemClick(final AdapterView parent, View v, int position, long id) {
        final BluetoothDevice device = devices.get(position);
        ((Main) context).loading(true);

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
                e.printStackTrace();
                connectionError();
            }
        };

        BTWiz.connectAsClientAsync(context, device, connectionListener);
    }

    public void connectionError(){
        ((Main) context).loading(true);
        Crouton.makeText((Activity)context, "Could not connect :(", Style.ALERT);
    }

}
