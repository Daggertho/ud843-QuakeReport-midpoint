package com.example.android.quakereport;


import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import android.graphics.drawable.GradientDrawable;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
/**
 * Created by User on 22/5/2017.
 */

public class earthquakeAddapter extends ArrayAdapter<earthquakedata> {

    private static final String LOCATION_SEPARATOR = " of ";
   // public static final String LOG_TAG = earthquakeAddapter.class.getSimpleName();

    public earthquakeAddapter(Activity context, ArrayList<earthquakedata> earthquakes)
    {
        super(context, 0, earthquakes);
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View listItemView = convertView;
            if(listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            }

        //--------------------------------------------------------------------------------------------------------------------------------------
        DecimalFormat formatter = new DecimalFormat("0.00");
        String output = formatter.format(2.3234);
        // Get the {@link AndroidFlavor} object located at this position in the list
        earthquakedata currentEarthquakedata = getItem(position);
            // Find the TextView with view ID magnitude
            TextView magnitudeView = (TextView) listItemView.findViewById(R.id.value);
            // Format the magnitude to show 1 decimal place
            String formattedMagnitude = formatMagnitude(currentEarthquakedata.getVb());
            // Display the magnitude of the current earthquake in that TextView
            magnitudeView.setText(formattedMagnitude);
        //--------------------------------------------------------------------------------------------------------------------------------------

       //--------------------------------------------------------------------------------------------------------------------------------------

            // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.locate);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        numberTextView.setText(currentEarthquakedata.getL());

       //--------------------------------------------------------------------------------------------------------------------------------------

      //--------------------------------------------------------------------------------------------------------------------------------------

        Date dateObject = new Date(currentEarthquakedata.getI());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);
        //--------------------------------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------------------------------------------------------------------------------------------

            //Must declare these strings to store the words before and after the splits
        String primaryLocation;
        String locationOffset;
      //--Gets the string words before and after the word "of"-----------------------------------------------------------------------------------------------
        String originalLocation = currentEarthquakedata.getL();
        if (originalLocation.contains(LOCATION_SEPARATOR)) {

            //the split() method splits the word "of" which was declared  at private static final String LOCATION_SEPARATOR = " of "; above^^^
           String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];

        }
        else
        {
            //if there is no "of" word, then you can replace the word with "Near the" string in strings.xml file at front and splits
            //the words and locations after
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.locate);
        primaryLocationView.setText(primaryLocation);

        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);
        locationOffsetView.setText(locationOffset);
        //--------------------------------------------------------------------------------------------------------------------------------------------

            //--------------------------------------------------------------------------------------------------------------------------------------------
            // Set the proper background color on the magnitude circle.
            // Fetch the background from the TextView, which is a GradientDrawable.
            GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

            // Get the appropriate background color based on the current earthquake magnitude
            int magnitudeColor = getMagnitudeColor(currentEarthquakedata.getVb());

            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor);
            //--------------------------------------------------------------------------------------------------------------------------------------------
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }


    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */


    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

}
