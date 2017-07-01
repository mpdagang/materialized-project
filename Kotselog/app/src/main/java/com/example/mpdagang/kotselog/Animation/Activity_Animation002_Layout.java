package com.example.mpdagang.kotselog.Animation;

import com.example.mpdagang.kotselog.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by MPDagang on 30/06/2017.
 */

public class Activity_Animation002_Layout extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    Thread thread = null;
    boolean canDraw = true;

    public Paint red_paintbrush_fill;
    public Paint blue_paintbrush_fill;
    public Paint green_paintbrush_fill;

    public Paint red_paintbrush_stroke;
    public Paint blue_paintbrush_stroke;
    public Paint green_paintbrush_stroke;
    public Paint transparent;

    public Path eraser;
    public Path square;

    Bitmap cherry;
    int cherry_x, cherry_y;

    Canvas canvas;
    SurfaceHolder surfaceHolder;

    public Activity_Animation002_Layout(Context context) {
        super(context);
        //setBackgroundColor(Color.GRAY);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        cherry = BitmapFactory.decodeResource(getResources(), R.drawable.small_dot);
        cherry_x = 650;
        cherry_y = 130;

        transparent = new Paint();
        transparent.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser = new Path();
    }

    public Activity_Animation002_Layout(Context context, AttributeSet attrs) {
        super(context, attrs);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        cherry = BitmapFactory.decodeResource(getResources(), R.drawable.small_dot);
        cherry_x = 650;
        cherry_y = 130;

        transparent = new Paint();
        transparent.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser = new Path();
    }

    public Activity_Animation002_Layout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        cherry = BitmapFactory.decodeResource(getResources(), R.drawable.small_dot);
        cherry_x = 650;
        cherry_y = 130;

        transparent = new Paint();
        transparent.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser = new Path();
    }


    @Override
    public void run() {

        prepPaintBrushes();

        while(canDraw){
            //carry out drawing

            if(!surfaceHolder.getSurface().isValid()){
                continue;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            canvas = surfaceHolder.lockCanvas();
            canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(), transparent);
            motionQueen(10);
            drawSquare(130,130,650,650);
            canvas.drawBitmap(cherry, cherry_x - (cherry.getWidth()/2), cherry_y - (cherry.getHeight()/2), null);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    public void drawGraph(){

    }

    private void prepPaintBrushes(){
        red_paintbrush_fill = new Paint();
        red_paintbrush_fill.setColor(Color.RED);
        red_paintbrush_fill.setStyle(Paint.Style.FILL);

        blue_paintbrush_fill = new Paint();
        blue_paintbrush_fill.setColor(Color.BLUE);
        blue_paintbrush_fill.setStyle(Paint.Style.FILL);

        green_paintbrush_fill = new Paint();
        green_paintbrush_fill.setColor(Color.GREEN);
        green_paintbrush_fill.setStyle(Paint.Style.FILL);

        red_paintbrush_stroke = new Paint();
        red_paintbrush_stroke.setColor(Color.RED);
        red_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        red_paintbrush_stroke.setStrokeWidth(3);

        blue_paintbrush_stroke = new Paint();
        blue_paintbrush_stroke.setColor(Color.BLUE);
        blue_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        blue_paintbrush_stroke.setStrokeWidth(3);

        green_paintbrush_stroke = new Paint();
        green_paintbrush_stroke.setColor(Color.GREEN);
        green_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        green_paintbrush_stroke.setStrokeWidth(3);
    }

    private void drawSquare(int x1, int y1, int x2, int y2){

        square = new Path();
        square.moveTo(x1, y1);
        square.lineTo(x2, y1);
        square.moveTo(x2, y1);
        square.lineTo(x2, y2);
        square.moveTo(x2, y2);
        square.lineTo(x1, y2);
        square.moveTo(x1, y2);
        square.lineTo(x1, y1);

        this.canvas.drawPath(square, green_paintbrush_stroke);
    }

    private void motionQueen(int speed){

        if( (cherry_y == 130) && (cherry_x < 650) ){
            cherry_x = cherry_x + speed;
        }

        if( (cherry_y < 650) && (cherry_x == 650) ){
            cherry_y = cherry_y + speed;
        }

        if( (cherry_y == 650) && (cherry_x > 130) ){
            cherry_x = cherry_x - speed;
        }

        if( (cherry_y > 130) && (cherry_x == 130) ){
            cherry_y = cherry_y - speed;
        }

    }

    public void pause(){

        canDraw = false;
        while(true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = null;
    }

    public void resume(){
        canDraw = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
