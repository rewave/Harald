package com.harald.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.btwiz.library.BTWiz;
import com.btwiz.library.DeviceNotSupportBluetooth;
import com.harald.R;
import com.harald.listners.DeviceListing.Select;
import com.harald.listners.DeviceListing.SwipeRefresh;

public class DeviceListing extends Fragment{

    ArrayAdapter<String> deviceNames;
    SwipeRefreshLayout swipeLayout;

    public static DeviceListing newInstance() {
        DeviceListing fragment = new DeviceListing();
        return fragment;
    }

    public DeviceListing() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device_listing, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();

        ListView deviceList = (ListView) activity.findViewById(R.id.device_list);
        deviceNames = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1);

        try {
            if (!BTWiz.isEnabled(activity)) {
                startActivity(BTWiz.enableBTIntent());
                return;
            }
        } catch (DeviceNotSupportBluetooth e) {
            return;
        }

        for (BluetoothDevice d : BTWiz.getAllBondedDevices(activity)) {
            addDeviceToList(d);
        }
        deviceList.setAdapter(deviceNames);

        //TODO : Need to discover devices as well
        //swipeLayout = (SwipeRefreshLayout) activity.findViewById(R.id.swipe_container);
        //swipeLayout.setOnRefreshListener(new SwipeRefresh(activity));

        deviceList.setOnItemClickListener(new Select());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void addDeviceToList(BluetoothDevice d) {
        deviceNames.add(d.getName());
    }

}
