package com.example.hp.verifyimagination.verify.layout.funtion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hp.verifyimagination.R;

/**
 * Created by hp on 18-4-19.
 */

public class VerifyMatrixView extends View {

    private Bitmap bitmap;
    private Matrix matrix;
    private Paint paint;

    public VerifyMatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerifyMatrixView(Context context) {
        super(context);
        init();
    }


    /**
     * 获取一个 Bitmap 对象
     */
    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        matrix = new Matrix();
        paint = new Paint();
    }


    /**
     * Matrix 类的 setTranslate 方法
     * @param x
     * @param y
     */
    public void setTranslate(int x, int y){
        matrix.setTranslate(x, y);
        invalidate();
    }

    /**
     * Matrix 类的扩大缩小方法, sx,sy 代表缩放的倍数, px,py代表缩放扩大的中心
     * @param sx
     * @param sy
     * @param px
     * @param py
     */
    public void setScale(float sx, float sy, float px, float py){
        matrix.setScale(sx, sy, px, py);
        invalidate();
    }

    /**
     * Matrix 类的扩大缩小方法,缩放中心为中间.
     * @param sx
     * @param sy
     */
    public void setScale(float sx, float sy){
        matrix.setScale(sx, sy);
        invalidate();
    }


    /**
     * Matrix 类设置旋转,px,py为旋转点
     * @param degrees
     * @param px
     * @param py
     */
    public void setRotate(float degrees, float px, float py){
        matrix.setRotate(degrees,px,py);
        invalidate();
    }

    /**
     * Matrix 类旋转方法, degrees为旋转角度,旋转点默认
     * @param degrees
     */
    public void setRotate(float degrees){
        matrix.setRotate(degrees);
        invalidate();
    }


    public void setSinCos(float sinValue,float cosValue,float px,float py){
        matrix.setSinCos(sinValue,cosValue,px,py);
        invalidate();
    }

    public void setSinCos(float sinValue, float cosValue){
        matrix.setSinCos(sinValue,cosValue);
        invalidate();
    }

    /**
     * 重置矩阵
     */
    public void reset(){
        matrix.reset();
        invalidate();
    }

    /**
     * 错切
     * @param kx
     * @param ky
     * @param px
     * @param py
     */
    public void setSkew(float kx, float ky, float px, float py){
        matrix.setSkew(kx,ky,px,py);
        invalidate();
    }


    public void setSkew(float kx, float ky){
        matrix.setSkew(kx,ky);
        invalidate();
    }


    public void inverts(){
        boolean invert = matrix.invert(matrix);
        if( invert ){
            invalidate();
        }
    }

    public void mapRect(RectF rect){
        matrix.mapRect(rect);
        Toast.makeText(getContext(),"left : "+rect.left+" top : "+rect.top+
                " right : "+rect.right+" bottom : "+rect.bottom,Toast.LENGTH_SHORT).show();
        Log.e("matrix","mapRect : "+"left : "+rect.left+" top : "+rect.top+
                " right : "+rect.right+" bottom : "+rect.bottom);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,matrix,paint);
    }
}
