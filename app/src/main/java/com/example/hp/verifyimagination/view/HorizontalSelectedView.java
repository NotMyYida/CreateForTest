package com.example.hp.verifyimagination.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;


import com.example.hp.verifyimagination.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruedy on 2017/5/5.
 * Modified by Qs on 2018/5/3.
 */

public class HorizontalSelectedView extends View {

    private Context context;
    private List<String> strings = new ArrayList<String>(); //source number array

    private int seeSize = 5;    //visible count

    private int anInt;// size of words
    private TextPaint textPaint;
    private boolean firstVisible = true;
    private int width;// width of view
    private int height;// height of view
    private Paint selectedPaint;//the paint of the word which is selected
    private int n;
    private float downX;
    private float anOffset;
    private float selectedTextSize;
    private int selectedColor;
    private float textSize;
    private int textColor;
    private Rect rect = new Rect();
    private ModuleSwitchListener mListener ;

    private int textWidth = 0;
    private int textHeight = 0;
    private int centerTextHeight = 0;
    private Handler mHandler = new Handler(){};
    int margin = 40 ;           // margin between two string

    private ValueAnimator valueAnimator;
    private int lastN;                  // last selected INDEX

//    private VelocityTracker velocityTracker = VelocityTracker.obtain();
    private GestureDetector mGestureDetector;
    private int centerTextWidth;
    public ModuleChangeListener moduleChangeListener ;  // module change Listener

    public HorizontalSelectedView(Context context) {
        this(context, null);
    }

    public HorizontalSelectedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalSelectedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setWillNotDraw(false);
        setClickable(true);
        initAttrs(attrs,context);// initialize attribute
        initPaint();// initialize paint
        mGestureDetector = new GestureDetector(context,mOnGestureListener);
    }


    private void initPaint() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        selectedPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        selectedPaint.setColor(selectedColor);
        selectedPaint.setTextSize(selectedTextSize);
        valueAnimator = new ValueAnimator();
    }


    public interface ModuleChangeListener{
        void onModuleChange(int moduleIndex);
    }



    public void setOnModuleChangeListener(ModuleChangeListener moduleChangeListener){
        this.moduleChangeListener = moduleChangeListener ;
    }


    public interface ModuleSwitchListener {
        public void onModuleSelected2(int i);

        public void onShowSwitcherPopup();
    }

    /**
     * initialize the attribute
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs, Context context) {

        TypedArray tta = context.obtainStyledAttributes(attrs, R.styleable.HorizontalselectedView);
        //two kinds of color and size
        seeSize = tta.getInteger(R.styleable.HorizontalselectedView_HorizontalselectedViewSeesize, 5);
        selectedTextSize = tta.getFloat(R.styleable.HorizontalselectedView_HorizontalselectedViewSelectedTextSize, 50);
        selectedColor = tta.getColor(R.styleable.HorizontalselectedView_HorizontalselectedViewSelectedTextColor, context.getResources().getColor(android.R.color.black));
        textSize = tta.getFloat(R.styleable.HorizontalselectedView_HorizontalselectedViewTextSize, 40);
        textColor = tta.getColor(R.styleable.HorizontalselectedView_HorizontalselectedViewTextColor, context.getResources().getColor(android.R.color.darker_gray));
    }

    GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if( velocityX > 200 ) {         // swipe to right
                if (n >= 1 && n <= strings.size() -1 ) {        // n-1 >= 0 && n-1 < strings.size() -1

                    int choosedIndex = n - 1;
                    anOffset = getPositionByIndex(choosedIndex) - getWidth()/2 + stringWidthList.get(choosedIndex)/2;
                    n = n - 1;
                    setModuleIndex(n);

                    if( moduleChangeListener != null )
                        moduleChangeListener.onModuleChange(n); // module has been changed
                    delayToLoadModule(300);
                }
            }else if( velocityX < -200 ){   // swipe to left
                if ( n >= 0 && n <= strings.size() - 2) {   // n+1 >= 0 && n+1 < strings.size() -1

                    int choosedIndex = n + 1;
                    anOffset = getPositionByIndex(choosedIndex) - getWidth()/2 + stringWidthList.get(choosedIndex)/2;
                    n = n + 1;
                    setModuleIndex(n);

                    if( moduleChangeListener != null )
                        moduleChangeListener.onModuleChange(n); // module has been changed
                    delayToLoadModule(300);
                }
            }
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            float x = e.getX();
            // the distance between touch point and middle vertical line, divide 4/5 length of anInt ,could get offset of n
            int offset = calculateIndexN(x);
            if( offset == 0 ){
                return false;
            }
//            int offset = (int) ((x - width / 2) / (anInt*4/5));
            if (n + offset >= 0 && n + offset <= strings.size() - 1) {

                int choosedIndex = n + offset;
                anOffset = getPositionByIndex(choosedIndex) - getWidth()/2 + stringWidthList.get(choosedIndex)/2;
                n = n + offset;
                setModuleIndex(n);

                if( moduleChangeListener != null )
                    moduleChangeListener.onModuleChange(n); // module has changed
                delayToLoadModule(300);
            }
            return true;
        }
    };

    /**
     * calculate the index of offset with x position
     * @param x
     * @return
     */
    public int calculateIndexN(float x){
        int sumTab = stringWidthList.size();
        int restOffset = sumTab - n - 1;
        if( x > getWidth()/2 ) {
            for (int i = restOffset; i > 0; i--) {
                int sumOfTextLength = sumOfTextLength(i);
                if (x > getWidth()/2 + stringWidthList.get(n) / 2 + i * margin + sumOfTextLength) {
                    return i;
                }
            }
        }else{
            for (int i = n ; i >= 1; i-- ){
                int sumOfTextLengthLeft = sumOfTextLengthInDirectionLeft(i);
                if( x < getWidth()/2 - stringWidthList.get(n)/2 - i * margin - sumOfTextLengthLeft){
                    return -i;
                }
            }
        }
        return 0;
    }

    public int sumOfTextLength(int offset){
        int length = 0 ;
        if( offset == 1 ){
            return length ;
        }
        for (int i = 1 ; i <= offset-1 ; i++ ){
            if( n+i < 5 ) {
                length += stringWidthList.get(n + i);
            }
        }
        return length;
    }

    public int sumOfTextLengthInDirectionLeft(int offset){
        int length = 0;
        if (offset == 1){
            return length;
        }
        for (int i = 1 ; i<= offset-1 ; i++ ){
            if( n-i >= 0 ) {
                length += stringWidthList.get(n - i);
            }
        }
        return length;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        Log.e("HorizontalselectedView","x : "+event.getX()+" y : "+event.getY());

        return super.onTouchEvent(event);
    }


    public void delayToLoadModule(int ms){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if( mListener != null)
                    mListener.onModuleSelected2(switchHIdxToModuleSwitcherIdx(n));
            }
        },ms);
    }

    private void animateToCenter() {

        if (valueAnimator == null || valueAnimator.isRunning()) {
            return;
        }
        valueAnimator = ValueAnimator.ofInt(0,(int)anOffset).setDuration(200);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                anOffset = (int) animation.getAnimatedValue();
                invalidate();
            }

        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });

        valueAnimator.start();
    }

//    public void adapterSeeSize(){
//        int stringsTotalLength = 0;
//        for (int i = 0 ; i < strings.size() ; i++ ){
//            String text = strings.get(i);
//            textPaint.getTextBounds(text,0,text.length(),rect);
//            stringsTotalLength += rect.width();
//        }
//        int tempTotalLength = stringsTotalLength ;
//        while( width < stringsTotalLength ){
//            seeSize -- ;
//            stringsTotalLength = tempTotalLength -
//        }
//    }
    List<Integer> stringWidthList = new ArrayList<>();          // store the width of each string

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (firstVisible) {// we could get width and height of View,when we draw at the first time.
            width = getWidth();
            height = getHeight();
            for(int i = 0 ;  i < strings.size() ; i++ ){
                textPaint.getTextBounds(strings.get(i),0,strings.get(i).length(),rect);
                stringWidthList.add(rect.width());
            }



            anInt = width / seeSize;
            lastN = n;
        }
        if( firstVisible || lastN == n ) {
            if (n >= 0 && n <= strings.size() - 1) {// prevent out of index bound.

                String s = strings.get(n); // get the selected words.
                /**
                 * get the width and height of selected words
                 */
                selectedPaint.getTextBounds(s, 0, s.length(), rect);
                centerTextWidth = rect.width();
                centerTextHeight = rect.height();
                canvas.drawText(strings.get(n), getWidth() / 2 - centerTextWidth / 2 , getHeight() / 2 + centerTextHeight / 2, selectedPaint);//绘制被选中文字，注意点是y坐标

                for (int i = 0; i < strings.size(); i++) { //traversal strings，draw every words

                    if (i == 0) {
                        textPaint.getTextBounds(strings.get(0), 0, strings.get(0).length(), rect);
                        textHeight = rect.height();
                    }

                    if (i != n) {
                        canvas.drawText(strings.get(i), getPositionByIndex(i), getHeight() / 2 + textHeight / 2, textPaint);//画出每组文字
                    }
                }

            }
            firstVisible = false;
        }
        else{

            int xPosition = getPositionByIndexAtLastTime(n);
            canvas.drawText(strings.get(n),xPosition - anOffset,getHeight()/2 + textHeight/2,selectedPaint);
            for (int i = 0; i < strings.size(); i++) { //traversal strings，draw every words

                if (i == 0) {
                    textPaint.getTextBounds(strings.get(0), 0, strings.get(0).length(), rect);
                    textHeight = rect.height();
                }

                if (i != n) {
                    if( i == lastN ){
                        canvas.drawText(strings.get(i),getWidth() / 2 - stringWidthList.get(lastN) / 2 - anOffset, getHeight() / 2 + centerTextHeight / 2, textPaint);
                    }else {
                        canvas.drawText(strings.get(i), getPositionByIndexAtLastTime(i) - anOffset, getHeight() / 2 + textHeight / 2, textPaint);//画出每组文字
                    }

                }
            }
        }
    }

    // according to index ,calculate the distance
    public int getPositionByIndex(int index){
       int offset = n - index;
       int positionDistanceToMid = 0;
       if( offset > 0 ){
           for (int i = 1; i <= offset ; i++ ){
             positionDistanceToMid += stringWidthList.get(n-i);
           }
           positionDistanceToMid = getWidth()/2 - positionDistanceToMid - offset*margin - stringWidthList.get(n)/2;
       }else{
           for (int i = 1 ; i < -offset ; i++ ){
             positionDistanceToMid += stringWidthList.get(n+i);
           }
           positionDistanceToMid = getWidth()/2 + positionDistanceToMid - offset*margin + stringWidthList.get(n)/2;
       }

       return positionDistanceToMid;
    }



    // according to index ,calculate the distance
    public synchronized int getPositionByIndexAtLastTime(int index){
        int offset = lastN - index;
        int positionDistanceToMid = 0;
        if( offset > 0 ){
            for (int i = 1; i <= offset ; i++ ){
                positionDistanceToMid += stringWidthList.get(lastN-i);
            }
            positionDistanceToMid = getWidth()/2 - positionDistanceToMid - offset*margin - stringWidthList.get(lastN)/2;
        }else{
            for (int i = 1 ; i < -offset ; i++ ){
                positionDistanceToMid += stringWidthList.get(lastN+i);
            }
            positionDistanceToMid = getWidth()/2 + positionDistanceToMid - offset*margin + stringWidthList.get(lastN)/2;
        }

        return positionDistanceToMid;
    }


    /**
     * change the visible num counts
     *
     * @param seeSizes visible numbers
     */
    public void setSeeSize(int seeSizes) {
        if (seeSize > 0) {
            seeSize = seeSizes;
            invalidate();
        }

    }


    /**
     * move an unit to left
     */
    public void setAnLeftOffset() {
        if (n < strings.size() - 1) {
            n = n + 1;
            invalidate();
        }

    }

    /**
     * move an unit to right
     */
    public void setAnRightOffset() {
        if (n > 0) {
            n = n - 1;
            invalidate();
        }
    }

    /**
     * set data resource
     *
     * @param strings list of data resource
     */
    public void setData(List<String> strings) {
        this.strings = strings;
        n = strings.size() / 2;
        invalidate();
    }

    /**
     * get selected string
     *
     * @return selected string
     */
    public String getSelectedString() {
        if (strings.size() != 0) {
            return strings.get(n);
        }
        return null;
    }


    /**
     * set current module
     * @param moduleIndex    Notice that moduleIndex and moduleIndex of camera is different. See the switchHIdxToModuleSwitcherIdx(int hIdx)
     */
    public void setModuleIndex(int moduleIndex){
        this.n = moduleIndex ;
        if( n != lastN ) {
//            invalidate();
            animateToCenter();
//            moduleChangeListener.onModuleChange(n); // 模式发生改变
//            delayToLoadModule(300);
        }
        // save the selected INDEX, add a delay time to prevent some error
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lastN = n;
            }
        },300);
    }

    /**
     *
     * transfer the index of HorizontalSelectedView to index of ModuleSwitcher.
     * @param hIdx
     * @return
     */
    public int switchHIdxToModuleSwitcherIdx(int hIdx){
        int switcherIdx = 0 ;
        if( hIdx == 0 ){
            switcherIdx = 0 ;
        }else if( hIdx == 1 ){
            switcherIdx = 1 ;
        }else if( hIdx == 2 ){
            switcherIdx = 0 ;
        }else if( hIdx == 3 ){
            switcherIdx = 5 ;
        }else if( hIdx == 4 ){
            switcherIdx = 2 ;
        }
        Log.e("HorizontalselectedView","switcherIdx : "+switcherIdx);
        return switcherIdx ;
    }

    public void setSwitchListener(ModuleSwitchListener l) {
        mListener = l;
    }
}
