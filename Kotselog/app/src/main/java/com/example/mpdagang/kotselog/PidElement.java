/*
	KotseLog 1.0
	July 31, 2017
	Marion Paulo A. Dagang

	filename:
*/

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
        setSubAttributes();
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
        return this.codeName;
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
        switch (this.id) {
            case "null":
                this.codeName = "";
                break;
            case "0101":
                this.codeName = "Monitor DLC";
                break;
            case "0103":
                this.codeName = "Fuel System Stat";
                break;
            case "0104":
                this.name = "Calculated Engine Load";
                this.codeName = "Engine Load";
                this.symbol = "%";
                this.min = 0;
                this.max = 100;
                break;
            case "0105":
                this.name = "Engine Coolant Tempeerature";
                this.codeName = "Coolant Temp";
                this.symbol = "C";
                this.min = -40;
                this.max = 215;
                break;
            case "0106":
                this.name = "Short Term Fuel Trim Bank 1";
                this.codeName = "F Trim S B1";
                this.symbol = "%";
                this.min = -100;
                this.max = (float) 99.2;
                break;
            case "0107":
                this.name = "Long Term Fuel Trim Bank 1";
                this.codeName = "F Trim L B1";
                this.symbol = "%";
                this.min = -100;
                this.max = (float) 99.2;
                break;
            case "0108":
                this.name = "Short Term Fuel Trim Bank 2";
                this.codeName = "F Trim S B2";
                this.symbol = "%";
                this.min = -100;
                this.max = (float) 99.2;
                break;
            case "0109":
                this.name = "Long Term Fuel Trim bank 2";
                this.codeName = "F Trim L B2";
                this.symbol = "%";
                this.min = -100;
                this.max = (float) 99.2;
                break;
            case "010A":
                this.name = "Fuel Pressure";
                this.codeName = "Fuel Pressure";
                this.symbol = "kPa";
                this.min = 0;
                this.max = 765;
                break;
            case "010B":
                this.name = "Intake Manifold Absolute Pressure";
                this.codeName = "MAP";
                this.symbol = "kPa";
                this.min = 0;
                this.max = 255;
                break;
            case "010C":
                this.name = "Engine RPM";
                this.codeName = "RPM";
                this.symbol = "r/m";
                this.min = 0;
                this.max = 16383;
                break;
            case "010D":
                this.name = "Vehicle Speed";
                this.codeName = "Vehicle Speed";
                this.symbol = "km/h";
                this.min = 0;
                this.max = 255;
                break;
            case "010E":
                this.name = "Timing Advance";
                this.codeName = "Timing";
                this.symbol = "TDC";
                this.min = -64;
                this.max = (float) 63.5;
                break;
            case "010F":
                this.name = "Intake Air Temperature";
                this.codeName = "Air Temp";
                this.symbol = "C";
                this.min = -40;
                this.max = 215;
                break;

            case "0110":
                this.name = "MAF Air Flow Rate";
                this.codeName = "MAF";
                this.symbol = "grams/sec";
                this.min = 0;
                this.max = (float)655.35;
                break;
            case "0111":
                this.name = "Throttle Position";
                this.codeName = "Throttle";
                this.symbol = "%";
                this.min = 0;
                this.max = 100;
                break;
            case "0113":
                this.name = "Oxygen Bank";
                this.codeName = "Oxygen Bank";
                this.symbol = "%";
                this.min = 0;
                this.max = 100;
                break;
            case "0114":
            case "0115":
            case "0116":
            case "0117":
            case "0118":
            case "0119":
            case "011A":
            case "011B":
                this.name = "Oxygen";
                this.codeName = "Oxygen";
                this.symbol = "%";
                this.min = -100;
                this.max = (float)99.2;
                break;
        }
    }

    @Override
    public String toString() {
        return this.codeName;
    }
}
