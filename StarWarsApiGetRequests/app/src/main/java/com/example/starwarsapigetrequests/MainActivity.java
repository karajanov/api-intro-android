package com.example.starwarsapigetrequests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerPlanetIds;
    StarWarsApi apiReference = RetrofitBuilder
            .getApiReference(RetrofitBuilder.getBuilder());

    TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerPlanetIds = findViewById(R.id.spinner_ids);

        tvTest = findViewById(R.id.text_view_test);

        addPlanetIdsToSpinner(apiReference);

//        String[] arr = {"1", "2", "3"};
//
//
//        spinnerIds.setAdapter(adapter);
    }

    private void addPlanetIdsToSpinner(StarWarsApi apiReference) {

        Call<PlanetCounter> call = apiReference.getPlanetCount();

        //executing the request on a background thread to prevent
        //freezing the main thread and crashing the app
        call.enqueue(new Callback<PlanetCounter>() {
            @Override
            public void onResponse(Call<PlanetCounter> call, Response<PlanetCounter> response) {

                //HTTP 4** , 5** status codes
                if (!response.isSuccessful()) {

                    Log.e(this.getClass().getSimpleName(), String.valueOf(response.code()));

                } else {

                    int planetCount = response.body().getCount();
                    Integer[] planetIds = getPlanetIds(planetCount);
                    addItemsToSpinner(planetIds, spinnerPlanetIds);
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

        for(int i=0; i < count; ++i) {
            ids[i] = i+1;
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
}
