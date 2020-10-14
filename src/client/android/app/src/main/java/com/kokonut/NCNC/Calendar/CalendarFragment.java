package com.kokonut.NCNC.Calendar;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.kokonut.NCNC.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CalendarFragment extends Fragment{
    static Calendar calToday = Calendar.getInstance();
    TextView textView1;
    ViewGroup viewGroup;
    CalendarDay clickedDate;
    MaterialCalendarView materialCalendarView;
    Drawable drawable_lightpurple;
    Drawable drawable_darkblue;
    FragmentTransaction ft;
    String popupResult;
    TextView bottomeText1;
    TextView bottomeText2;
    TextView bottomeText3;
    TextView initbutton;
    TextView text1, text2, text3;
    int[] dates_info;
    private CalendarDBHelper CalendardbHelper;
    int maxScoreDay;
    customDecorator CustomDecorator;
    customDecorator CustomDecorator_darkpurple;
    String maxDayString;
    ArrayList<CalendarInfo> CalendarList;
    int recommendDate[] = {0,0};

    SQLiteDatabase sqlDB;

    private CalendarInfo CalendarInfo;

    private static final int LOADER_ID = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_calendar,container,false);
        materialCalendarView = viewGroup.findViewById(R.id.calendar_view);
        textView1 = viewGroup.findViewById(R.id.plan_text1);
        bottomeText1 = viewGroup.findViewById(R.id.bottom1_1);
        bottomeText2 = viewGroup.findViewById(R.id.bottom2_2);
        bottomeText3 = viewGroup.findViewById(R.id.bottom3_3);
        initbutton = viewGroup.findViewById(R.id.initbutton);
        text1 = viewGroup.findViewById(R.id.text1);
        text2 = viewGroup.findViewById(R.id.text2);
        text3 = viewGroup.findViewById(R.id.text3);
        CalendarList = new ArrayList<CalendarInfo>();
        drawable_lightpurple = this.getResources().getDrawable(R.drawable.calendar_circle_inside);
        drawable_darkblue = this.getResources().getDrawable(R.drawable.calendar_fullcircle);
        //drawable_darkblue = this.getResources().getDrawable();

        CalendardbHelper = CalendarDBHelper.getInstance(getActivity());

        maxScoreDay = getArguments().getInt("maxScoreDay");
        Log.d("끝0", "initCalendarDB: " + maxScoreDay);

        //세차추천일
        maxDayString = getDate(maxScoreDay, recommendDate);


        initCalendarDB();
        initCalendar();

        text3.setText(maxDayString);
        //Log.d("알았다", "onCreateView: -"+maxDayString);
        text3.setPaintFlags(text1.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

        return viewGroup;
    }

    @Override
    public void onStart(){
        super.onStart();
        textView1.setText("나의 세차 일정");
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onPause(){
        super.onPause();

    }


    public static String getDate(int weekday, int recommendedDate[]){
        /*
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("MM월dd일");
        Calendar calendar = Calendar.getInstance(); //현재 날짜
        calendar.add(Calendar.DAY_OF_MONTH, weekday); //오늘로부터 일주일일때
        String day = format.format(calendar.getTime());
        Log.d("끝5", "getDate: "+day);

        return day;

         */
       // Log.d("끝5", "getDate2: "+ weekday);
        weekday--;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("MM-dd");
        Calendar calendar = Calendar.getInstance(); //현재 날짜
        calendar.add(Calendar.DAY_OF_MONTH, weekday); //오늘로부터 일주일일때
        String day = format.format(calendar.getTime());
       // Log.d("끝5", "getDate: "+day);

        String[] res = day.split("-");
        for(int i=0; i<2; i++){
            recommendedDate[i] = Integer.parseInt(res[i]);
        }

        String res_day = String.format("%d월%d일", recommendedDate[0], recommendedDate[1]);

        return res_day;

    }

    public void initCalendar() {

        // 세차추천일 view 추가

        CustomDecorator_darkpurple = new customDecorator(getActivity(), drawable_darkblue,CalendarDay.from(2020, recommendDate[0], recommendDate[1]), 5);
        materialCalendarView.addDecorators(CustomDecorator_darkpurple);

        Log.d("추천 일 임니", ",," + recommendDate[0] + " / "+ recommendDate[1] );

        bottomeText1.setText("세차 예정일 ");
        bottomeText2.setText("세차한 날 ");
        bottomeText3.setText("세차하기 좋은 날");

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        materialCalendarView.state().edit()
                //.setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(2020, 1, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 30))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        /*오늘 날짜에 초록색 효과를 줌*/
        OneDayDecorator oneDayDecorator = new OneDayDecorator("세차새차");
        materialCalendarView.addDecorators(oneDayDecorator);

        /**달력 날짜를 클릭했을 때 이벤트 처리**/
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Bundle args = new Bundle();
                args.putString("clickedDate", date.toString());
                Calendar_PopupFragment calendar_popupfragment = new Calendar_PopupFragment();
                calendar_popupfragment.setArguments(args);
                calendar_popupfragment.show(getFragmentManager(), "tag");
                clickedDate = date;
            }
        });


        initbutton.setText("초기화");
        initbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //dynamic_maxmilli[0] = -1; //maxmilli[0] 내부세차, maxmilli[1] 외부세차
                //dynamic_maxmilli[1] = -1;

                //1. view 의 데코레이터 모두 삭제
                materialCalendarView.removeDecorators();

                //2. arraylist 의 정보 모두 삭제
                CalendarList.clear();

                //3. db 데이터 모두 삭제
                CalendardbHelper.deleteAllrecord();

                //4. 나의 세차 일정 view 초기화
                text1.setText("");
                text2.setText("");

                //5. 세차하기 좋은 날 뷰 추가
                CustomDecorator_darkpurple = new customDecorator(getActivity(), drawable_darkblue,CalendarDay.from(2020, recommendDate[0], recommendDate[1]), 5);
                materialCalendarView.addDecorators(CustomDecorator_darkpurple);

                //6.오늘 날짜 뷰 추가
                OneDayDecorator oneDayDecorator = new OneDayDecorator("세차새차");
                materialCalendarView.addDecorators(oneDayDecorator);


                /*
                sqlDB = CalendardbHelper.getWritableDatabase();
                CalendardbHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
                 */
            }
        });
    }

    private void callUploadDialog()
    {
        Calendar_PopupFragment fragment = new Calendar_PopupFragment();
        fragment.setTargetFragment(this, 0);
        FragmentManager manager;
        manager = getFragmentManager();
        ft = manager.beginTransaction();
        fragment.show(ft, "UploadDialogFragment");
        fragment.setCancelable(false);
    }

    //캘린더 뷰에 동그라미 뷰를 추가할 경우
    public void devidepopupValue(int checkedList){
        long[] dynamic_maxmilli = {-1,-1}; //maxmilli[0] 내부세차, maxmilli[1] 외부세차

        /*value 가 내부 , 외부 , 전체 인지에 따라 동그라미 아래 text 달리해줄것 */
        int cur_part;

        //이미 동그라미가 처져있는 날짜를 또다시 클릭했을 때 전체로 바꿀지 판단
        for(int p=0; p<CalendarList.size(); p++){

            if(CalendarList.get(p).getDate().toString().equals(clickedDate.toString())){

                //1. Arraylist 에서 삭제
                materialCalendarView.removeDecorator(CalendarList.get(p).getCustomDecorator());
                materialCalendarView.invalidateDecorators();
                CalendarList.remove(CalendarList.get(p));

                //2. DB에서 삭제
                CalendardbHelper.deleteRecord(clickedDate.toString());

            }
        }

        // 1. view 추가
        CustomDecorator = new customDecorator(getActivity(), drawable_lightpurple, clickedDate, checkedList);
        materialCalendarView.addDecorators(CustomDecorator);

        // 2. arraylist 에 날짜대입
        CalendarList.add(new CalendarInfo(CustomDecorator, clickedDate, checkedList));

        // 3. db에 날짜 추가
        Log.d("csg", "devidepopupValue: " + checkedList);
        CalendardbHelper.insertRecord(clickedDate.toString(), checkedList, CustomDecorator.getColor());

        Cursor cursor = CalendardbHelper.readRecordOrderByAge();
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry._ID)); //?
            String gotDate = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_DATE));
            int gotPart = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_PART));
            Log.d("클릭했을 때 디비 안 날짜", "initCalendarDB: "+gotDate);
            Log.d("--> 클릭했을 때 디비 안 dynamic", ": "+dynamic_maxmilli[0] + ", "+ dynamic_maxmilli[1]);

            //나의 세차일정 갱신함 -- 안될거같은데;

            parceDate(clickedDate.toString());
            CalendarDateView calendarDateView = new CalendarDateView(dynamic_maxmilli);
            Log.d("dynamic 내부 콜---", " "+dynamic_maxmilli[0]);
            Log.d("dynamic 외부 콜---", " "+dynamic_maxmilli[1]);

            ///&&&&
            Date datee11 = new Date(dynamic_maxmilli[0]);
            SimpleDateFormat format22 = new SimpleDateFormat("yyyy-MM-dd");
            Log.d("1. datee1 클릭한 날짜 ", " : " + format22.format(datee11));
            Date datee21 = new Date(dynamic_maxmilli[1]);
            SimpleDateFormat format31 = new SimpleDateFormat("yyyy-MM-dd");
            Log.d("1. datee2 클릭한 날짜 ", " : " + format31.format(datee21));
            ///&&&&

            calendarDateView.findAdjacentDateFromToday(dates_info, checkedList, dynamic_maxmilli);

            Log.d("dynamic 내부 콜_2---", " "+dynamic_maxmilli[0]);
            Log.d("dynamic 외부 콜_2---", " "+dynamic_maxmilli[1]);

            ///&&&&
            Date datee1 = new Date(dynamic_maxmilli[0]);
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            Log.d("2. datee1 클릭한 날짜 ", " : " + format2.format(datee1));
            Date datee2 = new Date(dynamic_maxmilli[1]);
            SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
            Log.d("2. datee2 클릭한 날짜 ", " : " + format3.format(datee2));
            ///&&&&

            setmyPlanView(dynamic_maxmilli);

        }

    }


    //long in_maxmilli =-1; //내부세차
    //long out_maxmilli = -1; //외부세차
    long[] maxmilli = {-1,-1}; //maxmilli[0] 내부세차, maxmilli[1] 외부세차
    long calmilli = 0;

    private void initCalendarDB(){

        Calendar calTemp;
        calTemp = calToday;

        //1. 빼고
        //2. 뺀 정보를 info class 에 나눠서 넣기
        Cursor cursor = CalendardbHelper.readRecordOrderByAge();

        int i = 0;
        while (cursor.moveToNext()) {

            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry._ID)); //?
            String gotDate = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_DATE));
            int gotPart = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_PART));
            Log.d("gjkd1", "initCalendarDB: "+gotPart);
            String getCalendarObject = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.CalendarEntry.COLUMN_CALENDAROBJECT));

            //db
            // 초기화 과정*
            parceDate(gotDate);
            CalendarDay CalendarDay_date = CalendarDay.from(dates_info[0], dates_info[1], dates_info[2]);
            Log.d("222223", "initCalendarDB: " + CalendarDay_date.toString());

            //오늘로부터 과거에 가장 최근에 세차한 날을 계산하는 함수
            CalendarDateView calendarDateView = new CalendarDateView(maxmilli);
            calendarDateView.findAdjacentDateFromToday(dates_info, gotPart, maxmilli);
            Log.d("콜 - 내부세차", "initCalendarDB: " + maxmilli[0]);
            Log.d("콜 - 외부세차", "initCalendarDB: " + maxmilli[1]);

            //customDecorator CustomDecorator = new customDecorator(getActivity(), drawable_lightpurple, CalendarDay_date , gotPart);
            CustomDecorator = new customDecorator(getActivity(), drawable_lightpurple, CalendarDay_date , gotPart);

            // 1. arraylist 에 날짜대입
            CalendarList.add(new CalendarInfo(CustomDecorator, CalendarDay_date, gotPart));

            // 2. view 추가
            materialCalendarView.addDecorators(CustomDecorator);

        }
        cursor.close();


        Date date = new Date(calToday.getTimeInMillis());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format1.format(date));
        Log.d("포맷 데이트3", "getGapDay: " + format1.format(date));

        Log.d("오늘로부터 가장 가까운 외부세차", "initCalendarDB: " + maxmilli[1]);
        Log.d("오늘로부터 가장 가까운 내부세차", "initCalendarDB: " + maxmilli[0]);

        //나의 세차일정 뷰 세팅
        setmyPlanView(maxmilli);

    }

    private void setmyPlanView(long maxmilli[]){

        long subCal;

        if(maxmilli[0] > -1){
            Log.d("...오늘로부터내부세차 on text", "initCalendarDB: " + maxmilli[1]);
            subCal = getGapDay(maxmilli[0]);
            text1.setText(Long.toString(subCal)+"일 경과");
            text1.setPaintFlags(text1.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        }

        if(maxmilli[1] > -1){
            Log.d("...오늘로부터외부세차 on text", "initCalendarDB: " + maxmilli[1]);
            subCal = getGapDay(maxmilli[1]);
            Log.d("백 subcal", " : "+subCal);
            text2.setText(Long.toString(subCal)+"일 경과");
            text2.setPaintFlags(text1.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        }
    }

    private boolean isPastDate(long maxmilli){

        Log.d("파데", "isPastDate: " + maxmilli);
        Calendar calendarToday = Calendar.getInstance();
        Date date = calendarToday.getTime();
        long today = date.getTime();

        Log.d("파데오늘", "isPastDate: " + today);


        Date date7 = new Date(today);
        SimpleDateFormat format7 = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("포맷 쭉", "getGapDay: " + format7.format(date7));


        if(maxmilli < today) {

            Log.d("과거다", "initCalendarDB: ");
            Log.d("쭉", "initCalendarDB: " + maxmilli);
            Date date3 = new Date(maxmilli);
            SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
            Log.d("포맷 쭉", "getGapDay: " + format3.format(date3));
            Log.d("포맷 쭉", "getGapDay: " + maxmilli);


            return true;
        }
        else {

            Log.d("미래다", "initCalendarDB: ");
            Log.d("쭉", "initCalendarDB: " + maxmilli);
            Date date4 = new Date(maxmilli);
            SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM-dd");
            Log.d("포맷 쭉", "getGapDay: " + format4.format(date4));
            Log.d("포맷 쭉", "getGapDay: " + maxmilli);
            return false;
        }
    }

    private long getGapDay(long calendaree){
        Calendar calendarToday = Calendar.getInstance();

        Date date = new Date(calendarToday.getTimeInMillis());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date2 = new Date(calendaree);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

        Log.d("gapday 클릭한 날짜 ", " : " + format2.format(date2));

        long sub = (calendarToday.getTimeInMillis()- calendaree);
        //long sub = (calendaree - calendarToday.getTimeInMillis());
        Log.d("포맷 데이트4410", "getGapDay: " + sub);

        if(sub < 0){
            sub *= -1;
        }

        long oneday = 1000 * 60 * 60 * 24;
        long day = sub / oneday;
        Log.d("차이", "getGapDay: " + day);

        return day;
    }


    //calendarday 형식을 int 형으로 배열에 각각 저장한다
    private void parceDate(String calendar_date){

        int i=0; int last = 0;
        String result;
        dates_info = new int[3];

        result = calendar_date.substring(12);
        String[] dates_string = result.split("-");

        for(i=0; i<2; i++)
            dates_info[i] = Integer.parseInt(dates_string[i]);

        result = dates_string[i].replace("}", "");
        dates_info[i] = Integer.parseInt(result);

        for(i=0; i<3; i++)
            Log.d("$$$$$", "parceDate: " + dates_info[i]);

    }

    //-월-일 형식을 int 형으로 분리한다
    /*
    private String parceDateFromDateKor(String calendarday_kor){

        int res[] = new int[2];
        int i=0; int date_ind, date, day, year;
        String result;

        Date  = new Date(calToday.getTimeInMillis());
        SimpleDateFormat formatofyear = new SimpleDateFormat("yyyy");
        year = Integer.toString(formatofyear.format(date));

        date_ind = calendarday_kor.indexOf("월");
        date = Integer.parseInt(calendarday_kor.substring(0,date_ind));
        Log.d("변환 날짜 ", "parceDateFromDateKor: " + date_ind + "// " + calendarday_kor.indexOf("일"));
        day = Integer.parseInt(calendarday_kor.substring(date_ind+1, calendarday_kor.indexOf("일")));
        Log.d("변환 날짜 ", "parceDateFromDateKor: " + date +"/ " + day);

        res[0] = date;
        res[1] = day;

        return intToCalendarDay(res);  //Calendar{0000-00-00} 형식 return

    }*/
/*
    private String intToCalendarDay(int res[]){
        String year, calendarDay;

        Date date = new Date(calToday.getTimeInMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        year = format.format(date);
        Log.d("이번년도는 ", "getGapDay: " + format.format(date));

        calendarDay = String.format("CalendarDay{%s-%d-%d}", year, res[0], res[1]);

        Log.d("바꿈", "intToCalendarDay: "+calendarDay);

        return calendarDay;
    }
*/



    public void removeCustomDecorator(int checkedList) {
        //dynamic_maxmilli[0] = -1; //maxmilli[0] 내부세차, maxmilli[1] 외부세차
        //dynamic_maxmilli[1] = -1;


        //삭제 버튼을 눌렀을 때
        if (CalendarList.size() > 0 && checkedList == 4) {
            for (int i = 0; i < CalendarList.size(); i++) {
                if (CalendarList.get(i).getDate().equals(clickedDate.toString())) {
                    Log.d("2-----isthesame", "removeCustomDecorator: " + CalendarList.get(i).getDate());

                    //1. 나의 세차일정 view 삭제
                    if(CalendarList.get(i).getPart()==1)
                        text1.setText(" ");
                    else if(CalendarList.get(i).getPart()==2)
                        text2.setText(" ");

                    //2. Arraylist 에서 삭제
                    materialCalendarView.removeDecorator(CalendarList.get(i).getCustomDecorator());
                    materialCalendarView.invalidateDecorators();
                    CalendarList.remove(CalendarList.get(i));

                    //3. DB에서 삭제
                    CalendardbHelper.deleteRecord(clickedDate.toString());

                }
            }
        }
    }
}