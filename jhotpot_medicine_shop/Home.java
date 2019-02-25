package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {
ImageButton buy,tips,faid,abus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        buy=findViewById(R.id.bmid);
        tips= findViewById(R.id.htid);
        faid=findViewById(R.id.fstaidid);
        abus=findViewById(R.id.abusid);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,MainActivity.class);
                startActivity(i);
            }
        });

        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, HealthTips.class);
                startActivity(i);
            }
        });

        faid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,FirstAid.class);
                startActivity(i);
            }
        });

        abus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,About.class);
                startActivity(i);
            }
        });






    }
}
