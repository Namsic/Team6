package com.example.team6project.tab_clinic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.team6project.R;

// Intent 활용하여 넘겨받은 각 Clinic 정보 표시
// Editor - 김남재
public class ClinicActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_info);

        Intent intent = getIntent();
        ((TextView)findViewById(R.id.tv_info_name)).setText(intent.getStringExtra("clinicName"));
        ((TextView)findViewById(R.id.tv_info_position)).setText(intent.getStringExtra("clinicPosition"));
        ((TextView)findViewById(R.id.tv_info_phone)).setText(intent.getStringExtra("clinicPhone"));
        ((TextView)findViewById(R.id.tv_info_type)).setText(intent.getStringExtra("clinicType"));
        String[] time = intent.getStringArrayExtra("clinicTime");
        ((TextView)findViewById(R.id.tv_info_time_week)).setText(time[0]);
        ((TextView)findViewById(R.id.tv_info_time_sat)).setText(time[1]);
        ((TextView)findViewById(R.id.tv_info_time_sun)).setText(time[2]);
    }
}
