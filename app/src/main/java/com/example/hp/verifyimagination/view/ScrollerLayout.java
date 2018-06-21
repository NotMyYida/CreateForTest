package com.example.hp.verifyimagination.view;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

import javax.security.auth.login.LoginException;

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
    private int margin = 50;  // horizontal margin between child view
    private int[] scrollXIndexCollection ;  //difference scrollX correspond to it's index
    private final int DEFAULT = 3;
    private int INDEX = DEFAULT;
    private GestureDetector mGestureDetector;

    public ScrollerLayout(Context context) {
        this(context,null);
    }


    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
        mGestureDetector = new GestureDetector(context,mOnGestureListener);
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

    /**
     * 　GestureDetector , when we fling to left moduleIdx plus 1, when we fling to right moduleIdx minus 1.
     */
    GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("ScrollerLayout","onFling ->  INDEX : "+INDEX);
            if( velocityX > 150 ){          // swipe to right  minus
                if( INDEX >=1 ){
                    INDEX -- ;
                    Log.e("ScrollerLayout","velocityX > 200 onFling ->  INDEX : "+INDEX);
                    scrollTo(-scrollXIndexCollection[INDEX],0);
                    TextView textView = (TextView) getChildAt(INDEX);
                    textView.setTextSize(25);
                }
            }else if( velocityX < -150 ){   // swipe to left   plus
                if( INDEX < getChildCount()-1){
                    INDEX ++ ;
                    Log.e("ScrollerLayout","velocityX < 200 onFling ->  INDEX : "+INDEX);
                    scrollTo(-scrollXIndexCollection[INDEX],0);
                    TextView textView = (TextView) getChildAt(INDEX);
                    textView.setTextSize(25);
                }
            }
            return true;
        }
    };

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if( changed ){
            int childCount = getChildCount();
            int viewWidth = 0;
            for( int i = 0; i < childCount ; i++ ){
                View child = getChildAt(i);
                Log.e("ScrollerLayout","view["+i+"].getMeasureWidth() : "+child.getMeasuredWidth());
//                child.layout(i*child.getMeasuredWidth(),0,(i+1)*child.getMeasuredWidth(),child.getMeasuredHeight());
                child.layout(margin + viewWidth , 0 , margin + viewWidth + child.getMeasuredWidth(), child.getMeasuredHeight());
                viewWidth += child.getMeasuredWidth();
            }
            scrollXIndexCollection = new int[childCount];   //  store the scrollXIndexCollection
            initScrollXIndex();
            for (int i = 0 ; i < scrollXIndexCollection.length ; i++ ){
                Log.e("ScrollerLayout","scrollerIndex["+i+"]: "+scrollXIndexCollection[i]);
            }
            for (int i = 0 ; i < getChildCount(); i++ ) {
                View view = getChildAt(i);
                final int index = i ;
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("ScrollerLayout","onLayout --> onClick ->  scrollTo : "+scrollXIndexCollection[index]);
                        scrollTo(-scrollXIndexCollection[index],0);
                    }
                });
            }
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(getChildCount() - 1).getRight();
            scrollTo(-scrollXIndexCollection[INDEX],0);
            TextView textView = (TextView) getChildAt(INDEX);
            textView.setTextSize(25);
        }
    }

    public void initScrollXIndex(){
        for (int i = 0 ; i < getChildCount(); i++ ){
            scrollXIndexCollection[i] = getScrollXWidth(i);
        }
    }

    /**
     * Calculate the scrollX width
     * @param index
     * @return
     */
    public int getScrollXWidth(int index){
        int scrollXWidth = 0;
        for (int i = 0 ; i <= index ; i++ ){
           if( i == index ){
               scrollXWidth += getChildAt(i).getMeasuredWidth()/2;
           }else {
               scrollXWidth += getChildAt(i).getMeasuredWidth();
           }
        }
        if( index != 0 ){
            scrollXWidth += margin*index;
        }
        scrollXWidth = getWidth()/2 - scrollXWidth;
        return scrollXWidth;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
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
//                    return true ;
                }
                break;

        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if( event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float rawX = event.getRawX();
            float y = event.getY();
            float rawY = event.getRawY();
            Log.e("ScrollerLayout","ACTION_DOWN -> x : "+x+"  y : "+y+"  rawX : "+rawX+"  rawY : "+rawY);
        }
//        switch(event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                float x = event.getX();
//                float rawX = event.getRawX();
//                float y = event.getY();
//                float rawY = event.getRawY();
//                Log.e("ScrollerLayout","ACTION_DOWN -> x : "+x+"  y : "+y+"  rawX : "+rawX+"  rawY : "+rawY);
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                mXMove = event.getRawX();
//                int scrolledX = (int) (mLastMove - mXMove);
//                Log.e("ScrollerLayout"," scrolledX : "+scrolledX);
//                if( getScrollX() + scrolledX < -scrollXIndexCollection[0]){
//                    Log.e("ScrollerLayout","getScrollX() : "+getScrollX()+"  scrolledX : " + scrolledX +"  leftBorder : "+leftBorder);
//                    scrollTo(-scrollXIndexCollection[0], 0);
//                    return true;
//                }else if(getScrollX() + scrolledX > -scrollXIndexCollection[getChildCount()-1]){
//                    Log.e("ScrollerLayout","getScrollX() : "+getScrollX()+" getWidth() : "+getWidth()+" scrolledX : "+scrolledX+"  rightBorder : "+rightBorder);
//                    scrollTo(-scrollXIndexCollection[getChildCount()-1], 0);
//                    return true;
//                }
//
//                int scroll = getScrollX() + scrolledX;
//
//
//                scrollBy(scrolledX, 0);
//                mLastMove = mXMove;
//                break;
//
//            case MotionEvent.ACTION_UP:
////                int targetIndex = (getScrollX() + getWidth()/2)/getWidth();
//////                Log.e("ScrollerLayout","ACTION_UP -> getScrollX() : "+getScrollX()+"  getWidth() : "+getWidth()+"  targetIndex : "+targetIndex);
////                int dx = targetIndex * getWidth() - getScrollX();
////                Log.e("ScrollerLayout","ACTION_UP -> getScrollX() : "+getScrollX()+"  getWidth() : "+getWidth()+" targetIndex : "+targetIndex+"  dx : "+dx);
////                mScroller.startScroll(getScrollX(),0,dx,0);
////                Log.e("ScrollerLayout","getScrollX() : "+getScrollX()+"  dx : "+dx);
////                invalidate();
//                break;
//
//        }
        return mGestureDetector.onTouchEvent(event);
    }

    public int findClosestPoint(int scroll){
        for (int i = 0 ; i < getChildCount(); i++){
            int distance = Math.abs(scroll - scrollXIndexCollection[i]);
        }

        return 0 ;
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
