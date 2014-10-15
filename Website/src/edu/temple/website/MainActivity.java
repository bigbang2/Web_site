package edu.temple.website;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private WebView webview;
	private Button button;
	private EditText website;




	Handler showContent = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			webview.loadData((String) msg.obj, "text/html", "UTF-8");
			return false;
		}
	});


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		webview = (WebView) findViewById(R.id.webview);
		website = (EditText) findViewById(R.id.webname);
		
		//webview.setWebViewClient(new WebViewClient());

		button = (Button) findViewById(R.id.goButton);

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Thread loadContent = new Thread(){
					@Override
					public void run(){

				URL url = null;

						try {
							url = new URL(website.getText().toString());
							BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

							String response = "", tmpResponse = "";
							tmpResponse = reader.readLine();
							while (tmpResponse != null){
								response = response + tmpResponse;
								tmpResponse = reader.readLine();
							}
							Message msg = Message.obtain();
							msg.obj = response;
							showContent.sendMessage(msg);
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				};

				loadContent.start();
			}
		});
	}
}
