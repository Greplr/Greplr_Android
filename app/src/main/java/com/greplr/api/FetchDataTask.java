package com.greplr.api;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by championswimmer on 20/6/15.
 */

public class FetchDataTask <T> extends AsyncTask<String, Void, List<T>> {
    private String cat, subCat;

    public FetchDataTask(String category, String subcategory) {
        this.cat = category;
        this.subCat = subcategory;
    }

    @Override
    protected List<T> doInBackground(String... args) {

        List<T> objects = new ArrayList<>();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Api.BASE_URL)
                .build();

        Api endPoint = restAdapter.create(Api.class);

        switch (args.length) {
            case 2:default:
                objects = endPoint.getDataSync(
                        cat, subCat,
                        args[0],
                        args[1]);
                break;
            case 3:
                objects = endPoint.getDataSync(
                        cat, subCat,
                        args[0],
                        args[1],
                        args[2]);
                break;
        }

        return objects;
    }
}