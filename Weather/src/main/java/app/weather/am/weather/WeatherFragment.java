package app.weather.am.weather;

/**
 * Created by Edgar on 13.11.2016.
 */

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/** Фрагмент, в нашем случае - основной UI приложения */

public class WeatherFragment extends Fragment {

    //Классовые переменные

    //Реализация иконок погоды через шрифт (но можно и через setImageDrawable)
    Typeface _weatherFont;

    TextView _cityField = null;
    TextView _updatedField = null;
    TextView _detailsField = null;
    TextView _currentTemperatureField = null;
    TextView _weatherIcon = null;

    Handler _handler = null;

    //Конструктор класса
    public WeatherFragment() {
        _handler = new Handler();
    }

    //Callback при создании класса (Жизненный цикл Фрагмента)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        updateWeatherData(new CityPreference(getActivity()).getCity());
    }

    //Callback при создании класса (Жизненный цикл Фрагмента)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        _cityField = (TextView) rootView.findViewById(R.id.city_field);
        _updatedField = (TextView) rootView.findViewById(R.id.updated_field);
        _detailsField = (TextView) rootView.findViewById(R.id.details_field);
        _currentTemperatureField = (TextView) rootView.findViewById(R.id.current_temperature_field);
        _weatherIcon = (TextView) rootView.findViewById(R.id.weather_icon);

        _weatherIcon.setTypeface(_weatherFont);
        return rootView;
    }

    //Обновление/загрузка погодных данных
    private void updateWeatherData(final String city) {
        new Thread() {
            public void run() {
                final JSONObject json = WeatherData.getJSONData(getActivity(), city);
                if (json == null) {
                    _handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    _handler.post(new Runnable() {
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    //Обработка загруженных данных
    private void renderWeather(JSONObject json) {
        try {
            _cityField.setText(json.getString("name").toUpperCase(Locale.US) + ", "
                    + json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            _detailsField.setText(details.getString("description").toUpperCase(Locale.US) + "\n" + "Humidity: "
                    + main.getString("humidity") + "%" + "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            _currentTemperatureField.setText(String.format("%.2f", main.getDouble("temp")) + " ℃");

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt") * 1000));
            _updatedField.setText("Last update: " + updatedOn);

            setWeatherIcon(details.getInt("id"), json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    //Подстановка нужной иконки
    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            Log.d("SimpleWeather", "id " + id);
            switch (id) {
                case 2:
                    icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 5:
                    icon = getActivity().getString(R.string.weather_rainy);
                    break;
                case 6:
                    icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 7:
                    icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = getActivity().getString(R.string.weather_cloudy);
                    break;
            }
        }
        _weatherIcon.setText(icon);
    }

    //Метод для доступа кнопки меню к данным
    public void changeCity(String city) {
        updateWeatherData(city);
    }

}
