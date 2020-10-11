package com.kokonut.NCNC.Home.Tab2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.kokonut.NCNC.GpsTracker;
import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.R;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Tab2Fragment extends Fragment {
    private RetrofitAPI retrofitAPI;

    private GpsTracker gpsTracker;
    public Double myLat, myLon;

    ArrayAdapter<CharSequence> adspin1, adspin2, adspin3;
    ArrayAdapter<CharSequence> dayspin, timespin;

    public ArrayList<SelectedSearchInfo> selectedSearchInfo = new ArrayList<>();
    public String selectedLocation1; //시
    public String selectedLocation2; //구
    public String selectedLocation3; //동
    public String selectedTime1 = "선택안함"; //요일
    public String selectedTime2 = "선택안함";//시간
    public ArrayList<String> selectedKind = new ArrayList(); //세차종류

    public Tab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_tab2, container, false);

        ImageButton searchButton = viewGroup.findViewById(R.id.home_tab2_searchbutton);

        RadioGroup radioGroup = viewGroup.findViewById(R.id.home_tab2_radiogroup);
        RadioButton rb1 = viewGroup.findViewById(R.id.home_tab2_mylocation);
        RadioButton rb2 = viewGroup.findViewById(R.id.home_tab2_locationsetting);

        CheckBox time_check = viewGroup.findViewById(R.id.home_tab2_vistingtime);
        CheckBox kind_check = viewGroup.findViewById(R.id.home_tab2_kindofnewcar);
        CheckBox carwash1_check = viewGroup.findViewById(R.id.checkbox_newcar1);
        CheckBox carwash2_check = viewGroup.findViewById(R.id.checkbox_newcar2);
        CheckBox carwash3_check = viewGroup.findViewById(R.id.checkbox_newcar3);
        CheckBox carwash4_check = viewGroup.findViewById(R.id.checkbox_newcar4);

        final Spinner address_sp1 = viewGroup.findViewById(R.id.home_tab2_spinner_location1);
        final Spinner address_sp2 = viewGroup.findViewById(R.id.home_tab2_spinner_location2);
        final Spinner address_sp3 = viewGroup.findViewById(R.id.home_tab2_spinner_location3);
        address_sp1.setPrompt(getResources().getString(R.string.location_spinner1)); address_sp2.setPrompt(getResources().getString(R.string.location_spinner2)); address_sp3.setPrompt(getResources().getString(R.string.location_spinner3));

        adspin1 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region, android.R.layout.simple_spinner_dropdown_item);
        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        address_sp1.setAdapter(adspin1);

        address_sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adspin1.getItem(i).equals("서울특별시")){
                    selectedLocation1 = "서울시";

                    adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    address_sp2.setAdapter(adspin2);

                    address_sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedLocation2 = adspin2.getItem(i).toString();

                            switch (i) {
                                case 0:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_gangnam, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 1:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_gangdong, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 2:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_gangbuk, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 3:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_gangseo, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 4:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_gwanak, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 5:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_gwangjin, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 6:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_guro, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 7:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_geumcheon, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 8:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_nowon, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 9:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_dobong, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 10:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_dongdaemun, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 11:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_dongjag, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 12:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_mapo, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 13:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_seodaemun, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 14:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_seocho, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 15:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_seongdong, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 16:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_seongbuk, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 17:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_songpa, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 18:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_yangcheon, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 19:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_yeongdeungpo, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 20:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_yongsan, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 21:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_eunpyeong, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 22:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_jongno, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 23:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_jung, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                                case 24:
                                    adspin3 = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_region_seoul_jungnanggu, android.R.layout.simple_spinner_dropdown_item);
                                    break;
                            }
                            adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            address_sp3.setAdapter(adspin3);

                            address_sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    selectedLocation3 = adspin3.getItem(i).toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Spinner time_sp1 = viewGroup.findViewById(R.id.home_tab2_spinner_visitingtime1);
        Spinner time_sp2 = viewGroup.findViewById(R.id.home_tab2_spinner_visitingtime2);
        time_sp1.setPrompt("요일"); time_sp2.setPrompt("시간");


        dayspin = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_day, android.R.layout.simple_spinner_dropdown_item);
        //ArrayAdapter<String> timeSp1Adapter = new ArrayAdapter<String>.createFromResource(getActivity(), R.array.spinner_day, android.R.layout.simple_spinner_dropdown_item);
        dayspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_sp1.setAdapter(dayspin);

        //ArrayAdapter<String> timeSp2Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sp2_items);
        timespin = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_time, android.R.layout.simple_spinner_dropdown_item);
        timespin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_sp2.setAdapter(timespin);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.home_tab2_mylocation){
                    address_sp1.setVisibility(View.INVISIBLE);
                    address_sp2.setVisibility(View.INVISIBLE);
                    address_sp3.setVisibility(View.INVISIBLE);

                    //현재 위치
                    gpsTracker = new GpsTracker(getContext());
                    myLat = gpsTracker.getLatitude();
                    myLon = gpsTracker.getLongitude();
                    List<Address> address = null;
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    try {
                        address = geocoder.getFromLocation(myLat, myLon, 1);
                    }
                    catch(IOException e){
                        e.printStackTrace();
                        Log.d("getaddress","input/output error");
                    }

                    if(!address.isEmpty()){
                        selectedLocation1 = address.get(0).getLocality(); //시 - 서울시
                        selectedLocation2 = address.get(0).getSubLocality(); //구
                        selectedLocation3 = address.get(0).getThoroughfare(); //동
                    }
                }
                else{
                    address_sp1.setVisibility(View.VISIBLE);
                    address_sp2.setVisibility(View.VISIBLE);
                    address_sp3.setVisibility(View.VISIBLE);
                }
            }
        });

        //'방문시각' 체크박스
        time_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(time_check.isChecked()){
                    time_sp1.setVisibility(View.VISIBLE);
                    time_sp2.setVisibility(View.VISIBLE);

                }
                else{
                    time_sp1.setVisibility(View.INVISIBLE);
                    time_sp2.setVisibility(View.INVISIBLE);
                    selectedTime1 = "선택안함";
                    selectedTime2 = "선택안함";
                }
            }
        });

        //요일 spinner 선택리스너
        time_sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position!=0){
                    dayspin.notifyDataSetChanged();
                    selectedTime1 = "";
                    selectedTime1 = (String) dayspin.getItem(position);
                }
                else{
                    time_sp1.requestFocus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(),"요일을 선택해주세요.",Toast.LENGTH_LONG).show();
                time_sp1.requestFocus();
            }
        });

        //시간 spinner 선택리스너
        time_sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                selectedTime2 = "";
                selectedTime2 = String.valueOf(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(),"시간을 선택해주세요.",Toast.LENGTH_LONG).show();
                time_sp2.requestFocus();
            }
        });

        //'세차종류' 체크박스
        //selectedKind.add("선택안함");
        kind_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(kind_check.isChecked()){
                    carwash1_check.setVisibility(View.VISIBLE);
                    carwash2_check.setVisibility(View.VISIBLE);
                    carwash3_check.setVisibility(View.VISIBLE);
                    carwash4_check.setVisibility(View.VISIBLE);

                    switch(buttonView.getId()){
                        case R.id.checkbox_newcar1:
                            if(isChecked){
                                selectedKind.add("셀프세차");
                            }
                            break;
                        case R.id.checkbox_newcar2:
                            if(isChecked){
                                selectedKind.add("자동세차");
                            }
                            break;
                        case R.id.checkbox_newcar3:
                            if(isChecked){
                                selectedKind.add("손세차");
                                selectedKind.add("디테일링");
                            }
                            break;
                        case R.id.checkbox_newcar4:
                            if(isChecked){
                                selectedKind.add("실내세차");
                            }
                            break;
                    }
                }
                else{
                    carwash1_check.setVisibility(View.INVISIBLE);
                    carwash2_check.setVisibility(View.INVISIBLE);
                    carwash3_check.setVisibility(View.INVISIBLE);
                    carwash4_check.setVisibility(View.INVISIBLE);
                }
            }
        });


        //'세차장 검색' 버튼 클릭 시 - Tab2_searchList 액티비티로 전환
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedLocation1==null || selectedLocation2==null || selectedLocation3==null){
                    Toast.makeText(getContext(),"위치를 선택해주세요.",Toast.LENGTH_LONG).show();
                }
                else{
                    selectedSearchInfo.add(new SelectedSearchInfo(selectedLocation1, selectedLocation2, selectedLocation3,
                            selectedTime1, selectedTime2, selectedKind));

                    Intent intent = new Intent(getActivity(), Tab2_SearchList.class);
                    intent.putExtra("selectedinfodata", (Serializable)selectedSearchInfo);
                    startActivity(intent);
                }

                if(selectedKind == null){
                    selectedKind.add("선택안함");
                }

                //System.out.println(selectedLocation1+selectedLocation2+selectedLocation3+selectedTime1+selectedTime2+selectedKind.toString());
            }
        });
    return viewGroup;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}