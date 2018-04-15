package com.example.Reel_Gym;

import android.app.Application;

public class Main_Value extends Application {

	//声明一个变量
	public String nameString;
	public float Result;
	public int identity;
	public int buttonvisible;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setname("xxx");
		Result=0;
	}

	//实现setname()方法，设置变量的值
	public void setname(String name) {
		this.nameString = name;
	}

	//实现getname()方法，获取变量的值
	public String getname() {
		return nameString;
	}
	
	public void setResult(float Setdata) {
		this.Result = Setdata;
	}

	//实现getname()方法，获取变量的值
	public float getResult() {
		return Result;
	}
	
	public void setIdentity(int Setdata) {
		this.identity = Setdata;
	}
	
	public int getIdentity() {
		return identity;
	}
	public void setvisible(int Setdata) {
		this.buttonvisible = Setdata;
	}
	
	public int getvisible() {
		return buttonvisible;
	}
}