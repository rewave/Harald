package com.harald.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.harald.R;
import com.harald.common.Helper;
import com.harald.listners.DeviceListing.DeviceRefresh;
import com.harald.listners.DeviceListing.DeviceSelector;

import java.util.ArrayList;
import java.util.List;

public class DeviceListing extends Fragment {

    ArrayAdapter<String> deviceNames;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device_listing, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();

        ListView deviceList = (ListView) activity.findViewById(R.id.device_list);
        deviceNames = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1);


        (new Helper(activity)).ensureBTOn();
        List<BluetoothDevice> devices =
                new ArrayList<BluetoothDevice>(BluetoothAdapter.getDefaultAdapter().getBondedDevices());
        deviceList.setAdapter(deviceNames);

        for (BluetoothDevice d : devices) {
            deviceNames.add(d.getName());
        }

        swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new DeviceRefresh(activity, devices, deviceNames, swipeRefreshLayout));

        DeviceSelector deviceSelector = new DeviceSelector(activity, devices, swipeRefreshLayout);
        deviceList.setOnItemClickListener(deviceSelector);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
