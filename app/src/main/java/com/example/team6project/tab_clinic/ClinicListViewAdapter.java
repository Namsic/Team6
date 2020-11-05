package com.example.team6project.tab_clinic;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.team6project.MainActivity;
import com.example.team6project.R;
import com.example.team6project.tab_movement.MapFragment;

import java.io.IOException;
import java.util.ArrayList;

// 검색결과를 표시하는 ListView Adapter
// Editor - 김남재
public class ClinicListViewAdapter extends BaseAdapter {
    private ArrayList<Clinic> arr;

    // ArrayList 객체 받아와 ListView 연결
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

    // 각 ListView Item Button 클릭 시 해당 병원 & 진료소 위치와 이름으로 검색 후 MapFragment Marker 표시
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_clinic, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_clinic_name);
        ImageButton btnMap = (ImageButton) convertView.findViewById(R.id.btn_mapURL);
        btnMap.setFocusable(false);

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
                ((MapFragment)((MainActivity)context).getTabFragment(2)).pickMarker(clinicAddress, listViewItem.name);
            }
        });
        return convertView;
    }
}
