package com.example.pockey;

import static android.telephony.AvailableNetworkInfo.PRIORITY_HIGH;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;

    private int year,month,day;
    private int currentyear,currentmonth,currentday;
    private String notifInt, notifName, notifCat;

    private String mmYM, mmMD;
    private String weekStart;
    private String weekEnd;
    private TextView dayLimitInfo1, dayLimitInfoText, dayLimitInfo2, allBudgetInfo;

    private String datePeriod0, datePeriod1, datePeriod2;
    private String datendPeriod0, datendPeriod1, datendPeriod2;
    private String timePeriod0, timePeriod1, timePeriod2;
    private String DateZero = "0-00-00";

    private TextView name, namee, sum_month, sum_month_income;
    private int ara, arara, araar, cool, notcool;
    //кнопка уведомления
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    Button b1;
    Button btnScanBarcode;

    public String Fuck2;

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    Cursor userCursorWeek, userCursorMonth, userCursorMonthIncome;
    Cursor userCursorNewPay, userCursorNewPeriod0, userCursorNewPeriod1, userCursorNewPeriod2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseHelper = new DatabaseHelper(getApplicationContext());

        allBudgetInfo = findViewById(R.id.sum_budget);
        dayLimitInfo1 = findViewById(R.id.sum_day_limit);
        dayLimitInfoText = findViewById(R.id.textView20);
        dayLimitInfo2 = findViewById(R.id.sum_day_limit2);


        b1 = findViewById(R.id.button123);
        b1.setVisibility(View.GONE);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        b1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setAutoCancel(false)
                            .setAutoCancel(false)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setWhen(System.currentTimeMillis())
                            .setContentIntent(pendingIntent)
                            .setContentTitle("Регулярный платеж")
                            .setContentText(Fuck2)
                            .setPriority(PRIORITY_HIGH);
            createChannelIfNeeded(notificationManager);
            notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
        });


        ImageButton btnScanBarcode = findViewById(R.id.buttonQR);
        btnScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ScannedBarcodeActivity.class));

            }
        });


        //начало кнопки добавления средств
        ImageButton buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {

            }
        });
        //конец кнопки добавления средств

        //начало кнопки все расходы
        ImageButton buttonRate = findViewById(R.id.buttonRate);
        buttonRate.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, MainAllRate.class);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {

            }
        });
        //конец кнопки все расходы

        //начало кнопки все доходы
        ImageButton buttonIncome = findViewById(R.id.buttonIncome);
        buttonIncome.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, MainAllincome.class);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {

            }
        });
        //конец кнопки все доходы


        //начало кнопки настроек
        ImageButton buttonPref = findViewById(R.id.buttonPref);
        buttonPref.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, MainSettingsPref.class);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {
            }
        });
        //конец кнопки все настройки

    }


    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    @SuppressLint({"Range", "SetTextI18n"})
    @Override
    public void onResume() {
        super.onResume();

        db = databaseHelper.getReadableDatabase();

        Cursor catCursor = db.rawQuery("select _id, sum(sum) as sum FROM ftp_main where date = CURRENT_DATE and purpose = 'Расход'", null);
        catCursor.moveToFirst(); // переходим на первую строку
        // извлекаем данные из курсора
        int item_id = catCursor
                .getInt(catCursor.getColumnIndex(DatabaseHelper.SUM));
//        String item_content = catCursor.getString(catCursor
//                .getColumnIndex(CatsDataBase.CATNAME));
        catCursor.close();
        name = findViewById(R.id.nametxt);
        name.setText("");
        name.setText(name.getText().toString() + " " + item_id);

        try {
            Cursor AllIncomeCursor = db.rawQuery("select sum(sum) as sum from ftp_main group by purpose", null);
            AllIncomeCursor.moveToFirst();
            int item_lkj = AllIncomeCursor.getInt(AllIncomeCursor.getColumnIndex(DatabaseHelper.SUM));
            AllIncomeCursor.moveToNext();
            int item_jkl = AllIncomeCursor.getInt(AllIncomeCursor.getColumnIndex(DatabaseHelper.SUM));
            int item_pooi = item_lkj - item_jkl;
            allBudgetInfo.setText("");
            allBudgetInfo.setText(allBudgetInfo.getText().toString() + " " + item_pooi);

                Cursor FirstDateCursor = db.rawQuery("select min(date) as date FROM ftp_main", null);
                FirstDateCursor.moveToFirst();
                String FirstDate = FirstDateCursor.getString(FirstDateCursor.getColumnIndex(DatabaseHelper.DATE));
                String[] FirstDateCurrent;
                String delimeter = "-"; // Разделитель
                FirstDateCurrent = FirstDate.split(delimeter);
                FirstDateCursor.close();

                for (int i = 0; i < FirstDateCurrent.length; i++) {
                    year = Integer.parseInt(FirstDateCurrent[0]);
                    month = Integer.parseInt(FirstDateCurrent[1]);
                    day = Integer.parseInt(FirstDateCurrent[2]);
                }
            DateTimeFormatter dtfd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            String dada = dtfd.format(now);
            String[] CurrentDate;
            CurrentDate = dada.split(delimeter);
            for (int i = 0; i < CurrentDate.length; i++) {
                currentyear = Integer.parseInt(CurrentDate[0]);
                currentmonth = Integer.parseInt(CurrentDate[1]);
                currentday = Integer.parseInt(CurrentDate[2]);
            }
                //средний расход в месяц по всем месяцам начало
                for (int q = 1; q <= currentmonth; q++) {
                    if (currentmonth < 10) {
                        mmYM = "-0";
                    } else {
                        mmYM = "-";
                    }
                    String datePeriod0for0 = currentyear + mmYM + q + "-01";
                    String datePeriod0for1 = currentyear + mmYM + q + "-31";

                    try {
                        Cursor AveMne1 = db.rawQuery("select sum(sum) as sum FROM ftp_main where purpose = 'Расход' and date >= '" + datePeriod0for0 + "' and date <= '" + datePeriod0for1 + "'", null);
                        if (AveMne1.getCount() > 0) {
                            AveMne1.moveToFirst();
                            int arara = AveMne1.getInt(AveMne1.getColumnIndex(DatabaseHelper.SUM));
                            System.out.println("arara = " + arara);
                            cool += arara;
                            if (arara > 0) {
                                notcool += 1;
                            }
                        } else {
                            int pol = +1;
                        }
                    } catch (Exception e) {
                        System.out.println("епросто пошёл нахуй  вот такая ошибка ожидал ? правильно нет и пошёл нахуй");
                    }

                    //выход цикла hgb
                    if (q == currentmonth + 1) {
                        break;
                    }
                }

                dayLimitInfo1.setText("");
                dayLimitInfo1.setText(dayLimitInfo1.getText().toString() + " " + ((cool / notcool) / 31) * 2);

                int ure = ((((cool / notcool) / 31) * 2) - item_id ) ;
                dayLimitInfo2.setText("");
                dayLimitInfo2.setText(dayLimitInfo2.getText().toString() + " " + ((((cool / notcool) / 31) * 2) - item_id )  );
                dayLimitInfoText = findViewById(R.id.textView20);
                if (ure > 0) {
                    dayLimitInfoText.setText("");
                    dayLimitInfoText.setText("Доступно ");
                    dayLimitInfoText = findViewById(R.id.textView20);
                } else {
                    dayLimitInfoText.setText("");
                    dayLimitInfoText.setText("Превышен ");
                    dayLimitInfoText = findViewById(R.id.textView20);
                }




            } catch (Exception ignored) { }

            DateTimeFormatter dtfd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            String dada = dtfd.format(now);

            String[] subStr;
            String delimeter = "-"; // Разделитель
            subStr = dada.split(delimeter);

            for (int i = 0; i < subStr.length; i++) {
                //System.out.println(subStr[i]);
                //int year = Integer.parseInt(subStr.getText().toString());
                year = Integer.parseInt(subStr[0]);
                month = Integer.parseInt(subStr[1]);
                day = Integer.parseInt(subStr[2]);
            }

            String monthStart = "2022-0" + month + "-00";
            String monthEnd = "2022-0" + month + "-31";

            if (month == 3) {
                try {
                    //System.out.println("это второе условие месяца 3 для дней");
                    if (day >= 1 && day < 7) {//(day == 7 || day == 14 || day == 22 || day == 28){
                        try {
                            day = 1;
                            weekStart = Integer.toString(day);
                            int el = day + 6;
                            String ele = Integer.toString(el);
                            weekStart = "2022-0" + month + "-" + day;
                            weekEnd = "2022-0" + month + "-" + ele;
                        } catch (Exception ignored) {
                        }
                    } else if (day >= 7 && day < 14) {
                    } else if (day >= 14 && day < 21) {
                    } else if (day >= 21 && day < 28) {
                    } else {
                        try {
                            day = 28;
                            weekStart = Integer.toString(day);
                            int el = day + 6;
                            String ele = Integer.toString(el);
                            weekStart = "2022-0" + month + "-" + day;
                            weekEnd = "2022-0" + month + "-" + ele;
                        } catch (Exception ignored) {
                        }
                    }
                } catch (Exception ignored) {
                }
            }
            else if (month == 4) {
                try {
                    if (day >= 4 && day < 10) {
                        try {
                            day = 4;
                            weekStart = Integer.toString(day);
                            int el = day + 6;
                            String ele = Integer.toString(el);
                            weekStart = "2022-0" + month + "-0" + day;
                            weekEnd = "2022-0" + month + "-" + ele;
                        } catch (Exception ignored) {
                        }
                    } else if (day >= 11 && day < 17) {
                        try {
                            day = 11;
                            weekStart = Integer.toString(day);
                            int el = day + 6;
                            String ele = Integer.toString(el);
                            weekStart = "2022-0" + month + "-" + day;
                            weekEnd = "2022-0" + month + "-" + ele;
                        } catch (Exception ignored) {
                        }
                    } else if (day >= 18 && day < 24) {
                        try {
                            day = 18;
                            weekStart = Integer.toString(day);
                            int el = day + 6;
                            String ele = Integer.toString(el);
                            weekStart = "2022-0" + month + "-" + day;
                            weekEnd = "2022-0" + month + "-" + ele;
                        } catch (Exception ignored) {
                        }
                    } else if (day >= 1 && day < 4) {
                        {
                            try {
                                day = 28;
                                weekStart = Integer.toString(day);
                                int el = day + 6;
                                String ele = Integer.toString(el);
                                weekStart = "2022-0" + month + day;
                                weekEnd = "2022-0" + month + "-" + ele;
                            } catch (Exception ignored) {
                            }
                        }
                    } else {
                        try {
                            day = 25;
                            weekStart = Integer.toString(day);
                            int el = day + 6;
                            String ele = Integer.toString(el);
                            weekStart = "2022-0" + month + "-" + day;
                            weekEnd = "2022-0" + month + "-" + ele;
                        } catch (Exception ignored) {
                        }
                    }
            } catch (Exception ignored) {
            }
        }
            else if (month == 5) {
                try {
                    if (day >= 2 && day < 8) {
                        try {
                            day = 2;
                            weekStart = Integer.toString(day);
                            int el = day + 6;
                            String ele = Integer.toString(el);
                            weekStart = "2022-0" + month + "-0" + day;
                            weekEnd = "2022-0" + month + "-0" + ele;
                        } catch (Exception ignored) {
                        }
                    } else if (day >= 9 && day < 15) {
                        try {
                            day = 9;
                            weekStart = Integer.toString(day);
                            int el = day + 6;
                            String ele = Integer.toString(el);
                            weekStart = "2022-0" + month + "-0" + day;
                            weekEnd = "2022-0" + month + "-" + ele;
                        } catch (Exception ignored) {
                        }
                    } else if (day >= 16 && day < 22) {
                        try {
                            day = 16;
                            weekStart = Integer.toString(day);
                            int el = day + 6;
                            String ele = Integer.toString(el);
                            weekStart = "2022-0" + month + "-" + day;
                            weekEnd = "2022-0" + month + "-" + ele;
                        } catch (Exception ignored) {
                        }
                    } else if (day >= 23 && day < 29) {
                        {
                            try {
                                day = 23;
                                weekStart = Integer.toString(day);
                                int el = day + 6;
                                String ele = Integer.toString(el);
                                weekStart = "2022-0" + month + day;
                                weekEnd = "2022-0" + month + "-" + ele;
                            } catch (Exception ignored) {
                            }
                        }
                    } else {
                        try {
                            day = 30;
                            weekStart = Integer.toString(day);
                            int el = day + 6;
                            String ele = Integer.toString(el);
                            weekStart = "2022-0" + month + "-" + day;
                            weekEnd = "2022-0" + month + "-" + ele;
                        } catch (Exception ignored) {
                        }
                    }
                } catch (Exception ignored) {
                }

            }
            else if (month == 6) {
                {
                    try {
                        if (day >= 1 && day < 5) {
                            try {
                                day = 2;
                                weekStart = Integer.toString(day);
                                int el = 5;
                                String ele = Integer.toString(el);
                                weekStart = "2022-05-30" + day;
                                weekEnd = "2022-0" + month + "-0" + ele;
                            } catch (Exception ignored) {
                            }
                        } else if (day >= 6 && day < 12) {
                            try {
                                day = 6;
                                weekStart = Integer.toString(day);
                                int el = day + 6;
                                String ele = Integer.toString(el);
                                weekStart = "2022-0" + month + "-0" + day;
                                weekEnd = "2022-0" + month + "-" + ele;
                            } catch (Exception ignored) {
                            }
                        } else if (day >= 13 && day < 19) {
                            try {
                                day = 13;
                                weekStart = Integer.toString(day);
                                int el = day + 6;
                                String ele = Integer.toString(el);
                                weekStart = "2022-0" + month + "-" + day;
                                weekEnd = "2022-0" + month + "-" + ele;
                            } catch (Exception ignored) {
                            }
                        } else if (day >= 20 && day < 26) {
                            {
                                try {
                                    day = 20;
                                    weekStart = Integer.toString(day);
                                    int el = day + 6;
                                    String ele = Integer.toString(el);
                                    weekStart = "2022-0" + month + day;
                                    weekEnd = "2022-0" + month + "-" + ele;
                                } catch (Exception ignored) {
                                }
                            }
                        } else {
                            try {
                                day = 27;
                                weekStart = Integer.toString(day);
                                int el = day + 6;
                                String ele = Integer.toString(el);
                                weekStart = "2022-0" + month + "-" + day;
                                weekEnd = "2022-0" + month + "-" + ele;
                            } catch (Exception ignored) {
                            }
                        }
                    } catch (Exception ignored) {
                    }

                }
            }
//        else if (month == 7) {}
//        else if (month == 8) {}
//        else if (month == 9) {}
//        else if (month == 10) {}
//        else if (month == 11) {}
        else {
        }


        userCursorWeek = db.rawQuery("select _id, sum(sum) as sum FROM ftp_main where date >= '" + weekStart + "' and date <= '" + weekEnd + "' and purpose = 'Расход'", null);
        userCursorWeek.moveToFirst(); // переходим на первую строку
        // извлекаем данные из курсора
        int week_sum = userCursorWeek
                .getInt(userCursorWeek.getColumnIndex(DatabaseHelper.SUM));
        userCursorWeek.close();
        namee = findViewById(R.id.nametxtt);
        namee.setText("");
        namee.setText(namee.getText().toString() + " " + week_sum);
        System.out.println(week_sum + " пидорас");
        System.out.println(weekStart + " пидорас");
        System.out.println(weekEnd   + " пидорас");

        userCursorMonth = db.rawQuery("select _id, sum(sum) as sum FROM ftp_main where date >= '" + monthStart + "' and date <= '" + monthEnd + "' and purpose = 'Расход'", null);
        userCursorMonth.moveToFirst();
        int month_sum = userCursorMonth
                .getInt(userCursorMonth.getColumnIndex(DatabaseHelper.SUM));
        userCursorMonth.close();
        sum_month = findViewById(R.id.sum_month);
        sum_month.setText("");
        sum_month.setText(sum_month.getText().toString() + " " + month_sum);

        userCursorMonthIncome = db.rawQuery("select _id, sum(sum) as sum FROM ftp_main where date >= '" + monthStart + "' and date <= '" + monthEnd + "' and purpose = 'Доход'", null);
        userCursorMonthIncome.moveToFirst(); // переходим на первую строку
        // извлекаем данные из курсора
        int month_sum_income = userCursorMonthIncome
                .getInt(userCursorMonthIncome.getColumnIndex(DatabaseHelper.SUM));
        sum_month_income = findViewById(R.id.sum_month_income);
        sum_month_income.setText("");

        sum_month_income.setText(sum_month_income.getText().toString() + " " + month_sum_income);
        userCursorMonthIncome.close();


        //один раз каждый день раз в месяц
        //условие добавления начало
        userCursorNewPay = db.rawQuery("select * FROM ftp_pay where check_pay = 1", null);
//условие если платежи активны

        if (userCursorNewPay.getCount() > 0) {
            //System.out.println("если check_pay равен 1, то есть он активный");

            try {
                userCursorNewPay = db.rawQuery("select * FROM ftp_pay where check_notification = 1 and check_pay = 1", null);
            }
            catch (Exception ignored) { }
            //условие если активны платжи и увебомления

            // условие включенных уведомлений начало
            String mmYM;
            String mmMD;
            if (userCursorNewPay.getCount() > 0) {
                //условие для один раз
                userCursorNewPeriod0 = db.rawQuery("select * FROM ftp_pay where check_notification = 1 and check_pay = 1 and check_period = 0", null);
                //период один раз
                if (userCursorNewPeriod0.getCount() > 0) {
                    System.out.println("если есть уведомление и если check_pay равен 1, то есть он активный и период 0 тое есть один раз ");
                    int pupa = userCursorNewPeriod0.getCount();
                    userCursorNewPeriod0.moveToFirst(); // переходим на первую строку
                    for (int y = 0; y < pupa; y++) {
                        //блок один раз
                        datePeriod0 = userCursorNewPeriod0.getString(userCursorNewPeriod0.getColumnIndex(DatabaseHelper.DATE));
                        timePeriod0 = userCursorNewPeriod0.getString(userCursorNewPeriod0.getColumnIndex(DatabaseHelper.TIME));
                        datendPeriod0 = userCursorNewPeriod0.getString(userCursorNewPeriod0.getColumnIndex(DatabaseHelper.CHECK_DATEND));
                        arara = userCursorNewPeriod0.getInt(userCursorNewPeriod0.getColumnIndex(DatabaseHelper._ID));
                        String sumPeriod0 = userCursorNewPeriod0.getString(userCursorNewPeriod0.getColumnIndex(DatabaseHelper.SUM));
                        String categoryPeriod0 = userCursorNewPeriod0.getString(userCursorNewPeriod0.getColumnIndex(DatabaseHelper.CATEGORY));
                        String purposePeriod0 = userCursorNewPeriod0.getString(userCursorNewPeriod0.getColumnIndex(DatabaseHelper.PURPOSE));
                        String namePeriod0 = userCursorNewPeriod0.getString(userCursorNewPeriod0.getColumnIndex(DatabaseHelper.CHECK_NAME));
                        ContentValues kv = new ContentValues();
                        kv.put(DatabaseHelper.PURPOSE, purposePeriod0);         //цель расход или доход
                        kv.put(DatabaseHelper.CATEGORY, categoryPeriod0);               //категория
                        kv.put(DatabaseHelper.SUM, sumPeriod0);
                        kv.put(DatabaseHelper.DATE, datePeriod0);
                        kv.put(DatabaseHelper.TIME, timePeriod0);

                        Cursor userCursorAveMaria = db.rawQuery("select * FROM ftp_main where category = '" + categoryPeriod0 + "'and purpose = '" + purposePeriod0 + "'and sum = " + sumPeriod0 + " and date = '" + datePeriod0 + "' and time = '" + timePeriod0 + "'", null);
                        if (userCursorAveMaria.getCount() > 0) {

                            String[] dateStrPeriod0;
                            dateStrPeriod0 = datePeriod0.split(delimeter);
                            for (int i = 0; i < dateStrPeriod0.length; i++) {
                                year = Integer.parseInt(subStr[0]);
                                month = Integer.parseInt(subStr[1]);
                                day = Integer.parseInt(subStr[2]);
                                //System.out.println("заебало уже нахуй блять");
                                //System.out.println(day);
                                //System.out.println("это уже блять не смешно");

                            }
                            for (int i = day; i + 1 > day; i++) {
                                if (month < 10) {
                                    mmYM = "-0";
                                } else {
                                    mmYM = "-";
                                }
                                if (day < 10) {
                                    mmMD = "-0";
                                } else {
                                    mmMD = "-";
                                }
                                String datePeriod0for = year + mmYM + month + mmMD + day;
                                //System.out.println(datePeriod0for);
                                try {
                                    userCursorAveMaria = db.rawQuery("select * FROM ftp_main where category = '" + categoryPeriod0 + "'and purpose = '" + purposePeriod0 + "'and sum = " + sumPeriod0 + " and date = '" + datePeriod0for + "'", null);
                                    if (userCursorAveMaria.getCount() > 0) {
                                        //System.out.println("если в мейне есть такая запись");
                                        //System.out.println(datePeriod0for);
                                        araar = 0;
                                        ContentValues ktr = new ContentValues();
                                        ktr.put(DatabaseHelper.CHECK_PAY, araar);
                                        db.update(DatabaseHelper.TABLE_PAY, ktr, DatabaseHelper._ID + "=" + arara, null);
                                    } else {
                                        kv.put(DatabaseHelper.DATE, datePeriod0for);
                                        db.insert(DatabaseHelper.TABLE_MAIN, null, kv);
                                        ara = 1;
                                    }
                                } catch (Exception e) {
                                    //System.out.println("епросто пошёл нахуй  такая запись");
                                }

                                //System.out.println(i);

                                if ((i == 31) || (datePeriod0for.equals(dada)) || (ara == 1)) {
                                    //System.out.println("лох пидр");
                                    //System.out.println(i);
                                    break;
                                } else {
                                    //System.out.println("нахуя здесь ретурне?");
                                }
                            }
                        } else {
                            //если даты равны между сегодняшним днём и датой начала в трате
                            if (datePeriod0.equals(dada)) {
                                db.insert(DatabaseHelper.TABLE_MAIN, null, kv);
                                //System.out.println("оно блять работает");
//[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq[eq
                                notifInt =  sumPeriod0;
                                notifName =  namePeriod0;
                                notifCat =  categoryPeriod0;
                                addNotification();
                            }

                            //если дата равна дате конца
                            //System.out.println("вновь какая то хуйня");
                            //System.out.println(dada);
                            //System.out.println(DateZero);

                            if (datendPeriod0.equals(dada) | datendPeriod0.equals(DateZero)) {
                                araar = 0;
                                ContentValues ktr = new ContentValues();
                                ktr.put(DatabaseHelper.CHECK_PAY, araar);
                                db.update(DatabaseHelper.TABLE_PAY, ktr, DatabaseHelper._ID + "=" + arara, null);
                            }
                        }
                        userCursorNewPeriod0.moveToNext();
                    }
                    userCursorNewPeriod0.close();
                } else {
                    userCursorNewPeriod0.close();
                }
                //период один раз конец

                //период каждый день начало
                userCursorNewPeriod1 = db.rawQuery("select * FROM ftp_pay where check_notification = 1 and check_pay = 1 and check_period = 1", null);
                if (userCursorNewPeriod1.getCount() > 0) {
                    //System.out.println("если есть уведомление и если check_pay равен 1, то есть он активный и период 1 ");
                    //System.out.println(userCursorNewPay.getCount());

                    //System.out.println(userCursorNewPeriod1.getCount());
                    int pupa = userCursorNewPeriod1.getCount();
                    userCursorNewPeriod1.moveToFirst(); // переходим на первую строку

                    for (int y = 0; y < pupa; y++) {
                        //System.out.println(y);
                        //System.out.println(pupa);
                        //System.out.println("всерху хуйня какая-то");
                        //блок каждый день
                        datePeriod1 = userCursorNewPeriod1.getString(userCursorNewPeriod1.getColumnIndex(DatabaseHelper.DATE));
                        timePeriod1 = userCursorNewPeriod1.getString(userCursorNewPeriod1.getColumnIndex(DatabaseHelper.TIME));
                        datendPeriod1 = userCursorNewPeriod1.getString(userCursorNewPeriod1.getColumnIndex(DatabaseHelper.CHECK_DATEND));
                        arara = userCursorNewPeriod1.getInt(userCursorNewPeriod1.getColumnIndex(DatabaseHelper._ID));
                        String sumPeriod1 = userCursorNewPeriod1.getString(userCursorNewPeriod1.getColumnIndex(DatabaseHelper.SUM));
                        String namePeriod1 = userCursorNewPeriod1.getString(userCursorNewPeriod1.getColumnIndex(DatabaseHelper.CHECK_NAME));
                        String categoryPeriod1 = userCursorNewPeriod1.getString(userCursorNewPeriod1.getColumnIndex(DatabaseHelper.CATEGORY));
                        String purposePeriod1 = userCursorNewPeriod1.getString(userCursorNewPeriod1.getColumnIndex(DatabaseHelper.PURPOSE));
                        //System.out.println(datePeriod1);
                        ContentValues kv = new ContentValues();
                        kv.put(DatabaseHelper.PURPOSE, purposePeriod1);         //цель расход или доход
                        kv.put(DatabaseHelper.CATEGORY, categoryPeriod1);               //категория
                        kv.put(DatabaseHelper.SUM, sumPeriod1);
                        kv.put(DatabaseHelper.DATE, datePeriod1);
                        kv.put(DatabaseHelper.TIME, timePeriod1);

                        Cursor userCursorAveMaria = db.rawQuery("select * FROM ftp_main where category = '" + categoryPeriod1 + "'and purpose = '" + purposePeriod1 + "'and sum = " + sumPeriod1 + " and date = '" + datePeriod1 + "' and time = '" + timePeriod1 + "'", null);
                        if (userCursorAveMaria.getCount() > 0) {

                            String[] dateStrPeriod1;
                            dateStrPeriod1 = datePeriod1.split(delimeter);
                            for (int i = 0; i < dateStrPeriod1.length; i++) {
                                ////System.out.println(subStr[i]);
                                //int year = Integer.parseInt(subStr.getText().toString());
                                year = Integer.parseInt(subStr[0]);
                                month = Integer.parseInt(subStr[1]);
                                day = Integer.parseInt(subStr[2]);
                            }

                            for (int i = day; i + 1 > day; i++) {

                                if (month < 10) {
                                    mmYM = "-0";
                                } else {
                                    mmYM = "-";
                                }
                                if (day < 10) {
                                    mmMD = "-0";
                                } else {
                                    mmMD = "-";
                                }
                                String datePeriod1for = year + mmYM + month + mmMD + day;
                                //System.out.println(datePeriod1for);
                                //System.out.println("смотрим на даты");
                                //System.out.println(dada);

                                try {
                                    userCursorAveMaria = db.rawQuery("select * FROM ftp_main where category = '" + categoryPeriod1 + "'and purpose = '" + purposePeriod1 + "'and sum = " + sumPeriod1 + " and date = '" + datePeriod1for + "'", null);
                                    if (userCursorAveMaria.getCount() > 0) {
                                        //System.out.println("если в мейне есть такая запись");
                                        //System.out.println(i);
                                        //System.out.println(datePeriod1for);
                                    } else {
                                        kv.put(DatabaseHelper.DATE, datePeriod1for);
                                        db.insert(DatabaseHelper.TABLE_MAIN, null, kv);
                                        ara = 1;
                                    }
                                } catch (Exception e) {
                                    //System.out.println("епросто пошёл нахуй  такая запись");
                                }
                                //System.out.println(i);

                                if ((i == 31) || (datePeriod1for.equals(dada)) || (ara == 1)) {
                                    //System.out.println("лох пидр");
                                    //System.out.println(i);
                                    break;
                                } else {
                                    //System.out.println("нахуя здесь ретурне?");
                                }
                            }
                        } else {
                            if (datePeriod1.equals(dada)) {
                                db.insert(DatabaseHelper.TABLE_MAIN, null, kv);
                                //System.out.println("оно блять работает");
                                notifInt =  sumPeriod1;
                                notifName =  namePeriod1;
                                notifCat =  categoryPeriod1;
                                addNotification();
                            }

                            //если дата равна дате конца
                            if (datendPeriod1.equals(dada) || datendPeriod1.equals(DateZero)) {
                                araar = 0;
                                ContentValues ktr = new ContentValues();
                                ktr.put(DatabaseHelper.CHECK_PAY, araar);
                                db.update(DatabaseHelper.TABLE_PAY, ktr, DatabaseHelper._ID + "=" + arara, null);
                            }
                        }
                        userCursorNewPeriod1.moveToNext();
                    }
                    userCursorNewPeriod1.close();
                } else {
                    userCursorNewPeriod1.close();
                }
                //период каджый день конец

                //период каждый месяц начало
                userCursorNewPeriod2 = db.rawQuery("select * FROM ftp_pay where check_notification = 1 and check_pay = 1 and check_period = 2", null);
                if (userCursorNewPeriod2.getCount() > 0) {
                    ////System.out.println(userCursorNewPay.getCount());

                    //System.out.println(userCursorNewPeriod2.getCount());
                    int pupa2 = userCursorNewPeriod2.getCount();
                    userCursorNewPeriod2.moveToFirst(); // переходим на первую строку

                    for (int z = 0; z < pupa2; z++) {
                        //System.out.println(z);
                        //блок каждый месяц
                        datePeriod2 = userCursorNewPeriod2.getString(userCursorNewPeriod2.getColumnIndex(DatabaseHelper.DATE));
                        timePeriod2 = userCursorNewPeriod2.getString(userCursorNewPeriod2.getColumnIndex(DatabaseHelper.TIME));
                        datendPeriod2 = userCursorNewPeriod2.getString(userCursorNewPeriod2.getColumnIndex(DatabaseHelper.CHECK_DATEND));
                        arara = userCursorNewPeriod2.getInt(userCursorNewPeriod2.getColumnIndex(DatabaseHelper._ID));
                        String sumPeriod2 = userCursorNewPeriod2.getString(userCursorNewPeriod2.getColumnIndex(DatabaseHelper.SUM));
                        String namePeriod2 = userCursorNewPeriod2.getString(userCursorNewPeriod2.getColumnIndex(DatabaseHelper.CHECK_NAME));
                        String categoryPeriod2 = userCursorNewPeriod2.getString(userCursorNewPeriod2.getColumnIndex(DatabaseHelper.CATEGORY));
                        String purposePeriod2 = userCursorNewPeriod2.getString(userCursorNewPeriod2.getColumnIndex(DatabaseHelper.PURPOSE));
                        //System.out.println(datePeriod2);
                        ContentValues kv = new ContentValues();
                        kv.put(DatabaseHelper.PURPOSE, purposePeriod2);         //цель расход или доход
                        kv.put(DatabaseHelper.CATEGORY, categoryPeriod2);               //категория
                        kv.put(DatabaseHelper.SUM, sumPeriod2);
                        kv.put(DatabaseHelper.DATE, datePeriod2);
                        kv.put(DatabaseHelper.TIME, timePeriod2);

                        Cursor userCursorAveMaria2 = db.rawQuery("select * FROM ftp_main where category = '" + categoryPeriod2 + "'and purpose = '" + purposePeriod2 + "'and sum = " + sumPeriod2 + " and date = '" + datePeriod2 + "' and time = '" + timePeriod2 + "'", null);
                        if (userCursorAveMaria2.getCount() > 0) {

                            String[] dateStrPeriod2;
                            dateStrPeriod2 = datePeriod2.split(delimeter);
                            for (int i = 0; i < dateStrPeriod2.length; i++) {
                                ////System.out.println(subStr[i]);
                                //int year = Integer.parseInt(subStr.getText().toString());
                                year = Integer.parseInt(subStr[0]);
                                month = Integer.parseInt(subStr[1]);
                                day = Integer.parseInt(subStr[2]);
                            }

                            for (int i = month; i + 1 > month; i++) {

                                if (month < 10) {
                                    mmYM = "-0";
                                } else {
                                    mmYM = "-";
                                }
                                if (day < 10) {
                                    mmMD = "-0";
                                } else {
                                    mmMD = "-";
                                }
                                String datePeriod2for = year + mmYM + month + mmMD + day;
                                //System.out.println(datePeriod2for);
                                //System.out.println("смотрим на даты");
                                //System.out.println(dada);

                                try {
                                    userCursorAveMaria2 = db.rawQuery("select * FROM ftp_main where category = '" + categoryPeriod2 + "'and purpose = '" + purposePeriod2 + "'and sum = " + sumPeriod2 + " and date = '" + datePeriod2for + "'", null);
                                    if (userCursorAveMaria2.getCount() > 0) {
                                        //System.out.println("если в мейне есть такая запись");
                                        //System.out.println(i);
                                        //System.out.println(datePeriod2for);
                                    } else {
                                        kv.put(DatabaseHelper.DATE, datePeriod2for);
                                        db.insert(DatabaseHelper.TABLE_MAIN, null, kv);
                                        ara = 1;
                                    }
                                } catch (Exception e) {
                                    //System.out.println("епросто пошёл нахуй  такая запись");
                                }
                                //System.out.println(i);

                                if ((i == 12) || (datePeriod2for.equals(dada)) || (ara == 1)) {
                                    //System.out.println("лох пидр");
                                    //System.out.println(i);
                                    break;
                                } else {
                                    //System.out.println("нахуя здесь ретурне?");
                                }
                            }
                        } else {

                            if (datePeriod2.equals(dada)) {
                                db.insert(DatabaseHelper.TABLE_MAIN, null, kv);
                                //System.out.println("оно блять работает");
                                notifInt =  sumPeriod2;
                                notifName =  namePeriod2;
                                notifCat =  categoryPeriod2;
                                addNotification();
                            }

                            //если дата равна дате конца
                            if (datendPeriod2.equals(dada) || datendPeriod2.equals(DateZero)) {
                                araar = 0;
                                ContentValues ktr = new ContentValues();
                                ktr.put(DatabaseHelper.CHECK_PAY, araar);
                                db.update(DatabaseHelper.TABLE_PAY, ktr, DatabaseHelper._ID + "=" + arara, null);
                            }
                        }
                        userCursorNewPeriod2.moveToNext();
                    }
                    userCursorNewPeriod2.close();
                } else {
                    userCursorNewPeriod2.close();
                }
                //период каджый меся конец
            } else {
                //System.out.println("нечего добавлять1");
            }
            //условие для уведомлений конец

            //условия без уведолмений начало
            userCursorNewPay = db.rawQuery("select * FROM ftp_pay where check_notification = 0 and check_pay = 1", null);
            //условие если активны только платжи без увебомления
            // условие без уведомлений начало
            if (userCursorNewPay.getCount() > 0) {
                //условие для один раз
                userCursorNewPeriod0 = db.rawQuery("select * FROM ftp_pay where check_notification = 0 and check_pay = 1 and check_period = 0", null);
                //период один раз
                if (userCursorNewPeriod0.getCount() > 0) {
                    //System.out.println("если есть уведомление и если check_pay равен 1, то есть он активный и период 0 тое есть один раз ");
                    int pupa = userCursorNewPeriod0.getCount();
                    userCursorNewPeriod0.moveToFirst(); // переходим на первую строку
                    for (int y = 0; y < pupa; y++) {
                        //System.out.println("всерху хуйня какая-то");
                        //блок один раз
                        datePeriod0 = userCursorNewPeriod0.getString(userCursorNewPeriod0.getColumnIndex(DatabaseHelper.DATE));
                        timePeriod0 = userCursorNewPeriod0.getString(userCursorNewPeriod0.getColumnIndex(DatabaseHelper.TIME));
                        datendPeriod0 = userCursorNewPeriod0.getString(userCursorNewPeriod0.getColumnIndex(DatabaseHelper.CHECK_DATEND));
                        arara = userCursorNewPeriod0.getInt(userCursorNewPeriod0.getColumnIndex(DatabaseHelper._ID));
                        String sumPeriod0 = userCursorNewPeriod0.getString(userCursorNewPeriod0.getColumnIndex(DatabaseHelper.SUM));
                        String categoryPeriod0 = userCursorNewPeriod0.getString(userCursorNewPeriod0.getColumnIndex(DatabaseHelper.CATEGORY));
                        String purposePeriod0 = userCursorNewPeriod0.getString(userCursorNewPeriod0.getColumnIndex(DatabaseHelper.PURPOSE));
                        ContentValues kv = new ContentValues();
                        kv.put(DatabaseHelper.PURPOSE, purposePeriod0);         //цель расход или доход
                        kv.put(DatabaseHelper.CATEGORY, categoryPeriod0);               //категория
                        kv.put(DatabaseHelper.SUM, sumPeriod0);
                        kv.put(DatabaseHelper.DATE, datePeriod0);
                        kv.put(DatabaseHelper.TIME, timePeriod0);

                        Cursor userCursorAveMaria = db.rawQuery("select * FROM ftp_main where category = '" + categoryPeriod0 + "'and purpose = '" + purposePeriod0 + "'and sum = " + sumPeriod0 + " and date = '" + datePeriod0 + "' and time = '" + timePeriod0 + "'", null);
                        if (userCursorAveMaria.getCount() > 0) {

                            String[] dateStrPeriod0;
                            dateStrPeriod0 = datePeriod0.split(delimeter);
                            for (int i = 0; i < dateStrPeriod0.length; i++) {
                                year = Integer.parseInt(subStr[0]);
                                month = Integer.parseInt(subStr[1]);
                                day = Integer.parseInt(subStr[2]);
                                //System.out.println("заебало уже нахуй блять");
                                //System.out.println(day);
                                //System.out.println("это уже блять не смешно");

                            }
                            for (int i = day; i + 1 > day; i++) {
                                if (month < 10) {
                                    mmYM = "-0";
                                } else {
                                    mmYM = "-";
                                }
                                if (day < 10) {
                                    mmMD = "-0";
                                } else {
                                    mmMD = "-";
                                }
                                String datePeriod0for = year + mmYM + month + mmMD + day;
                                //System.out.println(datePeriod0for);
                                try {
                                    userCursorAveMaria = db.rawQuery("select * FROM ftp_main where category = '" + categoryPeriod0 + "'and purpose = '" + purposePeriod0 + "'and sum = " + sumPeriod0 + " and date = '" + datePeriod0for + "'", null);
                                    if (userCursorAveMaria.getCount() > 0) {
                                        //System.out.println("если в мейне есть такая запись");
                                        //System.out.println(datePeriod0for);
                                        araar = 0;
                                        ContentValues ktr = new ContentValues();
                                        ktr.put(DatabaseHelper.CHECK_PAY, araar);
                                        db.update(DatabaseHelper.TABLE_PAY, ktr, DatabaseHelper._ID + "=" + arara, null);
                                    } else {
                                        kv.put(DatabaseHelper.DATE, datePeriod0for);
                                        db.insert(DatabaseHelper.TABLE_MAIN, null, kv);
                                        ara = 1;
                                    }
                                } catch (Exception e) {
                                    //System.out.println("епросто пошёл нахуй  такая запись");
                                }

                                //System.out.println(i);

                                if ((i == 31) || (datePeriod0for.equals(dada)) || (ara == 1)) {
                                    //System.out.println("лох пидр");
                                    //System.out.println(i);
                                    break;
                                } else {
                                    //System.out.println("нахуя здесь ретурне?");
                                }
                            }
                        } else {
                            //если даты равны между сегодняшним днём и датой начала в трате
                            if (datePeriod0.equals(dada)) {
                                db.insert(DatabaseHelper.TABLE_MAIN, null, kv);
                                //System.out.println("оно блять работает");
                            }

                            //если дата равна дате конца
                            if (datendPeriod0.equals(dada) || datendPeriod0.equals(DateZero)) {
                                araar = 0;
                                ContentValues ktr = new ContentValues();
                                ktr.put(DatabaseHelper.CHECK_PAY, araar);
                                db.update(DatabaseHelper.TABLE_PAY, ktr, DatabaseHelper._ID + "=" + arara, null);
                            }
                        }
                        userCursorNewPeriod0.moveToNext();
                    }
                    userCursorNewPeriod0.close();
                } else {
                    userCursorNewPeriod0.close();
                }
                //период один раз конец

                //период каждый день начало
                userCursorNewPeriod1 = db.rawQuery("select * FROM ftp_pay where check_notification = 0 and check_pay = 1 and check_period = 1", null);
                if (userCursorNewPeriod1.getCount() > 0) {
                    //System.out.println("если есть уведомление и если check_pay равен 1, то есть он активный и период 1 ");
                    //System.out.println(userCursorNewPay.getCount());

                    //System.out.println(userCursorNewPeriod1.getCount());
                    int pupa = userCursorNewPeriod1.getCount();
                    userCursorNewPeriod1.moveToFirst(); // переходим на первую строку

                    for (int y = 0; y < pupa; y++) {
                        //System.out.println(y);
                        //System.out.println(pupa);
                        //System.out.println("всерху хуйня какая-то");
                        //блок каждый день
                        datePeriod1 = userCursorNewPeriod1.getString(userCursorNewPeriod1.getColumnIndex(DatabaseHelper.DATE));
                        timePeriod1 = userCursorNewPeriod1.getString(userCursorNewPeriod1.getColumnIndex(DatabaseHelper.TIME));
                        datendPeriod1 = userCursorNewPeriod1.getString(userCursorNewPeriod1.getColumnIndex(DatabaseHelper.CHECK_DATEND));
                        arara = userCursorNewPeriod1.getInt(userCursorNewPeriod1.getColumnIndex(DatabaseHelper._ID));
                        String sumPeriod1 = userCursorNewPeriod1.getString(userCursorNewPeriod1.getColumnIndex(DatabaseHelper.SUM));
                        String categoryPeriod1 = userCursorNewPeriod1.getString(userCursorNewPeriod1.getColumnIndex(DatabaseHelper.CATEGORY));
                        String purposePeriod1 = userCursorNewPeriod1.getString(userCursorNewPeriod1.getColumnIndex(DatabaseHelper.PURPOSE));
                        //System.out.println(datePeriod1);
                        ContentValues kv = new ContentValues();
                        kv.put(DatabaseHelper.PURPOSE, purposePeriod1);         //цель расход или доход
                        kv.put(DatabaseHelper.CATEGORY, categoryPeriod1);               //категория
                        kv.put(DatabaseHelper.SUM, sumPeriod1);
                        kv.put(DatabaseHelper.DATE, datePeriod1);
                        kv.put(DatabaseHelper.TIME, timePeriod1);

                        Cursor userCursorAveMaria = db.rawQuery("select * FROM ftp_main where category = '" + categoryPeriod1 + "'and purpose = '" + purposePeriod1 + "'and sum = " + sumPeriod1 + " and date = '" + datePeriod1 + "' and time = '" + timePeriod1 + "'", null);
                        if (userCursorAveMaria.getCount() > 0) {

                            String[] dateStrPeriod1;
                            dateStrPeriod1 = datePeriod1.split(delimeter);
                            for (int i = 0; i < dateStrPeriod1.length; i++) {
                                ////System.out.println(subStr[i]);
                                //int year = Integer.parseInt(subStr.getText().toString());
                                year = Integer.parseInt(subStr[0]);
                                month = Integer.parseInt(subStr[1]);
                                day = Integer.parseInt(subStr[2]);
                            }

                            for (int i = day; i + 1 > day; i++) {

                                if (month < 10) {
                                    mmYM = "-0";
                                } else {
                                    mmYM = "-";
                                }
                                if (day < 10) {
                                    mmMD = "-0";
                                } else {
                                    mmMD = "-";
                                }
                                String datePeriod1for = year + mmYM + month + mmMD + day;
                                //System.out.println(datePeriod1for);
                                //System.out.println("смотрим на даты");
                                //System.out.println(dada);

                                try {
                                    userCursorAveMaria = db.rawQuery("select * FROM ftp_main where category = '" + categoryPeriod1 + "'and purpose = '" + purposePeriod1 + "'and sum = " + sumPeriod1 + " and date = '" + datePeriod1for + "'", null);
                                    if (userCursorAveMaria.getCount() > 0) {
                                        //System.out.println("если в мейне есть такая запись");
                                        //System.out.println(i);
                                        //System.out.println(datePeriod1for);
                                    } else {
                                        kv.put(DatabaseHelper.DATE, datePeriod1for);
                                        db.insert(DatabaseHelper.TABLE_MAIN, null, kv);
                                        ara = 1;
                                    }
                                } catch (Exception e) {
                                    //System.out.println("епросто пошёл нахуй  такая запись");
                                }
                                //System.out.println(i);

                                if ((i == 31) || (datePeriod1for.equals(dada)) || (ara == 1)) {
                                    //System.out.println("лох пидр");
                                    //System.out.println(i);
                                    break;
                                } else {
                                    //System.out.println("нахуя здесь ретурне?");
                                }
                            }
                        } else {
                            if (datePeriod1.equals(dada)) {
                                db.insert(DatabaseHelper.TABLE_MAIN, null, kv);
                                //System.out.println("оно блять работает");
                            }

                            //если дата равна дате конца
                            if (datendPeriod1.equals(dada) || datendPeriod2.equals(DateZero)) {
                                araar = 0;
                                ContentValues ktr = new ContentValues();
                                ktr.put(DatabaseHelper.CHECK_PAY, araar);
                                db.update(DatabaseHelper.TABLE_PAY, ktr, DatabaseHelper._ID + "=" + arara, null);
                            }
                        }
                        userCursorNewPeriod1.moveToNext();
                    }
                    userCursorNewPeriod1.close();
                } else {
                    userCursorNewPeriod1.close();
                }
                //период каджый день конец

                //период каждый месяц начало
                userCursorNewPeriod2 = db.rawQuery("select * FROM ftp_pay where check_notification = 0 and check_pay = 1 and check_period = 2", null);
                if (userCursorNewPeriod2.getCount() > 0) {
                    //System.out.println(userCursorNewPay.getCount());

                    //System.out.println(userCursorNewPeriod2.getCount());
                    int pupa2 = userCursorNewPeriod2.getCount();
                    userCursorNewPeriod2.moveToFirst(); // переходим на первую строку

                    for (int z = 0; z < pupa2; z++) {
                        //System.out.println(z);
                        //блок каждый месяц
                        datePeriod2 = userCursorNewPeriod2.getString(userCursorNewPeriod2.getColumnIndex(DatabaseHelper.DATE));
                        timePeriod2 = userCursorNewPeriod2.getString(userCursorNewPeriod2.getColumnIndex(DatabaseHelper.TIME));
                        datendPeriod2 = userCursorNewPeriod2.getString(userCursorNewPeriod2.getColumnIndex(DatabaseHelper.CHECK_DATEND));
                        arara = userCursorNewPeriod2.getInt(userCursorNewPeriod2.getColumnIndex(DatabaseHelper._ID));
                        String sumPeriod2 = userCursorNewPeriod2.getString(userCursorNewPeriod2.getColumnIndex(DatabaseHelper.SUM));
                        String categoryPeriod2 = userCursorNewPeriod2.getString(userCursorNewPeriod2.getColumnIndex(DatabaseHelper.CATEGORY));
                        String purposePeriod2 = userCursorNewPeriod2.getString(userCursorNewPeriod2.getColumnIndex(DatabaseHelper.PURPOSE));
                        //System.out.println(datePeriod2);
                        ContentValues kv = new ContentValues();
                        kv.put(DatabaseHelper.PURPOSE, purposePeriod2);         //цель расход или доход
                        kv.put(DatabaseHelper.CATEGORY, categoryPeriod2);               //категория
                        kv.put(DatabaseHelper.SUM, sumPeriod2);
                        kv.put(DatabaseHelper.DATE, datePeriod2);
                        kv.put(DatabaseHelper.TIME, timePeriod2);

                        Cursor userCursorAveMaria2 = db.rawQuery("select * FROM ftp_main where category = '" + categoryPeriod2 + "'and purpose = '" + purposePeriod2 + "'and sum = " + sumPeriod2 + " and date = '" + datePeriod2 + "' and time = '" + timePeriod2 + "'", null);
                        if (userCursorAveMaria2.getCount() > 0) {

                            String[] dateStrPeriod2;
                            dateStrPeriod2 = datePeriod2.split(delimeter);
                            for (int i = 0; i < dateStrPeriod2.length; i++) {
                                ////System.out.println(subStr[i]);
                                //int year = Integer.parseInt(subStr.getText().toString());
                                year = Integer.parseInt(subStr[0]);
                                month = Integer.parseInt(subStr[1]);
                                day = Integer.parseInt(subStr[2]);
                            }

                            for (int i = month; i + 1 > month; i++) {

                                if (month < 10) {
                                    mmYM = "-0";
                                } else {
                                    mmYM = "-";
                                }
                                if (day < 10) {
                                    mmMD = "-0";
                                } else {
                                    mmMD = "-";
                                }
                                String datePeriod2for = year + mmYM + month + mmMD + day;
                                //System.out.println(datePeriod2for);
                                //System.out.println("смотрим на даты");
                                //System.out.println(dada);

                                try {
                                    userCursorAveMaria2 = db.rawQuery("select * FROM ftp_main where category = '" + categoryPeriod2 + "'and purpose = '" + purposePeriod2 + "'and sum = " + sumPeriod2 + " and date = '" + datePeriod2for + "'", null);
                                    if (userCursorAveMaria2.getCount() > 0) {
                                        //System.out.println("если в мейне есть такая запись");
                                        //System.out.println(i);
                                        //System.out.println(datePeriod2for);
                                    } else {
                                        kv.put(DatabaseHelper.DATE, datePeriod2for);
                                        db.insert(DatabaseHelper.TABLE_MAIN, null, kv);
                                        ara = 1;
                                    }
                                } catch (Exception e) {
                                    //System.out.println("епросто пошёл нахуй  такая запись");
                                }
                                //System.out.println(i);

                                if ((i == 12) || (datePeriod2for.equals(dada)) || (ara == 1)) {
                                    //System.out.println("лох пидр");
                                    //System.out.println(i);
                                    break;
                                } else {
                                    //System.out.println("нахуя здесь ретурне?");
                                }
                            }
                        } else {

                            if (datePeriod2.equals(dada)) {
                                db.insert(DatabaseHelper.TABLE_MAIN, null, kv);
                                //System.out.println("оно блять работает");
                            }

                            //если дата равна дате конца
                            if (datendPeriod2.equals(dada) || datendPeriod2.equals(DateZero)) {
                                araar = 0;
                                ContentValues ktr = new ContentValues();
                                ktr.put(DatabaseHelper.CHECK_PAY, araar);
                                db.update(DatabaseHelper.TABLE_PAY, ktr, DatabaseHelper._ID + "=" + arara, null);
                            }
                        }
                        userCursorNewPeriod2.moveToNext();
                    }
                    userCursorNewPeriod2.close();
                } else {
                    userCursorNewPeriod2.close();
                }
                //период каджый меся конец
            } else {
                //System.out.println("нечего добавлять1");
                userCursorNewPay.close();
            }
            //условия без уведолмений конец
        } else {
            userCursorNewPay.close();
            //System.out.println("нечего добавлять");
            System.out.println("или кто-то просто криворукий еьоан");
        }
    }
    //условие добавления конец


    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Регулярный платёж " + notifName + " был добавлен")
                        .setContentText("Сумма: " + notifInt + " Категория: " + notifCat)
                        .setAutoCancel(false)
                        .setWhen(System.currentTimeMillis())
                        .setPriority(PRIORITY_HIGH);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFY_ID, builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    //функция выхода по двойному нажатию кнопки назад в ОС
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Нажмите еще раз, чтобы выйти.", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}