package com.example.parttime.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.parttime.R;
import com.example.parttime.base.BaseActivity;
import com.example.parttime.entity.node.UserEntity;
import com.example.parttime.net.ApiService;
import com.example.parttime.net.AppHttpClient;
import com.example.parttime.net.bean.LoginBean;
import com.example.parttime.net.callback.CallBackWrapper;
import com.example.parttime.utils.LogUtil;
import com.example.parttime.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
/**
 *  Create By  791243928@qq.com
 *
 *
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.et_phone_num)
    EditText etUsername;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);//显示出时间
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(R.id.toolbar)
                .init();
    }

    @OnClick({R.id.et_phone_num, R.id.et_password, R.id.btn_login,R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_phone_num:
                break;
            case R.id.et_password:
                break;
            case R.id.tv_register:
                gotoActivity(RegisterActivity.class);
                break;
            case R.id.btn_login:
                LoginBean loginBean = new LoginBean();
                String num = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(num)){
                    ToastUtil.showShort("手机号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    ToastUtil.showShort("密码不能为空");
                    return;
                }
                loginBean.setMobile(etUsername.getText().toString());
                loginBean.setPassword(etPassword.getText().toString());
//                loginBean.setMobile("15564690768");
//
//                loginBean.setPassword("123456");

                loadingPopup.show();
                AppHttpClient.getInstance().getRetrofit().create(ApiService.class)
                        .loginWithPassword(loginBean)
                        .subscribeOn(Schedulers.io())

                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CallBackWrapper<UserEntity>() {
                            @Override
                            public void onSuccess(UserEntity user) {
                                loadingPopup.dismiss();
                                LogUtil.e("LoginActivity", "onSuccess(LoginActivity.java:59)" + user.toString());
                                UserEntity.updatePropertyValues(user);
                                gotoActivity(MainActivity.class);
                            }

                            @Override
                            public void onError(String msg, int code) {
                                LogUtil.e("LoginActivity","onError(LoginActivity.java:80)"+msg);
//                                loadingPopup.dismiss();
                              ToastUtil.showShort(msg);
                                loadingPopup.dismiss();
                            }
                        });
                break;
        }
    }


}
