package com.dan.myarchitectproject.test;

import android.view.View;
import android.widget.Button;

import com.dan.myarchitectproject.R;
import com.dan.myarchitectproject.common.BaseFragment;
import com.dan.myarchitectproject.common.BaseFragmentActivity;

import butterknife.BindView;

/**
 * Author: Dan
 * Date: 2017/4/11 上午11:08
 * Description:
 * PackageName: com.dan.myarchitectproject.common.test
 * Copyright: 杭州安存网络科技有限公司
 **/

public class TestFragmentActivity extends BaseFragmentActivity {
    @BindView(R.id.button_one)
    Button btnOne;

    @BindView(R.id.button_two)
    Button btnTwo;

    @BindView(R.id.button_three)
    Button btnThree;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_container;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.id_container;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return FragmentOne.newInstance();
    }

    @Override
    protected void initOnClick() {
        super.initOnClick();
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(FragmentOne.newInstance());
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(FragmentTwo.newInstance());
            }
        });

        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(FragmentThree.newInstance());
            }
        });
    }
}
