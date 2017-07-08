package com.example.mpdagang.kotselog;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by MPDagang on 05/07/2017.
 */

public class OBDManager {
    private static final String TAG = "OBDManager";
    public String test = "A3 00 BE 3E B0 00";
    public String test2 = "A3 0C 0E 96";
    public ArrayList<String> pidListA;
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
        pidListA = new ArrayList<>();
        pidListA.add("0101");
        pidListA.add("0102");
        pidListA.add("0103");
        pidListA.add("0104");
        pidListA.add("0105");
        pidListA.add("0106");
        pidListA.add("0107");
        pidListA.add("0108");
        pidListA.add("0109");
        pidListA.add("010A");
        pidListA.add("010B");
        pidListA.add("010C");
        pidListA.add("010D");
        pidListA.add("010E");
        pidListA.add("010F");
        pidListA.add("0110");
        pidListA.add("0111");
        pidListA.add("0112");
        pidListA.add("0113");
        pidListA.add("0114");
        pidListA.add("0115");
        pidListA.add("0116");
        pidListA.add("0117");
        pidListA.add("0118");
        pidListA.add("0119");
        pidListA.add("011A");
        pidListA.add("011B");
        pidListA.add("011C");
        pidListA.add("011D");
        pidListA.add("011E");
        pidListA.add("011F");
        pidListA.add("0120");
    }


    public ArrayList<String> decodeAvailable(String response){
        String step1 = test.replaceAll("\\s+", "");
        String step2 = step1.substring(4);
        String[] step3 = step2.split("");
        //String[] dummy = test.split("\\s+");

        ArrayList<String> step4 = new ArrayList<>();

        for(int i = 1; i < step3.length; i++){
            //int j = Integer.parseInt(step3[i], 16);
            //String bin = Integer.toBinaryString(j);
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

        //Log.d(TAG, "--------------------value is:" + step2 + ". ");
        //Log.d(TAG, "---------------------size is:" + step3.length + ". ");
        //Log.d(TAG, "---------------------size is:" + step4.toString() + ". ");
        //Log.d(TAG, "---------------------size is:" + step5 + ". ");
        //Log.d(TAG, "---------------------size is:" + Integer.parseInt(step6[1]) + ". ");
        //Log.d(TAG, "---------------------size is:" + step4.size() + ". ");

        return step4;

    }

    public static float calRPM(String response){
        float rpm = 0;
        String step1 = response.substring(6);
        step1 = step1.replaceAll("\\>", "");
        String[] step2 = step1.split("\\s+");
        int A = Integer.parseInt(step2[0],16);
        int B = Integer.parseInt(step2[1],16);


        rpm = ((256*A) + B)/4;

        //Log.d(TAG, "--------------------value is:" + step1 + ". ");
        //Log.d(TAG, "--------------------value is:" + A + ". ");
        //Log.d(TAG, "--------------------value is:" + B + ". ");
        //Log.d(TAG, "--------------------value is:" + rpm + ". ");

        return rpm;
    }

    public static double calEngineCoolantTemp(double A){
        return A;
    }

    public static double calIntakeManifoldPressure(double A){
        return A;
    }


}
