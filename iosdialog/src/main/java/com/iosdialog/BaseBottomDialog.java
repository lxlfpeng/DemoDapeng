package com.iosdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Author: palmer
 * time: 2017/10/30
 * email:lxlfpeng@163.com
 * desc:
 */

public abstract class BaseBottomDialog extends Dialog {
    private Context mContext;

    public BaseBottomDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_base_bottom);
        //获取屏幕大小
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;
        //获取窗体
        Window window = this.getWindow();
        //低版本holo主题蓝色分割线去掉
        try {
            int divierId = mContext.getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = this.findViewById(divierId);
            divider.setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {
        }
        //设置窗体背景
        window.setBackgroundDrawable(new BitmapDrawable());
        //设置窗体大小
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        //设置弹框的大小
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialog);
        window.setAttributes(params);
        FrameLayout RootView = (FrameLayout) findViewById(R.id.rl_dialog_base_bottom);
        RootView.addView(getChildView());
    }

    abstract View getChildView();
}
