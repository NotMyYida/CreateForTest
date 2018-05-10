package com.example.hp.verifyimagination.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hp on 18-4-28.
 */

public class JustTouchScroll extends View {

    private GestureDetector mGestureDetector;

    public JustTouchScroll(Context context) {
        super(context);
        init(context);
    }

    public JustTouchScroll(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mGestureDetector = new GestureDetector(context,mOnGestureListener);
    }

    GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("onFling","e1 : "+e1.getX()+","+e1.getY()+"  e2 : "+e2.getX()+","+e2.getY()+"  velocityX : "+velocityX+"  velocityY : "+velocityY);
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("onSingleTapUp","e1 : "+e.getX()+","+e.getY());
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e("onSingleTapConfirmed","e1 : "+e.getX()+","+e.getY());
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.e("onDoubleTap","e1 : "+e.getX()+","+e.getY());
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.e("onDoubleTapEvent","e1 : "+e.getX()+","+e.getY());
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("onDown","e1 : "+e.getX()+","+e.getY());
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e("onScroll","e1 : "+e1.getX()+","+e1.getY()+"  e2 : "+e2.getX()+","+e2.getY()+"  distanceX : "+distanceX+" distanceY : "+distanceY);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.e("onLongPress","e1 : "+e.getX()+","+e.getY());
            super.onLongPress(e);
        }
//
//        @Override
//        public void onShowPress(MotionEvent e) {
//            Log.e("onShowPress","e1 : "+e.getX()+","+e.getY());
//            super.onShowPress(e);
//        }

    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        //　左下角表示剩余多少空间　, 注释后滑不动了,不注释点哪里都可以滑
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                float downX = event.getX();//获得点下去的x坐标
//                float downY = event.getY();
//                Log.e("ACTION_DOWN","downX : "+downX+"  downY : "+downY);
//                break;
//            case MotionEvent.ACTION_MOVE://复杂的是移动时的判断
//                float moveX = event.getX();
//                float moveY = event.getY();
//                Log.e("ACTION_MOVE","moveX : "+moveX+"  moveY : "+moveY);
//                break;
//            case MotionEvent.ACTION_UP:
//                float upX = event.getX();
//                float upY = event.getY();
//                Log.e("ACTION_UP","upX : "+upX+"  upY : "+upY);
//                break;
//        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
    }
}
