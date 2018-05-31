//Class Избранных
package com.example.windows.translate;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
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


public class Fragment_favarites extends Fragment {
    WordBaseHelper WBH;
    SQLiteDatabase db;
    ArrayList<TranHistory> tranHistoryArray = new ArrayList<TranHistory>();
    ListView lvSimple;
    MySimpleAdapter sAdapter;
    ImageButton btnDel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        WBH = new WordBaseHelper(getActivity());
        db = WBH.getWritableDatabase();
        lvSimple = (ListView) v.findViewById(R.id.lvSimple);
        btnDel = (ImageButton) v.findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SomeDialog newFragment = new SomeDialog ();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                newFragment.show(ft, "dialog");
            }
        });
        Fragment_History.refresh2(null);
        refresh(null);

        return v;
    }

    public void refresh(View v) {  //Добовляем всех избранных слов
        tranHistoryArray.clear();
        Cursor c = db.query(WordDbSchema.WordTable.NAME, null, "favorites = 1", null, null, null, null);
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

        sAdapter = new Fragment_favarites.MySimpleAdapter(getActivity().getApplicationContext(), tranHistoryArray);
        lvSimple.setAdapter(sAdapter);
    }
    public void refresh2(View v) {  //Убираем всех Избранных из таблицы
        Cursor c = db.query(WordDbSchema.WordTable.NAME, null, "favorites = 1", null, null, null, null);
        if(c.moveToFirst()) {
            do{
                int ID = c.getInt(c.getColumnIndex("_id"));
                db.execSQL("update words set favorites = 0 where _id = " + ID);

            }while(c.moveToNext());
        }
        refresh(null);
    }


    class MySimpleAdapter extends ArrayAdapter<TranHistory> {  //Создаем свой Адаптер для ListView

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
                    .setMessage("Вы уверены что хотите очистить избранное?")
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing (will close dialog)
                        }
                    })
                    .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            refresh2(null);
                        }
                    })
                    .create();
        }
    }
}
