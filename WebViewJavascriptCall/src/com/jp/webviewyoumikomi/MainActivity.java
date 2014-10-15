package com.jp.webviewyoumikomi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class MainActivity extends Activity {

	private WebView webView;
	private Button nextButton;
	private TextView editTextView;

	private WebSettings settings;
	private String editValue = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		webView = (WebView) findViewById(R.id.webView1);
		nextButton = (Button) findViewById(R.id.button1);
		editTextView = (TextView) findViewById(R.id.editText1);

		webView.loadUrl("file:///android_asset/index.html");

		webView.addJavascriptInterface(new WebViewInterface(), "webcall");

		settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);

		nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				webView.loadUrl("javascript:webcall.textValue(buttonPress())");
			}
		});

	}

	private class WebViewInterface {
		@JavascriptInterface
		public void textValue(String value) throws UnsupportedEncodingException {
			editValue = new String(value.getBytes(), "UTF-8");
			Log.i(this.getClass().getSimpleName(), editValue);

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					editTextView.setText(editValue);
				}
			});
		}
	}
}
