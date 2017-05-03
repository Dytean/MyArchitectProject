package com.dan.myarchitectproject.test;

import android.widget.TextView;

import com.dan.myarchitectproject.R;
import com.dan.myarchitectproject.common.BaseActivity;

import butterknife.BindView;

/**
 * Author: Dan
 * Date: 2017/4/11 上午11:02
 * Description:
 * PackageName: com.dan.myarchitectproject.common.test
 * Copyright: 杭州安存网络科技有限公司
 **/

public class TestActivity extends BaseActivity {
    @BindView(R.id.tv)
    TextView mTextView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {

    }

}
