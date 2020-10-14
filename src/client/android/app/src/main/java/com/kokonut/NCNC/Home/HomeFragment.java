package com.kokonut.NCNC.Home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kokonut.NCNC.Home.Tab1.Tab1Fragment;
import com.kokonut.NCNC.R;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {


    private static final int PERMISSIONS_REQUEST_CODE = 100;

    // 앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    LinearLayout layout;
    static int REQUEST_CODE = 1;


    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewGroup viewGroup;
    List<String> tabLayoutTextArray = Arrays.asList("오늘의 세차", "세차장 검색");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("HomeFragment", "onCreate: 1");


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);


        tabLayout = viewGroup.findViewById(R.id.home_tablayout);
        viewPager2 = viewGroup.findViewById(R.id.home_viewpager2);
        viewPager2.setAdapter(new Tab_Viewpager2Adapter(this));
        viewPager2.setSaveEnabled(false);

        layout = viewGroup.findViewById(R.id.home_layout);

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(tabLayoutTextArray.get(position))
        ).attach();


        //int locationPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        Log.d("HomeFragment", "onCreateView: 1");






/*
        if ( locationPermission == PermissionChecker.PERMISSION_GRANTED) {
            // 이전에 사용자가 위치 접근 권한 허용한 경우
            Log.d("locationPermission", "PERMISSION_ACCEPTED");
            //Toast.makeText(getContext(), "위치 정보 접근 권한 허용 상태", Toast.LENGTH_LONG).show();
            getCurrentLocation();
            //result.putString("bundleKey", "result");
            //getChildFragmentManager().setFragmentResult("requestKey", result);
        }else {
            Log.d("locationPermission", "PERMISSION_DENIED");
            // 이전에 사용자가 위치 접근 권한 거부한 경우
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), PERMISSIONS[0])) {
                //Toast.makeText(getContext(), "위치 정보 접근 권한 거부 상태", Toast.LENGTH_LONG).show();

            } else {
                // 사용자가 퍼미션 거부를 한 적이 없는 경우(앱 최초 접속시), '다시 묻지 않음 - 거부'한 경우
                // 요청 결과는 onRequestPermissionResult에서 수신.
                //Toast.makeText(getContext(), "위치 정보 접근 권한 없음", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions( getActivity(), PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
                getCurrentLocation();
                //result.putString(resultKey, str1);
                //getParentFragmentManager().setFragmentResult(requestKey, result);
            }
        }





*/

        return viewGroup;
    }
/*
    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){ super.onResume(); }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("11111", "onRequestPermissionsResult in");
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(getContext(), R.string.no_permission_msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //위도,경도 -> 시,구,동 변환
    private void GetAddress(Double latitude, Double longitude){
        List<Address> address = null;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            address = geocoder.getFromLocation(latitude, longitude, 1);
        }
        catch(IOException e){
            e.printStackTrace();
            Log.d("getaddress","input/output error");
        }

        if(!address.isEmpty()){
            str1 = address.get(0).getAdminArea(); //시
            str2 = address.get(0).getSubLocality(); //구
            str3 = address.get(0).getThoroughfare(); //동
            Log.d("Home_GetAddress", str1+" "+str2+" "+str3);
            //passingData();
        }


    }


    public void getCurrentLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // location.getLatitude()로 위도, locaation.getLongitude()로 경도 받아옴!
                        if (location != null) {
                            //tvLocation.setText("위도 " + location.getLatitude() + ", 경도 " + location.getLongitude());
                            GetAddress(location.getLatitude(), location.getLongitude());
                        }
                        else{
                            Log.d("Home_GetCurrentLocation", "위치를 알 수 없습니다");
                            //tvLocation.setText("위치를 알 수 없습니다.");
                        }
                    }
                });
    }

    private boolean isLocationPermissionGranted() {
        SharedPreferences preference = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 위치 필요한데 위치 접근 거부했을 때 권한이 필요하다고 TOAST 띄우기
                Snackbar snackBar = Snackbar.make(layout, R.string.suggest_permissison_grant, Snackbar.LENGTH_INDEFINITE);
                snackBar.setAction("권한 승인", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(getActivity()
                                , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                                , REQUEST_CODE);
                    }
                });
                snackBar.show();
            } else {
                if (isFirstCheck) {
                    // 처음 물었는지 여부를 저장
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply();
                    // 권한 요청
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                } else {
                    // 사용자가 권한을 거부하면서 다시 묻지않음 옵션을 선택한 경우
                    // requestPermission을 요청해도 창이 나타나지 않기 때문에 설정창으로 이동한다.
                    Snackbar snackBar = Snackbar.make(layout, R.string.suggest_permissison_grant_in_setting, Snackbar.LENGTH_INDEFINITE);
                    snackBar.setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
                    snackBar.show();
                }
            }
            return false;
        } else {
            return true;
        }
    }
 */
}