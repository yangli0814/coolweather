package com.weather.app.model;

public class City {
	private int city_id;
	private String city_name;
	private int  city_code;
	private int province_id;
	public void setId(int id)
	{
		this.city_id = id;
	}
	public int getId()
	{
		return city_id;
	}
	public void setName(String name)
	{
		this.city_name = name;
	}
	public String getName()
	{
		return city_name;
	}
	public void setCode(int code)
	{
		this.city_code = code;
	}
	public int getCode()
	{
		return city_code;
	}
	public void setProvinceId(int id)
	{
		this.province_id = id;
	}
	public int getProvinceId()
	{
		return this.province_id;
	}
}
