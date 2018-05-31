package com.example.windows.translate;

//import android.support.v4.app.FragmentTransaction;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int FV;
    Fragment_Translate current1;
    Fragment_History current2;
    Fragment_favarites current3;
    FragmentTransaction fragmentTransaction;
    ImageButton IV1, IV2, IV3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FV = R.id.FL;
        current1 = new Fragment_Translate();
        current2 = new Fragment_History();
        current3 = new Fragment_favarites();
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(FV ,current1);
        fragmentTransaction.commit();

        IV1 = (ImageButton) findViewById(R.id.IV1);
        IV2 = (ImageButton) findViewById(R.id.IV2);
        IV3 = (ImageButton) findViewById(R.id.IV3);

        IV1.setActivated(true);

        IV1.setOnClickListener(this);
        IV2.setOnClickListener(this);
        IV3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.IV1 :
                fragmentTransaction.replace(FV ,current1);
                IV1.setActivated(true);
                IV2.setActivated(false);
                IV3.setActivated(false);
                break;
            case  R.id.IV2 :
                fragmentTransaction.replace(FV ,current2);
                IV1.setActivated(false);
                IV2.setActivated(true);
                IV3.setActivated(false);
                break;
            case R.id.IV3 :
                fragmentTransaction.replace(FV ,current3);
                IV1.setActivated(false);
                IV2.setActivated(false);
                IV3.setActivated(true);
                break;
        }
        fragmentTransaction.commit();
    }
}
