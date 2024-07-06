package com.example.pockey;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainAllHistory extends AppCompatActivity {

    Dialog dialog;
    ListView userList;
    ListView userList1;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    Cursor userCursor1;
    SimpleCursorAdapter userAdapter;
    SimpleCursorAdapter userAdapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_all_info);


        userList = findViewById(R.id.list);
        userList1 = findViewById(R.id.list1);
//        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
//                intent.putExtra("id", id);
//                startActivity(intent);
//            }
//        });

//блок прокрутки двух listview
        final View[] clickSource = new View[1];
        final View[] touchSource = {null};

        int offset = 0;
        userList = (ListView) findViewById(R.id.list);
        userList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(touchSource[0] == null)
                    touchSource[0] = v;

                if(v == touchSource[0]) {
                    userList1.dispatchTouchEvent(event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        clickSource[0] = v;
                        touchSource[0] = null;
                    }
                }

                return false;
            }
        });
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(parent == clickSource[0]) {
                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });

        userList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(view == clickSource[0])
                    userList1.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop() + offset);
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}
        });
//конец блока прокрутки двух listview

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        userList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id){

                // TODO Auto-generated method stub
                Log.v("long clicked", "pos: " + pos );
                Log.v("long clicked", "id: " + id );

                dialog.show();
                //al.setText( aloha );

                Button btndelete = (Button)dialog.findViewById(R.id.btndelete);
                btndelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            db.delete(DatabaseHelper.TABLE_MAIN, "_id = ?", new String[]{String.valueOf(id)});
                            db.delete(DatabaseHelper.TABLE_COMMENT, "comment_id = ?", new String[]{String.valueOf(id)});
                            db.delete(DatabaseHelper.TABLE_QR, "qr_code_id = ?", new String[]{String.valueOf(id)});
                            dialog.dismiss();

                        }catch (Exception e){}
                    }
                });

                Button btnback = (Button)dialog.findViewById(R.id.btnback);
                btnback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            dialog.dismiss();
                        }catch (Exception e){}
                    }
                });
                return true;
            }
        });


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

        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_MAIN + "  order by " + DatabaseHelper._ID + " desc ", null );
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.CATEGORY, DatabaseHelper.SUM};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        userList.setAdapter(userAdapter);

        userCursor1 = db.rawQuery("select * from " + DatabaseHelper.TABLE_MAIN + "  order by " + DatabaseHelper._ID + " desc ", null );
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headeras = new String[]{DatabaseHelper.PURPOSE, DatabaseHelper.DATE};
        // создаем адаптер, передаем в него курсор
        userAdapter1 = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor1, headeras, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        userList1.setAdapter(userAdapter1);

        //начало кнопки настроек
        Button buttonPref = (Button)findViewById(R.id.baseButton);
        buttonPref.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Intent intent = new Intent(MainAllHistory.this,MainSettingsPref.class);
                    startActivity(intent);finish();
                }catch (Exception e){
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
    }
    //системная кнопка
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(this, MainSettingsPref.class);
            startActivity(intent);finish();
        }catch (Exception e){}
    }
}
