package com.example.hp.verifyimagination.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.KeyEvent;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by hp on 18-6-20.
 */

public class SunnyGLRenderer implements GLSurfaceView.Renderer {

    private int mProgram;
    private int maPostionHandle;

    private FloatBuffer mTriangleVB;

    //定义顶点(vertex)着色命令语句
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix; \n" +
                    "attribute vec4 vPosition; \n" +
                    "void main(){              \n" +
                    " gl_Position = vPosition; \n" +
                    "}                         \n";


    private final String fragmentShaderCode =
            "precision mediump float;\n" +
                    "void main(){ \n" +
                    " gl_FragColor = vec4 (0.63671875,0.76953125,0.22265625,1.0);\n" +
                    "}\n";

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);
        GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT );
        initShape();

        int vertextShader = loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);
        //申请特定的着色器，program != 0 申请成功
        mProgram = GLES20.glCreateProgram();
        if(mProgram != 0 ) {
            GLES20.glAttachShader(mProgram, vertextShader);
            GLES20.glAttachShader(mProgram, fragmentShader);
            //连接着色器
            GLES20.glLinkProgram(mProgram);
            int[] linkStatus = new int[1];
            //查看着色器连接情况
            GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                //连接失败
                Log.e("Sunny", "Could not link program: ");
                Log.e("Sunny", GLES20.glGetProgramInfoLog(mProgram));
                GLES20.glDeleteProgram(mProgram);
                mProgram = 0;
            }
            //获取着特定着色器vPosition参数。
            maPostionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        }

    }

    private void initShape() {
        /**
         * X,Y,Z三轴坐标
         * U,V也即S,T，是图片，视频等以纹理的形式加载到GLSurfaceView中时的坐标
         * U,V是无方向的
         */
        float trianlgeCoords[] = {
                //X,Y,Z，U,V
                -1.0f, -0.5f, 0, -0.5f, 0.0f,
                1.0f, -0.5f, 0, 1.5f, -0.0f,
                0.0f,  1.11803399f, 0, 0.5f,  1.61803399f
        };

        ByteBuffer vbb = ByteBuffer.allocateDirect(trianlgeCoords.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mTriangleVB = vbb.asFloatBuffer();
        mTriangleVB.put(trianlgeCoords);
        mTriangleVB.position(0);
    }


    /**
     * 加载指定着色器
     * @param type
     * @param shaderCode
     * @return
     */
    private int loadShader(int type,String shaderCode){

        int shader = GLES20.glCreateShader(type);
        if(shader != 0 ){
            //加载着色器脚本程序(即本例的String 变量命令语句)
            GLES20.glShaderSource(shader,shaderCode);
            //编译着色器脚本程序
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            //查看编译结果
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                //编译失败，释放申请的着色器
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
