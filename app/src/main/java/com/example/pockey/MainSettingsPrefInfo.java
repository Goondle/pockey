package com.example.pockey;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.DialerKeyListener;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainSettingsPrefInfo extends AppCompatActivity {

    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //активное окно
        setContentView(R.layout.main_settings_pref_info);

        TextView textView = findViewById(R.id.texttest); //находим TextView
//Экранируем кавычки в атрибуте html тега слэшем:
        String textWithLink = "<a href=\"https://www.youtube.com/watch?v=dQw4w9WgXcQ\">Ссылка на великое</a>";
//Указываем с помощью Html.fromHtml, что у нас не просто текст:
        textView.setText(Html.fromHtml(textWithLink, null, null));
////Указываем что разрешаем ссылки кликать:
        textView.setLinksClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
//Научаемся отлавливать клики пропустив текст через наш класс из пред. пункта.
        CharSequence text = textView.getText();
        if (text instanceof Spannable)
        {
            textView.setText(MakeLinksClicable.reformatText(text));
        }

        TextView textVie = findViewById(R.id.texttestt); //находим TextView
//Экранируем кавычки в атрибуте html тега слэшем:
        String textWithLink2 = "<a href=\"mailto:mmssms_1988@mail.ru?subject=Отзыв%20Pockey&body=Что%20не%20понравилось?%0\">Goondle</a>";
//Указываем с помощью Html.fromHtml, что у нас не просто текст:
        textVie.setText(Html.fromHtml(textWithLink2, null, null));
////Указываем что разрешаем ссылки кликать:
        textVie.setLinksClickable(true);
        textVie.setMovementMethod(LinkMovementMethod.getInstance());
//Научаемся отлавливать клики пропустив текст через наш класс из пред. пункта.
        CharSequence textt = textVie.getText();
        if (textt instanceof Spannable)
        {
            textVie.setText(MakeLinksClicable.reformatText(textt));
        }

        //кнопка
        Button button_back = (Button)findViewById(R.id.button_back2);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent( MainSettingsPrefInfo.this, MainSettingsPref.class);
                    startActivity(intent);finish();
                }catch (Exception e){}
            }
        });


    //диалоговое окно
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        TextView btnclose = (TextView)dialog.findViewById(R.id.btnclose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainSettingsPrefInfo.this, MainSettingsPref.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){}
            }
        });

        Button btncontinue = (Button)dialog.findViewById(R.id.btncontinue);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                }catch (Exception e){}
            }
        });


    }

//    public void back(View v){
//        switch (v.getId()) {
//            case R.id.button_back2:
//                Intent intent = new Intent(this, MainSettingsPref.class);
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
//    }


    //системная кнопка
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(this, MainSettingsPref.class);
            startActivity(intent);finish();
        }catch (Exception e){}
    }
}

class MakeLinksClicable
{
    private final static String LOG = MakeLinksClicable.class.getSimpleName();

    public static class CustomerTextClick extends ClickableSpan
    {
        String mUrl;

        public CustomerTextClick(String url)
        {
            mUrl = url;
        }

        @Override
        public void onClick(View widget)
        {
            //Тут можно как-то обработать нажатие на ссылку
            //Сейчас же мы просто открываем браузер с ней
            Log.i(LOG, "url clicked: " + this.mUrl);

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(mUrl));
            widget.getContext().startActivity(i);
        }
    }

    public static SpannableStringBuilder reformatText(CharSequence text)
    {
        int end = text.length();
        Spannable sp = (Spannable) text;
        URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        for (URLSpan url : urls)
        {
            style.removeSpan(url);
            MakeLinksClicable.CustomerTextClick click = new MakeLinksClicable.CustomerTextClick(url.getURL());
            style.setSpan(click, sp.getSpanStart(url), sp.getSpanEnd(url),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return style;
    }
}