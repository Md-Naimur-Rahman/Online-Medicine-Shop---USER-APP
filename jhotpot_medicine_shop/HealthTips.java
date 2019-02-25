package com.sdmgapl1a0501.naimur.jhotpot_medicine_shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HealthTips extends AppCompatActivity {
    private WebView wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);

        wb=(WebView) findViewById(R.id.webvw1);
        WebSettings wbset=wb.getSettings();
        wbset.setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient());
        wb.loadUrl("https://www.health.com/");
    }
}
