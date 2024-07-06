package com.example.pockey;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class CategoryChooseAdd extends AppCompatActivity {



    EditText categoryBox;

    TextView textratee;
    TextView textincomee;
    Button delButton;
    Button saveButton;
    Switch checkBoxSwitch;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;


    public String purposeBox = "Расход";
    long userId = 0;

    public int userPurposeBoxInt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category_add);


        categoryBox = findViewById(R.id.category);


        textratee = findViewById(R.id.textrate);
        textincomee = findViewById(R.id.textincome);

        textincomee.setVisibility(View.GONE);

        delButton = findViewById(R.id.deleteButton);
        saveButton = findViewById(R.id.saveButton);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();


        //начало кнопки назад
        Button buttonBack = findViewById(R.id.buttonback1);
        buttonBack.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(CategoryChooseAdd.this, MainActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {
            }
        });
        //конец кнопки назад


        //блок checkbox
        checkBoxSwitch = findViewById(R.id.switch1);
        Switch switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                purposeBox = "Доход"; // Your code
                userPurposeBoxInt = 1;
                textratee.setVisibility(View.GONE);
                textincomee.setVisibility(View.VISIBLE);
            } else {
                purposeBox = "Расход";// Your code
                userPurposeBoxInt = 2;
                textincomee.setVisibility(View.GONE);
                textratee.setVisibility(View.VISIBLE);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        if (userId > 0) {
            // получаем элемент по id из бд
            Cursor userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_STORE + " where " +
                    DatabaseHelper._ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            categoryBox.setText(userCursor.getString(1));
            userCursor.close();
        }

    }

    public void save(View view) {
    if (userId > 0) {
        String rtp = categoryBox.getText().toString();
        System.out.println("ну это же бред");
        System.out.println(rtp);

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.STORE_PURPOSE, purposeBox);
        cv.put(DatabaseHelper.STORE_CATEGORY, rtp);
        db.update(DatabaseHelper.TABLE_STORE, cv, DatabaseHelper._ID + "=" + userId, null);
    }
    else {
            String rtp = categoryBox.getText().toString();
            System.out.println("ну это же бред");
            System.out.println(rtp);

            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.STORE_PURPOSE, purposeBox);
            cv.put(DatabaseHelper.STORE_CATEGORY, rtp);

            if (!rtp.equals("")) {
                System.out.println("это какая то хуйня");
                db.insert(DatabaseHelper.TABLE_STORE, null, cv);

            } else {
                System.out.println("это какая то хуйня d rdflhfntd");
                db.insert(DatabaseHelper.TABLE_STORE, null, cv);

            }
        }
            goHome();
    }


    public void delete(View view) {
        db.delete(DatabaseHelper.TABLE_STORE, "_id = ?", new String[]{String.valueOf(userId)});
        goHome();
    }

    private void goHome() {
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
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


