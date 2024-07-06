package com.example.pockey;

import static android.telephony.AvailableNetworkInfo.PRIORITY_HIGH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Calendar;


public class UserActivity extends AppCompatActivity {

    private Toast backToast;
    //EditText purposeBox;
    EditText sumBox;
    EditText categoryBox;
    EditText commentBox;
    EditText commentID;

    TextView textratee, textincomee;
    Button delButton;
    Button saveButton;
    Switch checkBoxSwitch;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    Cursor userCursorComment;

    long userId = 0;
    //переменная checkBox
    public String purposeBox = "Расход";

    private String rtp;
    private String datepick;
    private String timepick;
    private int saveDay;
    private int saveMonth;
    private int saveYear;
    private int saveHour;
    private int saveMinute;

    TextView categoryBoxCat;
    TextView textViewCat;

    TextView textViewlinktextQR, textViewlinkQR;
    ImageView textViewlinkBackGround, textViewlink;
    Cursor userCursorCat;
    long userIdCat = 1;
    public int userPurposeBoxInt;
    public int userFuckingIdNahuiBlyat;
    public int jojoReference;
    public String userFuck;
    public String userFuck2;
    public String avd;
    public String avde;

    public String mmYM;
    public String mmMD;

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    Button b1;

    private Button btnDatePicker, btnTimePicker;
    private TextView viewTextTime, viewTextDate;
    Calendar dateAndTime = Calendar.getInstance();
    // делаем переменные даты/времени полями, т.к. в реальных
    // приложениях они чаще всего используются и в других местах.
    private int mYear, mMonth, mDay, mHour, mMinute;

    //начало oncreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //время и дата
        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        viewTextDate = (TextView) findViewById(R.id.picked_date);
        viewTextTime = (TextView) findViewById(R.id.picked_time);

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
                }
            }
        };
        btnDatePicker.setOnClickListener(abtnDatePicker);
        btnTimePicker.setOnClickListener(abtnDatePicker);
        // конец времени и даты

        sumBox = findViewById(R.id.sum);
        categoryBox = findViewById(R.id.category);
        commentBox = findViewById(R.id.comment);

        categoryBoxCat = findViewById(R.id.category9);

        textratee = findViewById(R.id.textrate);
        textincomee = findViewById(R.id.textincome);
        textViewCat = findViewById(R.id.category9);

        textViewlink = findViewById(R.id.main_back_back_item_link);
        textViewlinkBackGround = findViewById(R.id.main_background_backlink);
        textViewlinktextQR = findViewById(R.id.textViewQRLink);
        textViewlinkQR = findViewById(R.id.textViewlinkQR);


        textincomee.setVisibility(View.GONE);

        delButton = findViewById(R.id.deleteButton);
        saveButton = findViewById(R.id.saveButton);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();


        //начало кнопки назад
        Button buttonBack = findViewById(R.id.buttonback1);
        buttonBack.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
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
        //начало кнопки категория
        Button buttonCategory = findViewById(R.id.buttonCategory);
        buttonCategory.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(UserActivity.this, CategoryChoose.class);
                intent.putExtra("purpose_b", userPurposeBoxInt);
                intent.putExtra("user_fuckingidnahuiblyat", userFuckingIdNahuiBlyat);
                startActivity(intent);
            } catch (Exception e) {
            }
        });
        //конец кнопки категория
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
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_MAIN + " where " +
                    DatabaseHelper._ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            sumBox.setText(String.valueOf(userCursor.getInt(3)));
            String catBox = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.CATEGORY));
            purposeBox = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.PURPOSE));
            avd = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.DATE));
            viewTextDate.setText(avd);

            avde = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.TIME));
            viewTextTime.setText(avde);
            userCursor.close();
            try {
                Cursor userCursorQRCODE = db.rawQuery("select * from " + DatabaseHelper.TABLE_QR + " where " +
                        DatabaseHelper.CODE_QR_IDE + "=?", new String[]{String.valueOf(userId)});
                userCursorQRCODE.moveToFirst();
                if (userCursorQRCODE.getCount() > 0) {
                    String Boxlink = userCursorQRCODE.getString(userCursorQRCODE.getColumnIndex(DatabaseHelper.CODE_QR_LINK));

                    String textWithLink = "<a href=\""+ Boxlink +"\">Перейти по ссылке</a>";
                    textViewlinkQR.setText(Html.fromHtml(textWithLink, null, null));
                    textViewlinkQR.setLinksClickable(true);
                    textViewlinkQR.setMovementMethod(LinkMovementMethod.getInstance());
                    CharSequence text = textViewlinkQR.getText();
                    if (text instanceof Spannable) { textViewlinkQR.setText(MakeLinksClicable.reformatText(text)); }
                    textratee.setVisibility(View.VISIBLE);
                    textViewlink.setVisibility(View.VISIBLE);
                    textViewlinkQR.setVisibility(View.VISIBLE);
                    textViewlinktextQR.setVisibility(View.VISIBLE);
                    textViewlinkBackGround.setVisibility(View.VISIBLE);
                }
                else {
                    textViewlink.setVisibility(View.GONE);
                    textViewlinkQR.setVisibility(View.GONE);
                    textViewlinktextQR.setVisibility(View.GONE);
                    textViewlinkBackGround.setVisibility(View.GONE);
                } } catch (Exception ignored) { }


            userCursorCat = db.rawQuery("select " + DatabaseHelper._ID + " from " + DatabaseHelper.TABLE_STORE + " where " + DatabaseHelper.STORE_CATEGORY + " = '" + catBox + "' ", null);
            userCursorCat.moveToFirst();

            String idBox = userCursorCat.getString(userCursorCat.getColumnIndex(DatabaseHelper._ID));
            System.out.println(idBox);
            System.out.println("хуид бокс");
            int id_category = userCursorCat.getInt(userCursorCat.getColumnIndex(DatabaseHelper._ID));
            userCursorCat.close();

//            System.out.println(id_category);
//            System.out.println("ид кате");
//            System.out.println("работает?");
//            System.out.println(catBox);
//            System.out.println(categoryBoxCat);
            textViewCat.setText(catBox);

            System.out.println("оно живое?");
            System.out.println(id_category);


            userCursorComment = db.rawQuery("select * from " + DatabaseHelper.TABLE_COMMENT + " where " +
                    DatabaseHelper.COMMENT_ID + "=?", new String[]{String.valueOf(userId)});

            if (userCursorComment != null) {
                try {
                    userCursorComment.moveToFirst();
                    commentBox.setText(userCursorComment.getString(1));
                    commentID.setText(userCursorComment.getString(0));
                    System.out.println(commentID);

                    userCursorComment.close();
                } catch (Exception ignored) {
                }
            } else {
                //userCursorComment.moveToFirst();
                userCursorComment.close();
            }
        }
        else if (jojoReference > 0) {

            userFuckingIdNahuiBlyat = jojoReference;
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_MAIN + " where " +
                    DatabaseHelper._ID + "=?", new String[]{String.valueOf(jojoReference)});
            userCursor.moveToFirst();
            sumBox.setText(String.valueOf(userCursor.getInt(3)));
            avd = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.DATE));
            viewTextDate.setText(avd);
            avde = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.TIME));
            viewTextTime.setText(avde);
            //String catBox = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.CATEGORY));
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

            userCursorComment = db.rawQuery("select * from " + DatabaseHelper.TABLE_COMMENT + " where " +
                    DatabaseHelper.COMMENT_ID + "=?", new String[]{String.valueOf(jojoReference)});

            if (userCursorComment != null) {
                try {
                    userCursorComment.moveToFirst();
                    commentBox.setText(userCursorComment.getString(1));
                    commentID.setText(userCursorComment.getString(0));
                    System.out.println(commentID);

                    userCursorComment.close();
                } catch (Exception ignored) {
                }
            } else { userCursorComment.close(); }
            try {
                Cursor userCursorQRCODE = db.rawQuery("select * from " + DatabaseHelper.TABLE_QR + " where " +
                        DatabaseHelper.CODE_QR_IDE + "=?", new String[]{String.valueOf(userId)});
                userCursorQRCODE.moveToFirst();
                if (userCursorQRCODE.getCount() > 0) {
                    String Boxlink = userCursorQRCODE.getString(userCursorQRCODE.getColumnIndex(DatabaseHelper.CODE_QR_LINK));

                    String textWithLink = "<a href=\""+ Boxlink +"\">Перейти по ссылке</a>";
                    textViewlinkQR.setText(Html.fromHtml(textWithLink, null, null));
                    textViewlinkQR.setLinksClickable(true);
                    textViewlinkQR.setMovementMethod(LinkMovementMethod.getInstance());
                    CharSequence text = textViewlinkQR.getText();
                    if (text instanceof Spannable) { textViewlinkQR.setText(MakeLinksClicable.reformatText(text)); }
                    textratee.setVisibility(View.VISIBLE);
                    textViewlink.setVisibility(View.VISIBLE);
                    textViewlinkQR.setVisibility(View.VISIBLE);
                    textViewlinktextQR.setVisibility(View.VISIBLE);
                    textViewlinkBackGround.setVisibility(View.VISIBLE);
                }
                else {
                    textViewlink.setVisibility(View.GONE);
                    textViewlinkQR.setVisibility(View.GONE);
                    textViewlinktextQR.setVisibility(View.GONE);
                    textViewlinkBackGround.setVisibility(View.GONE);
                } } catch (Exception ignored) { }
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

            try {
                Cursor userCursorQRCODE = db.rawQuery("select * from " + DatabaseHelper.TABLE_QR + " where " +
                        DatabaseHelper.CODE_QR_IDE + "=?", new String[]{String.valueOf(userId)});
                userCursorQRCODE.moveToFirst();
                if (userCursorQRCODE.getCount() > 0) {
                    String Boxlink = userCursorQRCODE.getString(userCursorQRCODE.getColumnIndex(DatabaseHelper.CODE_QR_LINK));

                    String textWithLink = "<a href=\""+ Boxlink +"\">Перейти по ссылке</a>";
                    textViewlinkQR.setText(Html.fromHtml(textWithLink, null, null));
                    textViewlinkQR.setLinksClickable(true);
                    textViewlinkQR.setMovementMethod(LinkMovementMethod.getInstance());
                    CharSequence text = textViewlinkQR.getText();
                    if (text instanceof Spannable) { textViewlinkQR.setText(MakeLinksClicable.reformatText(text)); }
                    textratee.setVisibility(View.VISIBLE);
                    textViewlink.setVisibility(View.VISIBLE);
                    textViewlinkQR.setVisibility(View.VISIBLE);
                    textViewlinktextQR.setVisibility(View.VISIBLE);
                    textViewlinkBackGround.setVisibility(View.VISIBLE);
                }
                else {
                    textViewlink.setVisibility(View.GONE);
                    textViewlinkQR.setVisibility(View.GONE);
                    textViewlinktextQR.setVisibility(View.GONE);
                    textViewlinkBackGround.setVisibility(View.GONE);
                } } catch (Exception ignored) { }
            //ноль оставить так как мне лень делать месяц ровным
            //в slq вносится месяц и числа с нулем перед ними если они не 2-х символьные

            //это кнопка уведомления
            Cursor catCursorFUCK = db.rawQuery("select _id, sum(sum) as sum FROM ftp_main where _id > 20 and purpose = 'Расход'", null);
            catCursorFUCK.moveToFirst(); // переходим на первую строку
            // извлекаем данные из курсора
            userFuck = catCursorFUCK
                    .getString(catCursorFUCK.getColumnIndex(DatabaseHelper.SUM));
            userFuck2 = catCursorFUCK
                    .getString(catCursorFUCK.getColumnIndex(DatabaseHelper._ID));
//        String item_content = catCursor.getString(catCursor
//                .getColumnIndex(CatsDataBase.CATNAME));
            catCursorFUCK.close();

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
            try {
                Cursor userCursorQRCODE = db.rawQuery("select * from " + DatabaseHelper.TABLE_QR + " where " +
                        DatabaseHelper.CODE_QR_IDE + "=?", new String[]{String.valueOf(userId)});
                userCursorQRCODE.moveToFirst();
                if (userCursorQRCODE.getCount() > 0) {
                    String Boxlink = userCursorQRCODE.getString(userCursorQRCODE.getColumnIndex(DatabaseHelper.CODE_QR_LINK));

                    String textWithLink = "<a href=\""+ Boxlink +"\">Перейти по ссылке</a>";
                    textViewlinkQR.setText(Html.fromHtml(textWithLink, null, null));
                    textViewlinkQR.setLinksClickable(true);
                    textViewlinkQR.setMovementMethod(LinkMovementMethod.getInstance());
                    CharSequence text = textViewlinkQR.getText();
                    if (text instanceof Spannable) { textViewlinkQR.setText(MakeLinksClicable.reformatText(text)); }
                    textratee.setVisibility(View.VISIBLE);
                    textViewlink.setVisibility(View.VISIBLE);
                    textViewlinkQR.setVisibility(View.VISIBLE);
                    textViewlinktextQR.setVisibility(View.VISIBLE);
                    textViewlinkBackGround.setVisibility(View.VISIBLE);
                }
                else {
                    textViewlink.setVisibility(View.GONE);
                    textViewlinkQR.setVisibility(View.GONE);
                    textViewlinktextQR.setVisibility(View.GONE);
                    textViewlinkBackGround.setVisibility(View.GONE);
                } } catch (Exception ignored) { }
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

    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    public int getCountsOfDigits(long number) {
        return String.valueOf(Math.abs(number)).length();
    }

    public void save(View view) {
        DateTimeFormatter dtfd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String dada = dtfd.format(now);

        DateTimeFormatter dtft = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime noww = LocalDateTime.now();
        String nene = dtft.format(noww);

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


            int dead = Integer.parseInt(sumBox.getText().toString());
            String rtp = categoryBoxCat.getText().toString();
            String rcom = commentBox.getText().toString();
            String rcomdate = viewTextDate.getText().toString();
            String rcomtime = viewTextTime.getText().toString();
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

            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.PURPOSE, purposeBox);
            cv.put(DatabaseHelper.CATEGORY, rtp);
            cv.put(DatabaseHelper.DATE, datepick);
            cv.put(DatabaseHelper.TIME, timepick);
            cv.put(DatabaseHelper.SUM, dead);

//            System.out.println("коммент мжду");


            if (userId > 0) {
                try {
                    if (mYear == 0) {
                        System.out.println("если ид польз есть");
                        System.out.println(datepick);
                        cv.put(DatabaseHelper.DATE, rcomdate);
                        cv.put(DatabaseHelper.TIME, rcomtime);
                        db.update(DatabaseHelper.TABLE_MAIN, cv, DatabaseHelper._ID + "=" + userId, null);
                    } else {
                        System.out.println("еслиgrsfdесть");
                        System.out.println(datepick);
                        System.out.println(rcomdate);
                        db.update(DatabaseHelper.TABLE_MAIN, cv, DatabaseHelper._ID + "=" + userId, null);
                    }


                    if (rcom != null) {
                        try {

                            Cursor catCursor = db.rawQuery("select _id, sum, purpose, category, date FROM ftp_main where sum = " + dead + " and category = '" + rtp + "' and _id = " + userId + " and purpose =  '" + purposeBox + "' ", null);
                            catCursor.moveToFirst();
                            @SuppressLint("Range") int item_id = catCursor
                                    .getInt(catCursor.getColumnIndex(DatabaseHelper._ID));
                            catCursor.close();
                            Cursor comCursor = db.rawQuery("select comment_id FROM ftp_comment where comment_id = " + userId, null);
                            comCursor.moveToFirst();
                            comCursor.close();
                            System.out.println("х");

                            ContentValues newcom = new ContentValues();
                            newcom.put(DatabaseHelper.COMMENT, rcom);
                            newcom.put(DatabaseHelper.COMMENT_ID, item_id);

                            db.delete(DatabaseHelper.TABLE_COMMENT, "comment_id = ?", new String[]{String.valueOf(userId)});
                            db.insert(DatabaseHelper.TABLE_COMMENT, null, newcom);
                            db.update(DatabaseHelper.TABLE_COMMENT, newcom, DatabaseHelper.COMMENT_ID + "=" + item_id, null);


                        } catch (Exception e) {
                            System.out.println("это если ошибка при update пользователя");
                        }
                    } else {
                        System.out.println("если коммент пустой");
                    }
                } catch (Exception e) {
                }


            } else if (jojoReference > 0) {
                try {
                    if (mYear == 0) {
                        System.out.println("если ид польз есть");
                        System.out.println(datepick);
                        cv.put(DatabaseHelper.DATE, rcomdate);
                        cv.put(DatabaseHelper.TIME, rcomtime);
                        db.update(DatabaseHelper.TABLE_MAIN, cv, DatabaseHelper._ID + "=" + jojoReference, null);
                    } else {
                        System.out.println("еслиgrsfdесть");
                        System.out.println(datepick);
                        System.out.println(rcomdate);
                        db.update(DatabaseHelper.TABLE_MAIN, cv, DatabaseHelper._ID + "=" + jojoReference, null);
                    }

                    if (rcom != null) {
                        System.out.println("ошибка 2");

                        try {
                            System.out.println("ошибка 3");

                            Cursor catCursor = db.rawQuery("select _id FROM ftp_main where sum = " + dead + " and category = '" + rtp + "' and _id = " + jojoReference + " and purpose =  '" + purposeBox + "' ", null);
                            catCursor.moveToFirst();
                            System.out.println("ошибка ?");
                            System.out.println(dead);
                            System.out.println(rtp);
                            System.out.println(jojoReference);
                            System.out.println(purposeBox);

                            @SuppressLint("Range") int item_id = catCursor.getInt(catCursor.getColumnIndex(DatabaseHelper._ID));
                            System.out.println("ошибка А?");

                            catCursor.close();
                            Cursor comCursor = db.rawQuery("select comment_id FROM ftp_comment where comment_id = " + jojoReference, null);
                            comCursor.moveToFirst();
                            comCursor.close();
                            System.out.println("х");
                            System.out.println("ошибка 6");

                            ContentValues newcom = new ContentValues();
                            newcom.put(DatabaseHelper.COMMENT, rcom);
                            newcom.put(DatabaseHelper.COMMENT_ID, item_id);
                            System.out.println("ошибка 8");

                            db.delete(DatabaseHelper.TABLE_COMMENT, "comment_id = ?", new String[]{String.valueOf(userId)});
                            System.out.println("ошибка 9");

                            db.insert(DatabaseHelper.TABLE_COMMENT, null, newcom);
                            db.update(DatabaseHelper.TABLE_COMMENT, newcom, DatabaseHelper.COMMENT_ID + "=" + item_id, null);


                        } catch (Exception e) {
                            System.out.println("это если ошибка при update");
                        }
//                        System.out.println("это полсе update");
                    } else {
                        System.out.println("если коммент пустой");
                    }
                    //db.update(DatabaseHelper.TABLE_COMMENT, newcom, DatabaseHelper.COMMENT_ID + "=" + userId, null);
                } catch (Exception e) {
                }
            } else {
                db.insert(DatabaseHelper.TABLE_MAIN, null, cv);
                System.out.println("жопа 1");
                if (commentBox != null) {

                    try {
                        Cursor catCurso = db.rawQuery("select _id, sum, purpose, category, date FROM ftp_main where sum = " + dead + " and category = '" + rtp + "' and purpose =  '" + purposeBox + "' ", null);
                        catCurso.moveToFirst();
                        int item_id = catCurso.getInt(catCurso.getColumnIndex(DatabaseHelper._ID));
                        catCurso.close();


                        ContentValues com = new ContentValues();
                        com.put(DatabaseHelper.COMMENT, rcom);
                        com.put(DatabaseHelper.COMMENT_ID, item_id);
                        db.insert(DatabaseHelper.TABLE_COMMENT, null, com);
                    } catch (Exception e) {
                        System.out.println("ошибка в меню вставки в бд новой записи");
                    }
                } else {
                    System.out.println("ой, ошибка вышла, да? ну так и иди ты нахуй");
                }
            }
            goHome();
        }
    }


    public void delete(View view) {
        db.delete(DatabaseHelper.TABLE_MAIN, "_id = ?", new String[]{String.valueOf(userId)});
        db.delete(DatabaseHelper.TABLE_QR, "qr_code_id = ?", new String[]{String.valueOf(userId)});
        db.delete(DatabaseHelper.TABLE_COMMENT, "comment_id = ?", new String[]{String.valueOf(userId)});
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
