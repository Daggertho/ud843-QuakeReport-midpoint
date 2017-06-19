package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by User on 9/6/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<earthquakedata>> {

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<earthquakedata> loadInBackground() {
        if(mUrl==null){
            return null;
        }
        List<earthquakedata> result = QueryUtils.fetchEarthquakeData(mUrl);
        return result;
    }
}
