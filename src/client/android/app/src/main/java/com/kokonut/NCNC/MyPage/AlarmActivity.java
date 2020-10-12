package com.kokonut.NCNC.MyPage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kokonut.NCNC.BroadcastAlarm;
import com.kokonut.NCNC.MainActivity;
import com.kokonut.NCNC.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmActivity extends AppCompatActivity {

    ImageButton login_but;
    Switch switch1, switch2, switch3, switch4;
    int check1=0, check2=0, check3=0, check4=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        switch1 = findViewById(R.id.visibilitySwitch1);
        switch2 = findViewById(R.id.visibilitySwitch2);
        switch3 = findViewById(R.id.visibilitySwitch3);
        switch4 = findViewById(R.id.visibilitySwitch4);

        ImageButton preButton = findViewById(R.id.alram_backarrow);
        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        MainActivity mainActivity = new MainActivity();
        int maxDayScore = mainActivity.getScoreDay();

        switchInit();

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            int a;
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                new AlarmHATT(getApplicationContext(), 1, b).Alarm();
            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                new AlarmHATT(getApplicationContext(), 2, b).Alarm();
            }
        });

        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                new AlarmHATT(getApplicationContext(), 3, b).Alarm();
            }
        });

        switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                new AlarmHATT(getApplicationContext(), 4, b).Alarm();
            }
        });

    }

    private void switchInit(){
        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences sf = getSharedPreferences("alarmFile",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        int getcheck1 = sf.getInt("check1",-1); // (key, 해당값이 없을경우 리턴)
        int getcheck2 = sf.getInt("check2",-1);
        int getcheck3 = sf.getInt("check3",-1);
        int getcheck4 = sf.getInt("check4",-1);

        Log.d("getcheck1", "switchInit: "+getcheck1);
        Log.d("getcheck2", "switchInit: "+getcheck2);
        Log.d("getcheck3", "switchInit: "+getcheck3);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("stoppppppppp", "onStop: ");
        // Activity가 종료되기 전에 저장
        //SharedPreferences를 alarmFile이름, 기본모드로 설정
        SharedPreferences sharedPreferences = getSharedPreferences("alarmFile",MODE_PRIVATE);

        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("check1", check1); //푸시 상태 저장
        editor.putInt("check2", check2);
        editor.putInt("check3", check3);
        editor.putInt("check4", check4);


        //최종 커밋
        editor.commit();
    }

    public class AlarmHATT {
        private Context context;
        int maxScore;
        int check;
        boolean ischecked;
        public AlarmManager am;
        public PendingIntent sender;

        public AlarmHATT(Context context, int check, boolean ischecked) {
            this.context = context;
            this.check = check;
            this.ischecked = ischecked;
        }

        public void getIntentInfo() {

        }

        public void Alarm() {
            getIntentInfo();

            /*푸시알람 설정 여부에 따라 실행 여부 결정*/

            // 현재시간을 msec 으로 구한다.
            long now = System.currentTimeMillis();
            // 현재시간을 date 변수에 저장한다.
            Date date = new Date(now);
            // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
            //SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            // nowDate 변수에 값을 저장한다.
            String formatDate = sdfNow.format(date);
            Log.d("formatDate", "Alarm: " + formatDate);


//            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(AlarmActivity.this, BroadcastAlarm.class);


            //알람시간 calendar에 set해주기

            //세차하기 좋은 날, 매주 월요일! 이번 주의 세차하기 좋은 날을 추천해줍니다.
            if (check == 1) {
                if (ischecked == true) {
                    check1 = 1; //sharedpreference 상태값을 위한 변수

                    sender = PendingIntent.getBroadcast(AlarmActivity.this, 1, intent, 0);
                    /*매주 월요일마다 알람이 울리기 위해서 check가 1일 때 알람이 울리고 그 날 기준으로
                    7일 뒤에 또 알람을 설정하는 방식 */
                    WeekCheck weekCheck = new WeekCheck(2); //매주 월요일 알람이 울리기 위해 2(월)로 설정
                    long monTime = weekCheck.getWeekTime();
                    long cur = weekCheck.getWeekTime();

                    //am.setRepeating(AlarmManager.RTC_WAKEUP, monTime, 604800000, sender); //일주일 단위로 월요일에 알람울림

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 9, 26, 30);
                    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 30000, sender); //일주일 단위로 월요일에 알람울림

                } else {
                    Log.d("cancle", "Alarm: "+222);
                    cancelAlarmManger(1);
                }
                check = 0;
                check1 = 0;
            }

            //세차 예정일, 사용자가 설정한 세차 주기에 따른 다음 세차 예정일을 알려줍니다.
            if (check == 2) {
                if (ischecked == true) {
                    check2 = 1; //sharedpreference 상태값을 위한 변수
                    sender = PendingIntent.getBroadcast(AlarmActivity.this, 2, intent, 0);
                    Calendar calendar = Calendar.getInstance();
                    Log.d("5552", "Alarm: ");
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 11, 34, 30);
                    //알람 예약
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                }else {
                    Log.d("cancle", "Alarm: "+222);
                    cancelAlarmManger(2);
                }
                check = 0;
                check2 = 0;
            }

            //세차 예약일, 예약된 세차 일정을 알려줍니다.
            if (check == 3) {
                if (ischecked == true) {

                    check3 = 1; //sharedpreference 상태값을 위한 변수
                    sender = PendingIntent.getBroadcast(AlarmActivity.this, 3, intent, 0);
                    Calendar calendar = Calendar.getInstance();
                    Log.d("5553", "Alarm: ");
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 11, 35, 30);
                    //알람 예약
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                }else {
                    Log.d("cancle", "Alarm: "+222);
                    cancelAlarmManger(2);
                }
                check = 0;
                check3 = 0;
            }

            //오늘의 주의사항, 오늘 날씨를 기준으로 운전자를 위한 자동차 운행 관련 주의사항을 제공합니다.
            if (check == 4) {
                if (ischecked == true) {

                    check4 = 1; //sharedpreference 상태값을 위한 변수
                    sender = PendingIntent.getBroadcast(AlarmActivity.this, 4, intent, 0);
                    Calendar calendar = Calendar.getInstance();
                    Log.d("5554", "Alarm: ");
                    //calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 11, 36, 30);
                    calendar.set(Calendar.DAY_OF_WEEK, 1); //1 : 일 2: 월 ...
                    calendar.set(Calendar.HOUR, 19);
                    calendar.set(Calendar.MINUTE, 9);
                    calendar.set(Calendar.SECOND, 30);

                    //알람 예약
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                }else {
                    Log.d("cancle", "Alarm: "+222);
                    cancelAlarmManger(2);
                }
                check = 0;
                check4 = 0;
            }
        }

        public void cancelAlarmManger(int START_ID) {
            if (sender != null) {
                am = (AlarmManager) getApplicationContext().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext().getApplicationContext(), BroadcastAlarm.class);
                sender = PendingIntent.getBroadcast(getApplicationContext().getApplicationContext(), START_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                am.cancel(sender);
                sender.cancel();
                am = null;
                sender = null;
            }
        }
    }
}

class WeekCheck {

    int week_temp;
    public WeekCheck(int week_temp){
        this.week_temp = week_temp;
    }

    public WeekCheck(String week){  //특정요일을 영어대문자로 받음
        String weekName = week;

        int week_temp = 0;

        if(weekName.equals("SUNDAY")){
            week_temp = 1;
        }else if(weekName.equals("MONDAY")){
            week_temp = 2;
        }else if(weekName.equals("TUESDAY")){
            week_temp = 3;
        }else if(weekName.equals("WEDNESDAY")){
            week_temp = 4;
        }else if(weekName.equals("THURSDAY")){
            week_temp = 5;
        }else if(weekName.equals("FRIDAY")){
            week_temp = 6;
        }else if(weekName.equals("SATURDAY")){
            week_temp = 7;
        }else{
            System.out.println("에러");
        }
    }

    public long getWeekTime(){
        long weekTime=0;
        Calendar calendar = Calendar.getInstance(); //시스템의 현재 날짜와 시간정보를 얻음
        Calendar values =  nextDayOfWeek(week_temp);  //그 요일에 해당하는 숫자를 넘김
        String sss = StringFromCalendar(values);   //그다음 주 해당요일 날짜를 뽑아와서 20130116 이런식으로 표현
        //substring : 주어진 범위만큼 문자열 자름
        String year = sss.substring(0, 4);     //년도를 뽑음
        String month = sss.substring(4,6);     //월을 뽑음
        String date = sss.substring(6,8);     //일을 뽑음

        //현재시간에서 그 다음주 원하는 요일의 12:00으로 시간 설정
        calendar.set(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(date), 12, 00); //년, 월, 일 , 시 , 분 까지 들어감. (서버시간 다음주 특정요일)

        weekTime = calendar.getTimeInMillis();    //그 날짜 분 시 까지 입력된 TimeInMillis 를 뽑음
        System.out.println(calendar.getTimeInMillis());
        return weekTime;         //리턴으로 넘어감.
    }

    public static Calendar nextDayOfWeek(int dow){    //입력받은 요일의 현재 서버시간의 다음주 요일을 뽑아내는 함수
        //Calendar date = Calendar.getInstance();
        Calendar date = Calendar.getInstance();

        int diff = dow - date.get(Calendar.DAY_OF_WEEK); // 입력받은 요일 - 현재 요일 , 수 - 월
        //dow: 원하는 요일
        //date.get(Calendar.DAY_OF_WEEK): 현재 요일


        if(!(diff > 0)) {
            diff += 7;
        } //ex) 목 --> 월 , 1 - 4 + 7 = 4, 목 --> 금, 토, 일, 월


        date.add(Calendar.DAY_OF_MONTH, diff); //DAY_OF_MONTH: ~일(ex, 1~31일)
        //Calendar.add: 기존의 날짜에 주어진 인자 날짜에 diff 만큼 더하는 함수

        return date;
    }

    public static String StringFromCalendar(Calendar cal)    {        // 날짜를 통신용 문자열로 변경
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(cal.getTime());
    }
}