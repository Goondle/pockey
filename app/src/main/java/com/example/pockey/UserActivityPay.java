package com.example.pockey;

import static android.telephony.AvailableNetworkInfo.PRIORITY_HIGH;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class UserActivityPay extends AppCompatActivity {

    private Toast backToast;

    //данные из полей
    EditText sumBox;
    EditText nameBox;
    EditText categoryBox;
    EditText commentBox;
    EditText commentID;

    TextView textratee;
    TextView textincomee;
    Button delButton;
    Button saveButton;
    Switch checkBoxSwitch;
    Switch checkBoxSwitchNotif;
    Switch checkBoxSwitchPay;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    Cursor userCursorComment;

    //ид в listview
    long userId = 0;
    //переменная checkBox
    public String purposeBox = "Расход";
    //checkbox для показа уведомлений нужно ли это
    public int notifBox = 0;
    //checkbox для активности платежа
    public int payBox = 0;

    private String rtp;

    //сохранение даты и времени
    private String datepick, timepick, datepickend;
    //дата и время выбранные пользователем
    private int saveDay, saveMonth, saveYear;
    private int saveDayEnd, saveMonthEnd, saveYearEnd;
    private int saveHour, saveMinute;
    //переменные для записи 0 к дням и месяцам
    public String mmYM, mmMD, mmYMEND, mmMDEND;
    //переменные определения даты и времени
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int mYearEnd, mMonthEnd, mDayEnd;

    //выбор id в spinner
    long spinid = 0;

    TextView categoryBoxCat;
    TextView textViewCat;
    Cursor userCursorCat;
    long userIdCat = 1;
    public int userPurposeBoxInt;
    public int userFuckingIdNahuiBlyat;
    public int jojoReference;
    public String userFuck;
    public String userFuck2;
    public String avd, avde, avder;

    //кнопки даты и времени и вывод в текст
    private Button btnDatePicker, btnTimePicker, btnDatEndPicker;
    private TextView viewTextTime, viewTextDate, viewTextDatEnd;
    Calendar dateAndTime = Calendar.getInstance();

    //выбор через spinner
    //String[] countries = {"Всегда", "Один раз", "Каждый день", "Каждую неделю", "Каждый месяц", "Каждый квартал", "Каждый год"};
    String[] countries = {"Один раз", "Каждый день", "Каждый месяц"};

    //начало oncreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pay);
        //начало времени и даты

        //время и дата кнопки выбора
        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        btnDatEndPicker = (Button) findViewById(R.id.btn_date_end);
        //вывод в textview даты и времени
        viewTextDate = (TextView) findViewById(R.id.picked_date);
        viewTextTime = (TextView) findViewById(R.id.picked_time);
        viewTextDatEnd = (TextView) findViewById(R.id.picked_date_end);
        //выбор вывода интерфейса даты и времени android
        View.OnClickListener abtnDatePicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id) {
                    case R.id.btn_date:
                        // вызываем диалог с выбором даты
                        callDatePicker();
                        break;
                    case R.id.btn_time:
                        // вызываем диалог с выбором времени
                        callTimePicker();
                        break;
                    case R.id.btn_date_end:
                        callDatEndPicker();
                        break;
                }
            }
        };
        //запуск выбора при нажатии кнопок
        btnDatePicker.setOnClickListener(abtnDatePicker);
        btnTimePicker.setOnClickListener(abtnDatePicker);
        btnDatEndPicker.setOnClickListener(abtnDatePicker);
        // конец времени и даты

        //начало spinner
        TextView selection = findViewById(R.id.selection);

        Spinner spinner = findViewById(R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countries);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String) parent.getItemAtPosition(position);
                selection.setText(item);
                System.out.println("а оно и но");
                System.out.println(item);
                System.out.println(id);
                spinid = id;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
        //конец spinner

        nameBox = findViewById(R.id.name);
        sumBox = findViewById(R.id.sum);
        categoryBox = findViewById(R.id.category);
        commentBox = findViewById(R.id.comment);

        categoryBoxCat = findViewById(R.id.category9);

        textratee = findViewById(R.id.textrate);
        textincomee = findViewById(R.id.textincome);
        textViewCat = findViewById(R.id.category9);

        textincomee.setVisibility(View.GONE);

        delButton = findViewById(R.id.deleteButton);
        saveButton = findViewById(R.id.saveButton);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();


        //начало кнопки назад
        Button buttonBack = findViewById(R.id.buttonback1);
        buttonBack.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(UserActivityPay.this, MainSettingsPref.class);
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
        checkBoxSwitchNotif = findViewById(R.id.switch2);
        Switch switch2 = findViewById(R.id.switch2);
        switch2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                notifBox = 1;// Your code
            } else {
                notifBox = 0;// Your code
            }
        });
        checkBoxSwitchPay = findViewById(R.id.switch3);
        Switch switch3 = findViewById(R.id.switch3);
        switch3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //активна
                payBox = 1;// Your code
            } else {
                //неактивна
                payBox = 0;// Your code
            }
        });
        //начало кнопки категория для платежей
        Button buttonCategory = findViewById(R.id.buttonCategory);
        buttonCategory.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(UserActivityPay.this, CategoryChoosePay.class);
                intent.putExtra("purpose_b", userPurposeBoxInt);
                intent.putExtra("user_fuckingidnahuiblyat", userFuckingIdNahuiBlyat);
                startActivity(intent);
            } catch (Exception e) {
            }
        });
        //конец кнопки категория для платежей

        //получение данных из другой activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");

            userFuckingIdNahuiBlyat = (int) userId;
            System.out.println("убийство 1");
            System.out.println(userFuckingIdNahuiBlyat);
            System.out.println("убийство 1");
            jojoReference = extras.getInt("jojo");
            if (jojoReference > 0) {
                userIdCat = extras.getLong("idd");
            } else {
                userIdCat = extras.getLong("idd");
            }
            System.out.println("ха жожо референс");
            System.out.println(jojoReference);
            System.out.println("ха жожо референ");
            System.out.println("bl 31");
            System.out.println(userId);
            System.out.println(userIdCat);
            System.out.println("bl 32");
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_PAY + " where " +
                    DatabaseHelper._ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            sumBox.setText(String.valueOf(userCursor.getInt(3)));
            String catBox = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.CATEGORY));
            nameBox.setText(userCursor.getString(7));
            commentBox.setText(userCursor.getString(6));


            avd = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.DATE));
            viewTextDate.setText(avd);
            avde = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.TIME));
            viewTextTime.setText(avde);
            avder = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.CHECK_DATEND));
            viewTextDatEnd.setText(avder);

            //блять сюда вернуться
 //когда перезапись делать
            checkBoxSwitch.setText(purposeBox);
            userCursor.close();

            userCursorCat = db.rawQuery("select " + DatabaseHelper._ID + " from " + DatabaseHelper.TABLE_STORE + " where " + DatabaseHelper.STORE_CATEGORY + " = '" + catBox + "' ", null);
            userCursorCat.moveToFirst();

            String idBox = userCursorCat.getString(userCursorCat.getColumnIndex(DatabaseHelper._ID));
            System.out.println(idBox);
            System.out.println("хуид бокс");
            int id_category = userCursorCat.getInt(userCursorCat.getColumnIndex(DatabaseHelper._ID));
            userCursorCat.close();
            System.out.println(id_category);
            System.out.println("ид кате");
            System.out.println("работает?");
            System.out.println(catBox);
            System.out.println(categoryBoxCat);
            textViewCat.setText(catBox);

            System.out.println("оно живое?");
            System.out.println(id_category);

        }
        else if (jojoReference > 0) {

            userFuckingIdNahuiBlyat = jojoReference;
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_PAY + " where " +
                    DatabaseHelper._ID + "=?", new String[]{String.valueOf(jojoReference)});
            userCursor.moveToFirst();
            sumBox.setText(String.valueOf(userCursor.getInt(3)));
            nameBox.setText(userCursor.getString(7));
            commentBox.setText(userCursor.getString(6));

            avd = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.DATE));
            viewTextDate.setText(avd);
            avde = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.TIME));
            viewTextTime.setText(avde);
            avder = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.CHECK_DATEND));
            viewTextDatEnd.setText(avder);

            purposeBox = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.PURPOSE));
            userCursor.close();

            userCursorCat = db.rawQuery("select * from " + DatabaseHelper.TABLE_STORE + " where " +
                    DatabaseHelper._ID + "=?", new String[]{String.valueOf(userIdCat)});

            //userCursorCat = db.rawQuery("select " + DatabaseHelper._ID + " from " + DatabaseHelper.TABLE_STORE + " where " + DatabaseHelper.STORE_CATEGORY + " = '" + catBox + "' ", null);

            userCursorCat.moveToFirst();

            String idBox = userCursorCat.getString(userCursorCat.getColumnIndex(DatabaseHelper.STORE_CATEGORY));
            System.out.println(idBox);
            System.out.println("хуид бокс");
            int id_category = userCursorCat.getInt(userCursorCat.getColumnIndex(DatabaseHelper._ID));
            userCursorCat.close();
            System.out.println(id_category);
            System.out.println("ид кате");
            System.out.println("работает?");
            System.out.println(userIdCat);
            System.out.println(categoryBoxCat);
            textViewCat.setText(idBox);

            delButton.setVisibility(View.GONE);

            System.out.println("оно живое?");
            System.out.println(id_category);

        }
        else {
            System.out.println("ftn&");
            delButton.setVisibility(View.GONE);
            setInitialDateTime(); //дата и время

            System.out.println("дед 1");
            System.out.println(viewTextDate);
            System.out.println(viewTextTime);
            System.out.println("дед 2");
            System.out.println(mHour);
            System.out.println("дед внутри");

            //ноль оставить так как мне лень делать месяц ровным
            //в slq вносится месяц и числа с нулем перед ними если они не 2-х символьные

            userCursorCat = db.rawQuery("select * from " + DatabaseHelper.TABLE_STORE + " where " +
                    DatabaseHelper._ID + "=?", new String[]{String.valueOf(userIdCat)});
            userCursorCat.moveToFirst();
            categoryBoxCat.setText(String.valueOf(userCursorCat.getInt(2)));
            System.out.println("хуй 3");
            delButton.setVisibility(View.GONE);
            categoryBoxCat.setText(userCursorCat.getString(1));
            rtp = categoryBoxCat.getText().toString();


            System.out.println("ёбнет?");
            System.out.println(rtp);

            System.out.println("не должно");
            System.out.println("а оно и не ебнуло");
            //вот эту команду нужно в update переделать
            textViewCat.setText(rtp);
            userCursorCat.close();
        }
        //блок spinner https://www.javatpoint.com/android-sqlite-example-with-spinner функция добавлнеия категории
    }
    //конец oncreate

    //войд времени и даты для выводы при добавлении
    private void setInitialDateTime() {

        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        String editTextDateParam = mYear + "-" + (mMonth + 1) + "-" + mDay;
        viewTextDate.setText(editTextDateParam);
        final Calendar cale = Calendar.getInstance();
        mHour = cale.get(Calendar.HOUR_OF_DAY);
        mMinute = cale.get(Calendar.MINUTE);
        String editTextTimeParam = mHour + " : " + mMinute;
        viewTextTime.setText(editTextTimeParam);
    }

    //войд времени при выборе
    private void callTimePicker() {
        // получаем текущее время
        final Calendar cal = Calendar.getInstance();
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        // инициализируем диалог выбора времени текущими значениями
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String editTextTimeParam = hourOfDay + " : " + minute;
                        viewTextTime.setText(editTextTimeParam);
                        saveHour = hourOfDay;
                        saveMinute = minute;
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    //войд  даты при выборе
    private void callDatePicker() {
        // получаем текущую дату
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        // инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String editTextDateParam = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        viewTextDate.setText(editTextDateParam);
                        saveDay = dayOfMonth;
                        saveMonth = monthOfYear + 1;
                        saveYear = year;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private void callDatEndPicker() {
        // получаем текущую дату
        final Calendar cal = Calendar.getInstance();
        mYearEnd = cal.get(Calendar.YEAR);
        mMonthEnd = cal.get(Calendar.MONTH);
        mDayEnd = cal.get(Calendar.DAY_OF_MONTH);

        // инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String editTextDateParam = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        viewTextDatEnd.setText(editTextDateParam);
                        saveDayEnd = dayOfMonth;
                        saveMonthEnd = monthOfYear + 1;
                        saveYearEnd = year;
                    }
                }, mYearEnd, mMonthEnd, mDayEnd);
        datePickerDialog.show();
    }


    public int getCountsOfDigits(long number) {
        return String.valueOf(Math.abs(number)).length();
    }

    public void save(View view) {

        sumBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)}); // Ограничиваем количество вводимых цифр
        if (sumBox.length() > 10) {
            try {
                backToast = Toast.makeText(getBaseContext(), "Ограничение на вводимые суммы в размере 10 символов.\n" +
                        "Пожалуйста удалите лишний символ.", Toast.LENGTH_SHORT);
                backToast.show();
                backToast = Toast.makeText(getBaseContext(), "Raffaello вкусные", Toast.LENGTH_SHORT);
                backToast.show();
            } catch (Exception ignored) {
            }
        } else {

            String rname = nameBox.getText().toString();
            int dead = Integer.parseInt(sumBox.getText().toString());
            String rtp = categoryBoxCat.getText().toString();
            String rcom = commentBox.getText().toString();
            String rcomdate = viewTextDate.getText().toString();
            String rcomtime = viewTextTime.getText().toString();
            String rcomtimend = viewTextDatEnd.getText().toString();
            System.out.println("ну это же бред");
            System.out.println(rtp);

            if (saveMonth == 0) {
                if (mMonth < 10) {
                    mmYM = "-0";
                } else {
                    mmYM = "-";
                }
                if (mDay < 10) {
                    mmMD = "-0";
                } else {
                    mmMD = "-";
                }
            } else {
                if (saveMonth < 10) {
                    mmYM = "-0";
                } else {
                    mmYM = "-";
                }
                if (saveDay < 10) {
                    mmMD = "-0";
                } else {
                    mmMD = "-";
                }
            }
            if (mMonthEnd < 10) {
                mmYMEND = "-0";
            } else {
                mmYMEND = "-";
            }
            if (mDayEnd < 10) {
                mmMDEND = "-0";
            } else {
                mmMDEND = "-";
            }
            datepickend = saveYearEnd + mmYMEND + saveMonthEnd + mmMDEND + saveDayEnd;

            //вставка выбранной даты
            if (saveYear == 0) {
                mMonth = mMonth + 1;
                datepick = mYear + mmYM + mMonth + mmMD + mDay;
            } else {
                datepick = saveYear + mmYM + saveMonth + mmMD + saveDay;
                //System.out.println(datepick);
            }
            //вставка выбранного времени
            if (saveHour == 0) {
                timepick = mHour + ":" + mMinute;
            } else {
                timepick = saveHour + ":" + saveMinute;
            }

            ContentValues kv = new ContentValues();
            kv.put(DatabaseHelper.PURPOSE, purposeBox);         //цель расход или доход
            kv.put(DatabaseHelper.CATEGORY, rtp);               //категория
            kv.put(DatabaseHelper.DATE, datepick);              //дата
            kv.put(DatabaseHelper.TIME, timepick);              //время
            kv.put(DatabaseHelper.SUM, dead);                   //сумма
            kv.put(DatabaseHelper.CHECK_NAME, rname);           //имея платежа
            kv.put(DatabaseHelper.CHECK_DATEND, datepickend);          //дата окончания платежа
            kv.put(DatabaseHelper.CHECK_PAY, payBox);             //активный ли платеж
            kv.put(DatabaseHelper.CHECK_NOTIFICATION, notifBox);//нужно ли уведомление
            kv.put(DatabaseHelper.CHECK_PERIOD, spinid);        //период добавления
            kv.put(DatabaseHelper.COMMENT, rcom);               //комментарий

//            System.out.println("коммент мжду");


            if (userId > 0) {
                try {
                    if (mYear == 0) {
                        System.out.println("если ид польз есть");
                        System.out.println(datepick);
                        kv.put(DatabaseHelper.DATE, rcomdate);
                        kv.put(DatabaseHelper.TIME, rcomtime);
                        db.update(DatabaseHelper.TABLE_PAY, kv, DatabaseHelper._ID + "=" + userId, null);
                    } else {
                        System.out.println("еслиgrsfdесть");
                        System.out.println(datepick);
                        System.out.println(rcomdate);
                        db.update(DatabaseHelper.TABLE_PAY, kv, DatabaseHelper._ID + "=" + userId, null);
                    }

                } catch (Exception e) {
                }


            }
            else if (jojoReference > 0) {
                try {
                    if (mYear == 0) {
                        System.out.println("если ид польз есть");
                        System.out.println(datepick);
                        kv.put(DatabaseHelper.DATE, rcomdate);
                        kv.put(DatabaseHelper.TIME, rcomtime);
                        db.update(DatabaseHelper.TABLE_PAY, kv, DatabaseHelper._ID + "=" + jojoReference, null);
                    } else {
                        System.out.println("еслиgrsfdесть");
                        System.out.println(datepick);
                        System.out.println(rcomdate);
                        db.update(DatabaseHelper.TABLE_PAY, kv, DatabaseHelper._ID + "=" + jojoReference, null);
                    }

                } catch (Exception e) {
                }
            }
            else {
                db.insert(DatabaseHelper.TABLE_PAY, null, kv);
                System.out.println("жопа 1");
            }
            goHome();
        }
    }


    public void delete(View view) {
        db.delete(DatabaseHelper.TABLE_PAY, "_id = ?", new String[]{String.valueOf(userId)});
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


