package com.example.pavel.favoriteobjectbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class MainActivity extends AppCompatActivity {

    String [] stations = {"Station1","Station2","Station3","Station4","Station5","Station6","Station7","Station8","Station9"};
    BoxStore boxStore;
    Box<Station> stationsBox;
    StationAdapter adapter;
    Button buttonSelect;
    boolean onlyFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boxStore = MyObjectBox.builder().androidContext(this).build();
        stationsBox = boxStore.boxFor(Station.class);

        if (stationsBox.getAll().isEmpty()) {
            // если база не заполнена, то заполняем данными
             for(String station:stations){
                 stationsBox.put(new Station(station));
             }
        }

        buttonSelect = findViewById(R.id.buttonSelect);
        RecyclerView rv = findViewById(R.id.list);

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // переключаем отображение всего списка или только избранного
                onlyFavorite = !onlyFavorite;
                String textButton = onlyFavorite? "All Station": "Only favorite station";
                buttonSelect.setText(textButton);
                //обновляем список
                getDataset(onlyFavorite);
            }
        });

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Station> dataset = getDataset(onlyFavorite);
        adapter = new StationAdapter(dataset);
        rv.setAdapter(adapter);
    }

    private ArrayList<Station> getDataset(boolean onlyFavorite) {
        // делаем выборку в БД. Показывать все станции или только избранные
        ArrayList<Station> dataset = (ArrayList<Station>) (onlyFavorite? stationsBox.query().equal(Station_.favorite, true).build().find(): stationsBox.getAll());
        //обновляем список
        if (adapter != null) adapter.notifyList(dataset);
        return dataset;
    }

    public void onClickFavorite(View view){
        // здесь обрабатываем клик на кнопке избранного в айтеме списка
        // метод вызывается по атрибуту android:onClick xml-разметки айтема

        // Получаем ID станции, на которой кликнули кнопку избранного
        long id = (long) view.getTag();
        // инвертируем отметку избранного
        Station station = stationsBox.get(id);
        station.setFavorite(!station.isFavorite());
        stationsBox.put(station);
        //обновляем список
        getDataset(onlyFavorite);
    }

    @Override
    protected void onStop() {
        super.onStop();
        boxStore.close();
    }
}
