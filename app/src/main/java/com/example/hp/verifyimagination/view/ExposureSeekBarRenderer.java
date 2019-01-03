package com.example.hp.verifyimagination.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.hp.verifyimagination.R;

/**
 * Created by hp on 18-6-15.
 */

public class ExposureSeekBarRenderer {

    private Context mContext ;
    public int mHeight = 120;
    private final Paint mPaint;
    private final Bitmap focusBitmap;
    private final int thumbHeight;
    private final int thumbWidth;
    private float mProgress = 0;
    public float coefficient = 0.5f;    // friction coefficient
    private float widthOffset = 60;    // width offset

    private OnExposureSeekBarChangeListener mOnExposureSeekBarChangeListener;
    private float mLastProgress;

    public interface OnExposureSeekBarChangeListener{
        /**
         * range [ -0.5, 0.5 ]
         * @param progress
         */
        void onExposureSeekBarChange(float progress);
    }

    public ExposureSeekBarRenderer(Context context){
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3);
        focusBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_focusing_sun);
        thumbHeight = focusBitmap.getHeight();
        thumbWidth = focusBitmap.getWidth();
    }

    public void draw(Canvas canvas,float width,float height){
        drawLine(canvas,width + widthOffset,height);
        drawThumb(canvas,width + widthOffset,height);
    }


    public void draw(Canvas canvas, float width, float height,float distanceX){
        setProgress ((distanceX*coefficient) / (mHeight*1.0f));
        drawLine(canvas,width + widthOffset,height);
        drawThumb(canvas,width + widthOffset,height);
    }


    /**
     * background of seekBar
     * @param canvas
     * @param width
     * @param height
     */
    public void drawLine(Canvas canvas,float width,float height){
//        canvas.drawLine(width,height-mHeight/2,width,height+mHeight/2,mPaint);
        //   A---B O C------D       A = height - mHeight/2;         B = height + mProgress*mHeight - thumbHeight/2 - 5
        //                          C = height + mProgress * mHeight - thumbHeight / 2 + thumbHeight + 5    D = height + mHeight / 2
        //  ABCD increase
        if( height - mHeight/2 > height + mProgress*mHeight - thumbHeight/2 - 5 ){
            canvas.drawLine(width + widthOffset,height + mProgress*mHeight - thumbHeight/2 + thumbHeight + 5,width + widthOffset,height+mHeight/2,mPaint);
        }else if( height + mProgress*mHeight - thumbHeight/2 + thumbHeight + 5 > height+mHeight/2 ){
            canvas.drawLine(width + widthOffset,height - mHeight/2,width + widthOffset,height + mProgress*mHeight - thumbHeight/2 - 5,mPaint);
        }else {
            canvas.drawLine(width + widthOffset, height - mHeight / 2, width + widthOffset, height + mProgress * mHeight - thumbHeight / 2 - 5, mPaint);
            canvas.drawLine(width + widthOffset, height + mProgress * mHeight - thumbHeight / 2 + thumbHeight + 5, width + widthOffset, height + mHeight / 2, mPaint);
        }
    }

    /**
     * thumb of seekBar
     * @param canvas
     * @param width
     * @param height
     */
    public void drawThumb(Canvas canvas,float width,float height){
        canvas.drawBitmap(focusBitmap,width - thumbWidth/2 + widthOffset,height + mProgress*mHeight - thumbHeight/2,mPaint);
    }



    public void setProgress(float progress){
        mProgress = progress ;
        if( mProgress >= 0.5f ){
            mProgress = 0.5f;
        }else if( mProgress <= -0.5f ){
            mProgress = -0.5f;
        }
        if( mProgress != mLastProgress && mOnExposureSeekBarChangeListener != null) {      // avoid repeat same value
            mOnExposureSeekBarChangeListener.onExposureSeekBarChange(mProgress);
        }
        mLastProgress = mProgress;
    }

    /**
     * should be less than 1,more than 0.1
     * the less the value is, drag harder
     * @param coefficient
     */
    public void setCoefficient(float coefficient){
        if( coefficient >= 1){
            coefficient = 1;
        }else if( coefficient <= 0.1 ){
            coefficient = 0.1f;
        }
        this.coefficient = coefficient;
    }


    /**
     * Set listener
     * @param onExposureSeekBarChangeListener
     */
    public void setOnExposureSeekBarChangeListener(OnExposureSeekBarChangeListener onExposureSeekBarChangeListener){
        this.mOnExposureSeekBarChangeListener = onExposureSeekBarChangeListener;
    }

}
