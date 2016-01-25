package com.example.covm9.prototipocodelco;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PagWeb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebView browser = (WebView)findViewById(R.id.webkit);
        browser.setWebViewClient(new WebViewClient());
        String url = getIntent().getStringExtra("URL");
        String qr = getIntent().getStringExtra("qr");
        if(url==null){
            browser.loadUrl(qr);
        }else{
            browser.loadUrl(url);
        }
    }

}
