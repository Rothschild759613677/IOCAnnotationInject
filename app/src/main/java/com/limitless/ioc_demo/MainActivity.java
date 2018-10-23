package com.limitless.ioc_demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.limitless.ioc_demo.annot.ClickEventInject;
import com.limitless.ioc_demo.annot.ContentView;
import com.limitless.ioc_demo.annot.ViewInject;

/**
 * 通过IOC架构实现View注入、控件的注入、事件的注入
 */

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @ViewInject(R.id.main_btn)
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: " + btn.getText());

    }

    @ClickEventInject({R.id.main_btn})
    public void onClickeEvent(View view) {
        switch (view.getId()) {
            case R.id.main_btn:
                Toast.makeText(this, "clicke event", Toast.LENGTH_SHORT).show();
                break;
                default:
                    Log.d(TAG, "onClickEvent: default");
                    break;
        }
    }
}
