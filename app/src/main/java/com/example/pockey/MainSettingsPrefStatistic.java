package com.example.pockey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainSettingsPrefStatistic extends AppCompatActivity {

    private long backPressedTime;
    private  Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_settings_pref);


        //начало кнопки аналитики
        Button buttonAnalytics = (Button)findViewById(R.id.buttonAnalytics);
        buttonAnalytics.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Intent intent = new Intent(MainSettingsPrefStatistic.this,UserActivity.class);
                    startActivity(intent);finish();
                }catch (Exception e){

                }
            }

        });
        //конец кнопки






    }

    //системная кнопка
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(this, MainSettingsPref.class);
            startActivity(intent);finish();
        }catch (Exception e){}
    }
}