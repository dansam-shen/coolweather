package net.itaem.db;

import java.util.ArrayList;
import java.util.List;

import net.itaem.model.City;
import net.itaem.model.County;
import net.itaem.model.Province;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherUtil {
	private final static int VERSION = 1;
	private final static String DB_NAME = "cool_weather";
	private SQLiteDatabase database;

	private CoolWeatherUtil(Context context) {
		CoolWeatherDBOpenHelper helper = new CoolWeatherDBOpenHelper(context,
				DB_NAME, null, VERSION);
		database = helper.getWritableDatabase();
	};

	private static CoolWeatherUtil helper;

	public synchronized static CoolWeatherUtil getInstance(Context context) {
		if (helper == null) {
			helper = new CoolWeatherUtil(context);
		}
		return helper;
	}

	// 插入省级数据
	public boolean saveProvince(Province province) {
		String sql = "insert into province(province_name,province_code) values(?,?)";
		if (province != null) {
			Object[] objs = new Object[] { province.getProvinceName(),
					province.getPrivinceCode() };
			database.execSQL(sql, objs);
			return true;
		}
		return false;
	}

	public List<Province> getProvinces() {
		String sql = "select * from province";
		Cursor cursor = database.rawQuery(sql, null);
		List<Province> provinces = new ArrayList<Province>();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String provinceName = cursor.getString(cursor
					.getColumnIndex("province_name"));
			String provinceCode = cursor.getString(cursor
					.getColumnIndex("province_code"));
			Province province = new Province(id, provinceName, provinceCode);
			provinces.add(province);
		}
		return provinces;
	}

	// 插入省级数据
	public boolean saveCity(City city) {
		String sql = "insert into city(city_name,city_code,province_id) values(?,?,?)";
		if (city != null) {
			Object[] objs = new Object[] { city.getCityName(),
					city.getCityCode(), city.getProvinceId() };
			database.execSQL(sql, objs);
			return true;
		}
		return false;
	}

	public List<City> getCitys() {
		String sql = "select * from city";
		Cursor cursor = database.rawQuery(sql, null);
		List<City> citys = new ArrayList<City>();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String cityName = cursor.getString(cursor
					.getColumnIndex("city_name"));
			String cityCode = cursor.getString(cursor
					.getColumnIndex("city_code"));
			int provinceId = cursor
					.getInt(cursor.getColumnIndex("province_id"));
			City city = new City(id, cityName, cityCode, provinceId);
			citys.add(city);
		}
		return citys;
	}

	// 插入省级数据
	public boolean saveCounty(County county) {
		String sql = "insert into county(county_name,county_code,city_id) values(?,?)";
		if (county != null) {
			Object[] objs = new Object[] { county.getCountyName(),
					county.getCountyCode(), county.getCityId() };
			database.execSQL(sql, objs);
			return true;
		}
		return false;
	}

	public List<County> getCountys() {
		String sql = "select * from county";
		Cursor cursor = database.rawQuery(sql, null);
		List<County> countys = new ArrayList<County>();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String countyName = cursor.getString(cursor
					.getColumnIndex("county_name"));
			String countyCode = cursor.getString(cursor
					.getColumnIndex("county_code"));
			int cityId = cursor.getInt(cursor.getColumnIndex("city_id"));

			County county = new County(id, countyName, countyCode, cityId);
			countys.add(county);
		}
		return countys;
	}

}
