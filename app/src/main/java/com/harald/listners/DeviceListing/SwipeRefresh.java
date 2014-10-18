package com.harald.listners.DeviceListing;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.btwiz.library.IDeviceComparator;
import com.btwiz.library.IDeviceLookupListener;

/**
 * Created by shivekkhurana on 17/10/14.
 */
public class SwipeRefresh implements SwipeRefreshLayout.OnRefreshListener {

    Context context;

    public SwipeRefresh(Context c) {
        context = c;
    }

    @Override
    public void onRefresh(){
        IDeviceComparator comparator = new IDeviceComparator() {
            @Override
            public boolean match(BluetoothDevice bluetoothDevice) {
                return false;
            }
        };

        IDeviceLookupListener lookupListener= new IDeviceLookupListener(){

            @Override
            public boolean onDeviceFound(BluetoothDevice device, boolean b) {
                return false;
            }

            public void onDeviceNotFound(boolean b) {
            }

        };

        //BTWiz.discoverBTDeviceAsync(context, comparator, lookupListener);
    }
}
