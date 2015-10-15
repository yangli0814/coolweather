package com.weather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

	public CoolWeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public static final String Create_province = "create table province(province_id integer primary key autoincrement,"+
	"province_name text,"+
	"province_code integer)";
	public static final String Create_city = "create table city(city_id integer primary key autoincrement,"+
			"city_name text,"+
			"city_code integer"+
			"province_id integer)";
	public static final String Create_county = "create table county(county_id integer primary key autoincrement,"+
			"county_name text,"+
			"county_code integer"+
			"city_id integer)";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(Create_province);
		db.execSQL(Create_city);
		db.execSQL(Create_county);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
