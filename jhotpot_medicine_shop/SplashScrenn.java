package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScrenn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screnn);
        Thread timer = new Thread(){

            public void run(){

                try{
                    sleep(5000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    Intent i = new Intent(SplashScrenn.this,Home.class);
                    startActivity(i);
                }



            }





        };
        timer.start();



    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
