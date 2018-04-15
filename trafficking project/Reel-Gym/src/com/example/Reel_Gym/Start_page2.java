package com.example.Reel_Gym;

import com.example.Reel_Gym.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Start_page2 extends Activity {

	private Button btn_Goback;
	private Button btn_02; // next page
	private EditText data1;
/*	private EditText data2;
	private EditText data3;
	private EditText data4;
	private EditText data5;
*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startpage2);

		btn_Goback = (Button) findViewById(R.id.button1);
		btn_02 = (Button) findViewById(R.id.button2);
		data1 = (EditText) findViewById(R.id.editText1);
//		data2 = (EditText) findViewById(R.id.editText2);
//		data3 = (EditText) findViewById(R.id.editText3);
//		data4 = (EditText) findViewById(R.id.editText4);
//		data5 = (EditText) findViewById(R.id.editText5);
		
		btn_Goback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {  
					Intent intentbutton = new Intent();
					intentbutton.setClass(Start_page2.this, Start_page.class);
					startActivity(intentbutton);
					finish();
				}
		});
		
		btn_02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
					
				try{
					Bundle bundle = getIntent().getExtras();		//data transfer
					float data_1 = bundle.getFloat("data01");
					float data_2 = bundle.getFloat("data02");
					float data_3 = bundle.getFloat("data03");
//					float data_4 = bundle.getFloat("data04");
					float data_5 = bundle.getFloat("data05");

/*					String datas1 = data1.getText().toString();
					float data_6= Float.parseFloat(datas1);
					String datas2 = data2.getText().toString();
					float data_7= Float.parseFloat(datas2);
					String datas3 = data3.getText().toString();
					float data_8= Float.parseFloat(datas3);
					String datas4 = data4.getText().toString();
					float data_9= Float.parseFloat(datas4);
					String datas5 = data5.getText().toString();
					float data_10= Float.parseFloat(datas5);*/
					
					Intent intentbutton = new Intent();
					intentbutton.setClass(Start_page2.this, Main_Model.class);
					intentbutton.putExtra("data01",data_1);
					intentbutton.putExtra("data02",data_2);
					intentbutton.putExtra("data03",data_3);
//					intentbutton.putExtra("data04",data_4);
				    intentbutton.putExtra("data05",data_5);
//					intentbutton.putExtra("data06",data_6);
//					intentbutton.putExtra("data07",data_7);
//					intentbutton.putExtra("data08",data_8);
//					intentbutton.putExtra("data09",data_9);
//					intentbutton.putExtra("data10",data_10);
					startActivity(intentbutton);
					
				}catch (Exception e){
	                 e.printStackTrace();
	             }
				}
		});
		

	}

}