package com.weather.app.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.app.R;
import com.weather.app.model.City;
import com.weather.app.model.CoolWeatherDB;
import com.weather.app.model.County;
import com.weather.app.model.Province;
import com.weather.app.util.HttpCallbackListener;
import com.weather.app.util.HttpUtils;
import com.weather.app.util.Utility;

public class ChooseAreaActivity extends Activity {
	private ProgressDialog progressDialog;
	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;
	private ListView listView;
	private TextView titleText;
	private ArrayAdapter<String> adapter;
	private CoolWeatherDB coolWeatherDB;
	private List<String> dataList = new ArrayList<String>();
	private int currentLevel;
	/**
	* 省列表
	*/
	private List<Province> provinceList;
	/**
	* 市列表
	*/
	private List<City> cityList;
	/**
	* 县列表
	*/
	private List<County> countyList;
	/**
	* 选中的省份
	*/
	private Province selectedProvince;
	/**
	* 选中的城市
	*/
	private City selectedCity;

protected void onCreate(Bundle savedInstanceState)
{
super.onCreate(savedInstanceState);
requestWindowFeature(Window.FEATURE_NO_TITLE);
setContentView(R.layout.choose_area);
listView = (ListView)findViewById(R.id.list_view);
titleText = (TextView)findViewById(R.id.title_text);
adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dataList);
listView.setAdapter(adapter);
coolWeatherDB = CoolWeatherDB.getInstance(this);
listView.setOnItemClickListener(new OnItemClickListener(){

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(currentLevel==LEVEL_PROVINCE)
		{
			selectedProvince = provinceList.get(position);
			queryCities();
		}else if(currentLevel == LEVEL_CITY)
		{
			selectedCity = cityList.get(position);
			queryCounties();
		}
	}
	
});
queryProvinces();
}
private void queryProvinces()
{
	provinceList = coolWeatherDB.loadProvince();
	if(provinceList.size()>0)
	{
		dataList.clear();
		for(int i=0;i<provinceList.size();i++)
		{
			Province province = new Province();
			province = provinceList.get(i);
			dataList.add(province.getName());
		}
		adapter.notifyDataSetChanged();
		listView.setSelection(0);
		titleText.setText("中国");
		currentLevel = LEVEL_PROVINCE;
	}else {
		queryFromServer(null, "province");}
}

private void queryCities()
{
	cityList = coolWeatherDB.loadCity(selectedProvince.getId());
	if (cityList.size() > 0) {
	dataList.clear();
	for (City city : cityList) {
	dataList.add(city.getName());
	}
	adapter.notifyDataSetChanged();
	listView.setSelection(0);
	titleText.setText(selectedProvince.getName());
	currentLevel = LEVEL_CITY;
	} else {
	queryFromServer(selectedProvince.getCode(), "city");
	}
}
private void queryCounties()
{
	countyList = coolWeatherDB.loadCounty(selectedCity.getId());
	if (countyList.size() > 0) {
	dataList.clear();
	for (County county : countyList) {
	dataList.add(county.getName());
	}
	adapter.notifyDataSetChanged();
	listView.setSelection(0);
	titleText.setText(selectedCity.getName());
	currentLevel = LEVEL_COUNTY;
	} else {
	queryFromServer(selectedCity.getCode(), "county");
	}
}
private void queryFromServer(String code, final String type) {
	// TODO Auto-generated method stub
	String address;
	if(!TextUtils.isEmpty(code))
	{
		address = "http://www.weather.com.cn/data/list3/city" + code +".xml";
	}else
		address = "http://www.weather.com.cn/data/list3/city.xml";
	showProgressDialog();
	HttpUtils.sendHttpRequest(address, new HttpCallbackListener() {
		boolean result;
		@Override
		public void onFinish(String response) {
			
			// TODO Auto-generated method stub
			if("province".equals(type))
			{
				result  = Utility.handleProvincesResponse(coolWeatherDB, response);
			}else if ("city".equals(type)) {
				result = Utility.handleCityResponse(coolWeatherDB,response, selectedProvince.getId());
				} else if ("county".equals(type)) {
				result = Utility.handleCountyResponse(coolWeatherDB,response, selectedCity.getId());
				}
			if(result)
			{
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						closeProgressDialog();
						if ("province".equals(type)) {
						queryProvinces();
						} else if ("city".equals(type)) {
						queryCities();
						} else if ("county".equals(type)) {
						queryCounties();
						}
					}

					
				});
			}
		}

		@Override
		public void onError(Exception e) {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					closeProgressDialog();
					Toast.makeText(ChooseAreaActivity.this,"加载失败", Toast.LENGTH_SHORT).show();
				}
			});
		}});
}
private void showProgressDialog()
{
	if (progressDialog == null)	
	{
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("正在加载...");
		progressDialog.setCanceledOnTouchOutside(false);
	}
	progressDialog.show();
}
private void closeProgressDialog() {
	// TODO Auto-generated method stub
	if (progressDialog != null) {
		progressDialog.dismiss();
		}
}
public void onBackPressed()
{
	if (currentLevel == LEVEL_COUNTY) {
		queryCities();
		} else if (currentLevel == LEVEL_CITY) {
		queryProvinces();
		} else {
		finish();
		}
}
}

