package com.example.hp.verifyimagination.verify.layout.funtion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hp.verifyimagination.R;
import com.example.hp.verifyimagination.base.BaseActivity;
import com.example.hp.verifyimagination.util.CommonUtil;

/**
 * Created by hp on 18-4-19.
 */

public class MatrixVerifyActivity extends BaseActivity implements View.OnClickListener{

    private VerifyMatrixView verifyMatrixView;
    private Button btnTranslate;
    private Button btnRotate;
    private Button btnScale;
    private Button btnReset;
    private Button btnSkew;
    private EditText etTranslateX;
    private EditText etTranslateY;
    private EditText etDegree;
    private EditText etRotatePx;
    private EditText etRotatePy;
    private EditText etScaleX;
    private EditText etScaleY;
    private EditText etScalePx;
    private EditText etScalePy;
    private EditText etSkewX;
    private EditText etSkewY;
    private EditText etSkewPx;
    private EditText etSkewPy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar("矩阵 Matrix 类");
        setToolbarDisplayHomeAsUp(true);

        TabLayout tabLayout = new TabLayout(this);

        verifyMatrixView = findViewById(R.id.verify_matrix_view);

        btnReset = findViewById(R.id.btn_reset);
        btnTranslate = findViewById(R.id.btn_translate);
        btnRotate = findViewById(R.id.btn_rotate);
        btnScale = findViewById(R.id.btn_scale);
        btnSkew = findViewById(R.id.btn_skew);

        btnReset.setOnClickListener(this);
        btnTranslate.setOnClickListener(this);
        btnRotate.setOnClickListener(this);
        btnScale.setOnClickListener(this);
        btnSkew.setOnClickListener(this);


        etTranslateX = findViewById(R.id.et_translate_x);
        etTranslateY = findViewById(R.id.et_translate_y);

        etDegree = findViewById(R.id.et_degree);
        etRotatePx = findViewById(R.id.et_px);
        etRotatePy = findViewById(R.id.et_py);

        etScaleX = findViewById(R.id.et_scale_x);
        etScaleY = findViewById(R.id.et_scale_y);
        etScalePx = findViewById(R.id.et_scale_px);
        etScalePy = findViewById(R.id.et_scale_py);

        etSkewX = findViewById(R.id.et_skew_x);
        etSkewY = findViewById(R.id.et_skew_y);
        etSkewPx = findViewById(R.id.et_skew_px);
        etSkewPy = findViewById(R.id.et_skew_py);

    }




    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify_matrix;
    }


    public boolean isEmptyString(String str){
        return TextUtils.isEmpty(str);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch ( id ){

            case R.id.btn_reset :
                verifyMatrixView.reset();
                break;

            case R.id.btn_translate :

                int translateX = CommonUtil.parseStringToInteger(etTranslateX.getText().toString());
                int translateY = CommonUtil.parseStringToInteger(etTranslateY.getText().toString());
                verifyMatrixView.setTranslate(translateX, translateY);

                break;

            case R.id.btn_rotate :

                int degree = CommonUtil.parseStringToInteger(etDegree.getText().toString());
                if ( isEmptyString( etRotatePx.getText().toString() ) || isEmptyString(etRotatePy.getText().toString()) ){
                    verifyMatrixView.setRotate(degree);
                }else{
                    int rotatePx = CommonUtil.parseStringToInteger(etRotatePx.getText().toString());
                    int rotatePy = CommonUtil.parseStringToInteger(etRotatePy.getText().toString());
                    verifyMatrixView.setRotate(degree,rotatePx,rotatePy);
                }

                break;

            case R.id.btn_scale :

                break;

            case R.id.btn_skew :

                break;

        }
    }
}
