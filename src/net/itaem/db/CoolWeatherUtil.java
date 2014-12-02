package net.itaem.db;

import java.util.ArrayList;
import java.util.List;

import net.itaem.model.City;
import net.itaem.model.County;
import net.itaem.model.Province;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Administrator 将数据进行持久化的类
 */
public class CoolWeatherUtil {
	private final static int VERSION = 1;
	private final static String DB_NAME = "my_cool_weather";
	private SQLiteDatabase db;
	private static CoolWeatherUtil dbUtil;

	private CoolWeatherUtil(Context context) {
		CoolWeatherOpenHelper helper = new CoolWeatherOpenHelper(context,
				DB_NAME, null, VERSION);
		db = helper.getWritableDatabase();
	}

	public synchronized static CoolWeatherUtil getInstance(Context context) {
		if (dbUtil == null) {
			dbUtil = new CoolWeatherUtil(context);
		}
		return dbUtil;
	}

	// 插入省级数据
	public boolean saveProvince(Province province) {
		String sql = "insert into Province(province_name,province_code) values(?,?)";
		if (province != null) {
			Object[] objs = new Object[] { province.getProvinceName(),
					province.getPrivinceCode() };
			db.execSQL(sql, objs);
			return true;
		}
		return false;
	}

	public List<Province> getProvinces() {
		String sql = "select * from Province";
		Cursor cursor = db.rawQuery(sql, null);
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
			db.execSQL(sql, objs);
			return true;
		}
		return false;
	}

	public List<City> getCitys(int provinceId) {
		String sql = "select * from city where province_id=?";
		String objs[] = new String[] { provinceId + "" };
		Cursor cursor = db.rawQuery(sql, objs);
		List<City> citys = new ArrayList<City>();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String cityName = cursor.getString(cursor
					.getColumnIndex("city_name"));
			String cityCode = cursor.getString(cursor
					.getColumnIndex("city_code"));
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
			db.execSQL(sql, objs);
			return true;
		}
		return false;
	}

	public List<County> getCountys(int cityId) {
		String sql = "select * from county where city_id=?";
		String objs[] = new String[] { cityId + "" };
		Cursor cursor = db.rawQuery(sql, objs);
		List<County> countys = new ArrayList<County>();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String countyName = cursor.getString(cursor
					.getColumnIndex("county_name"));
			String countyCode = cursor.getString(cursor
					.getColumnIndex("county_code"));
			County county = new County(id, countyName, countyCode, cityId);
			countys.add(county);
		}
		return countys;
	}

}
