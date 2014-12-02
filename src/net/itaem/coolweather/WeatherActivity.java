package net.itaem.coolweather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import net.itaem.util.HttpCallbackListener;
import net.itaem.util.HttpUtil;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

public class WeatherActivity extends Activity {
	private TextView placeView;
	private TextView weatherView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);
		
		placeView = (TextView) findViewById(R.id.place_view);
		weatherView = (TextView) findViewById(R.id.weather_view);

		String countyCode = getIntent().getStringExtra("county_code");
		queryWeatherCode(countyCode);
	}

	private void queryWeatherCode(String countyCode) {
		String address = "http://www.weather.com.cn/data/list3/city"
				+ countyCode + ".xml";
		queryFromServer(address, "countyCode");
	}

	private void queryWeatherInfo(String weatherCode) {
		String address = "http://www.weather.com.cn/data/cityinfo/"
				+ weatherCode + ".html";
		queryFromServer(address, "weatherCode");
	}

	private void handlerResponse(String response) {
		// 显示到控件中
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
			String cityName = weatherInfo.getString("city");
			String weatherCode = weatherInfo.getString("cityid");
			String temp1 = weatherInfo.getString("temp1");
			String temp2 = weatherInfo.getString("temp2");
			String weatherDesp = weatherInfo.getString("weather");
			String publishTime = weatherInfo.getString("ptime");
			saveWeatherInfo(this, cityName, weatherCode, temp1, temp2,
					weatherDesp, publishTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void saveWeatherInfo(WeatherActivity weatherActivity,
			String cityName, String weatherCode, String temp1, String temp2,
			String weatherDesp, String publishTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(this).edit();
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

	private void showWeatherInfo() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String cityName = pref.getString("city_name", "");
		String weatherInfo = pref.getString("weather_code", "");
		String temp1 = pref.getString("temp1", "");
		String temp2 = pref.getString("temp2", "");
		String weatherDesp = pref.getString("weather_desp", "");
		String publicTime = pref.getString("publish_time", "");
		String time = pref.getString("current_date", "");

		placeView.setText(cityName);
		weatherView.setText(weatherInfo + "|" + temp1 + "|" + temp2 + "|"
				+ weatherDesp + "|" + publicTime + "|" + time);

	}

	/*
	 * 获取县级对应的天气代码(每次都不同的)
	 */
	private void queryFromServer(String address, final String type) {

		HttpUtil.sendRequest(address, new HttpCallbackListener() {
			@Override
			public void onFinish(String response) {
				if (!TextUtils.isEmpty(response)) {
					if (type.equals("countyCode")) {
						Log.i("weatherCodeJson", response);
						String array[] = response.split("\\|");
						String weatherCode = array[1];
						queryWeatherInfo(weatherCode);
					} else if (type.equals("weatherCode")) {
						Log.i("weatherInfoJson", response);
						handlerResponse(response);// 保存,并显示到控件中
						runOnUiThread(new Runnable() {// 运行在ui主线程
							@Override
							public void run() {
								showWeatherInfo();
							}
						});
					}
				}
			}

			@Override
			public void onError() {
			}
		});
	}

}
