package com.example.pockey;

import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainSettingsPrefDate extends AppCompatActivity {
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_settings_pref_date);

        //начало кнопки платежи
        Button buttonDeleteDate = findViewById(R.id.buttonDeleteDate);
        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        //конец кнопки платежи




        //начало кнопки история
        Button buttonBack = (Button)findViewById(R.id.buttonback);
        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Intent intent = new Intent(MainSettingsPrefDate.this,MainSettingsPref.class);
                    startActivity(intent);finish();
                }catch (Exception e){
                }
            }
        });
    }


    public void delete(View view) {

        db.execSQL(DatabaseHelper.DROP_TABLE1);
        db.execSQL(DatabaseHelper.DROP_TABLE2);
        db.execSQL(DatabaseHelper.DROP_TABLE3);
        db.execSQL(DatabaseHelper.DROP_TABLE5);

        db.execSQL(DatabaseHelper.TABLE_STRUCTURE_MAIN);
        db.execSQL(DatabaseHelper.TABLE_STRUCTURE_SUB_COMMENT);
        db.execSQL(DatabaseHelper.TABLE_STRUCTURE_SUB_QR);
        db.execSQL(DatabaseHelper.TABLE_STRUCTURE_SUB_CATPUR);
        db.execSQL(DatabaseHelper.TABLE_STRUCTURE_SUB_PAY);
        db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_MAIN + " ( "
                + DatabaseHelper.CATEGORY + " , " + DatabaseHelper.PURPOSE + " , " + DatabaseHelper.SUM + " , " + DatabaseHelper.DATE + " , " + DatabaseHelper.TIME
                + " ) VALUES ('Транспорт', 'Расход', 80, CURRENT_DATE, CURRENT_TIME);");
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