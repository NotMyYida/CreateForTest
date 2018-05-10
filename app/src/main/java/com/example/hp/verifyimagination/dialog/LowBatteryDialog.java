package com.example.hp.verifyimagination.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.hp.verifyimagination.R;

/**
 * Created by hp on 18-4-28.
 */

public class LowBatteryDialog extends Dialog {

    private View view;

    public LowBatteryDialog(@NonNull Context context) {
        super(context,R.style.MyAnimDialog);
        initDialog(context);
    }

    private void initDialog(Context context) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.dialog_for_camera,null);
        setContentView(view);
        Button btnOK = view.findViewById(R.id.btn_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LowBatteryDialog.this.dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        // 只有在 show() 方法后面调用,宽高才有作用
        Window window = getWindow();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay =
                windowManager.getDefaultDisplay();      //获取屏幕宽高
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (defaultDisplay.getWidth()*0.95);
        attributes.y = 24 ;

        Log.e("dialog","width : "+defaultDisplay.getWidth()+"  height : "+defaultDisplay.getHeight());
        attributes.gravity = Gravity.BOTTOM ;
        window.setAttributes(attributes);
    }
}
