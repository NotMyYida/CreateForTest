package com.example.hp.verifyimagination.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hp on 18-5-4.
 */

public class GestureViewGroup extends ViewGroup {

    private GestureDetector mGestureDetector;

    public GestureViewGroup(Context context){
        super(context);
        init(context);
    }

    public GestureViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mGestureDetector = new GestureDetector(context,mOnGestureListener);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        child.layout(l, t, r-30, b-30);
    }

    GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("P:onFling","e1 : "+e1.getX()+","+e1.getY()+"  e2 : "+e2.getX()+","+e2.getY()+"  velocityX : "+velocityX+"  velocityY : "+velocityY);
            return false;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("P:onSingleTapUp","e1 : "+e.getX()+","+e.getY());
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e("P:onSingleTapConfirmed","e1 : "+e.getX()+","+e.getY());
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.e("P:onDoubleTap","e1 : "+e.getX()+","+e.getY());
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.e("P:onDoubleTapEvent","e1 : "+e.getX()+","+e.getY());
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("P:onDown","e1 : "+e.getX()+","+e.getY());
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e("P:onScroll","e1 : "+e1.getX()+","+e1.getY()+"  e2 : "+e2.getX()+","+e2.getY()+"  distanceX : "+distanceX+" distanceY : "+distanceY);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.e("P:onLongPress","e1 : "+e.getX()+","+e.getY());
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
        return mGestureDetector.onTouchEvent(event);
    }


}
