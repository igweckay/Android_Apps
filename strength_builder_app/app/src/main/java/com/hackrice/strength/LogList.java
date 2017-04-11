package com.hackrice.strength;

/**
 * Created by kayigwe on 1/16/16.
 */
public class LogList {
    private String _position, _date, _time, _data, _improve;

    public LogList (String position, String date, String time, String data, String improve){
        _position = position;
        _date = date;
        _time = time;
        _data = data;
        _improve = improve;
    }

    public String getPosition(){
        return _position;
    }

    public String getDate(){
        return _date;
    }

    public String getTime(){
        return _time;
    }

    public String getData(){
        return _data;
    }

    public String getImprove(){
        return _improve;
    }

}
