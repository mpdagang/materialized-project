/*
	KotseLog 1.0
	July 31, 2017
	Marion Paulo A. Dagang

	filename:
*/

package com.example.mpdagang.kotselog.LogBook;

/**
 * Created by MPDagang on 19/06/2017.
 */

public class XYValue {
    private double x;
    private double y;

    public XYValue(double x, double y){
        this.x = x;
        this.y = y;
    }

    public  double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }
}

