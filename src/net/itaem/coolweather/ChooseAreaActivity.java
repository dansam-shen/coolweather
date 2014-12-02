package net.itaem.coolweather;

import java.util.ArrayList;
import java.util.List;

import net.itaem.db.CoolWeatherUtil;
import net.itaem.model.City;
import net.itaem.model.County;
import net.itaem.model.Province;
import net.itaem.util.HttpCallbackListener;
import net.itaem.util.HttpUtil;
import net.itaem.util.Utility;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChooseAreaActivity extends Activity {
	private ArrayAdapter<String> adapter;
	private List<String> dataList=new ArrayList<String>();
	private CoolWeatherUtil db;

	private int currentLevel;
	private final int LEVEL_PROVINCE = 1;
	private final int LEVEL_CITY = 2;
	private final int LEVEL_COUNTY = 3;

	private Province selectedProvince;

	private City selectedCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		db = CoolWeatherUtil.getInstance(this);
		ListView chooses = (ListView) findViewById(R.id.chooses);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,dataList);
		chooses.setAdapter(adapter);
		
		chooses.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (currentLevel == LEVEL_PROVINCE) {
					// ��province��ҳ�汻���
					selectedProvince = provinceList.get(position);
					queryCitys(selectedProvince.getId());
				} else if (currentLevel == LEVEL_CITY) {
					// cityҳ����
					selectedCity = cityList.get(position);
					queryCountys(selectedCity.getId());
				} else if (currentLevel == LEVEL_COUNTY) {
					// ����������ѯ
					selectedCounty=countyList.get(position);
					queryWeather();
				}

			}
		});
		queryProvinces();
	}

	private List<City> cityList;
	private List<County> countyList;

	private void queryCitys(int provinceId) {
		// ͨ��ѡ�е�province��ѯcity
		// ��ѯ���ݿ�������
		cityList = db.getCitys(provinceId);
		if (cityList.size() > 0) {
			dataList.clear();
			for (City c : cityList) {
				dataList.add(c.getCityName());
			}
			adapter.notifyDataSetChanged();
			currentLevel=LEVEL_CITY;
		} else {
			// ������ݿ�û�����������������
			queryFromServer(selectedCity.getCityCode(), "county");
		}
	}
	private County selectedCounty ;
	private void queryWeather() {
		// �õ��ؼ���֮�����ڱ�ҳ�����������,��ת����һ��ҳ����в�ѯ
		Intent intent =new Intent(this,WeatherActivity.class);
		intent.putExtra("county_code", selectedCounty.getCountyCode());
		startActivity(intent);
	}

	private void queryCountys(int cityId) {
		// ͨ��ѡ�е�province��ѯcity
		// ��ѯ���ݿ�������
		countyList = db.getCountys(cityId);
		if (countyList.size() > 0) {
			dataList.clear();
			for (County c : countyList) {
				dataList.add(c.getCountyName());
			}
			adapter.notifyDataSetChanged();
			currentLevel=LEVEL_COUNTY;
		} else {
			// ������ݿ�û�����������������
			queryFromServer(selectedProvince.getPrivinceCode(), "city");
		}
	}

	private List<Province> provinceList;

	private void queryProvinces() {
		// ��ѯ���ݿ�������
		currentLevel = LEVEL_PROVINCE;
		provinceList = db.getProvinces();
		if (provinceList.size() > 0) {
			dataList.clear();
			for (Province p : provinceList) {
				dataList.add(p.getProvinceName());
			}
			adapter.notifyDataSetChanged();
		} else {
			// ������ݿ�û�����������������
			queryFromServer(null, "province");
		}
	}

	private void queryFromServer(String code, final String type) {
		String address = null;
		if (TextUtils.isEmpty(code)) {
			address = "http://www.weather.com.cn/data/list3/city" + code
					+ ".xml";
		} else if (type.equals("city")) {
			address = "http://www.weather.com.cn/data/list3/city.xml";
		}
		HttpUtil.sendRequest(address, new HttpCallbackListener() {
			@Override
			public void onFinish(String response) {
				if (type.equals("province")) {
					Utility.handlerProvinceResponse(response,
							ChooseAreaActivity.this);
					queryProvinces();
				} else if (type.equals("city")) {
					Utility.handlerCityResponse(response,
							ChooseAreaActivity.this);
					queryCitys(selectedProvince.getId());
				} else if (type.equals(countyList)) {
					Utility.handlerCountyResponse(response,
							ChooseAreaActivity.this);
					queryCountys(selectedCity.getId());
				}
			}

			@Override
			public void onError() {

			}
		});
	}

}
