/*
	KotseLog 1.0
	July 31, 2017
	Marion Paulo A. Dagang

	filename:
*/

package com.example.mpdagang.kotselog;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by MPDagang on 05/07/2017.
 */

public class OBDManager {
    private static final String TAG = "OBDManager";
    /*
    private static final ArrayList<PidElement> pidMasterList = new ArrayList<PidElement>() {{
        add(new PidElement("0101", "1", "1", "1", 1, 1));
        add(new PidElement("0102", "1", "1", "1", 1, 1));
        add(new PidElement("0103", "1", "1", "1", 1, 1));
        add(new PidElement("0104", "1", "1", "1", 1, 1));
        add(new PidElement("0105", "1", "1", "1", 1, 1));
        add(new PidElement("0106", "1", "1", "1", 1, 1));
        add(new PidElement("0107", "1", "1", "1", 1, 1));
        add(new PidElement("0108", "1", "1", "1", 1, 1));
        add(new PidElement("0109", "1", "1", "1", 1, 1));
        add(new PidElement("010A", "1", "1", "1", 1, 1));
        add(new PidElement("010B", "1", "1", "1", 1, 1));
        add(new PidElement("010C", "1", "1", "1", 1, 1));
        add(new PidElement("010D", "1", "1", "1", 1, 1));
        add(new PidElement("010E", "1", "1", "1", 1, 1));
        add(new PidElement("010F", "1", "1", "1", 1, 1));
        add(new PidElement("0110", "1", "1", "1", 1, 1));
        add(new PidElement("0111", "1", "1", "1", 1, 1));
        add(new PidElement("0112", "1", "1", "1", 1, 1));
        add(new PidElement("0113", "1", "1", "1", 1, 1));
        add(new PidElement("0114", "1", "1", "1", 1, 1));
        add(new PidElement("0115", "1", "1", "1", 1, 1));
        add(new PidElement("0116", "1", "1", "1", 1, 1));
        add(new PidElement("0117", "1", "1", "1", 1, 1));
        add(new PidElement("0118", "1", "1", "1", 1, 1));
        add(new PidElement("0119", "1", "1", "1", 1, 1));
        add(new PidElement("011A", "1", "1", "1", 1, 1));
        add(new PidElement("011B", "1", "1", "1", 1, 1));
        add(new PidElement("011C", "1", "1", "1", 1, 1));
        add(new PidElement("011D", "1", "1", "1", 1, 1));
        add(new PidElement("011E", "1", "1", "1", 1, 1));
        add(new PidElement("011F", "1", "1", "1", 1, 1));
        add(new PidElement("0120", "1", "1", "1", 1, 1));
    }}; */
    public String test = "A3 00 BE 3E B0 00";
    private static ArrayList<String> pidListA = new ArrayList<String>(){{
        add("0101");
        add("0102");
        add("0103");
        add("0104");
        add("0105");
        add("0106");
        add("0107");
        add("0108");
        add("0109");
        add("010A");
        add("010B");
        add("010C");
        add("010D");
        add("010E");
        add("010F");
        add("0110");
        add("0111");
        add("0112");
        add("0113");
        add("0114");
        add("0115");
        add("0116");
        add("0117");
        add("0118");
        add("0119");
        add("011A");
        add("011B");
        add("011C");
        add("011D");
        add("011E");
        add("011F");
        add("0120");
    }};
    private static String[] staticLookup = new String[] {
            "0000",
            "0001",
            "0010",
            "0011",
            "0100",
            "0101",
            "0110",
            "0111",
            "1000",
            "1001",
            "1010",
            "1011",
            "1100",
            "1101",
            "1110",
            "1111"
    };

    public OBDManager(){

    }


    public static ArrayList<String> decodeAvailable(String response){
        String step1 = response.replaceAll("\\s+", "").replaceAll(">", "");
        String step2 = step1.substring(4);
        String[] step3 = step2.split("");
        ArrayList<String> step4 = new ArrayList<>();

        for(int i = 1; i < step3.length; i++){
            String bin = staticLookup[Integer.parseInt(step3[i], 16)];
            step4.add(bin);
            Log.d(TAG, "--------------------value is:" + bin + ". ");
        }

        String step5 = step4.toString();
        step5 = step5.replaceAll(",", "");
        step5 = step5.replaceAll(" ","");
        step5 = step5.replaceAll("\\[","");
        step5 = step5.replaceAll("\\]","");
        String[] step6 = step5.split("");
        step4.clear();

        for(int i = 0; i < pidListA.size(); i++ ){
            if(Integer.parseInt(step6[i+1]) == 1){
                step4.add(pidListA.get(i));
            }
        }

        return step4;

    }

    // Calculated Engine Load - 0104
    public float calEngineLoad( String response){
        String[] step1 = response.substring(6).split("\\s+");
        int A = Integer.parseInt(step1[0], 16);
        return (float) (A/2.55);

    }
    // Engine Coolant Tempereture - 0105
    public float calEngineCoolantTemp(String response){
        String[] step1 = response.substring(6).split("\\s+");
        int A = Integer.parseInt(step1[0], 16);
        return (float) (A - 40);
    }
    // Term Fuel Trim - 0106,0107,0108,0109
    public float calTermFuelTrim(String response){
        String[] step1 = response.substring(6).split("\\s+");
        int A = Integer.parseInt(step1[0], 16);
        return (float) ((A/1.28)-100);
    }
    // Fuel Pressure - 010A
    public float calFuelPressure(String response){
        String[] step1 = response.substring(6).split("\\s+");
        int A = Integer.parseInt(step1[0], 16);
        return (float) (A*3);
    }
    // Intake Manifold Pressure - 010B
    public float calIntakeManifoldPressure(String response){
        if(!response.contains("NO") && !response.contains("STO")) {
            String[] step1 = response.substring(6).split("\\s+");
            return Integer.parseInt(step1[0], 16);
        }else{
            return 0;
        }
    }
    // RPM - 010C
    public float calRPM(String response){
        if(!response.contains("NO") && !response.contains("STO")) {
            String[] step2 = response.substring(6).split("\\s+");
            int A = Integer.parseInt(step2[0], 16);
            int B = Integer.parseInt(step2[1], 16);

            return (float) ((256 * A) + B) / 4;
        }else{
            return 0;
        }
    }
    // Vehicle Speed - 010D
    public float calVehicleSpeed(String response){
        String[] step1 = response.substring(6).split("\\s+");
        return Integer.parseInt(step1[0], 16);
    }
    // Timing Advance - 010E
    public float calTimingAdvance(String response){
        String[] step1 = response.substring(6).split("\\s+");
        int A = Integer.parseInt(step1[0], 16);
        return (float) ((A/2)-64);
    }
    // Intake Air Temperature - 010F
    public float calIntakeAirTemp(String response){
        String[] step1 = response.substring(6).split("\\s+");
        return (Integer.parseInt(step1[0], 16)) - 40;
    }
    // MAF Air FLow - 0110
    public float calMafAirFlow(String response){
        String[] step2 = response.substring(6).split("\\s+");
        int A = Integer.parseInt(step2[0], 16);
        int B = Integer.parseInt(step2[1], 16);

        return (float) ((256 * A) + B) / 100;
    }
    // Throttle Position - 0111
    public float calThrottlePos(String response){
        if(!response.contains("NO") && !response.contains("STO")) {
            String[] step1 = response.substring(6).split("\\s+");
            int A = Integer.parseInt(step1[0], 16);

            return (float) (100 * A) / 255;
        }else{
            return 0;
        }
    }
    // Commanded Secondary Air Status - 0112
    public float calComSecondaryAirStatus(){
        return 1;
    }
    // Oxygen Sensors Present - 0113
    public float calOxygenSensorPresent(){
        return 1;
    }
    // Oxygen - 0114, 0115, 0116, 0117, 0118, 0119, 011A, 011B
    public float calOxygen(){
        return 1;
    }




}
