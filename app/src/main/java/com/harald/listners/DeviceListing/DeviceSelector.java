package com.harald.listners.DeviceListing;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.harald.fragments.DeviceListing;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by shivekkhurana on 18/10/14.
 */
public class DeviceSelector implements AdapterView.OnItemClickListener {

    List<BluetoothDevice> devices;
    BluetoothDevice selectedDevice;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    ConnectionListener connectionListener;
    IntentFilter intentFilter;

    public DeviceSelector(Context c, List<BluetoothDevice> d, SwipeRefreshLayout s) {
        context = c;
        devices = d;
        swipeRefreshLayout = s;
        connectionListener = new ConnectionListener();

        intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        context.registerReceiver(new PairingReceiver(), intentFilter);
    }

    @Override
    public void onItemClick(final AdapterView parent, View v, int position, long id) {

        BTWiz.cancelDiscovery(context);
        swipeRefreshLayout.setRefreshing(false);
        ((TextView) v).setText("•••");

        selectedDevice = devices.get(position);

        if (BTWiz.getAllBondedDevices(context).contains(selectedDevice)) {
            BTWiz.connectAsClientAsync(context, selectedDevice, connectionListener);
        } else {
            Log.i("Trying to pair", ":)");
            try {
                Log.i("Pairing Procedure", "Will connect at end");
                createBond(selectedDevice);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean createBond(BluetoothDevice btDevice)
            throws Exception
    {
        Class deviceClass = Class.forName("android.bluetooth.BluetoothDevice");
        Method createBondMethod = deviceClass.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    class PairingReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BTWiz.connectAsClientAsync(context, selectedDevice, connectionListener);
            }
        }
    }

    class ConnectionListener implements IDeviceConnectionListener {
        @Override
        public void onConnectSuccess(BTSocket btSocket) {
            Log.i("DeviceSelector", "Connected to " + selectedDevice.getName());
            //((Main) context).loading(false);
            ((Main) context).setSocket(btSocket);
            ((Main) context).getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new DeviceConnected())
                    .commit();
        }

        @Override
        public void onConnectionError(Exception e, String s) {
            (new DeviceListing()).connectionError();
            //e.printStackTrace();
        }
    }


}
