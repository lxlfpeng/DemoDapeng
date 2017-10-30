package com.iosdialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Author: palmer
 * time: 2017/10/30
 * email:lxlfpeng@163.com
 * desc:
 */

public class BottomDialog extends BaseBottomDialog {
    public BottomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    View getChildView() {
        View view = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        return view;
    }
}
