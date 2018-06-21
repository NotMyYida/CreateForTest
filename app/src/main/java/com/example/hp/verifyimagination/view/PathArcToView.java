package com.example.hp.verifyimagination.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hp on 18-6-8.
 */

public class PathArcToView extends View {

    private Paint paint;
    private Path mPath;

    public PathArcToView(Context context) {
        super(context);
        init();
    }

    public PathArcToView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        paint = new Paint();
        mPath = new Path();
        paint.setStyle(Paint.Style.STROKE);
        RectF rectFBig = new RectF(0,0,300,300);
        RectF rectFSmall = new RectF(20,20,100,100);
        mPath.arcTo(rectFBig,0,90,true);
        mPath.arcTo(rectFSmall,0,90);
//        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,paint);

    }
}
