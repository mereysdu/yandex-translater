//Class Истории
package com.example.windows.translate;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.windows.translate.database.WordBaseHelper;
import com.example.windows.translate.database.WordDbSchema;

import java.util.ArrayList;

public class Fragment_History extends Fragment {
    ListView lvSimple;
    MySimpleAdapter sAdapter;
    WordBaseHelper WBH;
    static SQLiteDatabase db;
    ImageButton btnDel;

    ArrayList<TranHistory> tranHistoryArray = new ArrayList<TranHistory>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        WBH = new WordBaseHelper(getActivity().getApplicationContext());
        db = WBH.getWritableDatabase();
        lvSimple = (ListView) v.findViewById(R.id.lvSimple);
        btnDel = (ImageButton) v.findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Для запуска Allert диалога
                SomeDialog newFragment = new SomeDialog ();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                newFragment.show(ft, "dialog");
            }
        });
        refresh2(null);
        refresh(null);

        return v;
    }

    public void refresh(View v) {  // Добовляем в массив Истории
        tranHistoryArray.clear();
        Cursor c = db.query(WordDbSchema.WordTable.NAME, null, null, null, null, null, null);
        if(c.moveToFirst()) {
            do{

                String text = c.getString(c.getColumnIndex("word"));
                String fav = c.getString(c.getColumnIndex("favorites"));
                int ID = c.getInt(c.getColumnIndex("_id"));

                TranHistory tranHistory = new TranHistory();
                tranHistory.text = text;
                tranHistory.id = ID;
                tranHistory.favorite = fav;

                tranHistoryArray.add(tranHistory);

            }while(c.moveToNext());
        }

        sAdapter = new MySimpleAdapter (getActivity().getApplicationContext(), tranHistoryArray);
        lvSimple.setAdapter(sAdapter);

    }
    public static void refresh2(View v) {  //Добавляем в Избранные из Fragment_Translate
        Cursor c = db.query(WordDbSchema.WordTable.NAME, null, null, null, null, null, null);
        if (c.moveToLast() && Fragment_Translate.myBool) {
            int ID = c.getInt(c.getColumnIndex("_id"));
            db.execSQL("update words set favorites = 1 where _id = " + ID);
            Fragment_Translate.myBool = false;
        }
    }

    class MySimpleAdapter extends ArrayAdapter <TranHistory> {  //Создаем свой Адаптер для ListView

        public MySimpleAdapter(Context context, ArrayList<TranHistory> resource) {
            super(context, 0, resource);
        }
        ImageButton imageButton;
        public View getView(final int position, View cView, ViewGroup parent) {
            final TranHistory tranHistory = getItem(position);

            if (cView == null) {
                cView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            }

            TextView TV = (TextView) cView.findViewById(R.id.tvText);
            imageButton = (ImageButton) cView.findViewById(R.id.ivImg);
            TV.setText(tranHistory.getText());
            if (tranHistory.getFavorite().equals("0")) {
                imageButton.setActivated(false);
            } else {
                imageButton.setActivated(true);
            }
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isActivated()) {
                        SQLiteDatabase db = WBH.getWritableDatabase();
                        db.execSQL("update words set favorites = 0 where _id = " + tranHistory.getId());
                        v.setActivated(false);
                    } else {
                        SQLiteDatabase db = WBH.getWritableDatabase();
                        db.execSQL("update words set favorites = 1 where _id = " + tranHistory.getId());
                        v.setActivated(true);
                    }
                }
            });
            return cView;
        }
    }

    class SomeDialog extends DialogFragment {  //Allert диалог для Корзины

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle("История")
                    .setMessage("Вы уверены что хотите очистить историю?")
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing (will close dialog)
                        }
                    })
                    .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.execSQL("delete from words");
                            refresh(null);
                        }
                    })
                    .create();
        }
    }
}

