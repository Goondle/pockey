package com.example.pockey;


import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.media.AudioRecord;
import android.view.inspector.StaticInspectionCompanionProvider;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "fdbp.db";                  //название таблицы
    public static final int DB_version = 1;                  //название таблицы
    //public static final String TABLE_NAME = "ftp";                  //название таблицы
    public static final String TABLE_MAIN = "ftp_main";                  //название таблицы основной
    public static final String TABLE_COMMENT = "ftp_comment";                  //название таблицы для комментариев
    public static final String TABLE_QR = "ftp_qr_code";                  //название таблицы для qr сканера
    public static final String TABLE_STORE = "ftp_store_category";                  //название таблицы для добавления категорий
    public static final String TABLE_PAY = "ftp_pay";                  //название таблицы для добавления регулярных плаптежей
    public static final String _ID = "_id";                         //ид
    public static final String CATEGORY = "category";               //категория покупки или траты
    public static final String PURPOSE = "purpose";                  //названиение расходи или доход
    public static final String SUM = "sum";                         //сумма
    public static final String COMMENT = "comment";                 //комментарий к внесению
    public static final String COMMENT_ID = "comment_id";                 //комментарий к внесению
    public static final String DATE = "date";                       //дата день-мес-год
    public static final String TIME = "time";                       //вермя
   // public static final String CODE_QR_FIK = "qr_code_fik";         //какой-то qr code фиксальный признак
   // public static final String CODE_QR_KKM = "qr_code_kkm";    //какой-то qr code код ККМ КГД (РНМ)
    public static final String CODE_QR_LINK = "qr_code_link";    // qr code ссылка
    public static final String CODE_QR_IDE = "qr_code_id";    //какой-то qr code код id
    public static final String STORE_CATEGORY = "store_category";    //хранение созданной категории
    public static final String STORE_PURPOSE = "store_purpose";    //хранение цели категории
    public static final String CHECK_PAY = "check_pay";    //хранение активный ли платеж
    public static final String CHECK_NOTIFICATION = "check_notification";    //хранение НУЖНО ЛИ УВЕДОМЛЕНИЕ
    public static final String CHECK_NAME = "check_name";    //хранение имени платежа
    public static final String CHECK_PERIOD = "check_period";    //хранение периода добавления
    public static final String CHECK_DATEND = "check_datend";    //хранение даьа окончания

    //удаление таблиц
    //public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String DROP_TABLE1 = " DROP TABLE IF EXISTS " + TABLE_MAIN;
    public static final String DROP_TABLE2 = " DROP TABLE IF EXISTS " + TABLE_COMMENT;
    public static final String DROP_TABLE3 = " DROP TABLE IF EXISTS " + TABLE_QR;
    public static final String DROP_TABLE4 = " DROP TABLE IF EXISTS " + TABLE_STORE;
    public static final String DROP_TABLE5 = " DROP TABLE IF EXISTS " + TABLE_PAY;


    //создание при первом запуске
    public static final String TABLE_STRUCTURE_MAIN = "CREATE TABLE IF NOT EXISTS "
            + TABLE_MAIN + " ( "
            + _ID + " INTEGER PRIMARY KEY,"
            + CATEGORY + " TEXT,"
            + PURPOSE + " TEXT, "
            + SUM + " INTEGER, "
            + DATE + " CURRENT_DATE, "
            + TIME + " CURRENT_TIME);";
    public static final String TABLE_STRUCTURE_SUB_PAY = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PAY + " ( "
            + _ID + " INTEGER PRIMARY KEY,"
            + CATEGORY + " TEXT,"
            + PURPOSE + " TEXT, "
            + SUM + " INTEGER, "
            + DATE + " CURRENT_DATE, "
            + TIME + " CURRENT_TIME, "
            + COMMENT + " TEXT, "
            + CHECK_NAME + " TEXT, "
            + CHECK_PERIOD + " INTEGER, "
            + CHECK_NOTIFICATION + " INTEGER,"
            + CHECK_DATEND + " CURRENT_DATE, "
            + CHECK_PAY + " INTEGER);";
    public static final String TABLE_STRUCTURE_SUB_COMMENT = " CREATE TABLE IF NOT EXISTS "
            + TABLE_COMMENT + " ( " + _ID + " INTEGER PRIMARY KEY, "
            + COMMENT + " TEXT,"
            + COMMENT_ID + " INTEGER);";
    public static final String TABLE_STRUCTURE_SUB_QR = " CREATE TABLE IF NOT EXISTS "
            + TABLE_QR + " ( "
            + _ID + " INTEGER PRIMARY KEY,"
            + CODE_QR_LINK + " TEXT,"
            + CODE_QR_IDE + " INTEGER);";
    public static final String TABLE_STRUCTURE_SUB_CATPUR = " CREATE TABLE IF NOT EXISTS "
            + TABLE_STORE + " ( "
            + _ID + " INTEGER PRIMARY KEY,"
            + STORE_CATEGORY + " TEXT,"
            + STORE_PURPOSE + " INTEGER);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //sql запрос на создание структур таблиц
        db.execSQL(TABLE_STRUCTURE_MAIN);
        db.execSQL(TABLE_STRUCTURE_SUB_COMMENT);
        db.execSQL(TABLE_STRUCTURE_SUB_QR);
        db.execSQL(TABLE_STRUCTURE_SUB_CATPUR);
        db.execSQL(TABLE_STRUCTURE_SUB_PAY);

        //sql запрос на добавление шаблонных данных
        db.execSQL("INSERT INTO " + TABLE_MAIN + " ( "
                + CATEGORY + " , " + PURPOSE + " , " + SUM + " , " + DATE + " , " + TIME
                + " ) VALUES ('Транспорт', 'Расход', 80, CURRENT_DATE, CURRENT_TIME);");
        db.execSQL("INSERT INTO " + TABLE_PAY + " ( "
                + CATEGORY + " , " + PURPOSE + " , " + SUM + " , " + DATE + " , " + TIME + " , " + COMMENT + " , "
                + CHECK_NAME + " , " + CHECK_PERIOD + " , " + CHECK_NOTIFICATION + " , " + CHECK_DATEND + " , " + CHECK_PAY
                + " ) VALUES ('Транспорт', 'Расход', 80, CURRENT_DATE, CURRENT_TIME, 'работает', 'что-то', 1, 0, '2022-04-25', 1);");
        db.execSQL("INSERT INTO " + TABLE_COMMENT + " ( " + COMMENT + " , " + COMMENT_ID
                + " ) VALUES ('потратил на дорогу домой', 1 );");


        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Транспорт' , 'Расход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Еда' , 'Расход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Одежда' , 'Расход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Такси' , 'Расход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Обучение' , 'Расход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Разное' , 'Расход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Подарки' , 'Расход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'ЖКХ' , 'Расход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Кино' , 'Расход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Интернет' , 'Расход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'QR' , 'Расход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Зарплата' , 'Доход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Премия' , 'Доход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Грант' , 'Доход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Стипендия' , 'Доход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Подарок' , 'Доход' );");
        db.execSQL("INSERT INTO " + TABLE_STORE + " ( " + STORE_CATEGORY + " , " + STORE_PURPOSE + " ) VALUES ( 'Другое' , 'Доход' );");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //запрос при обновлении бд или удалении
        //db.execSQL(DROP_TABLE);
        db.execSQL(DROP_TABLE1);
        db.execSQL(DROP_TABLE2);
        db.execSQL(DROP_TABLE3);
        db.execSQL(DROP_TABLE4);
        db.execSQL(DROP_TABLE5);
        onCreate(db);
    }
}