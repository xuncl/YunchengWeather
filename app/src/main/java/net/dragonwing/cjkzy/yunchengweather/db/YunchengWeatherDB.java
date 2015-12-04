package net.dragonwing.cjkzy.yunchengweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.dragonwing.cjkzy.yunchengweather.model.City;
import net.dragonwing.cjkzy.yunchengweather.model.County;
import net.dragonwing.cjkzy.yunchengweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuncl on 2015/12/4.
 */
public class YunchengWeatherDB {

    /**
     * db name
     */
    public static final String DB_NAME = "yuncheng_weather";

    /**
     * db version
     */
    public static final int VERSION = 1;

    private static YunchengWeatherDB yunchengWeatherDB;

    private SQLiteDatabase db;

    /**
     *  build up YunchengWeatherDB
     * @param context
     */
    private YunchengWeatherDB(Context context){
        YunchengWeatherOpenHelper dbHelper = new YunchengWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * get the singlar implement of YunchengWeatherDB
     * @param context
     * @return implement of YunchengWeatherDB
     */
    public synchronized static YunchengWeatherDB getInstance(Context context){
        if (null == yunchengWeatherDB) {
            yunchengWeatherDB = new YunchengWeatherDB(context);
        }
        return yunchengWeatherDB;
    }

    /**
     * save province data to db
     * @param province
     */
    public void saveProvince(Province province) {
        if (null != province) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    /**
     * get all provinces from db
     * @return
     */
    public List<Province> loadProvinces() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                do {
                    Province province = new Province();
                    province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                    province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                    list.add(province);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    /**
     * save city data to db
     * @param city
     */
    public void saveCity(City city) {
        if (null != city) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id", city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    /**
     * get all cities from a certain province
     * @param provinceId
     * @return list of cities
     */
    public List<City> loadCities(int provinceId) {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                do {
                    City city = new City();
                    city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                    city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                    city.setProvinceId(provinceId);
                    list.add(city);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    /**
     * save county data to db
     * @param county
     */
    public void saveCounty(County county) {
        if (null != county) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("county_code", county.getCountyCode());
            values.put("city_id", county.getCityId());
            db.insert("County", null, values);
        }
    }

    /**
     * get all counties from a certain city
     * @param cityId
     * @return list of counties
     */
    public List<County> loadCounties(int cityId) {
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_id = ?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                do {
                    County county = new County();
                    county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                    county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                    county.setCityId(cityId);
                    list.add(county);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }



}
