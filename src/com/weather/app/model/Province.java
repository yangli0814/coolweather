package com.weather.app.model;

public class Province {
	private int province_id;
	private String province_name;
	private int  province_code;
	public void setId(int id)
	{
		this.province_id = id;
	}
	public int getId()
	{
		return province_id;
	}
	public void setName(String name)
	{
		this.province_name = name;
	}
	public String getName()
	{
		return province_name;
	}
	public void setCode(int code)
	{
		this.province_code = code;
	}
	public int getCode()
	{
		return province_code;
	}
}