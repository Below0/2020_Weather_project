package com.kokonut.NCNC.MyPage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kokonut.NCNC.R;


public class MypageFragment extends Fragment {
    ImageView login_but;
    ViewGroup viewGroup;
    LinearLayout setalarm;
    LinearLayout setasktosecha;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_mypage,container,false);
        login_but = viewGroup.findViewById(R.id.login_but);
        setalarm = viewGroup.findViewById(R.id.setalarm);
        setasktosecha = viewGroup.findViewById(R.id.setasktosecha);

        setalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("111111", "onClick: ");

                Intent intent = new Intent(getActivity(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        setasktosecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("111111", "onClick: ");

                Intent intent = new Intent(getActivity(), AsktoSechaSecha.class);
                startActivity(intent);
            }
        });

        return viewGroup;
    }
}