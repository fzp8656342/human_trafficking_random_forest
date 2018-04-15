package com.example.Reel_Gym;

import com.example.Reel_Gym.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Knowledge extends Activity{
	
	private Button btn_Goback;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.knowledge);
        
        btn_Goback=(Button)findViewById(R.id.button1);
        btn_Goback.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				Intent intentbutton=new Intent();
				intentbutton.setClass(Knowledge.this, News.class);
				startActivity(intentbutton);
				finish();
			}
		});
 
    }
    
    
}
