package net.dragonwing.cjkzy.yunchengweather.util;

import android.text.TextUtils;

import net.dragonwing.cjkzy.yunchengweather.db.YunchengWeatherDB;
import net.dragonwing.cjkzy.yunchengweather.model.City;
import net.dragonwing.cjkzy.yunchengweather.model.County;
import net.dragonwing.cjkzy.yunchengweather.model.Province;

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
}
