package com.kokonut.NCNC.Home.Tab1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.kokonut.NCNC.R;

public class Tab1_PopupFragment extends DialogFragment {

    private int lastValue1, lastValue2, lastValue3; //마지막으로 설정한 '맞춤형 세차점수 설정하기'
    public String result1, result2, result3; //설정한거

    View view;
    TextView textView_title, textView_subtitle;
    ImageButton buttonX, buttonOK;
    TextView textView1, textView2, textView3, textView1_score, textView2_score, textView3_score;
    ImageView imageView1, imageView2, imageView3;

    public Tab1_PopupFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //lastValue = UserConfig.getConfigInt(getActivity(), "gainvalue", 40);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tab1__popup, container, false);
        textView_title = view.findViewById(R.id.home_popup_title);
        textView_subtitle = view.findViewById(R.id.home_popup_subtitle);

        buttonX = view.findViewById(R.id.home_popup_buttonX);
        buttonOK = view.findViewById(R.id.home_popupButton);

        imageView1 = view.findViewById(R.id.home_popup_Image1);
        imageView2 = view.findViewById(R.id.home_popup_Image2);
        imageView3 = view.findViewById(R.id.home_popup_Image3);

        textView1 = view.findViewById(R.id.home_popup_text1);
        textView2 = view.findViewById(R.id.home_popup_text2);
        textView3 = view.findViewById(R.id.home_popup_text3);

        textView1_score = view.findViewById(R.id.home_popup_score1);
        textView2_score = view.findViewById(R.id.home_popup_score2);
        textView3_score = view.findViewById(R.id.home_popup_score3);

        final SeekBar seekBar1 = view.findViewById(R.id.home_popup_seekBar1);
        //seekBar1.setProgress(lastValue); 마지막으로 설정한 값 기억
        //seekBar1.incrementProgressBy(1); 1씩 증가
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekbar1, int progress, boolean fromUser) { //드래그하는중
                result1 = Integer.toString(progress);
                textView1_score.setText(result1);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekbar1) { //드래그 시작

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekbar1) { //드래그 멈춤

            }
        });

        final SeekBar seekBar2 = view.findViewById(R.id.home_popup_seekBar2);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekbar2, int progress, boolean fromUser) {
                result2 = Integer.toString(progress);
                textView2_score.setText(result2);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekbar2) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekbar2) {}
        });

        final SeekBar seekBar3 = view.findViewById(R.id.home_popup_seekBar3);
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekbar3, int progress, boolean fromUser) {

                result3 = Integer.toString(progress);
                textView3_score.setText(result3);

                Log.d("11111111", "onProgressChanged: " + result3);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekbar3) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekbar3) {}
        });

        seekBar1.setMax(10); seekBar2.setMax(10); seekBar3.setMax(10);
        seekBar1.setProgress(0); seekBar2.setProgress(0); seekBar3.setProgress(0); //초기값


        //X버튼 클릭 시 - 팝업창 닫기
        buttonX.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                /*FragmentManager manager = getFragmentManager();
                Fragment prev = manager.findFragmentByTag("tab1");
                if(prev!=null){
                    DialogFragment dialogfragment = (DialogFragment) prev;
                    dialogfragment.dismiss();
                }*/
            }
        });

        //설정하기버튼 클릭
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //result1,2,3 디비에 저장하기

                getDialog().dismiss();
            }
        });


        setCancelable(false); //popup에서 여백을 만져도 꺼지지 않게 함

        return view;
    }

}