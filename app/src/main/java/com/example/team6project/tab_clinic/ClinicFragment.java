package com.example.team6project.tab_clinic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.team6project.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

// 병원 & 진료소 정보를 검색할 수 있는 탭
// Editor - 김남재
public class ClinicFragment extends Fragment {
    ArrayList<Clinic> searchOutput = new ArrayList<Clinic>();
    ClinicListViewAdapter clinicListViewAdapter = new ClinicListViewAdapter(searchOutput);

    RadioButton[] radioSearchType;
    EditText editSearchValue;
    Button searchButton;
    ListView clinicListView;

    // EditText, ListView 등을 활용한 검색화면 구현\
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frag_hospital_main, container, false);

        radioSearchType = new RadioButton[2];
        radioSearchType[0] = (RadioButton) rootView.findViewById(R.id.rbtn_searchType0);
        radioSearchType[1] = (RadioButton) rootView.findViewById(R.id.rbtn_searchType1);
        radioSearchType[0].setChecked(true);
        editSearchValue = (EditText) rootView.findViewById(R.id.editSearchValue);
        searchButton = (Button) rootView.findViewById(R.id.btn_Search);
        clinicListView = (ListView) rootView.findViewById(R.id.lv_clinic);
        clinicListView.setAdapter(clinicListViewAdapter);

        // 검색 버튼 클릭 시 정보를 받아옴
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<radioSearchType.length; i++)
                    if(radioSearchType[i].isChecked())
                        new SearchClinic(i, editSearchValue.getText().toString()).execute();
                Toast.makeText(getContext(), "검색 중입니다", Toast.LENGTH_SHORT).show();
            }
        });

        // 각 ListView item 클릭 시 해당 Clinic 정보를 Intent 활용해서 새 Activity 띄움
        clinicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ClinicActivity.class);
                Clinic clicked = searchOutput.get(position);
                intent.putExtra("clinicName", clicked.name);
                intent.putExtra("clinicPosition", clicked.position);
                intent.putExtra("clinicPhone", clicked.phone_number);
                intent.putExtra("clinicTime", clicked.time);
                intent.putExtra("clinicType", clicked.type);
                startActivity(intent);
            }
        });

        return rootView;
    }

    // 입력받은 값 검색한 결과 크롤링
    private class SearchClinic extends AsyncTask<Void, Void, Void>{
        int searchType;
        final String baseURL = "https://www.mohw.go.kr";
        String target_URL;

        // 찾으려는 병원 & 진료소 종류, 찾으려는 값 입력받아 URL 설정
        SearchClinic(int searchType, String searchValue){
            this.searchType = searchType;
            switch (searchType){
                case 0:
                    target_URL = baseURL + "/react/ncov/selclinic01ls.jsp";
                    break;
                case 1:
                    target_URL = baseURL + "/react/ncov/selclinic04ls.jsp";
                    break;
            }
            if(!searchValue.equals(""))
                this.target_URL += "?SEARCHVALUE=" + searchValue;
        }

        // 설정한 URL html 받아와 필요한 정보 SearchOutput(ListView 연결)에 저장
        @Override
        protected Void doInBackground(Void... voids) {
            Document doc = null;
            try {
                doc = Jsoup.connect(target_URL).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements rows = doc.select(".tb_center tr");
            searchOutput.clear();
            for(Element row : rows) {
                String name, position, addressURL, phone_number, type = "-";
                String[] time = new String[]{
                        "등록된 정보가 없습니다.",
                        "등록된 정보가 없습니다.",
                        "등록된 정보가 없습니다."};

                Iterator<Element> col = row.select("td").iterator();
                position =  col.next().text();
                position += " " + col.next().text();
                if(searchType == 0){
                    name = col.next().text();
                    type = col.next().text();
                }else{
                    Element temp = col.next();
                    name = temp.selectFirst("strong").text();
                    time[0] = temp.selectFirst(".day_week").ownText();
                    time[1] = temp.selectFirst(".day_str").ownText();
                    time[2] = temp.selectFirst(".day_sun").ownText();
                }
                phone_number = col.next().text();
                searchOutput.add(new Clinic(name, position, phone_number, time, type));
            }
            return null;
        }

        // 작업 종료 후 변경내용 적용(ListView 갱신)
        @Override
        protected void onPostExecute(Void result){
            clinicListViewAdapter.notifyDataSetChanged();
        }
    }
}
