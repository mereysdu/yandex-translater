//Class для Перевода
package com.example.windows.translate;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windows.translate.database.WordBaseHelper;
import com.example.windows.translate.database.WordDbSchema;

import org.json.JSONException;


public class Fragment_Translate extends Fragment implements OnFocusChangeListener {
    EditText ET;
    static TextView TV;
    static TextView TV1, TV2;
    static String strOr = "";
    ExtendsTest ExT;
    ImageView imgChange;
    static boolean bool = false;
    WordBaseHelper WBH;
    SQLiteDatabase db;
    ImageButton myBtn;
    static boolean myBool;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater.inflate(R.layout.fragment_translate, container, false);
        View v = inflater.inflate(R.layout.fragment_translate, container, false);

        ET = (EditText) v.findViewById(R.id.ET);
        TV = (TextView) v.findViewById(R.id.TV);
        TV1 = (TextView) v.findViewById(R.id.TV1);
        TV2 = (TextView) v.findViewById(R.id.TV2);
        myBtn = (ImageButton) v.findViewById(R.id.myBtn);
        TV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ChangeLan.class);
                intent.putExtra("WHICH", "TV1");
                startActivity(intent);
            }
        });
        TV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ChangeLan.class);
                intent.putExtra("WHICH", "TV2");
                startActivity(intent);
            }
        });

        ET.setOnFocusChangeListener(this);
        imgChange = (ImageView) v.findViewById(R.id.IV4);
        myBool = false;
        imgChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ss1 = "", ss2 = "";
                ss1 = TV1.getText().toString();
                ss2 = TV2.getText().toString();
                if (bool) {
                    TV1.setText(ss2);
                    TV2.setText(ss1);
                    bool = false;
                } else {
                    TV1.setText(ss2);
                    TV2.setText(ss1);
                    bool = true;
                }
            }
        });
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBtn.isActivated()) {
                    myBool = false;
                    myBtn.setActivated(false);
                } else {
                    myBool = true;
                    myBtn.setActivated(true);
                }
            }
        });
        ExT = new ExtendsTest();

        ET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strOr = ET.getText().toString().replaceAll(" ", "+");
                try {
                    ExT.translate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
        WBH = new WordBaseHelper(getActivity().getApplicationContext());
        db = WBH.getWritableDatabase();
        return v;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus && !ET.getText().toString().equals("")) {
            ContentValues cv = new ContentValues();  //Добавление в ДБ
            cv.put(WordDbSchema.WordTable.Cols.WORD, ET.getText().toString() + "\n" + ExtendsTest.string + "\n" + ExtendsTest.str1 + "-" + ExtendsTest.str2);
            cv.put(WordDbSchema.WordTable.Cols.FAVORITES, "0");
            db.insert(WordDbSchema.WordTable.NAME, null, cv);
            db.close();
        }
    }
}
