package com.example.team6project.tab_movement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team6project.R;

import java.util.ArrayList;
import java.util.List;

public class RecordMovement extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);

        Button btnAddList = (Button) findViewById(R.id.addListItem);
        Button btnRemoveList = (Button) findViewById(R.id.removeListItem);

        listView = (ListView) findViewById(R.id.recordList);

        final List<String> data = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        final Intent inIntent = getIntent();

        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inIntent.getStringExtra("address") != null){
                    data.add(inIntent.getStringExtra("address"));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "현재위치를 등록하였습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        data.add("아무거나");
        data.add("종강원츄");
        data.add("언제끝남?");
        adapter.notifyDataSetChanged();

    }

    public void addData(String str){

    }
}
