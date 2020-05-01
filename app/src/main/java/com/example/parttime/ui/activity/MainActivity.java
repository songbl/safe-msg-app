package com.example.parttime.ui.activity;

import android.os.Bundle;

import com.example.parttime.R;
import com.example.parttime.base.BaseActivity;
import com.example.parttime.ui.fragment.InfoDeFragment;
import com.example.parttime.ui.fragment.InfoEnFragment;
import com.example.parttime.ui.fragment.MyFragment;
import com.example.parttime.ui.view.EasyNavigationBar;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 *  Create By  791243928@qq.com
 *
 *
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.navigationBar)
    EasyNavigationBar navigationBar;

    private String[] tabText = {"加密", "解密",  "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.message, R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.message1, R.mipmap.message1,R.mipmap.me1};

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noadd);
        ButterKnife.bind(this);

        fragments.add(new InfoEnFragment());
        fragments.add(new InfoDeFragment());
        fragments.add(new MyFragment());

        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .canScroll(true)
                .build();
    }

}
