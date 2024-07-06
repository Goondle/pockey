package com.example.pockey;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryChoose extends AppCompatActivity {

    ListView userList;
    ListView userList1;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    Cursor userCursor1;
    SimpleCursorAdapter userAdapter;
    SimpleCursorAdapter userAdapter1;
    public int userPurpose;
    public int userFuckingIdNahuiBlyat;

    private View abc;
    private View abcde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category);

        userList = findViewById(R.id.listcategory);
        //abc = findViewById(R.id.textIncomeChoose);
        //abcde = findViewById(R.id.textRateChoose);
        //int filelist = (int) getIntent().getSerializableExtra("id_to_idd");

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("idd", id);
                intent.putExtra("jojo", userFuckingIdNahuiBlyat);

                //intent.putExtra("idmayid", filelist);
                startActivity(intent);
            }
        });

//        userList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
//                intent.putExtra("idd", id);
//                intent.putExtra("jojo", userFuckingIdNahuiBlyat);
//                startActivity(intent);
//            }
//        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
        //начало кнопки назад
        Button buttonBack = (Button) findViewById(R.id.buttonback1);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(CategoryChoose.this, MainActivity.class);
                    //intent.putExtra("jojo", userFuckingIdNahuiBlyat);

                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }
            }
        });
        //конец кнопки назад
//        начало кнопки
//        Button buttonAddCate = findViewById(R.id.addcateButton);
//        buttonAddCate.setOnClickListener(v -> {
//            try {
//                Intent intent = new Intent(CategoryChoose.this, CategoryChooseAdd.class);
//                startActivity(intent);
//            } catch (Exception ignored) {
//            }
//        });
//        конец кнопки
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userPurpose = extras.getInt("purpose_b");
            userFuckingIdNahuiBlyat = extras.getInt("user_fuckingidnahuiblyat");

        }

        if (userPurpose == 1) {
            //условие дохода
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_STORE + " where " + DatabaseHelper.STORE_PURPOSE + " = 'Доход' order by " + DatabaseHelper._ID + " desc ", null);
            // определяем, какие столбцы из курсора будут выводиться в ListView
            String[] headers = new String[]{DatabaseHelper.STORE_CATEGORY, DatabaseHelper.STORE_PURPOSE};
            // создаем адаптер, передаем в него курсор
            userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                    userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        } else {
            //условие расхода
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_STORE + " where " + DatabaseHelper.STORE_PURPOSE + " = 'Расход' order by " + DatabaseHelper._ID + " desc ", null);
            // определяем, какие столбцы из курсора будут выводиться в ListView
            String[] headers = new String[]{DatabaseHelper.STORE_CATEGORY, DatabaseHelper.STORE_PURPOSE};
            // создаем адаптер, передаем в него курсор
            userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                    userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);


        }
        userList.setAdapter(userAdapter);

        //получаем данные из бд в виде курсора
        //userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " order by " + DatabaseHelper.COLUMN_ID + " desc ", null );
        // определяем, какие столбцы из курсора будут выводиться в ListView
        //String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_YEAR};
        // создаем адаптер, передаем в него курсор
//        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
//                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
//        userList.setAdapter(userAdapter);

//        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_STORE + " where " + DatabaseHelper.STORE_PURPOSE + " = 'Расход' order by " + DatabaseHelper._ID + " desc ", null);
//        // определяем, какие столбцы из курсора будут выводиться в ListView
//        String[] headers = new String[]{DatabaseHelper.STORE_CATEGORY, DatabaseHelper.STORE_PURPOSE};
//        // создаем адаптер, передаем в него курсор
//        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
//                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
//        userList.setAdapter(userAdapter);

//        userCursor1 = db.rawQuery("select * from " + DatabaseHelper.TABLE_STORE + " where " + DatabaseHelper.STORE_PURPOSE + " = 'Доход' order by " + DatabaseHelper._ID + " desc ", null );
//        // определяем, какие столбцы из курсора будут выводиться в ListView
//        String[] headeras = new String[]{DatabaseHelper.STORE_CATEGORY, DatabaseHelper.STORE_PURPOSE};
//        // создаем адаптер, передаем в него курсор
//        userAdapter1 = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
//                userCursor1, headeras, new int[]{android.R.id.text1, android.R.id.text2}, 0);
//        userList1.setAdapter(userAdapter1);

    }

//    // по нажатию на кнопку запускаем UserActivity для добавления данных
//    public void add(View view) {
//        Intent intent = new Intent(this, ViewPurposeActivity.class);// сделать новую активити
//        startActivity(intent);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
        //userCursor1.close();
    }
}
