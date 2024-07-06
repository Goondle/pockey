package com.example.pockey;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.time.chrono.ChronoLocalDateTime;

public class MainSettingsPref extends AppCompatActivity {



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
                    Intent intent = new Intent(MainSettingsPref.this,MainSettingsPrefAnalytics.class);
                    startActivity(intent);finish();
                }catch (Exception e){
                }
            }
        });
        //конец кнопки аналитики

        //начало кнопки платежи
        Button buttonPay = (Button)findViewById(R.id.buttonPay);
        buttonPay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Intent intent = new Intent(MainSettingsPref.this,MainSettingsPrefPay.class);
                    startActivity(intent);finish();
                }catch (Exception e){
                }
            }
        });
        //конец кнопки платежи

        //начало кнопки платежи
        Button buttonDate = (Button)findViewById(R.id.buttonDate);
        buttonDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Intent intent = new Intent(MainSettingsPref.this,MainSettingsPrefDate.class);
                    startActivity(intent);finish();
                }catch (Exception e){
                }
            }
        });
        //конец кнопки платежи
        //начало кнопки история
        Button buttonHistory = (Button)findViewById(R.id.buttonHistory);
        buttonHistory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Intent intent = new Intent(MainSettingsPref.this,MainAllHistory.class);
                    startActivity(intent);finish();
                }catch (Exception e){
                }
            }
        });
        //конец кнопки история
        //начало кнопки история
        Button buttonCategoryMain = (Button)findViewById(R.id.buttonCategoryMain);
        buttonCategoryMain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Intent intent = new Intent(MainSettingsPref.this, MainSettingsPrefCategory.class);
                    startActivity(intent);finish();
                    System.out.println("хуй 3");

                }catch (Exception e){             System.out.println("хуй 3");

                }
            }
        });
        //конец кнопки история



//        //начало кнопки
//        Button buttonBlock2 = (Button)findViewById(R.id.buttonBlock2);
//        buttonBlock2.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                try{
//                    Intent intent = new Intent(MainSettingsPref.this,MainActivity.class);
//                    startActivity(intent);finish();
//                }catch (Exception e){
//                }
//            }
//        });
//        //конец кнопки


        //начало кнопки
        Button buttonInfo = (Button)findViewById(R.id.buttonInfo);
        buttonInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Intent intent = new Intent(MainSettingsPref.this,MainSettingsPrefInfo.class);
                    startActivity(intent);finish();
                }catch (Exception e){

                }
            }

        });

        //конец кнопки
        //начало кнопки история
        Button buttonBack = (Button)findViewById(R.id.buttonback);
        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Intent intent = new Intent(MainSettingsPref.this,MainActivity.class);
                    startActivity(intent);finish();
                }catch (Exception e){
                }
            }
        });
        //конец кнопки история
        //начало кнопки
//        Button buttonAdd = (Button)findViewById(R.id.buttonAdd);
//        buttonAdd.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                try{
//                    Intent intent = new Intent(MainSettingsPref.this,UserActivity.class);
//                    startActivity(intent);finish();
//                }catch (Exception e){
//
//                }
//            }
//
//        });
        //конец кнопки




    }


    //системная кнопка
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);finish();
        }catch (Exception e){}
    }

}