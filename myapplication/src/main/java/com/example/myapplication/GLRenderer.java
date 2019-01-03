package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by brant on 18-10-30.
 */

public class GLRenderer implements GLSurfaceView.Renderer {

    private int program;
    private int vPosition;
    private int uColor;


    // 顶点着色器的脚本
    private static final String verticesShader
            = "attribute vec2 vPosition;            \n" // 顶点位置属性vPosition
            + "void main(){                         \n"
            + "   gl_Position = vec4(vPosition,0,1);\n" // 确定顶点位置
            + "}";

    // 片元着色器的脚本
    private static final String fragmentShader
            = "precision mediump float;         \n" // 声明float类型的精度为中等(精度越高越耗资源)
            + "uniform vec4 uColor;             \n" // uniform的属性uColor
            + "void main(){                     \n"
            + "   gl_FragColor = uColor;        \n" // 给此片元的填充色
            + "}";


    private int loadShader(int shaderType, String sourceCode){
        int shader = GLES20.glCreateShader(shaderType);
        if( shader != 0 ){
            GLES20.glShaderSource(shader,sourceCode);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader,GLES20.GL_COMPILE_STATUS,compiled,0);
            if( compiled[0] == 0 ){ // fail
                Log.e("ES20_ERROR","Could not compile shader " + shaderType + ":");
                Log.e("ES20_ERROR",GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    private int createProgram(String vertexSource, String fragmentSource){
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if( vertexShader == 0 ){
            return 0;
        }

        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentSource);
        if( pixelShader == 0 ){
            return 0;
        }

        int program = GLES20.glCreateProgram();
        if( program != 0 ){
            GLES20.glAttachShader(program,vertexShader);
            GLES20.glAttachShader(program,pixelShader);
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program,GLES20.GL_LINK_STATUS,linkStatus,0);
            if( linkStatus[0] != GLES20.GL_TRUE){
                Log.e("ES20_ERROR","Could not link program");
                Log.e("ES20_ERROR",GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0 ;
            }
        }

        return program;
    }

    private FloatBuffer getVertices(){
        float vertices[] = {
                0.f,     0.5f,
                -0.5f,   -0.5f,
                0.5f,    -0.5f
        };
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuf = vbb.asFloatBuffer();
        vertexBuf.put(vertices);
        vertexBuf.position(0);
        return vertexBuf;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // close dither
//        gl.glDisable(GL10.GL_DITHER);
//        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
//        gl.glClearColor(0,0,0,0);
//        gl.glShadeModel(GL10.GL_SMOOTH);
//        gl.glEnable(GL10.GL_DEPTH_TEST);
//        gl.glDepthFunc(GL10.GL_LEQUAL);
        program = createProgram(verticesShader, fragmentShader);
        vPosition = GLES20.glGetAttribLocation(program,"vPosition");
        uColor = GLES20.glGetUniformLocation(program,"uColor");
        GLES20.glClearColor(1.0f,0,0,1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0,0,width,height);
//        gl.glMatrixMode(GL10.GL_PROJECTION);
//        gl.glLoadIdentity();
//        float ratio = (float)width/height;
//        gl.glFrustumf(-ratio,ratio,-1,1,1,10);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
//        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//        gl.glMatrixMode(GL10.GL_MODELVIEW);
//        // begin to draw
//        gl.glLoadIdentity();
//        gl.glTranslatef(0.95f,-0.8f,-1.5f);
        // point
//        gl.glVertexPointer(3,GL10.GL_FLOAT,0,Point);

        FloatBuffer vertices = getVertices();
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(program);
        GLES20.glVertexAttribPointer(vPosition,2,GLES20.GL_FLOAT,false,0,vertices);
        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glUniform4f(uColor,0.0f,1.0f,0.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,3);
    }
}
