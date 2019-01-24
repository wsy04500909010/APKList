package com.italkbb.test;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.italkbb.apklist.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements com.italkbb.test.AppsAdapter.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    com.italkbb.test.AppsAdapter appsAdapter;
    List<MyAppInfo> myAppInfoList;

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        init();

    }

    protected void init() {

//        LinearLayoutManager lm = new LinearLayoutManager(this);

        GridLayoutManager glm = new GridLayoutManager(this, 3);
        rv.setLayoutManager(glm);

        myAppInfoList = new ArrayList<>();
        appsAdapter = new com.italkbb.test.AppsAdapter(myAppInfoList);
        appsAdapter.setOnItemClickListener(this);

        rv.setAdapter(appsAdapter);

        Animation alphaAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.text_twinkle);

        tv_title.startAnimation(alphaAnimation);

        initData();
    }

    private void initData() {
        progressbar.setVisibility(View.VISIBLE);

        new Thread() {
            @Override
            public void run() {
                super.run();
                //扫描得到APP列表
                final List<MyAppInfo> appInfos = com.italkbb.test.ApkTool.scanLocalInstallAppList(MainActivity.this.getPackageManager());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        appsAdapter.setData(appInfos);
                        if (appInfos.size() > 0) {
                            rv.requestFocus();
                        }
                        progressbar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }.start();
    }

    @Override
    public void onItemClick(View v) {
        int position = (int) v.getTag();
        startThridApp(this, ((MyAppInfo) appsAdapter.getmList().get(position)).getPackageName());
    }

    /**
     * 通过包名启动第三方app
     *
     * @param context
     * @param packageName
     */
    public static void startThridApp(Context context, String packageName) {
        try {

            Intent minIntent = context.getPackageManager()
                    .getLaunchIntentForPackage(packageName);
            context.startActivity(minIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:

                com.italkbb.test.ApkTool.customOnly = !com.italkbb.test.ApkTool.customOnly;
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //扫描得到APP列表
                        final List<MyAppInfo> appInfos = com.italkbb.test.ApkTool.scanLocalInstallAppList(MainActivity.this.getPackageManager());
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                appsAdapter.setData(appInfos);
                                if (appInfos.size() > 0) {
                                    rv.requestFocus();
                                }
                                progressbar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }.start();

        }

        return super.onKeyDown(keyCode, event);
    }
}

