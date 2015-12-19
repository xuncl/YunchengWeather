package net.dragonwing.cjkzy.yunchengweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import net.dragonwing.cjkzy.yunchengweather.db.YunchengWeatherDB;
import net.dragonwing.cjkzy.yunchengweather.model.City;
import net.dragonwing.cjkzy.yunchengweather.model.County;
import net.dragonwing.cjkzy.yunchengweather.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xuncl on 2015/12/4.
 */
public class Utility {

    /**
     * deal with the province data from server
     * @param yunchengWeatherDB
     * @param response
     * @return is the save successful
     */
    public synchronized static boolean handleProvincesResponse(YunchengWeatherDB yunchengWeatherDB, String response){
        if (!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if (null!=allProvinces && allProvinces.length>0){
                for (String p : allProvinces){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    yunchengWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * deal with the city data from server
     * @param yunchengWeatherDB
     * @param response
     * @return is the save successful
     */
    public static boolean handeCitiesResponse(YunchengWeatherDB yunchengWeatherDB, String response, int provinceId){
        if (!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if (null!=allCities && allCities.length>0){
                for (String c : allCities){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    yunchengWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * deal with the count data from server
     * @param yunchengWeatherDB
     * @param response
     * @return is the save successful
     */
    public static boolean handeCountiesResponse(YunchengWeatherDB yunchengWeatherDB, String response, int cityId){
        if (!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if (null!=allCounties && allCounties.length>0){
                for (String c : allCounties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    yunchengWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * analyse JSON data from server, store information to local
     * @param context
     * @param response
     */
    public static void handleWeatherResponse(Context context, String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2, weatherDesp, publishTime);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * Save all information to SharedRreferences files
     * @param context
     * @param cityName
     * @param weatherCode
     * @param temp1
     * @param temp2
     * @param weatherDesp
     * @param publishTime
     */
    public static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1, String temp2, String weatherDesp, String publishTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time",publishTime);
        editor.putString("current_date",sdf.format(new Date()));
        editor.commit();
    }

}
