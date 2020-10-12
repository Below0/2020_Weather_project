package com.kokonut.NCNC.Map;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kokonut.NCNC.GpsTracker;
import com.kokonut.NCNC.R;
import com.kokonut.NCNC.Retrofit.CarWashContents;
import com.kokonut.NCNC.Retrofit.CarWashDetail;
import com.kokonut.NCNC.Retrofit.CarWashDetailType;
import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationSource;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener, LocationSource {

    private GpsTracker gpsTracker;
    private static String IP_ADDRESS = "3.131.33.128";
    private static String TAG = "phptest";
    public static final int sub = 1001;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private class CarWashType {
        String carWashName;
        List<String> carWashTypeName;
    }
    private Map<String, String> carWashTypeMap = new HashMap<>();
    private Map<String, String> carWashScoreMap = new HashMap<>();

    private List<CarWashContents> carWashContentsList;
    private List<CarWashType> carWashTypeList;
    private List<String> mCarWashTypeList = new ArrayList<>();
    private String typeStr;

    FrameLayout carWashInfoBox;
    ImageButton ibMarkGPS;
    TextView tvBoxName, tvBoxType, tvBoxTime, tvBoxAddress, tvBoxScore;
    RatingBar rbBoxStar;
    private float score;

    private MapView mapView;
    Marker selectedMarker;

    private ArrayList<Marker> markerArrayList = new ArrayList();

    private CarWashAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;

    private NaverMap naverMap;

    private FusedLocationSource locationSource;
    @Nullable
    private LocationManager locationManager;
    @Nullable
    private LocationSource.OnLocationChangedListener listener;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "----------MapFragment OnCreate--------");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "----------MapFragment OnCreateView--------");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView)viewGroup.findViewById(R.id.map_view);
        carWashInfoBox = (FrameLayout) viewGroup.findViewById(R.id.map_car_wash_info_box);
        tvBoxName = (TextView)viewGroup.findViewById(R.id.map_info_name);
        tvBoxScore = (TextView)viewGroup.findViewById(R.id.map_info_score);
        tvBoxType = (TextView)viewGroup.findViewById(R.id.map_info_type);
        tvBoxTime = (TextView)viewGroup.findViewById(R.id.map_info_time);
        tvBoxAddress = (TextView)viewGroup.findViewById(R.id.map_info_address);

        rbBoxStar = (RatingBar)viewGroup.findViewById(R.id.map_info_rating_bar);

        ibMarkGPS = (ImageButton) viewGroup.findViewById(R.id.map_mark_gps);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
        selectedMarker = new Marker();

        carWashInfoBox.setVisibility(View.INVISIBLE);
        mapView.getMapAsync(this);


//        map이 늦게 뜸
//        new AsyncTask<Void, Void, String>() {
//
//            @Override
//            protected String doInBackground(Void... params) {
//                RetrofitAPI retrofitAPI = RetrofitAPI.retrofit.create(RetrofitAPI.class);
//                RetrofitAPI retrofitAPI2 = RetrofitAPI.retrofit.create(RetrofitAPI.class);
//                Call<List<CarWashContents>> call = retrofitAPI.fetchCarWash();
//
//                try {
//                    carWashContentsList = call.execute().body();
//                    for(int i = 0; i < carWashContentsList.size(); i++){
//                        Call<CarWashDetail> call2 = retrofitAPI2.getCarWashType(carWashContentsList.get(i).getId());
//                        CarWashDetail carWashDetail = call2.execute().body();
//                        typeStr = carWashDetail.getType().get(0).getName();
//                        score = (float)carWashDetail.getScore();
//
//                        System.out.println(carWashDetail.getName() + "의 score은 " + carWashDetail.getScore());
//                        if(carWashDetail.getType().size() > 1){
//                            for(int j = 1; j < carWashDetail.getType().size(); j++) {
//                                typeStr = typeStr + ", " + carWashDetail.getType().get(j).getName();
//                            }
//                        }
//
//                        carWashScoreMap.put(carWashDetail.getName(), Float.toString(score));
//                        carWashTypeMap.put(carWashDetail.getName(), typeStr);
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//            }
//        }.execute();

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                RetrofitAPI retrofitAPI = RetrofitAPI.retrofit.create(RetrofitAPI.class);
                try {
                    carWashContentsList = retrofitAPI.fetchCarWash().execute().body();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }.execute();


        ibMarkGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsTracker = new GpsTracker(getContext());

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude)).animate(CameraAnimation.Easing);;
                naverMap.moveCamera(cameraUpdate);
            }
        });

        return viewGroup;

    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        Log.d(TAG, "----------MapFragment OnStart--------");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "----------MapFragment OnStop--------");
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "----------MapFragment OnResume--------");
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "----------MapFragment OnPause--------");
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "----------MapFragment OnDestroy--------");
        super.onDestroy();
//        mapView.onLowMemory();
        markerArrayList.clear();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "----------MapFragment OnActivityCreated - mapView Create --------");
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수
        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d(TAG, "----------MapFragment OnMapReady--------");
        this.naverMap = naverMap;
        // GPS 허용할건지 묻는 팝업창
        naverMap.setContentPadding(0, 0, 0, 350);
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        OverlayImage markerImage = OverlayImage.fromResource(R.drawable.map_mark_black);
        OverlayImage selectedMarkerImage = OverlayImage.fromResource(R.drawable.map_mark_blue);
        while(true){
            System.out.println("OnMapReadyWhile--");
            if(carWashContentsList != null){
                System.out.println("OnMapReadyWhileBreak");
                for (int index = 0; index < carWashContentsList.size(); index++) {
                    Marker marker = new Marker();
                    marker.setPosition(new LatLng(carWashContentsList.get(index).getLat(),
                            carWashContentsList.get(index).getLon()));
                    marker.setIcon(markerImage);
                    marker.setCaptionText(carWashContentsList.get(index).getName());
                    marker.setCaptionRequestedWidth(200);
                    marker.setMap(naverMap);
                    markerArrayList.add(marker);

                    marker.setOnClickListener(overlay -> {
                        System.out.println("arraylist size는 " + markerArrayList.size());
                        for (int i = 0; i < markerArrayList.size(); i++) {
                            markerArrayList.get(i).setIcon(markerImage);
                        }
                        marker.setIcon(selectedMarkerImage);

                        carWashInfoBox.setVisibility(View.VISIBLE);

                        fetchCarWash(Integer.toString(carWashContentsList.get(markerArrayList.indexOf(marker)).getId()));

//                        tvBoxName.setText(carWashContentsList.get(markerArrayList.indexOf(marker)).getName());
//                        tvBoxTime.setText(carWashContentsList.get(markerArrayList.indexOf(marker)).getOpenWeek());
//                        tvBoxAddress.setText(carWashContentsList.get(markerArrayList.indexOf(marker)).getAddress());
//                        tvBoxType.setText(carWashTypeMap.get(carWashContentsList.get(markerArrayList.indexOf(marker)).getName()));
                        carWashInfoBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                carWashInfoBox.setVisibility(View.INVISIBLE);
                                marker.setIcon(markerImage);
                                Intent intent = new Intent(getContext(), CarWashInfoActivity.class);

                                intent.putExtra("id", Integer.toString(carWashContentsList.get(markerArrayList.indexOf(marker)).getId()));
                                intent.putExtra("name", carWashContentsList.get(markerArrayList.indexOf(marker)).getName());
//                                intent.putExtra("latitude", Double.toString(carWashContentsList.get(markerArrayList.indexOf(marker)).getLat()));
//                                intent.putExtra("longitude", Double.toString(carWashContentsList.get(markerArrayList.indexOf(marker)).getLon()));
//                                intent.putExtra("address", carWashContentsList.get(markerArrayList.indexOf(marker)).getAddress());
//                                intent.putExtra("phone", carWashContentsList.get(markerArrayList.indexOf(marker)).getPhone());
//                                intent.putExtra("city", carWashContentsList.get(markerArrayList.indexOf(marker)).getCity());
//                                intent.putExtra("type", carWashTypeMap.get(carWashContentsList.get(markerArrayList.indexOf(marker)).getName()));
//                                intent.putExtra("open_week", carWashContentsList.get(markerArrayList.indexOf(marker)).getOpenWeek());
//                                intent.putExtra("open_sat", carWashContentsList.get(markerArrayList.indexOf(marker)).getOpenSat());
//                                intent.putExtra("open_sun", carWashContentsList.get(markerArrayList.indexOf(marker)).getOpenSun());

                                startActivityForResult(intent, sub);
                            }
                        });

                        return true;
                    });
                }
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        Log.d(TAG, "----------MapFragment OnRequestPermissionsResult--------");
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // Permission denied
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void activate(
            @NonNull LocationSource.OnLocationChangedListener listener) {
        Log.d(TAG, "----------MapFragment activate--------");

        if (locationManager == null) {
            return;
        }

        if (PermissionChecker.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PermissionChecker.PERMISSION_GRANTED
                && PermissionChecker.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PermissionChecker.PERMISSION_GRANTED) {
            // Permission request logic omitted.
            return;
        }

        this.listener = listener;
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 10, this);
    }

    @Override
    public void deactivate() {
        if (locationManager == null) {
            return;
        }

        listener = null;
        locationManager.removeUpdates(this);
    }


    @Override
    public void onLocationChanged(Location location) {
        if (listener != null) {
            listener.onLocationChanged(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    public void fetchCarWash(String washerId){
        RetrofitAPI retrofitAPI = RetrofitAPI.retrofit.create(RetrofitAPI.class);
        retrofitAPI.fetchCarWash().enqueue(new Callback<List<CarWashContents>>() {
            @Override
            public void onResponse(Call<List<CarWashContents>> call, Response<List<CarWashContents>> response) {
                Log.d("fetch_review", "Success: "+new Gson().toJson(response.body()));
                List<CarWashContents> carWashContentsList = response.body();

                for (CarWashContents carWashContents : carWashContentsList) {
                    if (carWashContents.getId() == Integer.parseInt(washerId)) {
                        setInfo(carWashContents);


//                        retrofitAPI2.getCarWashType(carWashContents.getId()).enqueue(new Callback<CarWashDetail>() {
//                            public void onResponse(Call<CarWashDetail> call, Response<CarWashDetail> response) {
//                                Log.d("fetch_review", "Success: " + new Gson().toJson(response.body()));
//                                CarWashDetail carWashDetail = response.body();
//                                score = (float) carWashDetail.getScore();
//
//                                typeStr = carWashDetail.getType().get(0).getName();
//                                if(carWashDetail.getType().size() > 1){
//                                    for(int j = 1; j < carWashDetail.getType().size(); j++) {
//                                        typeStr = typeStr + ", " + carWashDetail.getType().get(j).getName();
//                                    }
//                                }
//                                setInfo(carWashContents, score, typeStr);
//                            }
//
//                            @Override
//                            public void onFailure(Call<CarWashDetail> call, Throwable t) {
//                                Log.e("fetch_review", "failure: "+t.toString());
//                            }
//                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CarWashContents>> call, Throwable t) {
                Log.e("fetch_review", "failure: "+t.toString());
            }
        });
    }

    void setInfo(CarWashContents carWashContents){
        String washType = carWashContents.getWash().get(0);
        if(carWashContents.getWash().size() > 1){
            for(int j = 1; j < carWashContents.getWash().size(); j++) {
                washType = washType + ", " + carWashContents.getWash().get(j);
            }
        }
        tvBoxName.setText(carWashContents.getName());
        tvBoxTime.setText(carWashContents.getOpenWeek());
        tvBoxAddress.setText(carWashContents.getAddress());
        tvBoxType.setText(washType);
        rbBoxStar.setRating(carWashContents.getScore());
        tvBoxScore.setText(Float.toString(carWashContents.getScore()));
    }

}