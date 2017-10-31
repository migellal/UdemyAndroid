package com.udemy.michalgellert.udemyandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by michal.gellert on 31.10.2017.
 */

public class TriangleView extends View {

    Paint paint = new Paint();

    public TriangleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.CYAN);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(drawTriangle(), paint);
    }

    private Path drawTriangle() {
        Path path = new Path();
        path.moveTo(5, 5);
        path.lineTo(5, 150);
        path.lineTo(150, 50);
        path.close();
        return path;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
