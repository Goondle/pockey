package com.example.pockey;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainSettingsPrefPay extends AppCompatActivity {

    ListView userList;
    ListView userList1;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    Cursor userCursor1;
    SimpleCursorAdapter userAdapter;
    SimpleCursorAdapter userAdapter1;

    // с нуля -0-1-2   6-год
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_all_pay);


        userList = findViewById(R.id.list);
        userList1 = findViewById(R.id.list2);

//блок прокрутки двух listview

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserActivityPay.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });
        userList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserActivityPay.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });

//конец блока прокрутки двух listview
        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        //userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " order by " + DatabaseHelper.COLUMN_ID + " desc ", null );
        // определяем, какие столбцы из курсора будут выводиться в ListView
        //String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_YEAR};
        // создаем адаптер, передаем в него курсор
//        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
//                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
//        userList.setAdapter(userAdapter);

        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_PAY + " where check_pay = 1 order by " + DatabaseHelper._ID + " desc ", null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.CHECK_NAME, DatabaseHelper.SUM};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1,android.R.id.text2}, 0);
        userList.setAdapter(userAdapter);

        userCursor1 = db.rawQuery("select * from " + DatabaseHelper.TABLE_PAY + " where check_pay = 0 order by " + DatabaseHelper._ID + " desc ", null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headeras = new String[]{DatabaseHelper.CHECK_NAME,DatabaseHelper.SUM};
        // создаем адаптер, передаем в него курсор
        userAdapter1 = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor1, headeras, new int[]{android.R.id.text1,android.R.id.text2}, 0);
        userList1.setAdapter(userAdapter1);


        //начало кнопки настроек
        Button buttonPref = (Button) findViewById(R.id.baseButton);
        buttonPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainSettingsPrefPay.this, MainSettingsPref.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }
            }
        });
        //начало кнопки настроек
        Button buttonPrefa = (Button) findViewById(R.id.dobButton);
        buttonPrefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainSettingsPrefPay.this, UserActivityPay.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
        userCursor1.close();
    }

    //системная кнопка
    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
    }
}

