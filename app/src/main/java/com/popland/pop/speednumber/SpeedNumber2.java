package com.popland.pop.speednumber;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;


public class SpeedNumber2 extends AppCompatActivity {

    EditText edt;
    String strC, tgran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_number2);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("18 DIGITS IN 7 SECONDS ???");
        toolbar.setTitleTextColor(Color.parseColor("#0a090a"));
        toolbar.setBackgroundColor(Color.parseColor("#5ef5f5"));
        setSupportActionBar(toolbar);

        edt =(EditText)findViewById(R.id.EDT);
        Intent intent = getIntent();
           strC = intent.getStringExtra("strC");
           tgran = intent.getStringExtra("tgran");

        edt.addTextChangedListener(new TextWatcher() {
            @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             int index = edt.getText().length();
             String str1 = strC.charAt(index - 1)+"";
             String str2 = edt.getText().subSequence(index - 1, index)+"";
             final Dialog dlg = new Dialog(SpeedNumber2.this,android.R.style.Theme_Translucent_NoTitleBar);
                dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dlg.setContentView(R.layout.result);
                dlg.setCancelable(false);
                //draw full screen Dialog
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dlg.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                //lp.gravity = Gravity.CENTER;
                dlg.getWindow().setAttributes(lp);

                TextView tvTitle =(TextView)dlg.findViewById(R.id.TVtitle);
                TextView tvSTRC = (TextView)dlg.findViewById(R.id.TVstrC);
                TextView tvCorrect = (TextView)dlg.findViewById(R.id.TVcorrect);
                TextView tvTime = (TextView)dlg.findViewById(R.id.TVtime);
                Button btnContinue = (Button)dlg.findViewById(R.id.BTNcontinue);
                Button btnStart = (Button)dlg.findViewById(R.id.BTNstart);
                ShareButton shareButton = (ShareButton)dlg.findViewById(R.id.BTNshare);
                if(str1.equals(str2)) {
                    MediaPlayer song = MediaPlayer.create(SpeedNumber2.this, R.raw.type1);
                    song.start();
                    if(index == 18) {
                        if(Float.parseFloat(tgran)<7) {
                            MediaPlayer mp =  MediaPlayer.create(SpeedNumber2.this, R.raw.votay);
                            mp.start();
                            tvTitle.setText("Yes, you can");
                            btnContinue.setEnabled(false);
                        }
                        else
                        tvTitle.setText("No, you can't");
                        tvCorrect.setText("Correct: 18 ");
                        dlg.show();
                    }
                }
                else{
                    MediaPlayer song = MediaPlayer.create(SpeedNumber2.this, R.raw.eee1);
                    song.start();
                    tvTitle.setText("No, you can't");
                    tvCorrect.setText("Correct : "+(index-1));
                    dlg.show();
                }
                tvSTRC.setText(strC);
                tvTime.setText("Time : "+tgran+"s");

                btnContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dlg.cancel();
                        Intent i = new Intent();
                        i.putExtra("level",5);
                        setResult(RESULT_CANCELED,i);
                        finish();
                    }
                });
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                           dlg.cancel();
                        Intent i = new Intent();
                        i.putExtra("level",1);
                        setResult(RESULT_CANCELED,i);
                        finish();
                    }
                });

                String outcome = "REMEMBER 18 DIGITS IN 7S???\n  "+tvSTRC.getText()+". "+tvTitle.getText()+". "
                                 +tvCorrect.getText()+". "+tvTime.getText();
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentTitle("18 DIGITS IN 7S?")
                        .setQuote(outcome)
                        .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.popland.pop.speednumber"))
                        .build();
                shareButton.setShareContent(content);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
