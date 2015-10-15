package com.weather.app.model;

public class County {
	private int county_id;
	private String county_name;
	private String  county_code;
	private int city_id;
	public void setId(int id)
	{
		this.county_id = id;
	}
	public int getId()
	{
		return county_id;
	}
	public void setName(String name)
	{
		this.county_name = name;
	}
	public String getName()
	{
		return county_name;
	}
	public void setCode(String code)
	{
		this.county_code = code;
	}
	public String getCode()
	{
		return county_code;
	}
	public void setCityId(int id)
	{
		this.city_id = id;
	}
	public int getCityId()
	{
		return city_id;
	}
}
