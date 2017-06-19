/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;


import android.content.Intent;
import android.content.Loader;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager;


import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

import java.util.List;


public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<earthquakedata>>
{

        private static final String LOG_TAG = EarthquakeActivity.class.getName();

        /**
         * URL for earthquake data from the USGS dataset
         */
        private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query.geojson?starttime=2017-06-12%2000:00:00&endtime=2017-06-19%2023:59:59&minmagnitude=2.5&maxmagnitude=10&orderby=time";

        /**
         * Constant value for the earthquake loader ID. We can choose any integer.
         * This really only comes into play if you're using multiple loaders.
         */
        private static final int EARTHQUAKE_LOADER_ID = 1;

        /**
         * Adapter for the list of earthquakes
         */
        private earthquakeAddapter mAdapter;

    /** TextView that is displayed when the list is empty */
        private TextView mEmptyStateTextView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.earthquake_activity);

            // Find a reference to the {@link ListView} in the layout
            ListView earthquakeListView = (ListView) findViewById(R.id.list);

            ArrayList<earthquakedata> blabla = new ArrayList<>();
            earthquakedata earth = new earthquakedata();
            // Create a new adapter that takes an empty list of earthquakes as input
            mAdapter = new earthquakeAddapter(this, blabla);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(mAdapter);

            /** TextView that is displayed when the list is empty */
            mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
            earthquakeListView.setEmptyView(mEmptyStateTextView);


            // Set an item click listener on the ListView, which sends an intent to a web browser
            // to open a website with more information about the selected earthquake.
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Find the current earthquake that was clicked on
                    earthquakedata currentEarthquake = mAdapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }

        @Override
        public Loader<List<earthquakedata>> onCreateLoader(int i, Bundle bundle) {
            // Create a new loader for the given URL
            return new EarthquakeLoader(this, USGS_REQUEST_URL);
        }

    @Override
    public void onLoadFinished(Loader<List<earthquakedata>> loader, List<earthquakedata> earthquakes) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_earthquakes);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }

        @Override
        public void onLoaderReset(Loader<List<earthquakedata>> loader) {
            // Loader reset, so we can clear out our existing data.
            mAdapter.clear();
        }
    }

/*public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<earthquakedata>> {

        private static final String JSON_RESPONSE =  "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";
        //"http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";
        //"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

        public static final String LOG_TAG = EarthquakeActivity.class.getName();

        private static final int EARTHQUAKE_LOADER_ID = 1;

        private earthquakeAddapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.earthquake_activity);

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        }

        private void updateUi(ArrayList<earthquakedata> earthquakes) {

            ListView earthquakeListView = (ListView) findViewById(R.id.list);

            adapter = new earthquakeAddapter(this, earthquakes);

            earthquakeListView.setAdapter(adapter);

        }

        @Override
        public Loader<List<earthquakedata>> onCreateLoader(int i, Bundle bundle) {
            return new EarthquakeLoader(this, JSON_RESPONSE);
        }

        @Override
        public void onLoadFinished(Loader<List<earthquakedata>> loader, List<earthquakedata> earthquakes) {
            if (earthquakes != null && !earthquakes.isEmpty()) {
                adapter.addAll(earthquakes);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<earthquakedata>> loader) {
            adapter.clear();
        }
    }*/