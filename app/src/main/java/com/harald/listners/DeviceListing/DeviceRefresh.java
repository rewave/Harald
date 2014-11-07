package com.harald.listners.DeviceListing;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.btwiz.library.BTWiz;
import com.btwiz.library.IDeviceComparator;
import com.btwiz.library.IDeviceLookupListener;

import java.util.List;

/**
 * Created by shivekkhurana on 2/11/14.
 */
public class DeviceRefresh implements SwipeRefreshLayout.OnRefreshListener {

    Context context;
    ArrayAdapter<String> adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    List<BluetoothDevice> devices;

    public DeviceRefresh(Context c, List<BluetoothDevice> d, ArrayAdapter<String> a, SwipeRefreshLayout l) {
        context = c;
        adapter = a;
        devices = d;
        swipeRefreshLayout =l;
    }

    @Override
    public void onRefresh() {
        IDeviceComparator comparator = new IDeviceComparator() {
            @Override
            public boolean match(BluetoothDevice device) {
                String deviceName;
                try {
                    deviceName = device.getName();
                } catch (NullPointerException e) {
                    deviceName = "";
                }
                Log.i("BT__1", deviceName);
                return adapter.getPosition(deviceName) < 0;
            }
        };

        IDeviceLookupListener lookupListener = new IDeviceLookupListener() {
            @Override
            public boolean onDeviceFound(BluetoothDevice device, boolean b) {
                Log.i("BT__2", device.getName());
                devices.add(device);
                adapter.add(device.getName());
                return true;
            }

            @Override
            public void onDeviceNotFound(boolean b) {
                Log.i("BT__3", "Stop Refreshing");
                swipeRefreshLayout.setRefreshing(false);
            }
        };

        BTWiz.discoverBTDeviceAsync(context, comparator, lookupListener);
    }

}
