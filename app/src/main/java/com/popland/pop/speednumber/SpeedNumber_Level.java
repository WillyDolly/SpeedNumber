package com.popland.pop.speednumber;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class SpeedNumber_Level extends AppCompatActivity {
String strC;
TextView TVshow;
int level,a =0,b, row, column;
ArrayList<String> numberArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_number__level);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("18 DIGITS IN 7 SECONDS ???");
        toolbar.setTitleTextColor(Color.parseColor("#0a090a"));
        toolbar.setBackgroundColor(Color.parseColor("#5ef5f5"));
        setSupportActionBar(toolbar);

        Intent in = getIntent();
        level = in.getIntExtra("level",0);
        strC = in.getStringExtra("strC");
        numberArray = new ArrayList<>();
        switch(level){
            case 1: row = 2;column = 1;
                    makeSubstring(2,9);   break;
            case 2: row = 3;column = 1;
                    makeSubstring(3,6);   break;
            case 3: row = 2;column = 3;
                    makeSubstring(6,3);   break;
            case 4: row = 3;column = 3;
                    makeSubstring(9,2);   break;
        }
        Collections.shuffle(numberArray);

        TVshow = (TextView)findViewById(R.id.TVshow);
        TableLayout TL = (TableLayout)findViewById(R.id.tableLayout);
        for(int r=1;r<=row;r++){
            TableRow TR = new TableRow(this);
            TR.setGravity(Gravity.CENTER_HORIZONTAL);
            for(int c=1;c<=column;c++){
              final Button btn = new Button(this);
                btn.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                int position = column*(r-1) + (c-1);
                btn.setText(numberArray.get(position));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        compare(btn);
                    }
                });
                TR.addView(btn);
            }
            TL.addView(TR);
        }
    }

    void compare(Button btn){
        TVshow.append(btn.getText());
        int index = TVshow.getText().length();
        String typingString = TVshow.getText().toString();
        String subString = strC.substring(0,index);
        if(typingString.matches(subString)){
            MediaPlayer type =  MediaPlayer.create(SpeedNumber_Level.this,R.raw.type1);
            type.start();
            btn.setBackgroundColor(Color.parseColor("#FFC2F541"));
            btn.setEnabled(false);
            if(index==18){
                MediaPlayer clap = MediaPlayer.create(SpeedNumber_Level.this,R.raw.votay);
                clap.start();
               Intent i = new Intent();
                i.putExtra("level",++level);
                setResult(RESULT_OK,i);
                finish();
            }
        }else{
            MediaPlayer ee = MediaPlayer.create(SpeedNumber_Level.this,R.raw.eee1);
            ee.start();
            Intent i = new Intent();
            i.putExtra("level",level);
            setResult(RESULT_CANCELED,i);
            finish();
        }
    }

    void makeSubstring(int StringCon, int uoc){
        for(int i=1;i<=StringCon;i++){
            b = a + uoc;
            String string = strC.substring(a,b);
            numberArray.add(string);
            a = b;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
