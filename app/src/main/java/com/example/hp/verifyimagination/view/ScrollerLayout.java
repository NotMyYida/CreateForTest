package com.example.hp.verifyimagination.view;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by hp on 18-5-22.
 */

public class ScrollerLayout extends ViewGroup {

    private Scroller mScroller;
    private int mTouchSlop;
    private int leftBorder;
    private int rightBorder;
    private float mXDown;
    private float mLastMove;
    private float mXMove;

    public ScrollerLayout(Context context) {
        this(context,null);
    }


    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for( int i = 0 ; i < childCount ; i++ ){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if( changed ){
            int childCount = getChildCount();
            for( int i = 0; i < childCount ; i++ ){
                View child = getChildAt(i);
                child.layout(i*child.getMeasuredWidth(),0,(i+1)*child.getMeasuredWidth(),child.getMeasuredWidth());
            }
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(getChildCount() - 1).getRight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mLastMove = mXDown;
                break;
                
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXDown - mXMove);
                mLastMove = mXMove;
                if( diff < mTouchSlop){
                    Log.e("ScrollerLayout","intercept event  diff : "+diff+" mTouchSlop : "+mTouchSlop);
                    return true ;
                }
                break;

        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrolledX = (int) (mLastMove - mXMove);
                Log.e("ScrollerLayout"," scrolledX : "+scrolledX);
                if( getScrollX() + scrolledX < leftBorder){
                    Log.e("ScrollerLayout","getScrollX() : "+getScrollX()+"  scrolledX : " + scrolledX +"  leftBorder : "+leftBorder);
                    scrollTo(leftBorder, 0);
                    return true;
                }else if(getScrollX() + getWidth() + scrolledX > rightBorder){
                    Log.e("ScrollerLayout","getScrollX() : "+getScrollX()+" getWidth() : "+getWidth()+" scrolledX : "+scrolledX+"  rightBorder : "+rightBorder);
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrolledX, 0);
                mLastMove = mXMove;
                break;

            case MotionEvent.ACTION_UP:
                int targetIndex = (getScrollX() + getWidth()/2)/getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                Log.e("ScrollerLayout","getScrollX() : "+getScrollX()+"  getWidth()/2 : "+getWidth()/2+" targetIndex : "+targetIndex+"  dx : "+dx);
                mScroller.startScroll(getScrollX(),0,dx,0);
                Log.e("ScrollerLayout","getScrollX() : "+getScrollX()+"  dx : "+dx);
                invalidate();
                break;

        }
        return super.onTouchEvent(event);
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.e("computeScroll","currX : "+mScroller.getCurrX());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            invalidate();
        }
    }
}
