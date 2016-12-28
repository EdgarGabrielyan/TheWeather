package app.weather.am.weather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/** Основной класс нашего приложения. По сути - просто контейнер для UI*/

public class WeatherActivity extends AppCompatActivity {

    //При создании Активити
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container_for_fragment, new WeatherFragment())
                    .commit();
        }
    }

    //Надуваем меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    //Ловим нажатие кнопки меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_city) {
            showInputDialog();
        }
        return false;
    }

    //Показываем диалоговое окно с выбором города
    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Антипаттерн getString(R.string.change_city_dialog)
        builder.setTitle("Change city");

        //Антипаттерн (MVP)
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString());
            }
        });
        builder.show();
    }

    //Обновляем вид, сохраняем выбранный город
    public void changeCity(String city) {
        WeatherFragment wf = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.container_for_fragment);
        wf.changeCity(city);
        new CityPreference(this).setCity(city);
    }

}