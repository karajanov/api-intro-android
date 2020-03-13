package com.example.starwarsapigetrequests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerPlanetIds;
    StarWarsApi apiReference = RetrofitBuilder
            .getApiReference(RetrofitBuilder.getBuilder());
    ProgressBar loadingBar;
    TextView textViewPlanetInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialization
        spinnerPlanetIds = findViewById(R.id.spinner_ids);
        loadingBar = findViewById(R.id.loading_bar);
        textViewPlanetInfo = findViewById(R.id.text_view_planet_info);

        //event handlers
        loadingBar.setVisibility(View.GONE);
        addPlanetIdsToSpinner(apiReference);
        spinnerPlanetIds.setOnItemSelectedListener(this);

    }

    private void addPlanetIdsToSpinner(StarWarsApi apiReference) {

        Call<PlanetCounter> call = apiReference.getPlanetCount();
        loadingBar.setVisibility(View.VISIBLE);

        //executing the request on a background thread to prevent
        //freezing the main thread and crashing the app
        call.enqueue(new Callback<PlanetCounter>() {
            @Override
            public void onResponse(Call<PlanetCounter> call, Response<PlanetCounter> response) {

                loadingBar.setVisibility(View.GONE);

                //HTTP 4** , 5** status codes
                if (!response.isSuccessful()) {

                    Log.e(this.getClass().getSimpleName(), String.valueOf(response.code()));
                    return;
                }

                try {

                    int planetCount = response.body().getCount();
                    Integer[] planetIds = getPlanetIds(planetCount);
                    addItemsToSpinner(planetIds, spinnerPlanetIds);

                }catch (Exception exc) {

                    Log.e(this.getClass().getSimpleName(), exc.getMessage());
                }
            }

            @Override
            public void onFailure(Call<PlanetCounter> call, Throwable t) {

                Log.e(this.getClass().getSimpleName(), t.getMessage());
            }
        });

    }

    private Integer[] getPlanetIds(int count) {

        Integer[] ids = new Integer[count];

        for (int i = 0; i < count; ++i) {
            ids[i] = i + 1;
        }

        return ids;
    }

    private <T> void addItemsToSpinner(T[] inputArr, Spinner s) {

        ArrayAdapter<T> adapter = new ArrayAdapter<T>(
                this, android.R.layout.simple_spinner_item, inputArr
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

    }

    private void displayPlanetInfo(int planetId) {

        Call<Planet> call = apiReference.getPlanetInfo(planetId);
        loadingBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(Call<Planet> call, Response<Planet> response) {

                loadingBar.setVisibility(View.GONE);

                if (!response.isSuccessful()) {
                    textViewPlanetInfo.setText("Resource not found");
                    return;
                }

                String info = null;

                try {

                    info = "Name: " + response.body().getName() +
                            "\nClimate: " + response.body().getClimate() +
                            "\nTerrain: " + response.body().getTerrain() +
                            "\nPopulation: " + response.body().getPopulation();

                } catch (Exception exc) {

                    info = exc.getMessage();
                }

                textViewPlanetInfo.setText(info);
            }

            @Override
            public void onFailure(Call<Planet> call, Throwable t) {

                textViewPlanetInfo.setText(t.getMessage());
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int selectedPlanetId = Integer
                .parseInt(spinnerPlanetIds.getSelectedItem().toString());

        displayPlanetInfo(selectedPlanetId);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
