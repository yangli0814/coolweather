package com.weather.app.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.weather.app.db.CoolWeatherOpenHelper;

public class CoolWeatherDB {
public static final String DB_NAME ="cool_weather";
public static final int VERSION = 1;
public static CoolWeatherDB coolWeatherDB;
public SQLiteDatabase db;
private CoolWeatherDB(Context context)
{
	CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
	db = dbHelper.getWritableDatabase();
}
public synchronized static CoolWeatherDB getInstance(Context context)
{
	if(coolWeatherDB==null)
	{
		coolWeatherDB = new CoolWeatherDB(context);
	}
	return coolWeatherDB;
}
public void saveProvince(Province province)
{
	if(province!=null)
	{
		ContentValues values = new ContentValues();
		values.put("province_name", province.getName());
		values.put("province_code", province.getCode());
		db.insert("province", null, values);
		values.clear();
	}
}
public List<Province> loadProvince()
{
	List<Province> list = new ArrayList<Province>();
	Cursor cursor = db.query("province", null, null, null, null, null, null);
	
	if(cursor.moveToFirst()){
		do{
			Province province = new Province();
			province.setId(cursor.getInt(cursor.getColumnIndex("province_id")));
			province.setName(cursor.getString(cursor.getColumnIndex("province_name")));
			province.setCode(cursor.getString(cursor.getColumnIndex("province_code")));
			list.add(province);
		}while(cursor.moveToNext());
	}
	
	return list;
}
public void saveCity(City city)
{
if(city!=null)
{
ContentValues values = new ContentValues();
values.put("city_name", city.getName());
values.put("city_code", city.getCode());
values.put("province_id", city.getProvinceId());
db.insert("city", null, values);
values.clear();
}
}
public List<City> loadCity(int province_id)
{
	List<City> list = new ArrayList<City>(); 
	Cursor cursor = db.query("city", null, "province_id = ?", new String[]{String.valueOf(province_id)}, null, null, null);
	if (cursor.moveToFirst()) {
		do {
		City city = new City();
		city.setId(cursor.getInt(cursor.getColumnIndex("city_id")));
		city.setName(cursor.getString(cursor.getColumnIndex("city_name")));
		city.setCode(cursor.getString(cursor.getColumnIndex("city_code")));
		city.setProvinceId(province_id);
		list.add(city);
		} while (cursor.moveToNext());
		}
	return list;
}
public void saveCounty(County county)
{
	if(county!=null)
	{
		ContentValues values = new ContentValues();
		values.put("county_name", county.getName());
		values.put("county_code", county.getCode());
		values.put("city_id", county.getCityId());
		db.insert("county", null, values);
	}
}
public List<County> loadCounty(int city_id)
{
	List<County> list = new ArrayList<County>(); 
	Cursor cursor = db.query("County", null, "city_id = ?", new String[]{String.valueOf(city_id)}, null, null, null);
	if (cursor.moveToFirst()) {
		do {
		County county = new County();
		county.setId(cursor.getInt(cursor.getColumnIndex("county_id")));
		county.setName(cursor.getString(cursor.getColumnIndex("county_name")));
		county.setCode(cursor.getString(cursor.getColumnIndex("county_code")));
		county.setCityId(city_id);
		list.add(county);
		} while (cursor.moveToNext());
		}
	return list;
}
}
 