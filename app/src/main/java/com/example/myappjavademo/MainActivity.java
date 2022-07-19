package com.example.myappjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import org.mariuszgromada.math.mxparser.*;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private EditText workingsTV;
    TextView resultsTV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        trans_Statusbar();




        resultsTV=(TextView) findViewById(R.id.ResultsTextview);
        workingsTV = (EditText) findViewById(R.id.WorkingsTextview);
        workingsTV.setShowSoftInputOnFocus(false);
        workingsTV.setSelection(workingsTV.getText().length());


    }

    private void trans_Statusbar(){
        if(Build.VERSION.SDK_INT>=19 && Build.VERSION.SDK_INT<21){
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if(Build.VERSION.SDK_INT>=19){
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if(Build.VERSION.SDK_INT>=21){
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(int i, boolean b) {
        Window win=getWindow();
        WindowManager.LayoutParams winParams=win.getAttributes();
        if(b){
            winParams.flags |=i;
        }
        else{
            winParams.flags &=~i;
        }
        win.setAttributes(winParams);
    }

    private void updateText(String s){
        String old_str=workingsTV.getText().toString();
        int cursorPos= workingsTV.getSelectionStart();
        String left_str=old_str.substring(0,cursorPos);
        String right_str=old_str.substring(cursorPos);
        if(workingsTV.equals(workingsTV.getText().toString()))
        {
            workingsTV.setText(s);
            workingsTV.setSelection(cursorPos+1);
        }
        else{
            workingsTV.setText(String.format("%s%s%s",left_str,s,right_str));
            workingsTV.setSelection(cursorPos+1);
        }

    }
    public void zero_btn(View view){
        updateText("0");
    }
    public void one_btn(View v){
        updateText("1");
    }
    public void two_btn(View v){
        updateText("2");
    }
    public void three_btn(View v){
        updateText("3");
    }
    public void four_btn(View v){
        updateText("4");
    }
    public void five_btn(View v){
        updateText("5");
    }
    public void six_btn(View v){
        updateText("6");
    }
    public void seven_btn(View v){
        updateText("7");
    }
    public void eight_btn(View v){
        updateText("8");
    }
    public void nine_btn(View v){
        updateText("9");
    }
    public void plus_btn(View v){
        updateText("+");
    }
    public void minus_btn(View v){
        updateText("-");
    }
    public void divide_btn(View v){
        updateText("÷");
    }
    public void multiply_btn(View v){
        updateText("×");
    }
    public void clear_btn(View v){
        workingsTV.setText("");
        resultsTV.setText("");
    }
    public void power_btn(View v){
        updateText("^");
    }
    public void dot_btn(View v){
        updateText(".");
    }
    public void paren_btn(View v){
       int cursor_pos=workingsTV.getSelectionStart();
       int open_par=0;
       int close_par=0;
       int text_len=workingsTV.getText().length();

       for (int i=0;i<cursor_pos;i++)
       {
           if(workingsTV.getText().toString().substring(i,i+1).equals("("))
           {
               open_par+=1;
           }
           if(workingsTV.getText().toString().substring(i,i+1).equals(")"))
           {
               close_par+=1;
           }
       }
       if(open_par == close_par || workingsTV.getText().toString().substring(text_len-1,text_len).equals("("))
       {
           updateText("(");
       }
        else if(close_par < open_par  && !workingsTV.getText().toString().substring(text_len-1,text_len).equals("("))
        {
            updateText(")");
        }
        workingsTV.setSelection(cursor_pos+1);
    }
    public void equal_btn(View v){
        String user_exp=workingsTV.getText().toString();
        user_exp=user_exp.replaceAll("÷","/");
        user_exp= user_exp.replaceAll("×","*");

        Expression exp=new Expression(user_exp);

        String  result=String.valueOf(exp.calculate());
        resultsTV.setText(result);

    }
    public void backspace_btn(View v){
    int cursor=workingsTV.getSelectionStart();
    int text_len=workingsTV.getText().length();

    if(cursor!=0 && text_len!=0){
        SpannableStringBuilder selection=(SpannableStringBuilder) workingsTV.getText();
        selection.replace(cursor-1,cursor,"");
        workingsTV.setText(selection);
        workingsTV.setSelection(cursor-1);
    }
    }


}