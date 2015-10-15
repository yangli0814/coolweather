package com.weather.app.util;

import android.text.TextUtils;

import com.weather.app.model.City;
import com.weather.app.model.CoolWeatherDB;
import com.weather.app.model.County;
import com.weather.app.model.Province;

public class Utility {
public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response)
{
	if(!TextUtils.isEmpty(response))
	{
		String[] allProvince = response.split(",");
		if(allProvince!=null&&allProvince.length>=0)
		{
			for( String p : allProvince)
			{
				String[] array = p.split("\\|");
				Province province = new Province();
				province.setCode(array[0]);
				province.setName(array[1]);
				coolWeatherDB.saveProvince(province);
			}
		}
		return true;
	}
	return false;
}
public synchronized static boolean handleCityResponse(CoolWeatherDB coolWeatherDB, String response,int province_id)
{
	if(!TextUtils.isEmpty(response))
	{
		String[] allCity = response.split(",");
		if(allCity!=null&&allCity.length>=0)
		{
			for( String p : allCity)
			{
				String[] array = p.split("\\|");
				City city = new City();
				city.setCode(array[0]);
				city.setName(array[1]);
				city.setProvinceId(province_id);
				coolWeatherDB.saveCity(city);
			}
		}
		return true;
	}
	return false;
}
public synchronized static boolean handleCountyResponse(CoolWeatherDB coolWeatherDB, String response,int city_id)
{
	if(!TextUtils.isEmpty(response))
	{
		String[] allCounty = response.split(",");
		if(allCounty!=null&&allCounty.length>=0)
		{
			for( String p : allCounty)
			{
				String[] array = p.split("\\|");
				County county = new County();
				county.setCode(array[0]);
				county.setName(array[1]);
				county.setCityId(city_id);
				coolWeatherDB.saveCounty(county);
			}
		}
		return true;
	}
	return false;
}
}
