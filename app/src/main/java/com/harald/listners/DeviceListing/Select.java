package com.harald.listners.DeviceListing;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by shivekkhurana on 17/10/14.
 */
public class Select extends AdapterView.OnItemClickListener {
    AdapterView adapterView;

    public Select(AdapterView a) {
        adapterView = a;
    }

    @Override
    public  void onItemClick (AdapterView a, View v, int position, long l) {

    }
}
