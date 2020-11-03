package com.example.team6project.tab_clinic;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.team6project.MainActivity;
import com.example.team6project.R;
import com.example.team6project.tab_movement.MapFragment;

import java.io.IOException;
import java.util.ArrayList;

public class ClinicListViewAdapter extends BaseAdapter {
    ArrayList<Clinic> arr;

    public ClinicListViewAdapter(ArrayList<Clinic> arr){
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_clinic, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_clinic_name);
        Button btnMap = (Button) convertView.findViewById(R.id.btn_mapURL);

        final Clinic listViewItem = arr.get(position);
        tvName.setText(listViewItem.name);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).changeView(2);
                Address clinicAddress = null;
                String searchString = listViewItem.position + " " + listViewItem.name;
                try {
                    clinicAddress = new Geocoder(context).getFromLocationName(searchString, 1).get(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((MapFragment)((MainActivity)context).getTabFragment(2)).addMarker(clinicAddress, listViewItem.name);
            }
        });
        return convertView;
    }
}
