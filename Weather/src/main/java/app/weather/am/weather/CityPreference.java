package app.weather.am.weather;

/**
 * Created by Edgar on 13.11.2016.
 */

import android.app.Activity;
import android.content.SharedPreferences;

/** Вспомогательный класс для хранения выбранного города */

public class CityPreference {

    SharedPreferences _prefs;

    public CityPreference(Activity activity) {
        _prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    // Возвращаем город по умолчанию, если SharedPreferences пустые
    String getCity() {
        return _prefs.getString("city", "Moscow");//Антипаттерн
    }

    void setCity(String city) {
        _prefs.edit().putString("city", city).commit();//Антипаттерн
    }

}