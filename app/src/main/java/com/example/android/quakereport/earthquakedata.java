package com.example.android.quakereport;

/**
 * Created by User on 22/5/2017.
 */

public class earthquakedata {
    private double values_blabla;
    private String location;
    private long dates;
    private String url;

    public earthquakedata() {
    }

    public earthquakedata(double vb, String l, long im, String u) {
        values_blabla = vb;
        location = l;
        dates = im;
        url = u;
    }

    public double getVb(){return values_blabla;}
    public String getL(){return location;}
    public long getI(){return dates;}
    public String getUrl(){return url;}

    @Override
    public String toString() {
        return "earthquakedata{" +
                "values_blabla=" + values_blabla +
                ", location='" + location + '\'' +
                ", dates=" + dates +
                ", url='" + url + '\'' +
                '}';
    }
}
