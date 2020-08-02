package com.kokonut.NCNC.Calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        textView_Date.setText("2020년 7월 23일");
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
                if(checkBox1.isChecked() == true) result = 1;
                if(checkBox2.isChecked() == true) {
                    if(result == 1){
                        result = 3; //내부, 외부 둘다 선택
                    }
                    result = 2;
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

    public interface uploadDialogInterface
    {
        //자동으로 public 으로 선언되기 때문에 public 안써도 됨
        void senddatatoCalendarFragment(int checkedlist);
    }
}
