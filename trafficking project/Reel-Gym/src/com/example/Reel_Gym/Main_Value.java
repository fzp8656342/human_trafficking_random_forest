package com.example.Reel_Gym;

import android.app.Application;

public class Main_Value extends Application {

	//����һ������
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

	//ʵ��setname()���������ñ�����ֵ
	public void setname(String name) {
		this.nameString = name;
	}

	//ʵ��getname()��������ȡ������ֵ
	public String getname() {
		return nameString;
	}
	
	public void setResult(float Setdata) {
		this.Result = Setdata;
	}

	//ʵ��getname()��������ȡ������ֵ
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