package com.popland.pop.speednumber;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.text.DecimalFormat;
import java.util.Random;

public class SpeedNumber extends AppCompatActivity {
    TextView tv, tvLevel;
    Button btnGo, btnStop;
    CountDownTimer cdt;
    float secondLeft;
    int level = 1;
    int specificSecond;
    long totalTime;
    String tgran, strC;
    MediaPlayer song;
    int requestCode1 = 111, requestCode2 = 222;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_number);
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-7964058081042027~4231874398");
        AdView adv = (AdView)findViewById(R.id.adView);
        AdRequest adr = new AdRequest.Builder()
               // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();//remove addTestDevice before release
        adv.loadAd(adr);
        //install openSSL: https://indy.fulgan.com/SSL/ -> copy to C:\ & System Variable -> run cmd
        //keytool -exportcert -alias androiddebugkey -keystore %HOMEPATH%\.android\debug.keystore | openssl sha1 -binary | openssl base64
        //keytool -exportcert -alias SpeedNumberKey -keystore D:\SpeedNumber.jks | openssl sha1 -binary | openssl base64
        //keyHash:  tHBg4UC6Opyeb0j1n/zNpP+9AyI=(debug)  haeMKImKqppxKTlh9QDbvW0lhvI=(release)
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("18 DIGITS IN 7 SECONDS ???");
        toolbar.setTitleTextColor(Color.parseColor("#0a090a"));
        toolbar.setBackgroundColor(Color.parseColor("#5ef5f5"));
        setSupportActionBar(toolbar);

        tv = (TextView) findViewById(R.id.TV);
        tvLevel = (TextView)findViewById(R.id.TVlevel);
        btnGo = (Button) findViewById(R.id.BTNgo);
        btnStop = (Button) findViewById(R.id.BTNstop);
        song = MediaPlayer.create(SpeedNumber.this, R.raw.woodclock1);

        tvLevel.setText("Level "+level);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGo.setEnabled(false);
                Random r = new Random();
                int soA = 123456789 + r.nextInt(876543211);
                String strA = String.valueOf(soA);
                int soB = 123456789 + r.nextInt(876543211);
                String strB = String.valueOf(soB);
                strC = strA + strB;
                tv.setText(strC);
                switch(level){
                    case 1: totalTime = 6000; specificSecond = 5; break;
                    case 2: totalTime = 9000; specificSecond = 8; break;
                    case 3: totalTime = 18000; specificSecond = 17; break;
                    case 4: totalTime = 27000; specificSecond = 26; break;
                    case 5: totalTime = 30000; specificSecond = 29; break;
                }
                cdt = new CountDownTimer(totalTime, 1) {
                    @Override
                    public void onTick(long ms) {
                        //determine 1s passed
                        secondLeft = (float) ms / 1000;
                        String str = "0."+String.format("%03d",ms%1000);
                        float STR = Float.parseFloat(str);
                        int f = Math.round(secondLeft-STR);
                        if(f!=specificSecond){//if current second's different from the previous one
                            specificSecond = f;
                            song.start();
                        }
                        // the clock goes off faster in the last 5s
                        if(totalTime == 30000) {
                            if (f <= 5)
                                song.start();
                        }
                    }

                    @Override
                    public void onFinish() {
                        chuyen();
                    }
                }.start();
                btnStop.setEnabled(true);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdt.cancel();
                chuyen();
            }
        });
    }

    public void chuyen(){
        if(level!=5){
           Intent i1 = new Intent(SpeedNumber.this, SpeedNumber_Level.class);
            i1.putExtra("level",level);
            i1.putExtra("strC",strC);
            startActivityForResult(i1,requestCode1);
        }else{
            Intent i2 = new Intent(SpeedNumber.this, SpeedNumber2.class);
            DecimalFormat dcf = new DecimalFormat("0.000");
            tgran = dcf.format(30-secondLeft);
            i2.putExtra("tgran",tgran);
            i2.putExtra("strC",strC);
            startActivityForResult(i2,requestCode2);
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==111 && (resultCode == RESULT_OK || resultCode == RESULT_CANCELED)){
            level = data.getIntExtra("level",0);
        }
        if(requestCode==222 && resultCode == RESULT_CANCELED){
            level = data.getIntExtra("level",0);
        }
        btnGo.setEnabled(true);
        btnStop.setEnabled(false);
        tv.setText("");
        tvLevel.setText("Level "+level);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
//C:\Users\hai\AppData\Local\Android\sdk1\platform-tools