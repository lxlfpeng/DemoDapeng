package com.iosdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Author: palmer
 * time: 2017/10/30
 * email:lxlfpeng@163.com
 * desc:
 */

public abstract class BaseIosDialog extends Dialog {
    private Context mContext;
    private CardView mRootView;

    public BaseIosDialog(@NonNull Context context) {
        super(context, R.style.IosDialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_base_ios);
        //获取屏幕的大小
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;
        //获取窗体
        Window window = this.getWindow();
        //window.setWindowAnimations(R.style.dialogAnima);//设置弹窗的动画效果
        WindowManager.LayoutParams params = window.getAttributes();
        //设置弹窗的大小
        // params.width = (int) (widthPixels * 0.7);
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
        mRootView = (CardView) findViewById(R.id.cv_dialog_root);
        mRootView.addView(setDialogView());
    }

    abstract View setDialogView();
}
