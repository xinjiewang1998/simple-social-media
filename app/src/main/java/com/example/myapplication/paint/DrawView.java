package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DrawView extends View {
    private int currentColor = getResources().getColor(R.color.blue);
    private float currentSize = 10f;
    private Paint myPaint;
    private Path myPath;
    private List<PathLine> lines;

    public DrawView(Context context) {
        super(context);
        lines = new ArrayList<>();
        initPaint();
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        lines = new ArrayList<>();
        initPaint();
    }

    public void initPaint() {
        myPaint = new Paint();
        myPaint.setColor(currentColor);
        myPaint.setStrokeWidth(currentSize);
        myPaint.setStrokeJoin(Paint.Join.ROUND);
        myPaint.setStrokeCap(Paint.Cap.ROUND);
        myPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(PathLine pathLine : lines) {
            myPaint.setColor(pathLine.color);
            myPaint.setStrokeWidth(pathLine.size);
            canvas.drawPath(pathLine.path, myPaint);
        }

        if(myPath != null) {
            myPaint.setColor(currentColor);
            myPaint.setStrokeWidth(currentSize);
            canvas.drawPath(myPath, myPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                myPath = new Path();
                myPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                myPath.lineTo(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                lines.add(new PathLine(myPath, currentColor, currentSize));
                myPath = null;
                break;
        }
        return true;
    }

    public void changeSize(float size) {
        currentSize = size;
    }

    public void changeColor(int color) {
        currentColor = color;
    }

    public void erase() {
        currentColor = Color.WHITE;
    }

    public void undo() {
        if(lines.size() > 0) {
            lines.remove(lines.size() - 1);
        }
        invalidate();
    }

    public Bitmap createBitmap()
    {
        Bitmap bitmap = Bitmap.createBitmap( this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888 );
        Canvas canvas = new Canvas(bitmap);
        for(PathLine pathLine : lines) {
            myPaint.setColor(pathLine.color);
            myPaint.setStrokeWidth(pathLine.size);
            canvas.drawPath(pathLine.path, myPaint);
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/temp.png");

        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
