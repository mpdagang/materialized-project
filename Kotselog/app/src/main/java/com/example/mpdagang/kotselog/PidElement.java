package com.example.mpdagang.kotselog;

/**
 * Created by MPDagang on 09/07/2017.
 */

public class PidElement {
    private String id;
    private String name;
    private String codeName;
    private String symbol;
    private float min;
    private float max;

    public PidElement(String id, String name, String codeName, String symbol, float min, float max){
        this.id = id;
        this.name = name;
        this.codeName = codeName;
        this.symbol = symbol;
        this.min = min;
        this.max =max;
    }

    public String getId(){
        return id;
    }

    public float getMax() {
        return max;
    }

    public float getMin() {
        return min;
    }

    public String getCodeName() {
        return codeName;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public static float translateValueToGraph(float value, float height){
        return value;
    }

}
