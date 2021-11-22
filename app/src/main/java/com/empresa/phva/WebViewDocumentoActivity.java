package com.empresa.phva;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewDocumentoActivity extends AppCompatActivity {

    public String urln;
    //public TextView tvUrl = (TextView)findViewById(R.id.tvURL);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_documento);

        Bundle bundle = getIntent().getExtras();
        //tvUrl.setText(bundle.getString("url"));

        WebView webView = findViewById(R.id.webViewForm);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSflz8mrviuiyyKahWYui0abjRLVpUWNct2v_izDkoKt7QUi1g/viewform?usp=sf_link");
        //webView.loadUrl(url);



    }





}