package com.example.pockey;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QRActivity extends AppCompatActivity implements View.OnClickListener {

    TextView inSubject,inSubject2,inSubject3,inSubject4, inSubject5,inSubject6,inSubject7, inBody;
    TextView txtEmailAddress;
    Button btnSendEmail;
    private String userQR, trash1, trash2;
    private String year, month, day;
    private String hour, second, minute;
    private String linkQR, sumQR;
    private String data, time;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_add);
        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();
        saveButton = findViewById(R.id.btnSend);


        //начало кнопки назад
        Button buttonBack = findViewById(R.id.buttonback1);
        buttonBack.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(QRActivity.this, ScannedBarcodeActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {
            }
        });
        //конец кнопки назад

        initViews();
    }


    private void initViews() {
//        inSubject = findViewById(R.id.inSubject);
//        inSubject2 = findViewById(R.id.inSubject2);
//        inSubject3 = findViewById(R.id.inSubject3);
        inSubject4 = findViewById(R.id.inSubject4);
//        inSubject5 = findViewById(R.id.inSubject5);
//        inSubject6 = findViewById(R.id.inSubject6);
//        inSubject7 = findViewById(R.id.inSubject7);
//        inBody = findViewById(R.id.inBody);
        txtEmailAddress = findViewById(R.id.txtEmailAddress);


        if (getIntent().getStringExtra("email_address") != null) {
            userQR = getIntent().getStringExtra("email_address");
            linkQR = userQR;
            txtEmailAddress.setText("Код : " + userQR);
            String[] subStr;
            String delimeter = "="; // Разделитель
            subStr = userQR.split(delimeter);

            for (int i = 0; i < subStr.length; i++) {
                trash1 = new String (subStr[0]);
                sumQR = new String (subStr[3]);
                trash2 = new String(subStr[4]);
            }

            String srt = "ofd1.kz"; //без лишнего нуля
            String srt1 = "oofd.kz";
            String srtQR1 = ".00&t=";
            String srtQR2 = ".0&t=";

            if (trash1.contains(srt)) {
                if (userQR.contains(srtQR1)){
                    sumQR = sumQR.substring(0, sumQR.length() - 5);
                }
                else {
                    sumQR = sumQR.substring(0, sumQR.length() - 4);
                }
                System.out.println(userQR);

            }
            else if (trash1.contains(srt1)){
                if (userQR.contains(srtQR1)){
                    sumQR = sumQR.substring(0, sumQR.length() - 5);
                }
                else {
                    sumQR = sumQR.substring(0, sumQR.length() - 4);
                }

                //umQR = sumQR.substring(0, sumQR.length() - 5);
                System.out.println("жопа 2");

            }
            else {}
            year = trash2.substring(0, trash2.length() - 11);
            month = trash2.substring( 4, trash2.length() - 9);
            day = trash2.substring(6, trash2.length() -7);
            data = year + "-" + month + "-" + day;

            hour = trash2.substring(9, trash2.length() -4);
            minute = trash2.substring(11, trash2.length() -2);
            time = hour + ":" + minute;
            System.out.println("жопа 3");


            //inSubject.setText(String.valueOf(trash1));
            inSubject4.setText(String.valueOf(sumQR));
            //inSubject5.setText(String.valueOf(trash2));
//            inSubject6.setText(data);
//            inSubject7.setText(time);
        }
    }

    public void save(View view) {

        String purposeBox = "Расход";
        String categoryBox = "QR";
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.PURPOSE, purposeBox);
        cv.put(DatabaseHelper.CATEGORY, categoryBox);
        cv.put(DatabaseHelper.DATE, data);
        cv.put(DatabaseHelper.TIME, time);
        cv.put(DatabaseHelper.SUM, sumQR);
        System.out.println("жопа 4");
        System.out.println("жопа 1 " + data);
        System.out.println("жопа 1 " + time);
        System.out.println("жопа 1 " + sumQR);


        Cursor Ave = db.rawQuery("select * FROM ftp_main where category = '" + categoryBox + "'and purpose = '" + purposeBox + "'and sum = " + sumQR + " and date = '" + data + "' and time = '" + time+ "'", null);
        System.out.println("жопа 5");
        Ave.moveToFirst();
        if (Ave.getCount() == 0){
            System.out.println("жопа 5+6");

            db.insert(DatabaseHelper.TABLE_MAIN, null, cv);
            System.out.println("жопа 6");
            try {
                Cursor catCurso = db.rawQuery("select _id, sum, purpose, category, date, time FROM ftp_main where sum = "
                        + sumQR + " and category = '" + categoryBox + "' and purpose =  '" + purposeBox + "' and date = '" + data + "' and time = '" + time + "' ", null);
                catCurso.moveToFirst();
                int item_id = catCurso.getInt(catCurso.getColumnIndex(DatabaseHelper._ID));
                catCurso.close();
                ContentValues com = new ContentValues();
                System.out.println("жопа 7 " + item_id);
                System.out.println("жопа 8 " + linkQR);

                com.put(DatabaseHelper.CODE_QR_IDE, item_id);
                com.put(DatabaseHelper.CODE_QR_LINK, linkQR);
                db.insert(DatabaseHelper.TABLE_QR, null, com);
            } catch (Exception e) {
                System.out.println("ошибка в меню вставки в бд новой записи");
            }
        }

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
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonQR:
                startActivity(new Intent(QRActivity.this, ScannedBarcodeActivity.class));
                break;
        }
    }
}