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

    public PidElement(String id){
        this.id = id;
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

    private void setSubAttributes(){
        /*
        * 0 1 2 3 4 5 6 - 0
        * 0 1 2 3 4 5 6 - 1
        * 0 1 2 3 4 5 6 - 2
        * 0 1 2 3 4 5 6 - 3
        * 0 1 2 3 4 5 6 - 4
        * 0 1 2 3 4 5 6 - 5
        * 0 1 2 3 4 5 6 - 6
        * 0 1 2 3 4 5 6 - 7
        * 0 1 2 3 4 5 6 - 8
        * 0 1 2 3 4 5 6 - 9
        * 0 1 2 3 4 5 6 - A
        * 0 1 2 3 4 5 6 - B
        * 0 1 2 3 4 5 6 - C
        * 0 1 2 3 4 5 6 - D
        * 0 1 2 3 4 5 6 - E
        * 0 1 2 3 4 5 6 - F
        * */

    }

}
