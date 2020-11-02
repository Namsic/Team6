package com.example.team6project.tab_movement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team6project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordMovement extends AppCompatActivity {

    private ListView listView;
    private SharedPreferences pref = null;
    final List<String> data = new ArrayList<>();
    int t1;
    String str = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);



        Button btnAddList = (Button) findViewById(R.id.addListItem);
        Button btnRemoveList = (Button) findViewById(R.id.removeListItem);

        listView = (ListView) findViewById(R.id.recordList);

        pref = getSharedPreferences("Savestate", MODE_PRIVATE);
        t1 = pref.getInt("start", 0);

        final SharedPreferences.Editor editor = pref.edit();


        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        for (int i = 0; i <= t1; i++) {
            str = pref.getString("" + i,"default");
            if (str != "default"){
                data.add(i, str);
            }
            adapter.notifyDataSetChanged();
        }

        if (str != null)
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "" + listView.getCount(), Toast.LENGTH_SHORT).show();


        final Intent inIntent = getIntent();

        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now = System.currentTimeMillis();

                Date date = new Date(now);

                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                final String formatDate = sdfNow.format(date);

                if (inIntent.getStringExtra("address") != null){
                    data.add(inIntent.getStringExtra("address") + "(" + formatDate + ")");
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "현재위치를 등록하였습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRemoveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int del = data.size() - 1;
                if (data.size() > 0) {
                    data.remove(data.size() - 1);
                    editor.remove("" + del).commit();
                    editor.putInt("start", data.size() - 1);
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getApplicationContext(), "더 이상 삭제할 데이터가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences pref = getSharedPreferences("Savestate", MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        for (int i = 0; i < listView.getCount(); i++){
            editor.putString(""+i, data.get(i));
            editor.putInt("start", i);

            editor.commit();
        }

    }
}
