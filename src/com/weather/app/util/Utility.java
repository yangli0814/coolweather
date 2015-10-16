package com.weather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.weather.app.model.City;
import com.weather.app.model.CoolWeatherDB;
import com.weather.app.model.County;
import com.weather.app.model.Province;

public class Utility {
/**
* 解析和处理服务器返回的省级数据
*/
public synchronized static boolean handleProvincesResponse(CoolWeatherDB
coolWeatherDB, String response) {
if (!TextUtils.isEmpty(response)) {
String[] allProvinces = response.split(",");
if (allProvinces != null && allProvinces.length > 0) {
for (String p : allProvinces) {
String[] array = p.split("\\|");
Province province = new Province();
province.setProvinceCode(array[0]);
province.setProvinceName(array[1]);
// 将解析出来的数据存储到Province表
coolWeatherDB.saveProvince(province);
}
return true;
}
}
return false;
}
/**
* 解析和处理服务器返回的市级数据
*/
public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,
String response, int provinceId) {
if (!TextUtils.isEmpty(response)) {
String[] allCities = response.split(",");
if (allCities != null && allCities.length > 0) {
for (String c : allCities) {
String[] array = c.split("\\|");
City city = new City();
city.setCityCode(array[0]);
city.setCityName(array[1]);
city.setProvinceId(provinceId);
// 将解析出来的数据存储到City表
coolWeatherDB.saveCity(city);
}
return true;
}
}
return false;
}
/**
* 解析和处理服务器返回的县级数据
*/
public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,
String response, int cityId) {
if (!TextUtils.isEmpty(response)) {
String[] allCounties = response.split(",");
if (allCounties != null && allCounties.length > 0) {
for (String c : allCounties) {
String[] array = c.split("\\|");
County county = new County();
county.setCountyCode(array[0]);
county.setCountyName(array[1]);
county.setCityId(cityId);
// 将解析出来的数据存储到County表
coolWeatherDB.saveCounty(county);
}
return true;
}
}
return false;
}
public static boolean handleWeatherResponse(Context context, String response)
{
	if(response!=null)
	{
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject weather = jsonObject.getJSONObject("weatherinfo");
			String cityName = weather.getString("city");
			String weatherCode = weather.getString("cityid");
			String temp1 = weather.getString("temp1");
			String temp2 = weather.getString("temp2");
			String weatherDesp = weather.getString("weather");
			String publishTime = weather.getString("ptime");
			saveWeatherInfo(context, cityName, weatherCode, temp1, temp2,
					weatherDesp, publishTime);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return false;
}
private static void saveWeatherInfo(Context context, String cityName,
		String weatherCode, String temp1, String temp2, String weatherDesp,
		String publishTime) {
	// TODO Auto-generated method stub
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
	SharedPreferences c = PreferenceManager.getDefaultSharedPreferences(context);
	Editor editor = c.edit();
	editor.putBoolean("city_selected", true);
	editor.putString("city_name", cityName);
	editor.putString("weather_code", weatherCode);
	editor.putString("temp1", temp1);
	editor.putString("temp2", temp2);
	editor.putString("weather_desp", weatherDesp);
	editor.putString("publish_time", publishTime);
	editor.putString("current_date", sdf.format(new Date()));
	editor.commit();
}
}