package net.itaem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CoolWeatherDBOpenHelper extends SQLiteOpenHelper {
	private static final String CREATE_CITY = "DROP TABLE IF EXISTS `city`;"
			+ "CREATE TABLE `city` (" + " `id` integer  AUTO_INCREMENT,"
			+ " `city_name` text DEFAULT NULL,"
			+ "`city_code` text DEFAULT NULL,"
			+ " `province_id` integer DEFAULT NULL," + " PRIMARY KEY (`id`)"
			+ ")";
	private static final String CREATE_PROVINCE = "DROP TABLE IF EXISTS `province`;"
			+ "CREATE TABLE `province` ("
			+ " `id` integer  AUTO_INCREMENT,"
			+ " `province_name` text DEFAULT NULL,"
			+ "`province_code` text DEFAULT NULL,"
			+ " PRIMARY KEY (`id`)"
			+ ")";
	private static final String CREATE_COUNTY = "DROP TABLE IF EXISTS `county`;"
			+ "CREATE TABLE `county` ("
			+ " `id` integer  AUTO_INCREMENT,"
			+ " `county_name` text DEFAULT NULL,"
			+ "`county_code` text DEFAULT NULL,"
			+ " `city_id` integer DEFAULT NULL," + " PRIMARY KEY (`id`)" + ")";

	public CoolWeatherDBOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PROVINCE);
		db.execSQL(CREATE_CITY);
		db.execSQL(CREATE_COUNTY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
