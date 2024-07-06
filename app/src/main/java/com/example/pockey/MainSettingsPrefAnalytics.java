package com.example.pockey;

import android.content.ContentValues;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainSettingsPrefAnalytics extends AppCompatActivity {

    //вывод в textview всех данных
    private TextView allRateInfo, allIncomeInfo, allBudgetInfo, dayLimitInfo, weekLimitInfo, allRateWeekInfo, allRateDayInfo, allRateMonthInfo;
    private TextView allRateDayMinMaxInfo, allRateWeekMinMaxInfo, allRateMonthMinMaxInfo;
    private TextView firstGraphNumber1Categ, firstGraphNumberInfo;
    private TextView allBDaddInfo, allBDadd1Info, allBDdeleteInfo, allBDrateInfo, allBDincomeInfo, allPop;

    private int AveMne2Count, AveMne3MaxSum1, AveMne3MaxSum3, AveMne4MaxSum4;
    private int day, month, year;
    private int cool, notcool, aqe;
    private int currentday, currentmonth, currentyear;
    private int item_sumIncome, item_sumRate, item_sumBudget;
    private String mmYM, mmMD, CurrentDateF, CurrentDateY;

    private HorizontalBarChart chart2;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;
    private String categ0;
    private float pert;

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    private com.github.mikephil.charting.charts.BarChart chart1;
    private Object MainSettingsPrefAnalytics;


    private LineChart chart3;
    private LineChart chart4;

    public MainSettingsPrefAnalytics() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_settings_pref_analytics);

        databaseHelper = new DatabaseHelper(getApplicationContext());


//        BarData data = new BarData((IBarDataSet) labels, dataset);
//        setData1(labels);

        //jopa второй график пока закрыт
//        chart2 = findViewById(R.id.chart2);
//
//        chart2.setDrawBarShadow(false);
//        chart2.setDrawValueAboveBar(true);
//        chart2.getDescription().setEnabled(false);
//
//        chart2.setMaxVisibleValueCount(60);
//
//        chart2.setPinchZoom(false);
//        chart2.setDrawGridBackground(false);
//        XAxis xl = chart2.getXAxis();
//        xl.setPosition(XAxisPosition.BOTTOM);
//        xl.setDrawAxisLine(true);
//        xl.setDrawGridLines(false);
//        xl.setGranularity(10f);
//        YAxis yl = chart2.getAxisLeft();
//        yl.setDrawAxisLine(true);
//        yl.setDrawGridLines(true);
//        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        YAxis yr = chart2.getAxisRight();
//        yr.setDrawAxisLine(true);
//        yr.setDrawGridLines(false);
//        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        chart2.setFitBars(true);
//        chart2.animateY(2500);
//        // setting data
//        Legend o = chart2.getLegend();
//        o.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        o.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        o.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        o.setDrawInside(false);
//        o.setFormSize(8f);
//        o.setXEntrySpace(4f);
//        setData2(5, 100);
        //jopa


        //начало кнопки назад
        Button buttonBack = findViewById(R.id.buttonback1);
        buttonBack.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainSettingsPrefAnalytics.this, MainSettingsPref.class);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {
            }
        });
        //конец кнопки назад
    }

    public void onResume() {
        super.onResume();

        db = databaseHelper.getReadableDatabase();

        allRateInfo = findViewById(R.id.allRateInfo);
        allIncomeInfo = findViewById(R.id.allIncomeInfo);
        allBudgetInfo = findViewById(R.id.allBudgetInfo);
        dayLimitInfo = findViewById(R.id.dayLimitInfo);
        weekLimitInfo = findViewById(R.id.weekLimitInfo);
        allRateWeekInfo = findViewById(R.id.allRateWeekInfo);
        allRateDayInfo = findViewById(R.id.allRateDayInfo);
        allRateMonthInfo = findViewById(R.id.allRateMonthInfo);
        allRateInfo = findViewById(R.id.allRateInfo);
        allRateDayMinMaxInfo = findViewById(R.id.allRateDayMinMaxInfo);
        allRateWeekMinMaxInfo = findViewById(R.id.allRateWeekMinMaxInfo);
        allRateMonthMinMaxInfo = findViewById(R.id.allRateMonthMinMaxInfo);
        allRateInfo = findViewById(R.id.allRateInfo);

        allBDaddInfo = findViewById(R.id.allBDaddInfo);
        allBDadd1Info = findViewById(R.id.allBDadd1Info);
        allBDdeleteInfo = findViewById(R.id.allBDdeleteInfo);
        allBDrateInfo = findViewById(R.id.allBDrateInfo);
        allBDincomeInfo = findViewById(R.id.allBDincomeInfo);
        allPop = findViewById(R.id.allPop);

        //все расходы
        Cursor AllRateCursor = db.rawQuery("select sum(sum) as sum FROM ftp_main where purpose = 'Расход'", null);
        AllRateCursor.moveToFirst();
        item_sumRate = AllRateCursor.getInt(AllRateCursor.getColumnIndex(DatabaseHelper.SUM));
//        String item_content = catCursor.getString(catCursor
//                .getColumnIndex(CatsDataBase.CATNAME));
        AllRateCursor.close();
        allRateInfo.setText("");
        allRateInfo.setText(allRateInfo.getText().toString() + " " + item_sumRate);
        //все доходы
        Cursor AllIncomeCursor = db.rawQuery("select sum(sum) as sum FROM ftp_main where purpose = 'Доход'", null);
        AllIncomeCursor.moveToFirst();
        item_sumIncome = AllIncomeCursor.getInt(AllIncomeCursor.getColumnIndex(DatabaseHelper.SUM));
        AllIncomeCursor.close();
        allIncomeInfo.setText("");
        allIncomeInfo.setText(allIncomeInfo.getText().toString() + " " + item_sumIncome);
        // весь бюджет

        item_sumBudget = item_sumIncome - item_sumRate;
        allBudgetInfo.setText("");
        allBudgetInfo.setText(allBudgetInfo.getText().toString() + " " + item_sumBudget);

        DateTimeFormatter dtfd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String dada = dtfd.format(now);
        String delimeter = "-"; // Разделитель
        String[] CurrentDate;
        CurrentDate = dada.split(delimeter);
        for (int i = 0; i < CurrentDate.length; i++) {
            currentyear = Integer.parseInt(CurrentDate[0]);
            currentmonth = Integer.parseInt(CurrentDate[1]);
            currentday = Integer.parseInt(CurrentDate[2]);
        }

        for (int y = currentyear; y + 1 > currentyear; y++) {
            //System.out.println("jopa" + y);
            int cool = +y;
            int o = +1;
            if (o == 2) {
                //System.out.println("pidor grazniy " + cool);
                //System.out.println("pidor nigger " + o);
            } else {
                //System.out.println("aaaaaaaa " + cool);
                //System.out.println("bbbbbb " + o);

            }
            if (currentmonth < 10) {
                mmYM = "-0";
            } else {
                mmYM = "-";
            }
            CurrentDateF = y + mmYM + currentmonth + "-01";
            CurrentDateY = y + mmYM + currentmonth + "-31";

            //выход цикла года
            if (y == currentyear) {
                break;
            }
        }
        try {
            Cursor CurrentAve = db.rawQuery("select sum(sum) as sum FROM ftp_main where date >= '" + CurrentDateF + "' and date <= '" + CurrentDateY + "'", null);
            if (CurrentAve.getCount() > 0) {
                //System.out.println("pidoras");
                CurrentAve.moveToFirst();
                int arara = CurrentAve.getInt(CurrentAve.getColumnIndex(DatabaseHelper.SUM));
                //System.out.println(arara); // расходы за этот месяц
                //System.out.println("penissssssssssssssssss");

            } else {
                int ara = 1;
            }
        } catch (Exception e) {
            //System.out.println("епросто пошёл нахуй  вот такая ошибка ожидал ? правильно нет и пошёл нахуй");
        }

//        String[] CurrentDate;
//        //CurrentDate = dada.split(delimeter);

        Cursor FirstDateCursor = db.rawQuery("select min(date) as date FROM ftp_main", null);
        FirstDateCursor.moveToFirst();
        String FirstDate = FirstDateCursor.getString(FirstDateCursor.getColumnIndex(DatabaseHelper.DATE));
        String[] FirstDateCurrent;
        FirstDateCurrent = FirstDate.split(delimeter);
        FirstDateCursor.close();

        for (int i = 0; i < FirstDateCurrent.length; i++) {
            year = Integer.parseInt(FirstDateCurrent[0]);
            month = Integer.parseInt(FirstDateCurrent[1]);
            day = Integer.parseInt(FirstDateCurrent[2]);
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
//                Cursor AveMne1 = db.rawQuery("select sum(" + DatabaseHelper.SUM + ") as sum FROM "+ DatabaseHelper.TABLE_MAIN
//                        +" where purpose = 'Расход' and date >= '" + datePeriod0for0
//                        + "' and date <= '" + datePeriod0for1 + "'", null);
                Cursor AveMne1 = db.rawQuery("select sum(sum) as sum FROM ftp_main where purpose = 'Расход' and date >= '" + datePeriod0for0 + "' and date <= '" + datePeriod0for1 + "'", null);
                if (AveMne1.getCount() > 0) {
                    AveMne1.moveToFirst();
                    int arara = AveMne1.getInt(AveMne1.getColumnIndex(DatabaseHelper.SUM));
                    cool += arara;
                    if (arara > 0) {
                        notcool += 1;
                    }
                } else {
                    int pol = +1;
                }
                AveMne1.close();
            } catch (Exception e) {
                System.out.println("ошибка в сумме расходов");
            }
            //выход цикла hgb
            if (q == currentmonth + 1) {
                break;
            }
        }
        allRateMonthInfo.setText("");
        allRateMonthInfo.setText(allRateMonthInfo.getText().toString() + " " + (cool / notcool)); //среднее значение трат в месяц
        allRateWeekInfo.setText("");
        allRateWeekInfo.setText(allRateWeekInfo.getText().toString() + " " + (cool / notcool) / 3); //среднее значение трат в неделю
        allRateDayInfo.setText("");
        allRateDayInfo.setText(allRateDayInfo.getText().toString() + " " + (cool / notcool) / 31); // среднее значение трат в день
        dayLimitInfo.setText("");
        dayLimitInfo.setText(dayLimitInfo.getText().toString() + " " + ((cool / notcool) / 31) * 2); // технически лимит в день по тратам
        weekLimitInfo.setText("");
        weekLimitInfo.setText(weekLimitInfo.getText().toString() + " " + Math.round(((cool / notcool) / 3) * (1.5))); // технически лимит в неделю  по тратам
        //средний расход в месяц по всем месяцам конец


        allRateDayMinMaxInfo.setText("");
        allRateDayMinMaxInfo.setText(allRateDayMinMaxInfo.getText().toString() + " " + Math.round(((cool / notcool) / 31) * (0.5)) + " / " + Math.round(((cool / notcool) / 31) * (1.4))); // технически
        //min/max трат в неделю средне
        allRateWeekMinMaxInfo.setText("");
        allRateWeekMinMaxInfo.setText(allRateWeekMinMaxInfo.getText().toString() + " " + Math.round((cool / notcool) * (0.3)) + " / " + Math.round((cool / notcool) * (0.7)));

        allRateMonthMinMaxInfo.setText("");
        allRateMonthMinMaxInfo.setText(allRateMonthMinMaxInfo.getText().toString() + " " + Math.round(((cool / notcool) * (0.8))) + " / " + Math.round((cool / notcool) * (1.3))); // технически лимит в неделю  по тратам

        Cursor AveMne1 = db.rawQuery("select max(_id) as _id from ftp_main", null);
        AveMne1.moveToFirst();
        int allDB = AveMne1.getInt(AveMne1.getColumnIndex(DatabaseHelper._ID));
        AveMne1.close();
        Cursor AveMne22 = db.rawQuery("select count(_id) as _id from ftp_main", null);
        AveMne22.moveToFirst();
        int allDB1 = AveMne22.getInt(AveMne22.getColumnIndex(DatabaseHelper._ID));
        AveMne22.close();

        allBDaddInfo.setText("");
        allBDaddInfo.setText(allBDaddInfo.getText().toString() + " " + allDB); // технически лимит в неделю  по тратам
        allBDadd1Info.setText("");
        allBDadd1Info.setText(allBDadd1Info.getText().toString() + " " + allDB1); // технически лимит в неделю  по тратам
        allBDdeleteInfo.setText("");
        allBDdeleteInfo.setText(allBDdeleteInfo.getText().toString() + " " + (allDB - allDB1)); // технически лимит в неделю  по тратам

        Cursor AveMne222 = db.rawQuery("select count(purpose) as _id from ftp_main where purpose = 'Расход'", null);
        AveMne222.moveToFirst();
        int allDB11 = AveMne222.getInt(AveMne222.getColumnIndex(DatabaseHelper._ID));
        AveMne222.close();
        allBDrateInfo.setText("");
        allBDrateInfo.setText(allBDrateInfo.getText().toString() + " " + allDB11); // технически лимит в неделю  по тратам

        Cursor AveMne2222 = db.rawQuery("select count(purpose) as _id from ftp_main where purpose = 'Доход'", null);
        AveMne2222.moveToFirst();
        int allDB111 = AveMne2222.getInt(AveMne2222.getColumnIndex(DatabaseHelper._ID));
        AveMne2222.close();
        allBDincomeInfo.setText("");
        allBDincomeInfo.setText(allBDincomeInfo.getText().toString() + " " + allDB111); // технически лимит в неделю  по тратам


        Cursor AveMneNGGYU = db.rawQuery("select sum(sum) as sum from ftp_main where purpose = 'Доход'", null);
        AveMneNGGYU.moveToFirst();
        int allDBNGGYU = AveMneNGGYU.getInt(AveMneNGGYU.getColumnIndex(DatabaseHelper.SUM));
        AveMneNGGYU.close();
        int a = 1;
        int b = 10;
        String aba = null;
        int rand = a + (int) (Math.random() * b); // Генерация 1-го числа
        if (rand == 1) {
            aba = "На всю вашу зарплату вы могли купить " + (allDBNGGYU / 80) + " проездок на автобусе.";
        } else if (rand == 2) {
            aba = "Вы могли купить " + (allDBNGGYU / 1100) + " билетов в кино.";
        } else if (rand == 3) {
            aba = "Вы заработали " + (allDBNGGYU) + " во время использования приложения Pockey.";
        } else if (rand == 4) {
            aba = "Вы можете внести на депозит " + (allDBNGGYU / 10) + ".";
        } else if (rand > 4) {
            aba = "Вы могли купить  " + (allDBNGGYU / 150) + " бутылок с водой объемом 1 литр.";
        }
        allPop.setText("");
        allPop.setText(allPop.getText().toString() + " " + aba); // технически лимит в неделю  по тратам


        allPop = findViewById(R.id.allPop);


        try {
            Cursor AveMne2 = db.rawQuery("select category from ftp_main where purpose = 'Расход' group by category order by count(category) desc", null);
            if (AveMne2.getCount() > 0) {
                AveMne2Count = AveMne2.getCount();

                Cursor AveMne3MaxSum = db.rawQuery("select max(sum) as sum  from ftp_main where purpose = 'Расход'", null);
                AveMne3MaxSum.moveToFirst();
                AveMne3MaxSum1 = AveMne3MaxSum.getInt(AveMne3MaxSum.getColumnIndex(DatabaseHelper.SUM));

                chart1 = findViewById(R.id.chart1);
                chart1.setDrawBarShadow(false);
                chart1.setDrawValueAboveBar(true);
                chart1.getDescription().setEnabled(false);
                chart1.setMaxVisibleValueCount(60);
                chart1.setPinchZoom(false);
                chart1.setDrawGridBackground(true);
                Legend l = chart1.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                l.setDrawInside(false);
                l.setForm(Legend.LegendForm.SQUARE);
                l.setFormSize(9f);
                l.setTextSize(11f);
                l.setXEntrySpace(4f);
                //количество столбцов и их ограничение
                setData1(AveMne2Count, AveMne3MaxSum1);

                //                cool += arara;
//                if (arara > 0) {
//                    notcool += 1;
//                }
//
//                //System.out.println("cool = " + cool);
//                //System.out.println("notcool = " + notcool);
            } else {
                int pol = +1;
            }
        } catch (Exception e) {
            //System.out.println("епросто пошёл нахуй  вот такая ошибка ожидал ? правильно нет и пошёл нахуй");
        }

        try {

            if (currentmonth < 10) {
                mmYM = "-0";
            } else {
                mmYM = "-";
            }
            CurrentDateF = currentyear + mmYM + currentmonth + "-01";
            CurrentDateY = currentyear + mmYM + currentmonth + "-31";
            Cursor AveMne3MaxSum = db.rawQuery("select sum(sum) as sum FROM ftp_main where  purpose = 'Расход' and date >= '" + CurrentDateF + "' and date <= '" + CurrentDateY + "'", null);
            AveMne3MaxSum.moveToFirst();
            AveMne3MaxSum3 = AveMne3MaxSum.getInt(AveMne3MaxSum.getColumnIndex(DatabaseHelper.SUM));
            AveMne3MaxSum.close();
            Cursor AveMne4MaxSum = db.rawQuery("select sum(sum) as sum FROM ftp_main where  purpose = 'Доход' and date >= '" + CurrentDateF + "' and date <= '" + CurrentDateY + "'", null);
            AveMne4MaxSum.moveToFirst();
            AveMne4MaxSum4 = AveMne4MaxSum.getInt(AveMne4MaxSum.getColumnIndex(DatabaseHelper.SUM));
            AveMne3MaxSum.close();
            System.out.println("что может пойти не так? = " + AveMne3MaxSum3);
            System.out.println("что может пойти не так? = " + AveMne4MaxSum4);

            for (int i = 1; i < currentmonth + 1; i++) {

                if (i < 10) {
                    mmYM = "-0";
                } else {
                    mmYM = "-";
                }
                String datePeriod0for0 = currentyear + mmYM + i + "-01";
                String datePeriod0for1 = currentyear + mmYM + i + "-31";

                try {
                    Cursor AveMneOpa = db.rawQuery("select sum(sum) as sum FROM ftp_main where purpose = 'Расход' and date >= '" + datePeriod0for0 + "' and date <= '" + datePeriod0for1 + "'", null);
                    if (AveMneOpa.getCount() > 0) {
                        AveMneOpa.moveToFirst();
                        int dio;
                        dio = AveMneOpa.getInt(AveMneOpa.getColumnIndex(DatabaseHelper.SUM));
                        if (dio > AveMne3MaxSum3) {
                            AveMne3MaxSum3 = dio;
                        }
                    }

                    Cursor AveMneOpa1 = db.rawQuery("select sum(sum) as sum FROM ftp_main where purpose = 'Доход' and date >= '" + datePeriod0for0 + "' and date <= '" + datePeriod0for1 + "'", null);
                    if (AveMneOpa1.getCount() > 0) {
                        AveMneOpa1.moveToFirst();
                        int dio1;
                        dio1 = AveMneOpa1.getInt(AveMneOpa1.getColumnIndex(DatabaseHelper.SUM));
                        if (dio1 > AveMne4MaxSum4) {
                            AveMne4MaxSum4 = dio1;
                            System.out.println(" первый пошёл? = " + AveMne4MaxSum4);

                        }
                        if (AveMne4MaxSum4 < AveMne3MaxSum3) {
                            AveMne4MaxSum4 = AveMne3MaxSum3;
                        }
                    }
//                    if (AveMne4MaxSum4> AveMne3MaxSum3){
//                        System.out.println("хуй первый пошёл? = " + AveMne4MaxSum4);
//                        System.out.println("хуй второй пошёл? = " + AveMne3MaxSum3);
//
//                        //AveMne4MaxSum4=AveMne3MaxSum3;
//                    }
                    //System.out.println("что может пойти не так? = " + arara);


                } catch (Exception e) {
                    //System.out.println("епросто пошёл нахуй  вот такая ошибка ожидал ? правильно нет и пошёл нахуй");
                }
                //выход цикла hgb
                if (i == currentmonth + 1) {
                    break;
                }
                //средний расход в месяц по всем месяцам конец

            }

        } catch (Exception ignored) {
        }

        //график номер 3 начало
        {   // // Chart Style // //
            chart3 = findViewById(R.id.chart8);
            chart3.setBackgroundColor(Color.WHITE);
            chart3.getDescription().setEnabled(false);
            chart3.setTouchEnabled(true);
            chart3.setDrawGridBackground(false);
            chart3.setDragEnabled(true);
            chart3.setScaleEnabled(true);
            chart3.setPinchZoom(true);
        }
        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart3.getXAxis();
            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }
        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart3.getAxisLeft();
            // disable dual axis (only use LEFT axis)
            chart3.getAxisRight().setEnabled(false);
            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);
            // axis range
            yAxis.setAxisMaximum(Math.round(AveMne3MaxSum3 * 1.5));
            yAxis.setAxisMinimum(0f);
        }
        {
            // // создание линий ограничения // //
            LimitLine llXAxis = new LimitLine(9f, "Index 10");
            llXAxis.setLineWidth(4f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);
            Typeface tfRegular = null;
            llXAxis.setTypeface(tfRegular);
            LimitLine ll1 = new LimitLine(AveMne3MaxSum3, "Верхний лимит");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(15f, 15f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
            ll1.setTextSize(10f);
            ll1.setTypeface(tfRegular);
            LimitLine ll2 = new LimitLine(0f, "Нижний лимит");
            ll2.setLineWidth(4f);
            ll2.enableDashedLine(10f, 10f, 0f);
            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll2.setTextSize(10f);
            ll2.setTypeface(tfRegular);
            yAxis.setDrawLimitLinesBehindData(false);
            xAxis.setDrawLimitLinesBehindData(false);
            yAxis.addLimitLine(ll1);
            yAxis.addLimitLine(ll2);
        }
        setData3(currentmonth, AveMne3MaxSum3);
        chart3.animateX(1500);
        Legend l = chart3.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        //график номер 3 конец

        //график номер 4 начало


        chart4 = findViewById(R.id.chart9);

        chart4.setBackgroundColor(Color.WHITE);

        // no description text
        chart4.getDescription().setEnabled(false);

        // enable touch gestures
        chart4.setTouchEnabled(true);

        chart4.setDragDecelerationFrictionCoef(1f);

        // enable scaling and dragging
        chart4.setDragEnabled(true);
        chart4.setScaleEnabled(true);
        chart4.setDrawGridBackground(false);
        chart4.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart4.setPinchZoom(false);

        // set an alternative background color

        setData4(currentmonth, AveMne4MaxSum4);
        System.out.println(" проверка 1 " + AveMne4MaxSum4);
        chart4.animateX(1500);

        // get the legend (only possible after setting data)
        Legend p = chart4.getLegend();

        // modify the legend ...
        p.setForm(Legend.LegendForm.LINE);
        Typeface tfLight = null;
        p.setTypeface(tfLight);
        p.setTextSize(11f);
        p.setTextColor(Color.BLACK);
        p.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        p.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        p.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        p.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis4 = chart4.getXAxis();
        xAxis4.setTypeface(tfLight);
        xAxis4.setTextSize(11f);
        xAxis4.setTextColor(Color.BLACK);
        xAxis4.setAxisMaximum(currentmonth);
        xAxis4.setAxisMinimum(1);
        xAxis4.setDrawGridLines(false);
        xAxis4.setDrawAxisLine(false);

        YAxis leftAxis4 = chart4.getAxisLeft();
        leftAxis4.setTypeface(tfLight);
        leftAxis4.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis4.setAxisMaximum(Math.round(AveMne4MaxSum4 * 1.5));
        leftAxis4.setAxisMinimum(0);
        leftAxis4.setDrawGridLines(true);
        leftAxis4.setGranularityEnabled(true);

        YAxis rightAxis = chart4.getAxisRight();
        rightAxis.setTypeface(tfLight);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(Math.round(AveMne4MaxSum4 * 1.5));
        rightAxis.setAxisMinimum(0);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        //график номер 4 конец


        //System.out.println("хуйня для проверки месяца");
        for (int y = year; y + 1 > year; y++) {
            //System.out.println("jopa" + y);
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
            String datePeriod0for0 = y + mmYM + month + "-01";
            String datePeriod0for1 = y + mmYM + month + "-31";
            //System.out.println(datePeriod0for0);
            //System.out.println(datePeriod0for1);
            //System.out.println("хз какая-то дата");

            try {
                Cursor AveMne = db.rawQuery("select sum(sum) as sum FROM ftp_main where date > '" + datePeriod0for0 + "' and date < '" + datePeriod0for1 + "'", null);
                if (AveMne.getCount() > 0) {
                    //System.out.println("pidor");
                    AveMne.moveToFirst();
                    int arara = AveMne.getInt(AveMne.getColumnIndex(DatabaseHelper.SUM));
                    //System.out.println(arara);
                    //System.out.println("penis");

                } else {


                    int ara = 1;
                }
            } catch (Exception e) {
                //System.out.println("епросто пошёл нахуй  вот такая ошибка ожидал ? правильно нет и пошёл нахуй");
            }

            //выход цикла года
            if (y == currentyear) {
                break;
            }
        }

//        for (int m = month; m + 1 > month; m++){
//            //System.out.println("jojo" + m);
//
//            for (int d = day; d + 1 > day; d++) {
//                //System.out.println("dio" + d);
//
//                //выход цикла дня
//                if ((d == currentday) || (d == 31)) {
//                    break;
//                }
//            }
//
//            //выход цикла месяца
//            if ((m == currentmonth) || ( m == 12)) {break;}
//        }

//            for (int d = day; d + 1 > day; d++) {
//                //System.out.println(day);
//                //System.out.println(d);
//                //System.out.println("хуйня для проверки дня");
//                if (month < 10) {
//                    mmYM = "-0";
//                } else {
//                    mmYM = "-";
//                }
//                if (day < 10) {
//                    mmMD = "-0";
//                } else {
//                    mmMD = "-";
//                }
//                String datePeriod0for = year + mmYM + month + mmMD + day;
//                //System.out.println(datePeriod0for);
//                //System.out.println("хз какая-то дата");
////                //try {
////                    userCursorAveMaria = db.rawQuery("select * FROM ftp_main where category = '" + categoryPeriod0 + "'and purpose = '" + purposePeriod0 + "'and sum = " + sumPeriod0 + " and date = '" + datePeriod0for + "'", null);
////                    if (userCursorAveMaria.getCount() > 0) {
////                        ////System.out.println("если в мейне есть такая запись");
////                        ////System.out.println(datePeriod0for);
////                        araar = 0;
////                        ContentValues ktr = new ContentValues();
////                        ktr.put(DatabaseHelper.CHECK_PAY, araar);
////                        db.update(DatabaseHelper.TABLE_PAY, ktr, DatabaseHelper._ID + "=" + arara, null);
////                    } else {
////                        kv.put(DatabaseHelper.DATE, datePeriod0for);
////                        db.insert(DatabaseHelper.TABLE_MAIN, null, kv);
////                        ara = 1;
////                    }
////                } catch (Exception e) {
//                    ////System.out.println("епросто пошёл нахуй  такая запись");
//            }
//
//                ////System.out.println(i);
//            if (month == 12){break;}
////                if ((i == 31) || (datePeriod0for.equals(dada)) || (ara == 1)) {
////                    ////System.out.println("лох пидр");
////                    ////System.out.println(i);
////                    break;
////                } else {
////                    ////System.out.println("нахуя здесь ретурне?");
////                }
//        }
    }


    private void setData1(int count, int range) {

        int start = 1;
        ArrayList<BarEntry> values = new ArrayList<>();
        try {
            Cursor AveMne2 = db.rawQuery("select category, count(category) as co, sum(sum) as sum from ftp_main where purpose = 'Расход' group by category order by count(category) desc", null);
            AveMne2.moveToFirst();
            int arara_sum_0 = AveMne2.getInt(AveMne2.getColumnIndex(DatabaseHelper.SUM));
            values.add(new BarEntry(0, arara_sum_0));
            categ0 = AveMne2.getString(AveMne2.getColumnIndex(DatabaseHelper.CATEGORY));

            Cursor AveMneop = db.rawQuery("select sum(sum) as sum  from ftp_main where purpose = 'Расход'", null);
            AveMneop.moveToFirst();
            int sumBooss = AveMneop.getInt(AveMneop.getColumnIndex(DatabaseHelper.SUM));
            float biba = arara_sum_0;
            float boba = sumBooss;
            pert = Math.round((biba / boba) * 100);
            firstGraphNumber1Categ = findViewById(R.id.firstGraphNumber1Categ);
            firstGraphNumber1Categ.setText("");
            firstGraphNumber1Categ.setText(firstGraphNumber1Categ.getText().toString() + " " + categ0 + " " + pert + "%");

            //System.out.println(categ1[0]);
            for (int i = start; i < start + count; i++) {
                //здесь менять значения
                float val = (float) (Math.random() * (range + 1));
                int jojoret = (int) (Math.random() * 100);
                //System.out.println("fffffffffffffffffffffff " + jojoret);
                if (count == 1) {
                    LinearLayout firstGraphNumberInfo = findViewById(R.id.line1firstGraph2);
                    firstGraphNumberInfo.setVisibility(View.GONE);
                    firstGraphNumberInfo = findViewById(R.id.line2firstGraph);
                    firstGraphNumberInfo.setVisibility(View.GONE);
                    firstGraphNumberInfo = findViewById(R.id.line3firstGraph);
                    firstGraphNumberInfo.setVisibility(View.GONE);
                    firstGraphNumberInfo = findViewById(R.id.line4firstGraph);
                    firstGraphNumberInfo.setVisibility(View.GONE);
                    firstGraphNumberInfo = findViewById(R.id.line5firstGraph);
                    firstGraphNumberInfo.setVisibility(View.GONE);
                }
                if (AveMne2.moveToNext()) {
                    System.out.println("жопа3");
                    int poka;
                    poka = AveMne2.getInt(AveMne2.getColumnIndex(DatabaseHelper.SUM));
                    categ0 = AveMne2.getString(AveMne2.getColumnIndex(DatabaseHelper.CATEGORY));
                    //алгоритм вывода информации категорий под первым графиком  лучше не смотреть
                    biba = poka;
                    pert = Math.round((biba / boba) * 100);
                    if (i == 1) {
                        firstGraphNumber1Categ = findViewById(R.id.firstGraphNumber2Categ);
                        firstGraphNumber1Categ.setText("");
                        firstGraphNumber1Categ.setText(firstGraphNumber1Categ.getText().toString() + " " + categ0 + " " + pert + "%");
                    } else if (i == 2) {
                        firstGraphNumber1Categ = findViewById(R.id.firstGraphNumber3Categ);
                        firstGraphNumber1Categ.setText("");
                        firstGraphNumber1Categ.setText(firstGraphNumber1Categ.getText().toString() + " " + categ0 + " " + pert + "%");
                    } else if (i == 3) {
                        firstGraphNumber1Categ = findViewById(R.id.firstGraphNumber4Categ);
                        firstGraphNumber1Categ.setText("");
                        firstGraphNumber1Categ.setText(firstGraphNumber1Categ.getText().toString() + " " + categ0 + " " + pert + "%");
                    } else if (i == 4) {
                        firstGraphNumber1Categ = findViewById(R.id.firstGraphNumber5Categ);
                        firstGraphNumber1Categ.setText("");
                        firstGraphNumber1Categ.setText(firstGraphNumber1Categ.getText().toString() + " " + categ0 + " " + pert + "%");
                    } else if (i == 5) {
                        firstGraphNumber1Categ = findViewById(R.id.firstGraphNumber6Categ);
                        firstGraphNumber1Categ.setText("");
                        firstGraphNumber1Categ.setText(firstGraphNumber1Categ.getText().toString() + " " + categ0 + " " + pert + "%");
                    } else if (i == 6) {
                        firstGraphNumber1Categ = findViewById(R.id.firstGraphNumber7Categ);
                        firstGraphNumber1Categ.setText("");
                        firstGraphNumber1Categ.setText(firstGraphNumber1Categ.getText().toString() + " " + categ0 + " " + pert + "%");
                    } else if (i == 7) {
                        firstGraphNumber1Categ = findViewById(R.id.firstGraphNumber8Categ);
                        firstGraphNumber1Categ.setText("");
                        firstGraphNumber1Categ.setText(firstGraphNumber1Categ.getText().toString() + " " + categ0 + " " + pert + "%");
                    } else if (i == 8) {
                        firstGraphNumber1Categ = findViewById(R.id.firstGraphNumber9Categ);
                        firstGraphNumber1Categ.setText("");
                        firstGraphNumber1Categ.setText(firstGraphNumber1Categ.getText().toString() + " " + categ0 + " " + pert + "%");
                    } else if (i == 9) {
                        firstGraphNumber1Categ = findViewById(R.id.firstGraphNumber10Categ);
                        firstGraphNumber1Categ.setText("");
                        firstGraphNumber1Categ.setText(firstGraphNumber1Categ.getText().toString() + " " + categ0 + " " + pert + "%");
                    }
                    //System.out.println("1337" + count);
                    System.out.println("жопа4");

                    if (count == 2) {
                        System.out.println("жопа[eq " + count);

                        //System.out.println("1337+1337");
                        LinearLayout firstGraphNumberInfo = findViewById(R.id.line2firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                        firstGraphNumberInfo = findViewById(R.id.line3firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                        firstGraphNumberInfo = findViewById(R.id.line4firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                        firstGraphNumberInfo = findViewById(R.id.line5firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                    } else if (count == 3) {
                        LinearLayout firstGraphNumberInfo = findViewById(R.id.line2firstGraph2);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                        firstGraphNumberInfo = findViewById(R.id.line3firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                        firstGraphNumberInfo = findViewById(R.id.line4firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                        firstGraphNumberInfo = findViewById(R.id.line5firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                    } else if (count == 4) {
                        LinearLayout firstGraphNumberInfo = findViewById(R.id.line3firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                        firstGraphNumberInfo = findViewById(R.id.line4firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                        firstGraphNumberInfo = findViewById(R.id.line5firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);

                    } else if (count == 5) {
                        LinearLayout firstGraphNumberInfo = findViewById(R.id.line3firstGraph2);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                        firstGraphNumberInfo = findViewById(R.id.line4firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                        firstGraphNumberInfo = findViewById(R.id.line5firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                    } else if (count == 6) {
                        LinearLayout firstGraphNumberInfo = findViewById(R.id.line4firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                        firstGraphNumberInfo = findViewById(R.id.line5firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                    } else if (count == 7) {
                        LinearLayout firstGraphNumberInfo = findViewById(R.id.line4firstGraph2);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                        firstGraphNumberInfo = findViewById(R.id.line5firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                    } else if (count == 8) {
                        LinearLayout firstGraphNumberInfo = findViewById(R.id.line5firstGraph);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                    } else if (count == 9) {
                        LinearLayout firstGraphNumberInfo = findViewById(R.id.line5firstGraph2);
                        firstGraphNumberInfo.setVisibility(View.GONE);
                    }

                    if (jojoret < 50) {
                        values.add(new BarEntry(i, poka));
                        //System.out.println("что эта хцуйня делает?");
                        //System.out.println(i + " " + poka);
                        ////System.out.println(i + " " + categ1[i]);
                    } else {
                        values.add(new BarEntry(i, poka));
                        //System.out.println("а это вообще что?");
                        //System.out.println(i + " " + poka);
                        ////System.out.println(i + " " + categ1[i]);
                    }
                }

            }
        } catch (Exception e) {
            //System.out.println("ehEHHEHEEHEHEEHEHEHEEHEHHEE");
        }

//        try {
//            Cursor AveMne2 = db.rawQuery("select category, count(category) as co, sum(sum) as sum  from ftp_main where purpose = 'Расход' group by category order by count(category) desc", null);
//            if (AveMne2.getCount() > 0) {
//
//                Cursor AveMne3MaxSum = db.rawQuery("select max(sum) as sum  from ftp_main where purpose = 'Расход'", null);
//                AveMne3MaxSum.moveToFirst();
//                AveMne3MaxSum1 = AveMne3MaxSum.getInt(AveMne3MaxSum.getColumnIndex(DatabaseHelper.SUM));
//
//                AveMne2Count = AveMne2.getCount();
//                AveMne2.moveToFirst();
//                String arara_categ_0 = AveMne2.getString(AveMne2.getColumnIndex(DatabaseHelper.CATEGORY));
//                int arara_sum_0 = AveMne2.getInt(AveMne2.getColumnIndex(DatabaseHelper.SUM));
//                int arara_co_0 = AveMne2.getInt(AveMne2.getColumnIndex("co"));
//                //System.out.println("==================================================");
//                //System.out.println("категория = " + arara_categ_0);
//                //System.out.println("сумма = " + arara_sum_0);
//                //System.out.println("их количество = " + arara_co_0);
//                //System.out.println(cool * 0.5);
//                //System.out.println("==================================================");
//
//                for (int w = 1; w < AveMne2.getCount(); w++) {
//                    AveMne2.moveToNext();
//                    if(w==1){
//                        //сюда еще трай закинуть
//                        String arara_categ_1 = AveMne2.getString(AveMne2.getColumnIndex(DatabaseHelper.CATEGORY));
//                        int arara_sum_1 = AveMne2.getInt(AveMne2.getColumnIndex(DatabaseHelper.SUM));
//                        int arara_co_1 = AveMne2.getInt(AveMne2.getColumnIndex("co"));
//                        //System.out.println("категория = " + arara_categ_1);
//                        //System.out.println("сумма = " + arara_sum_1);
//                        //System.out.println("их количество = " + arara_co_1);
//                        //System.out.println("==================================================");
//
//                    }
//                    else if(w==2){
//                        String arara_categ_2 = AveMne2.getString(AveMne2.getColumnIndex(DatabaseHelper.CATEGORY));
//                        int arara_sum_2 = AveMne2.getInt(AveMne2.getColumnIndex(DatabaseHelper.SUM));
//                        int arara_co_2 = AveMne2.getInt(AveMne2.getColumnIndex("co"));
//                        //System.out.println("категория = " + arara_categ_2);
//                        //System.out.println("сумма = " + arara_sum_2);
//                        //System.out.println("их количество = " + arara_co_2);
//                        //System.out.println("==================================================");
//
//                    }
//                    else if(w==3){
//                        String arara_categ_3 = AveMne2.getString(AveMne2.getColumnIndex(DatabaseHelper.CATEGORY));
//                        int arara_sum_3 = AveMne2.getInt(AveMne2.getColumnIndex(DatabaseHelper.SUM));
//                        int arara_co_3 = AveMne2.getInt(AveMne2.getColumnIndex("co"));
//                        //System.out.println("категория = " + arara_categ_3);
//                        //System.out.println("сумма = " + arara_sum_3);
//                        //System.out.println("их количество = " + arara_co_3);
//                        //System.out.println("==================================================");
//
//                    }
//                    else if(w==4){}
//                    else if(w==5){}
//                    else if(w>5){               }
//
//                }
//
//            } else {
//                int pol = +1;
//            }
//        } catch (Exception e) {
//            //System.out.println("ХАХАХАХХААХАХАХАХАХХАХАХАХААХАХАХАХАХААХАХХААХХААХХАХАХАХАХАХА");
//        }


//        for (int i = (int) start; i < start + count; i++) {
//            //здесь менять значения
//            float val = (float) (Math.random() * (range + 1));
//            int val1 = (int) (Math.random() * (range + 1));
//
//            if (Math.random() * 100 < 25) {
//                values.add(new BarEntry(i, val));
//                //System.out.println("что эта хцуйня делает?");
//                //System.out.println(i+" "+val);
//            } else {
//                values.add(new BarEntry(i, val));
//                //System.out.println("а это вообще что?");
//                //System.out.println(i+" "+val);
//            }
//        }

//***************************************************************************
        BarDataSet set1;
        BarDataSet set2, set3, set4, set5;

        if (chart1.getData() != null &&
                chart1.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart1.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart1.getData().notifyDataChanged();
            chart1.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, " ");
            set1.setDrawIcons(false);
//            int startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
//            int startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
//            int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
//            int startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light);
//            int startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light);
//            int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
//            int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
//            int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
//            int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
//            int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);
//

            int startColor1 = ContextCompat.getColor(this, R.color.crimson);
            int startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(this, R.color.greenyellow);
            int startColor5 = ContextCompat.getColor(this, R.color.purple_500);
            int startColor6 = ContextCompat.getColor(this, R.color.teal_200);
            int startColor7 = ContextCompat.getColor(this, R.color.orange);
            int startColor8 = ContextCompat.getColor(this, R.color.red);
            int startColor9 = ContextCompat.getColor(this, R.color.mediumblue);
            int startColor10 = ContextCompat.getColor(this, R.color.pink);
            int endColor1 = ContextCompat.getColor(this, R.color.red);
            int endColor2 = ContextCompat.getColor(this, R.color.blue);
            int endColor3 = ContextCompat.getColor(this, R.color.yellow);
            int endColor4 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
            int endColor5 = ContextCompat.getColor(this, R.color.purple_200);
            int endColor6 = ContextCompat.getColor(this, R.color.teal_700);
            int endColor7 = ContextCompat.getColor(this, R.color.darkorange);
            int endColor8 = ContextCompat.getColor(this, R.color.crimson);
            int endColor9 = ContextCompat.getColor(this, R.color.blue);
            int endColor10 = ContextCompat.getColor(this, R.color.deeppink);

            List<GradientColor> gradientFills = new ArrayList<>();
            gradientFills.add(new GradientColor(startColor1, endColor1));
            gradientFills.add(new GradientColor(startColor2, endColor2));
            gradientFills.add(new GradientColor(startColor3, endColor3));
            gradientFills.add(new GradientColor(startColor4, endColor4));
            gradientFills.add(new GradientColor(startColor5, endColor5));
            gradientFills.add(new GradientColor(startColor6, endColor6));
            gradientFills.add(new GradientColor(startColor7, endColor7));
            gradientFills.add(new GradientColor(startColor8, endColor8));
            gradientFills.add(new GradientColor(startColor9, endColor9));
            gradientFills.add(new GradientColor(startColor10, endColor10));

            set1.setGradientColors(gradientFills);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            chart1.setData(data);
        }
    }

//    private void setData2(int count, float range) {
//
//        float barWidth = 9f;
//        float spaceForBar = 10f;
//        ArrayList<BarEntry> values = new ArrayList<>();
//
//        for (int i = 0; i < count; i++) {
//            float val = (float) (Math.random() * range);
//            values.add(new BarEntry(i * spaceForBar, val,
//                    getResources().getDrawable(R.drawable.ic_launcher_foreground)));
//        }
//
//        BarDataSet set1,set2,set3;
//
//        if (chart2.getData() != null &&
//                chart2.getData().getDataSetCount() > 0) {
//            set1 = (BarDataSet) chart2.getData().getDataSetByIndex(0);
//            set1.setValues(values);
//            chart2.getData().notifyDataChanged();
//            chart2.notifyDataSetChanged();
//        } else {
//            set1 = new BarDataSet(values, "DataSet 1");
//            set2 = new BarDataSet(values, "DataSet 2");
//            set3 = new BarDataSet(values, "DataSet 3");
//
//            set1.setDrawIcons(false);
//            set2.setDrawIcons(false);
//            set3.setDrawIcons(false);
//
//            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//            dataSets.add(set1);
//            dataSets.add(set2);
//            dataSets.add(set3);
//
//            BarData data = new BarData(dataSets);
//            data.setValueTextSize(10f);
//            //data.setValueTypeface(tfLight);
//            data.setBarWidth(barWidth);
//            chart2.setData(data);
//        }
//    }

    private void setData3(int count, float range) {

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 1; i < count + 1; i++) {

            //float val = (float) (Math.random() * range) - 30;
            //values2.add(new Entry(i, val, getResources().getDrawable(R.drawable.ic_launcher_foreground)));

            //средний расход в месяц по всем месяцам начало
            //System.out.println("jopa" + i);
            if (i < 10) {
                mmYM = "-0";
            } else {
                mmYM = "-";
            }
            String datePeriod0for0 = currentyear + mmYM + i + "-01";
            String datePeriod0for1 = currentyear + mmYM + i + "-31";

            try {
                Cursor AveMne1 = db.rawQuery("select sum(sum) as sum FROM ftp_main where purpose = 'Расход' and date >= '" + datePeriod0for0 + "' and date <= '" + datePeriod0for1 + "'", null);
                if (AveMne1.getCount() > 0) {
                    AveMne1.moveToFirst();
                    int arara;
                    arara = AveMne1.getInt(AveMne1.getColumnIndex(DatabaseHelper.SUM));

                    values2.add(new BarEntry(i, arara));
                    //System.out.println("что может пойти не так? = " + arara);
                } else {
                    int pol = +1;
                }
            } catch (Exception e) {
                //System.out.println("епросто пошёл нахуй  вот такая ошибка ожидал ? правильно нет и пошёл нахуй");
            }

            //выход цикла hgb
            if (i == currentmonth + 1) {
                break;
            }
            //средний расход в месяц по всем месяцам конец

        }

        LineDataSet set1;

        if (chart3.getData() != null &&
                chart3.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart3.getData().getDataSetByIndex(0);
            set1.setValues(values2);
            set1.notifyDataSetChanged();
            chart3.getData().notifyDataChanged();
            chart3.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values2, "Начало - Январь --- " + AveMne3MaxSum3);

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart3.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.common_google_signin_btn_icon_dark);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart3.setData(data);
        }
    }

    private void setData4(int count, float range) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 1; i < count + 1; i++) {
            //float val = (float) (Math.random() * (range / 2f)) + 50;
            //values1.add(new Entry(i, val));
            {

                //float val = (float) (Math.random() * range) - 30;
                //values2.add(new Entry(i, val, getResources().getDrawable(R.drawable.ic_launcher_foreground)));

                //средний расход в месяц по всем месяцам начало
                //System.out.println("jopa" + i);
                if (i < 10) {
                    mmYM = "-0";
                } else {
                    mmYM = "-";
                }
                String datePeriod0for0 = currentyear + mmYM + i + "-01";
                String datePeriod0for1 = currentyear + mmYM + i + "-31";

                try {
                    Cursor AveMne1 = db.rawQuery("select sum(sum) as sum FROM ftp_main where purpose = 'Расход' and date >= '" + datePeriod0for0 + "' and date <= '" + datePeriod0for1 + "'", null);
                    if (AveMne1.getCount() > 0) {
                        AveMne1.moveToFirst();
                        int arara;
                        arara = AveMne1.getInt(AveMne1.getColumnIndex(DatabaseHelper.SUM));

                        values1.add(new BarEntry(i, arara));
                        //System.out.println("что может пойти не так? = " + arara);
                    } else {
                        int pol = +1;
                    }
                } catch (Exception e) {
                    //System.out.println("епросто пошёл нахуй  вот такая ошибка ожидал ? правильно нет и пошёл нахуй");
                }

                //выход цикла hgb
                if (i == currentmonth + 1) {
                    break;
                }
                //средний расход в месяц по всем месяцам конец

            }
        }

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 1; i < count + 1; i++) {
//            float val = (float) (Math.random() * range) + 450;
//            values2.add(new Entry(i, val));
            {

                //float val = (float) (Math.random() * range) - 30;
                //values2.add(new Entry(i, val, getResources().getDrawable(R.drawable.ic_launcher_foreground)));

                //средний расход в месяц по всем месяцам начало
                //System.out.println("jopa" + i);
                if (i < 10) {
                    mmYM = "-0";
                } else {
                    mmYM = "-";
                }
                String datePeriod0for0 = currentyear + mmYM + i + "-01";
                String datePeriod0for1 = currentyear + mmYM + i + "-31";

                try {
                    Cursor AveMne1 = db.rawQuery("select sum(sum) as sum FROM ftp_main where purpose = 'Доход' and date >= '" + datePeriod0for0 + "' and date <= '" + datePeriod0for1 + "'", null);
                    if (AveMne1.getCount() > 0) {
                        AveMne1.moveToFirst();
                        int arara;
                        arara = AveMne1.getInt(AveMne1.getColumnIndex(DatabaseHelper.SUM));

                        values2.add(new BarEntry(i, arara));
                        //System.out.println("что может пойти не так? = " + arara);
                    } else {
                        int pol = +1;
                    }
                } catch (Exception e) {
                    //System.out.println("епросто пошёл нахуй  вот такая ошибка ожидал ? правильно нет и пошёл нахуй");
                }

                //выход цикла hgb
                if (i == currentmonth + 1) {
                    break;
                }
                //средний расход в месяц по всем месяцам конец

            }
        }


        LineDataSet set1, set2;

        if (chart4.getData() != null &&
                chart4.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart4.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) chart4.getData().getDataSetByIndex(1);
            set1.setValues(values1);
            set2.setValues(values2);
            chart4.getData().notifyDataChanged();
            chart4.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "Расход ");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(Color.RED);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(Color.RED);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(values2, "Доход ");
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(ColorTemplate.getHoloBlue());
            set2.setCircleColor(Color.BLACK);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(ColorTemplate.getHoloBlue());
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            //set2.setFillFormatter(new MyFillFormatter(900f));


            // create a data object with the data sets
            LineData data = new LineData(set1, set2);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);

            // set data
            chart4.setData(data);
        }
    }

    //системная кнопка
    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(this, MainSettingsPref.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
    }
}

