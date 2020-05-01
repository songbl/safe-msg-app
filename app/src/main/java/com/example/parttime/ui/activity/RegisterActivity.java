package com.example.parttime.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.parttime.R;
import com.example.parttime.base.BaseActivity;
import com.example.parttime.net.ApiService;
import com.example.parttime.net.AppHttpClient;
import com.example.parttime.net.bean.RegisterBean;
import com.example.parttime.net.callback.CallBackWrapper;
import com.example.parttime.utils.LogUtil;
import com.example.parttime.utils.ToastUtil;
import com.example.parttime.utils.ValidateUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
/**
 *  Create By  791243928@qq.com
 *
 *
 */

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.rb_boy)
    RadioButton rbBoy;
    @BindView(R.id.rb_girl)
    RadioButton rbGirl;
    @BindView(R.id.rd_gender)
    RadioGroup rdGender;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.content)
    LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);//显示出时间
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImmersionBar.with(this).titleBar(R.id.toolbar)
                .init();
        ButterKnife.bind(this);

    }


    /**
     * {
     * 	"username": "吴奇隆",
     * 	"mobile": "13336371247",
     * 	"password": "123456",
     * 	"gender": 0
     * }
     * @param view
     */
    @OnClick({R.id.et_phone_num, R.id.et_password, R.id.et_nickname, R.id.rb_boy, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_phone_num:
                break;
            case R.id.et_password:
                break;
            case R.id.et_nickname:
                break;
            case R.id.rb_boy:
                break;
            case R.id.btn_register:
                // 注册的时候，
                KeyPair pair = SecureUtil.generateKeyPair("RSA");
                PrivateKey aPrivate = pair.getPrivate();
                PublicKey aPubKey = pair.getPublic();
                byte[] encoded = aPrivate.getEncoded();
                //私钥==privateKey
                String strPri = Base64.encode(encoded);

                //公钥
                byte[] deCode = aPubKey.getEncoded();
                //私钥==privateKey
                String strPub = Base64.encode(deCode);

                int gender = rbGirl.isChecked() ? 0 : 1;
                RegisterBean register = new RegisterBean();
                register.setUsername(etNickname.getText().toString());
                register.setPassword(etPassword.getText().toString());

                boolean phone = ValidateUtil.phone(etPhoneNum.getText().toString());
                if (!phone){
                    ToastUtil.showShort("请输入正确的手机号");
                    return  ;
                }else {
                    register.setMobile(etPhoneNum.getText().toString());
                }

                register.setPriKey(strPri);
                register.setPubKey(strPub);
                register.setGender(gender);



                loadingPopup.show();
                AppHttpClient.getInstance().getRetrofit().create(ApiService.class)
                        .register(register)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CallBackWrapper<Boolean>() {
                            @Override
                            public void onSuccess(Boolean aBoolean) {
                                LogUtil.e("RegisterActivity","onSuccess(RegisterActivity.java:91)"+aBoolean.toString());
                                 loadingPopup.dismiss();
                                 ToastUtil.showShort("注册成功");
                                 finish();
                            }

                            @Override
                            public void onError(String msg, int code) {
                                loadingPopup.dismiss();
                                ToastUtil.showShort("失败"+msg);
                                LogUtil.e("RegisterActivity","onError(RegisterActivity.java:96)"+msg);
                            }
                        });
                break;
        }
    }
}
