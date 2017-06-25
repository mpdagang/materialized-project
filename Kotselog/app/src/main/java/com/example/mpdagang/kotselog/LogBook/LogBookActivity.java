package com.example.mpdagang.kotselog.LogBook;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private Button genExcel;
    private TextView test;
    private LineGraphSeries<DataPoint> series1;
    public GraphView graph;

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

        graph = (GraphView) findViewById(R.id.lineGraph);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-2);
        graph.getViewport().setMaxY(2);


        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);
        graph.getGridLabelRenderer().setNumHorizontalLabels(15);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL );


        //graph.getViewport().setOnXAxisBoundsChangedListener();
        // graph.getViewport().computeScroll();
        graph.getViewport().setScalable(true);

        series1 = new LineGraphSeries<DataPoint>();
        series1.setColor(Color.GREEN);
        series1.setThickness(1);

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
         int numDataPoints = 500;
         x = 0;
         z = 0;
        while(true){
            try {
                x = x + 0.1;
                y = Math.sin(x);
               // xyValueArray.add(new XYValue(x,y));
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tHandler.post(new Runnable() {
               // double plotX;
               // double plotY;
                @Override
                public void run() {
                    graph = (GraphView) findViewById(R.id.lineGraph);
                    if(z == 500) {
                        series1 = new LineGraphSeries<DataPoint>();
                        series1.setColor(Color.GREEN);
                        //series1.setDrawDataPoints(true);
                        //series1.setDrawAsPath(true);
                        series1.setThickness(1);
                        z = 0;
                    }else{

                        z++;
                    }
                    series1.appendData(new DataPoint(x,y), true, 500);
                    //series1 = (LineGraphSeries<DataPoint>) list.get(0);


                   // for(int i = 0; i <xyValueArray.size(); i++){
                   //    plotX = xyValueArray.get(i).getX();
                     //  plotY = xyValueArray.get(i).getY();

                   // }


                    graph.removeAllSeries();
                    graph.addSeries(series1);

                }
            });

            //if(x)
        }


     }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }




}
