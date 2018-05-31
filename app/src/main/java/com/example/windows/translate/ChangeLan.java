//Выбор языка для перевода
package com.example.windows.translate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChangeLan extends AppCompatActivity implements View.OnClickListener {

    TextView tvEn, tvRu, tvTr, tvKk;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_lan);

        tvEn = (TextView) findViewById(R.id.tvEn);
        tvRu = (TextView) findViewById(R.id.tvRu);
        tvTr = (TextView) findViewById(R.id.tvTr);
        tvKk = (TextView) findViewById(R.id.tvKk);
        tvEn.setOnClickListener(this);
        tvRu.setOnClickListener(this);
        tvTr.setOnClickListener(this);
        tvKk.setOnClickListener(this);

        intent = getIntent();  //Берем данные которые передали с Fragment_Translate
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvEn :
                if (intent.getStringExtra("WHICH").equals("TV1")) {
                    Fragment_Translate.TV1.setText("Английский");
                } else {
                    Fragment_Translate.TV2.setText("Английский");
                }
                break;
            case R.id.tvRu :
                if (intent.getStringExtra("WHICH").equals("TV1")) {
                    Fragment_Translate.TV1.setText("Русский");
                } else {
                    Fragment_Translate.TV2.setText("Русский");
                }
                break;
            case R.id.tvTr :
                if (intent.getStringExtra("WHICH").equals("TV1")) {
                    Fragment_Translate.TV1.setText("Турецкий");
                } else {
                    Fragment_Translate.TV2.setText("Турецкий");
                }
                break;
            case R.id.tvKk :
                if (intent.getStringExtra("WHICH").equals("TV1")) {
                    Fragment_Translate.TV1.setText("Казахский");
                } else {
                    Fragment_Translate.TV2.setText("Казахский");
                }
                break;
        }
        finish();  //Заканчиваю Class ChangeLan
    }
}
