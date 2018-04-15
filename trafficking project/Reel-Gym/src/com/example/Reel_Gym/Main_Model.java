package com.example.Reel_Gym;

import com.example.Reel_Gym.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main_Model extends Activity {

	private Button btn_Goback;
	private Button btn_Start;
	private TextView Result_Text1;
	private Button Make_call;
	private EditText edit_phonenumber;
	
	private float Result=0;		//model data 
	private float[] model_array={1,1,1,1,1,1,1,1,1,1};	//training set data
	
	private float data1;				
	private float data2;
	private float data3;
	private float data4;
	private float data5;
	private float data6;				
	private float data7;
	private float data8;
	private float data9;
	private float data10;
	
	private String data60;
	private int data70;
	private float[] data80 = new float[10];			//input data array
	private float[][] data90 = new float[2][3];

/**************read data*************************/
	public float Get_data1(){
		return data1;
	}
	
	public float Get_data2(){
		return data2;
	}
	
	public float Get_data3(){
		return data3;
	}
	
	public float Get_data4(){
		return data4;
	}
	
	public float Get_data5(){
		return data5;
	}
	public float Get_data6(){
		return data6;
	}
	
	public float Get_data7(){
		return data7;
	}
	
	public float Get_data8(){
		return data8;
	}
	
	public float Get_data9(){
		return data9;
	}
	
	public float Get_data10(){
		return data10;
	}	
	
	public String Get_data60(){
		return data60;
	}
	
	public int Get_data70(){
		return data70;
	}
	
	public float[] Get_data80(){
		return data80;
	}
	
	public float[][] Get_data90(){
		return data90;
	}
	
/**************Write data*************************/
	public void Write_data1(float data1){
		this.data1=data1;
		this.data80[0]=data1;
	}
	
	public void Write_data2(float data2){
		this.data2=data2;
		this.data80[1]=data2;
	}
	
	public void Write_data3(float data3){
		this.data3=data3;
		this.data80[2]=data3;
	}
	
	public void Write_data4(float data4){
		this.data4=data4;
		this.data80[3]=data4;
	}
	
	public void Write_data5(float data5){
		this.data5=data5;
		this.data80[4]=data5;
	}
	public void Write_data6(float data6){
		this.data6=data6;
		this.data80[5]=data6;
	}
	
	public void Write_data7(float data7){
		this.data7=data7;
		this.data80[6]=data7;
	}
	
	public void Write_data8(float data8){
		this.data8=data8;
		this.data80[7]=data8;
	}
	
	public void Write_data9(float data9){
		this.data9=data9;
		this.data80[8]=data9;
	}
	
	public void Write_data10(float data10){
		this.data10=data10;
		this.data80[9]=data10;
	}
		
	public void Write_data60(String data60){
		this.data60=data60;
	}
	
	public void Write_data70(int data70){
		this.data70=data70;
	}
	
	public void Write_data80(float[] data80){
		this.data80=data80;
	}
	
	public void Write_data90(float[][] data90){
		this.data90=data90;
	}

/**************model *************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmodel);
		
		btn_Goback = (Button) findViewById(R.id.button2);
		btn_Start = (Button) findViewById(R.id.button1);
		Result_Text1 = (TextView) findViewById(R.id.textView2);
		Make_call = (Button) findViewById(R.id.button3);
		edit_phonenumber = (EditText) findViewById(R.id.editText1);
		
		btn_Goback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intentbutton = new Intent();
				intentbutton.setClass(Main_Model.this, Start_page2.class);
				startActivity(intentbutton);
				finish();
			}
		});		
		
		btn_Start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			try{
				Toast.makeText(getBaseContext(), "Calculating Model", Toast.LENGTH_SHORT).show();
				
				Bundle bundle = getIntent().getExtras();		//data transfer
				float data_1 = bundle.getFloat("data01");
				float data_2 = bundle.getFloat("data02");
				float data_3 = bundle.getFloat("data03");
//				float data_4 = bundle.getFloat("data04");
				float data_5 = bundle.getFloat("data05");
//				float data_6 = bundle.getFloat("data06");
//				float data_7 = bundle.getFloat("data07");
//				float data_8 = bundle.getFloat("data08");
//				float data_9 = bundle.getFloat("data09");
//				float data_10 = bundle.getFloat("data10");
				
/*				Toast.makeText(getApplicationContext(), String.valueOf(data_1), Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), String.valueOf(data_2), Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), String.valueOf(data_3), Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), String.valueOf(data_4), Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), String.valueOf(data_5), Toast.LENGTH_SHORT).show();
		*/		
				Main_Model Model1= new Main_Model();
				Model1.Write_data1(data_1);
				Model1.Write_data2(data_2);
				Model1.Write_data3(data_3);
//				Model1.Write_data4(data_4);
				Model1.Write_data5(data_5);
//				Model1.Write_data6(data_6);
//				Model1.Write_data7(data_7);
//				Model1.Write_data8(data_8);
//				Model1.Write_data9(data_9);
//				Model1.Write_data10(data_10);
				
				try{
				for(int i=0; i<2; i++){
					Model1.Result = Model1.model_array[i]*Model1.data80[i] + Model1.Result;				    
				}
				Model1.Result = Model1.model_array[4]*Model1.data80[4] + Model1.Result;	
				float finalresult;
				finalresult=(Model1.model_array[0]*Model1.data80[0])/Model1.Result;
			    ((Main_Value)getApplication()).setResult(((Main_Value)getApplicationContext()).getResult()+Model1.Result);
					Result_Text1.setText(" "+String.valueOf(finalresult));
					if(Model1.Result>100){
						Result_Text1.setTextColor(Color.rgb(255, 0, 0));
					}
					else if(Model1.Result>0){
						Result_Text1.setTextColor(Color.rgb(0, 255, 100));
					}
					else{
						Result_Text1.setTextColor(Color.rgb(0, 0, 255));
					}
//				Toast.makeText(getApplicationContext(), String.valueOf(((Main_Value)getApplicationContext()).getResult()), Toast.LENGTH_SHORT).show();

				}catch (Exception e){
	                e.printStackTrace();
	            }
				
			}catch (Exception e){
                e.printStackTrace();
            }
			}
		});		
		
		Make_call.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String phonenumber= edit_phonenumber.getText().toString();
				Intent intentbutton = new Intent();
				intentbutton.setAction("android.intent.action.CALL");
				intentbutton.addCategory("android.intent.category.DEFAULT");
				intentbutton.setData(Uri.parse("tel:"+phonenumber));
				startActivity(intentbutton);
			}
		});		
		
	}
}

