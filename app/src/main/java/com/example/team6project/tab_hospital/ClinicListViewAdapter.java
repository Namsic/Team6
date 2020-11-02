package com.example.team6project.tab_hospital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team6project.R;

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

        Clinic listViewItem = arr.get(position);
        tvName.setText(listViewItem.name);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "미구현", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
