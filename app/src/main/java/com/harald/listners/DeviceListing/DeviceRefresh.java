package com.harald.listners.DeviceListing;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.btwiz.library.BTWiz;
import com.btwiz.library.IDeviceComparator;
import com.btwiz.library.IDeviceLookupListener;

/**
 * Created by shivekkhurana on 2/11/14.
 */
public class DeviceRefresh implements SwipeRefreshLayout.OnRefreshListener {

    Context context;
    ArrayAdapter<String> adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public DeviceRefresh(Context c, ArrayAdapter<String> a, SwipeRefreshLayout l) {
        context = c;
        adapter = a;
        swipeRefreshLayout =l;
    }

    @Override
    public void onRefresh() {
        IDeviceComparator comparator = new IDeviceComparator() {
            @Override
            public boolean match(BluetoothDevice device) {
                Log.i("BT__1", device.getName());
                return adapter.getPosition(device.getName()) < 0;
            }
        };

        IDeviceLookupListener lookupListener = new IDeviceLookupListener() {
            @Override
            public boolean onDeviceFound(BluetoothDevice device, boolean b) {
                Log.i("BT__2", device.getName());
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
