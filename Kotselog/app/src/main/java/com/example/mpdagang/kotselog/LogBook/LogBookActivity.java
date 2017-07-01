package com.example.mpdagang.kotselog.LogBook;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mpdagang.kotselog.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.List;

public class LogBookActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "LogBookActivity";
    private Button genExcel;
    private TextView test;
    private LineGraphSeries<DataPoint> series1;
    public GraphView graph;
    public GraphView graph2;
    public GraphView graph3;
    public GraphView graph4;

    private ArrayList<XYValue> xyValueArray;
    private Handler tHandler;
    public double x;
    public double y;
    public int z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_book);

        test = (TextView) findViewById(R.id.testText);

        graph = (GraphView) findViewById(R.id.lineGraph1);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-2);
        graph.getViewport().setMaxY(2);


        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(20);
        graph.getGridLabelRenderer().setNumHorizontalLabels(20);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE );
        graph.setBackgroundColor(Color.BLACK);

        graph.getViewport().setScalable(true);

        graph2 = (GraphView) findViewById(R.id.lineGraph2);
        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setMinY(-2);
        graph2.getViewport().setMaxY(2);


        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(20);
        graph2.getGridLabelRenderer().setNumHorizontalLabels(20);
        graph2.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph2.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph2.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE );
        graph2.setBackgroundColor(Color.BLACK);

        graph2.getViewport().setScalable(true);

        graph3 = (GraphView) findViewById(R.id.lineGraph3);
        graph3.getViewport().setYAxisBoundsManual(true);
        graph3.getViewport().setMinY(-2);
        graph3.getViewport().setMaxY(2);


        graph3.getViewport().setXAxisBoundsManual(true);
        graph3.getViewport().setMinX(0);
        graph3.getViewport().setMaxX(20);
        graph3.getGridLabelRenderer().setNumHorizontalLabels(20);
        graph3.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph3.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph3.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE );
        graph3.setBackgroundColor(Color.BLACK);

        graph3.getViewport().setScalable(true);

        graph4 = (GraphView) findViewById(R.id.lineGraph4);
        graph4.getViewport().setYAxisBoundsManual(true);
        graph4.getViewport().setMinY(-2);
        graph4.getViewport().setMaxY(2);


        graph4.getViewport().setXAxisBoundsManual(true);
        graph4.getViewport().setMinX(0);
        graph4.getViewport().setMaxX(20);
        graph4.getGridLabelRenderer().setNumHorizontalLabels(20);
        graph4.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph4.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph4.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE );
        graph4.setBackgroundColor(Color.BLACK);

        graph4.getViewport().setScalable(true);




        series1 = new LineGraphSeries<DataPoint>();
        series1.setColor(Color.GREEN);
        series1.setThickness(5);

        xyValueArray = new ArrayList<>();
        tHandler = new Handler();

        startGraph();

    }

    private void startGraph() {
        Runnable drawer = new Runnable() {
            @Override
            public void run() {
                drawGraph();
            }
        };

        new Thread(drawer).start();
    }
     private void drawGraph(){
         final int numDataPoints = 200;
         x = 0;
         z = 0;
         try {
             while (true) {
                 try {
                     x = x + 0.1;
                     y = Math.sin(x);
                     Thread.sleep(50);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 tHandler.post(new Runnable() {
                     @Override
                     public void run() {
                         graph = (GraphView) findViewById(R.id.lineGraph1);
                         graph2 = (GraphView) findViewById(R.id.lineGraph2);
                         graph3 = (GraphView) findViewById(R.id.lineGraph3);
                         graph4 = (GraphView) findViewById(R.id.lineGraph4);
                         if (z == numDataPoints) {
                             series1 = new LineGraphSeries<>();
                             series1.setColor(Color.GREEN);
                             series1.setThickness(5);

                             z = 0;
                         } else {

                             z++;
                         }
                         series1.appendData(new DataPoint(x, y), true, numDataPoints);



                         graph.removeAllSeries();
                         graph.addSeries(series1);

                         graph2.removeAllSeries();
                         graph2.addSeries(series1);

                         graph3.removeAllSeries();
                         graph3.addSeries(series1);

                         graph4.removeAllSeries();
                         graph4.addSeries(series1);

                     }
                 });

             }
         }catch(Exception e){
             Log.d(TAG,"Loop interrupted");
         }


     }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }




}
