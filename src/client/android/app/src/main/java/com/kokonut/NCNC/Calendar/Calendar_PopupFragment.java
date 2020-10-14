package com.kokonut.NCNC.Calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.kokonut.NCNC.R;

public class Calendar_PopupFragment extends DialogFragment {
    View view;
    TextView textView_Date;
    TextView calendar_textview_delete;
    ImageButton buttonAdd;
    Context context;
    int result;

    uploadDialogInterface interfaceObj;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        view  = inflater.inflate(R.layout.activity_calendar_popup, container);
        textView_Date = view.findViewById(R.id.calendar_textview_date);
        buttonAdd = view.findViewById(R.id.buttonAdd);
        calendar_textview_delete = view.findViewById(R.id.calendar_textview_delete);

        Bundle mArgs = getArguments();
        String mValue = mArgs.getString("clickedDate");
        mValue = parceDate(mValue);
        textView_Date.setText(mValue);

        setCancelable(false); //popup에서 여백을 만져도 꺼지지 않게 함
        checkList();

        return view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            interfaceObj = (uploadDialogInterface)activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement interfaceObj");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //textView_Date.setText("2020년 7월 23일");

    }


    //.//.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interfaceObj = null;
    }

    private void checkList(){
        Log.d("wow", "customDecorator: is null 1111");

        final CheckBox checkBox1 = view.findViewById(R.id.checkBox1);
        final CheckBox checkBox2 = view.findViewById(R.id.checkBox2);


        /** 데이터 삭제를 눌렀을 때 **/
        calendar_textview_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ㄹ", "customDecorator: is null 1122-2");

                result = 4;

                if(interfaceObj != null && result != 0) {
                    Log.d("wow7", "customDecorator: is null 1122-2");
                    interfaceObj.senddatatoCalendarFragment(result);
                }
                else
                    Log.d("wow7", "customDecorator: is null 1122-3");

                dismiss();
            }
        });

        /** 체크박스 눌렀을 때 **/
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int ind = 0;
                result = 0;
                //String result = "_";
            /*
                if(checkBox1.isChecked() == true) result += (checkBox1.getText().toString() + "_");
                if(checkBox2.isChecked() == true) result += (checkBox2.getText().toString() + "_");
                if(checkBox3.isChecked() == true) result += (checkBox3.getText().toString() + "_");
*/
                if(checkBox1.isChecked() == true) result = 1; //내부 선택
                if(checkBox2.isChecked() == true) {
                    if(result == 1){
                        result = 3; //내부, 외부 둘다 선택
                    }
                    else
                        result = 2; //외부 선택
                }

                Log.d("wow", "customDecorator: is null 1122");


                if(interfaceObj != null && result != 0) {
                    Log.d("wow7", "customDecorator: is null 1122-2");
                    interfaceObj.senddatatoCalendarFragment(result);
                }
                else
                    Log.d("wow7", "customDecorator: is null 1122-3");

                dismiss();
            }
        });
    }

    private String parceDate(String calendar_date){


        int i=0; int last = 0;
        String result;
        int[] dates_info = new int[3];

        result = calendar_date.substring(12);
        String[] dates_string = result.split("-");

        for(i=0; i<2; i++)
            dates_info[i] = Integer.parseInt(dates_string[i]);

        result = dates_string[i].replace("}", "");
        dates_info[i] = Integer.parseInt(result);

        for(i=0; i<3; i++)
            Log.d("$$$$$", "parceDate: " + dates_info[i]);

        calendar_date = dates_info[0] + "년 " + dates_info[1] + "월 " + dates_info[2] + "일 ";

        Log.d("앙", "parceDate: " + calendar_date);

        return calendar_date;
    }

    public interface uploadDialogInterface
    {
        //자동으로 public 으로 선언되기 때문에 public 안써도 됨
        void senddatatoCalendarFragment(int checkedlist);
    }
}
