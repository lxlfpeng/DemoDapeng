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

public class NotiDialog extends BaseIosDialog {

    public NotiDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    View setDialogView() {
        View inflate = getLayoutInflater().inflate(R.layout.dialog_noti, null);
        return inflate;
    }
}
