package net.itaem.util;

import android.content.Context;
import android.text.TextUtils;
import net.itaem.db.CoolWeatherUtil;
import net.itaem.model.City;
import net.itaem.model.County;
import net.itaem.model.Province;

/**
 * @author Administrator ���������ӿڷ��ص����ݲ�����洢�����ݿ����
 */
public class Utility {
	public static boolean handlerProvinceResponse(String response,
			Context context) {
		// ʡ����������json������֮��洢�����ݿ���
		if (!TextUtils.isEmpty(response)) {
			String ps[] = response.split("|");
			for (String p : ps) {
				String array[] = p.split("|");
				String provinceCode = array[0];
				String provinceName = array[1];
				Province province = new Province(provinceName, provinceCode);
				CoolWeatherUtil db = CoolWeatherUtil.getInstance(context);
				db.saveProvince(province);
			}
			return true;
		}
		return false;
	}

	public static boolean handlerCityResponse(String response, Context context) {
		if (!TextUtils.isEmpty(response)) {
			String cs[] = response.split("|");
			for (String c : cs) {
				// ����֮��洢�����ݿ���
				String array[] = c.split("|");
				String cityCode = array[0];
				String cityName = array[1];
				City city = new City(cityName, cityCode);
				CoolWeatherUtil db = CoolWeatherUtil.getInstance(context);
				db.saveCity(city);
			}
			return true;
		}
		return false;
	}

	public static boolean handlerCountyResponse(String response, Context context) {
		if (!TextUtils.isEmpty(response)) {
			String cs[] = response.split("|");
			for (String c : cs) {
				// ����֮��洢�����ݿ���
				String array[] = c.split("|");
				String countyCode = array[0];
				String countyName = array[1];
				County county = new County(countyName, countyCode);
				CoolWeatherUtil db = CoolWeatherUtil.getInstance(context);
				db.saveCounty(county);
			}
			return true;
		}
		return false;
	}

}
