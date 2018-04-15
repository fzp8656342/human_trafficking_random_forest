package com.example.Reel_Gym;

import com.example.Reel_Gym.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activity01 extends Activity {

	private Button btn_Goback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity01);
		btn_Goback = (Button) findViewById(R.id.button1);
		btn_Goback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentbutton = new Intent();
				intentbutton.setClass(Activity01.this, MainActivity.class);
				startActivity(intentbutton);
				finish();
			}
		});
		
		
		Bundle bundle = getIntent().getExtras();		//data transfer
		String data01 = bundle.getString("data01");
		int data02 = bundle.getInt("data02");
		Log.d("Activity01", data01 + " " + data02);
		
		
	}
}
