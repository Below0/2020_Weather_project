package com.kokonut.NCNC.Home.Tab1;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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
import androidx.fragment.app.FragmentActivity;

import com.kokonut.NCNC.Home.HomeContract;
import com.kokonut.NCNC.Home.HomeDBHelper;
import com.kokonut.NCNC.Home.ScoreInfoData;
import com.kokonut.NCNC.R;
import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Retrofit.RetrofitClient;
import com.kokonut.NCNC.Retrofit.ScoreContents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class Tab1_PopupFragment extends DialogFragment {
    RetrofitAPI retrofitAPI;
    List<ScoreContents.Content> scoreContentsList;
    ArrayList<ScoreInfoData> scoreInfoData;
    int maxScore=0, maxScoreDay=0;
    public Tab1Fragment.scoreTask sct;




    private DialogInterface.OnDismissListener onDismissListener = null;

    private int lastValue1, lastValue2, lastValue3; //마지막으로 설정한 '맞춤형 세차점수 설정하기'
    public String result1, result2, result3; //설정한거

    String val1, val2, val3;  //ㅈㅇ
    int rain = -1, temp = -1, dust = -1;//ㅈㅇ
    SeekBar seekBar1, seekBar2, seekBar3;//ㅈㅇ
    private HomeDBHelper HomedbHelper;//ㅈㅇ

    uploadDialogInterface interfaceObj;

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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            interfaceObj = (Tab1_PopupFragment.uploadDialogInterface)activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement interfaceObj");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        interfaceObj = null;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        HomedbHelper = HomedbHelper.getInstance(getActivity()); //db 객체 생성

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

        seekBar1 = view.findViewById(R.id.home_popup_seekBar1);
        seekBar2 = view.findViewById(R.id.home_popup_seekBar2);
        seekBar3 = view.findViewById(R.id.home_popup_seekBar3);

        initDB(); //db 로 seekbar 초기화

        //seekBar1.setProgress(lastValue); 마지막으로 설정한 값 기억
        //seekBar1.incrementProgressBy(1); 1씩 증가
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekbar1, int progress, boolean fromUser) { //드래그하는중
                /*
                result1 = Integer.toString(progress);
                textView1_score.setText(result1);

                 */
                val1 = Integer.toString(progress);
                textView1_score.setText(val1);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekbar1) { //드래그 시작

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekbar1) { //드래그 멈춤

            }
        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekbar2, int progress, boolean fromUser) {
                /*result2 = Integer.toString(progress);
                textView2_score.setText(result2);
                 */
                val2 = Integer.toString(progress);
                textView2_score.setText(val2);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekbar2) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekbar2) {}
        });

        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekbar3, int progress, boolean fromUser) {

                /*
                result3 = Integer.toString(progress);
                textView3_score.setText(result3);

                String val3 = Integer.toString(progress);
                textView3_score.setText(val3);
                */
                val3 = Integer.toString(progress);
                textView3_score.setText(val3);

                Log.d("11111111", "onProgressChanged: " + val3);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekbar3) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekbar3) {}
        });

        seekBar1.setMax(10); seekBar2.setMax(10); seekBar3.setMax(10);
        //seekBar1.setProgress(0); seekBar2.setProgress(0); seekBar3.setProgress(0); //초기값


        //X버튼 클릭 시 - 팝업창 닫기
        buttonX.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                result1 = Integer.toString(temp);
                result2 = Integer.toString(rain);
                result3 = Integer.toString(dust);

                Log.d("result값들 --'x'눌렀을 경우 ", " "+ result1 + ", "+ result2 +", "+ result3);

                HomedbHelper.insertRecord(temp, rain, dust); //color 안씀


                getDialog().dismiss();
            }
        });

        //설정하기버튼 클릭
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //result1,2,3 디비에 저장하기
                int getTemp = 0, getRain = 0, getDust = 0;
                Log.d("2323", "onClick: ");

                Log.d("드래그 크기 ", "onCreateView: "+val1);
                Log.d("드래그 크기 ", "onCreateView: "+val2);
                Log.d("드래그 크기 ", "onCreateView: "+val3);

                //  rain, temp, dust : db에서 가져온 이전값
                // getRain, getTemp, getDust : 지금 선택한 값
                //세개의 seekbar 중 드래그 하지 않은값이 있으면 이전값으로 대체함

                if(val1 == null)
                    getTemp = temp;
                else
                    getTemp = Integer.parseInt(val1);

                if(val2 == null)
                    getRain = rain;
                else
                    getRain = Integer.parseInt(val2);

                if(val3 == null)
                    getDust = dust;
                else
                    getDust = Integer.parseInt(val3);

                Log.d("드래그 크기 111", "onCreateView: "+getTemp);
                Log.d("드래그 크기 222", "onCreateView: "+getRain);
                Log.d("드래그 크기 333", "onCreateView: "+getDust);

                /*result1, result2, result3 에 저장함!*/
                result1 = Integer.toString(getTemp);
                result2 = Integer.toString(getRain);
                result3 = Integer.toString(getDust);

                Log.d("result값들 --'설정' 눌렀을 경우 ", " "+ result1 + ", "+ result2 +", "+ result3);

                HomedbHelper.insertRecord(getTemp, getRain, getDust); //color 안씀

                if(interfaceObj != null) {
                    Log.d("wow7", "탭1한테 데이터 보낸다");
                    interfaceObj.senddatatoTab1Fragment();
                }
                else
                    Log.d("wow7", "탭1한테 데이터 못보냄");



                Cursor cursor = HomedbHelper.readRecordOrderByID();

                int i = 0;
                while (cursor.moveToNext()) {
                    temp = cursor.getInt(cursor.getColumnIndexOrThrow(HomeContract.homeEntry.COLUMN_TEMPERATURE));
                    rain = cursor.getInt(cursor.getColumnIndexOrThrow(HomeContract.homeEntry.COLUMN_RAIN));
                    dust = cursor.getInt(cursor.getColumnIndexOrThrow(HomeContract.homeEntry.COLUMN_DUST));

                    Log.d("캬캬컄캬캬", "onClick: "+temp);
                    Log.d("캬캬컄캬캬", "onClick: "+rain);
                    Log.d("캬캬컄캬캬", "onClick: "+dust);
                }


                if(onDismissListener!=null){
                    onDismissListener.onDismiss((DialogInterface) Tab1_PopupFragment.this);
                }

                getDialog().dismiss();
            }
        });


        setCancelable(false); //popup에서 여백을 만져도 꺼지지 않게 함
        return view;
    }
    private void initDB() {
        Cursor cursor;
        if (HomedbHelper == null) {
            return;
        }

        cursor = HomedbHelper.readRecordOrderByID();
        int i = 0;
        while (cursor.moveToNext()) {
            Log.d("데이터 있음 ", "initDB: ");
            temp = cursor.getInt(cursor.getColumnIndexOrThrow(HomeContract.homeEntry.COLUMN_TEMPERATURE));
            rain = cursor.getInt(cursor.getColumnIndexOrThrow(HomeContract.homeEntry.COLUMN_RAIN));
            dust = cursor.getInt(cursor.getColumnIndexOrThrow(HomeContract.homeEntry.COLUMN_DUST));
        }

        //db 데이터 모두 삭제
        SQLiteDatabase sqlDB = HomedbHelper.getWritableDatabase();
        HomedbHelper.onUpgrade(sqlDB, 1, 2);
        sqlDB.close();


        Log.d("~!~!---1", "devidepopupValue: " + temp);
        Log.d("~!~!---2", "devidepopupValue: " + rain);
        Log.d("~!~!---3", "devidepopupValue: " + dust);


        //db에 아무 정보가 없을 경우 = 처음일 경우 view 세팅은 0, 변수값은 5를 넣어줌
        if (temp < 0 || rain < 0 || dust < 0) {
            Log.d("정보가 없을 때 ", "initDB: ");
            seekBar1.setProgress(0);
            seekBar2.setProgress(0);
            seekBar3.setProgress(0);
            textView1_score.setText("0");
            textView2_score.setText("0");
            textView3_score.setText("0");

            //사용자가 0점을 줬을 땐 5점을 주라는말 맞나...? 아니면 0으로 바꾸면 됨!!
            temp = 5;
            rain = 5;
            dust = 5;
            seekBar1.setProgress(temp);
            seekBar2.setProgress(rain);
            seekBar3.setProgress(dust); //초기값

        }
        //db에 정보가 있을 경우 = 이전에 값을 설정했을 경우 그 값을 불러옴
        else {
            Log.d("정보가 있을 때 ", "initDB: ");
            seekBar1.setProgress(temp);
            seekBar2.setProgress(rain);
            seekBar3.setProgress(dust);

            textView1_score.setText(Integer.toString(temp));
            textView2_score.setText(Integer.toString(rain));
            textView3_score.setText(Integer.toString(dust));
            Log.d("TASD", "test");

        }

/*
        Log.d("밖임", "initDB: ");
        Tab1Fragment tab1Fragment = new Tab1Fragment();
        Tab1Fragment.scoreTask innerScoreTask = tab1Fragment.new scoreTask(getActivity(), temp, rain, dust);
        innerScoreTask.selfRestart();
*/
    }

    public interface uploadDialogInterface
    {
        //자동으로 public 으로 선언되기 때문에 public 안써도 됨
        void senddatatoTab1Fragment();
    }


}