package com.o9pathshala.student;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.o9paathshala.R;

public class WebViewDisplayActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_display);
		String url = getIntent().getExtras().getString("url");
		WebView webView = (WebView)findViewById(R.id.webView);
		webView.loadUrl("file:///android_asset/" + url + ".html");
	}

}
