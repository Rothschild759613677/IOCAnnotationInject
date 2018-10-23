package com.limitless.ioc_demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Nick on 2018/10/23
 *
 * @author Nick
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IOCUtils.inject(this);
    }
}
