package com.example.Reel_Gym;

import com.example.Reel_Gym.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Settings extends Activity {

	private Button btn_Goback;
//	public RadioGroup identity;
//	private RadioButton user;
//	private RadioButton officer;
	private CheckBox user;
	private CheckBox officer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		btn_Goback = (Button) findViewById(R.id.button1);
		user = (CheckBox) findViewById(R.id.checkBox1);
		officer = (CheckBox) findViewById(R.id.checkBox2);
//		identity = (RadioGroup) findViewById(R.id.);
		
		btn_Goback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentbutton = new Intent();
				intentbutton.setClass(Settings.this, MainActivity.class);
				startActivity(intentbutton);
				finish();
			}
		});
		
		user.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1){
					((Main_Value)getApplication()).setIdentity(1);
					Toast.makeText(getApplicationContext(), "identity changed"+String.valueOf(((Main_Value)getApplicationContext()).getIdentity()), Toast.LENGTH_SHORT).show();

				}
			}
		});
		
		officer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1){
					((Main_Value)getApplication()).setIdentity(2);
					Toast.makeText(getApplicationContext(), "identity changed"+String.valueOf(((Main_Value)getApplicationContext()).getIdentity()), Toast.LENGTH_SHORT).show();
	  //              Toast.makeText(getBaseContext(), "started recording", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}
	
	
}
