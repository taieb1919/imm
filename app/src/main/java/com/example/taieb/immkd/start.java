package com.example.taieb.immkd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.taieb.immkd.util.SystemUiHider;
import com.javacodegeeks.androidqrcodeexample.R;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class start extends Activity {
    private int idmodele;


    public void startscan(View v) {
        Intent myIntent = new Intent(this, ScanScreen.class);
       myIntent.putExtra("id_model", idmodele);
        myIntent.putExtra("searchmode", 1);
        //Optional parameters
        this.startActivity(myIntent);
       // System.out.println(""+id);

    }

    public void startsearch(View v) {
        Intent myIntent = new Intent(this, ScanScreen.class);
        myIntent.putExtra("id_model", idmodele); //Optional parameters
        myIntent.putExtra("searchmode", 2);
        this.startActivity(myIntent);
        // System.out.println(""+id);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);


        Intent intent = getIntent();
      // intent.getStringExtra("id_model"); //if it's a string you stored.
        final TextView textViewToChange = (TextView) findViewById(R.id.textview);
        this.idmodele=intent.getIntExtra("id_model",-1);

        textViewToChange.setText(intent.getStringExtra("model_name"));
//////////////////////////////////////////////////////////////////////
        final Button myButton1 = new Button(this);
        myButton1.setText("Start Scan");
        LinearLayout ll = (LinearLayout) findViewById(R.id.LLB1);
        ll.addView(myButton1);
        myButton1.setOnClickListener(new View.OnClickListener() {

                                        public void onClick(View v) {
                                            start.this.startscan(v);
                                        }

                                    }
        );

        myButton1.setPadding(0,8,0,8);
        //////////////////////////////////////
        final Button myButton2 = new Button(this);
        myButton2.setText("Search By Part NUM");
        LinearLayout l2 = (LinearLayout) findViewById(R.id.LLB2);
        l2.addView(myButton2);
        myButton2.setOnClickListener(new View.OnClickListener() {

                                         public void onClick(View v) {
                                             start.this.startsearch(v);
                                         }

                                     }
        );



    }

}
