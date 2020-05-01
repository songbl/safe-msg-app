package com.example.parttime.ui.fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.parttime.AppApplication;
import com.example.parttime.R;
import com.example.parttime.entity.node.UserEntity;
import com.example.parttime.net.ApiService;
import com.example.parttime.net.AppHttpClient;
import com.example.parttime.net.bean.UserBean;
import com.example.parttime.net.callback.CallBackWrapper;
import com.example.parttime.utils.LogUtil;
import com.example.parttime.utils.ToastUtil;
import com.example.parttime.utils.ValidateUtil;
import com.gyf.immersionbar.ImmersionBar;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Create By  791243928@qq.com
 */

public class InfoDeFragment extends BaseImmersionFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_symmetry)
    Button btnSymmetry;
    @BindView(R.id.btn_no_symmetry)
    Button btnNoSymmetry;
//    @BindView(R.id.et_en)
//    EditText etEn;
//    @BindView(R.id.btn_symmetry_en)
//    Button btnSymmetryEn;
    @BindView(R.id.et_de)
    EditText etDe;
    @BindView(R.id.et_no_de)//非对称解密输入
    EditText etNoDe;
//    @BindView(R.id.et_en__no)//非对称加密输入
//    EditText etNoEn;
    @BindView(R.id.et_phone_num)//对方手机号码
    EditText etPhone;


    @BindView(R.id.btn_symmetry_de)
    Button btnSymmetryDe;
    @BindView(R.id.ll_symmetry)
    LinearLayout llSymmetry;
    @BindView(R.id.ll_no_symmetry)
    LinearLayout llNoSymmetry;
//
//    @BindView(R.id.btn_no_symmetry_en)
//    Button btnNoSymmetryEn;
    @BindView(R.id.btn_no_de)
    Button btnNoDe;



    public static InfoDeFragment NewInstanse() {
        InfoDeFragment latestNewsFragment = new InfoDeFragment();
        return latestNewsFragment;
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarColor(R.color.btn3)
                .init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_de_info;
    }

    @Override
    protected void initView() {
        super.initView();

    }





    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            //界面可见
            LogUtil.e("BlessInfoFragment", "可见");
//            initDate(UserEntity.getSingleton().getUserId());
            ClipboardManager cm = (ClipboardManager) AppApplication.getInstance().getSystemService(CLIPBOARD_SERVICE);
            ClipData data = cm.getPrimaryClip();
            // ClipData 里保存了一个ArryList 的 Item 序列， 可以用 getItemCount() 来获取个数
            ClipData.Item item = data.getItemAt(0);
            String text = item.getText().toString();// 注意 item.getText 可能为空
            if (!TextUtils.isEmpty(text)){
                LogUtil.e("InfoDeFragment","initView(InfoDeFragment.java:106)"+text);
            }
        } else {
            //界面不可见 相当于onPause
            LogUtil.e("BlessInfoFragment", "不可见");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.btn_symmetry, R.id.btn_no_symmetry,  R.id.btn_symmetry_de, R.id.btn_no_de})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_symmetry:
                llSymmetry.setVisibility(View.VISIBLE);
                llNoSymmetry.setVisibility(View.GONE);
                break;
            case R.id.btn_no_symmetry:
                llSymmetry.setVisibility(View.GONE);
                llNoSymmetry.setVisibility(View.VISIBLE);
                break;
//            case R.id.btn_symmetry_en:
//                String s = etEn.getText().toString();
//                if (s.isEmpty()){
//                    ToastUtil.showShort("请输入内容");
//                }else {
//                    String symmetryEnCode = symmetryEnCode(s);
//                    etEn.setText(symmetryEnCode);
//                }
//                break;
            case R.id.btn_symmetry_de:
                String str = etDe.getText().toString();
                if (str.isEmpty()){
                    ToastUtil.showShort("请输入内容");
                }else {
                    String symmetryDecode = symmetryDecode(str);
                    etDe.setText(symmetryDecode);
                }
                break;
//            case R.id.btn_no_symmetry_en: //非对称加密
//                String noEncode = etNoEn.getText().toString();
//                if (noEncode.isEmpty()){
//                    ToastUtil.showShort("请输入内容");
//                }else {
//                    String noSymmetryEncodeEncode = noSymmetryEncode(noEncode);
//                    etNoEn.setText(noSymmetryEncodeEncode);
//                    ToastUtil.showShort("非对称加密成功");
//                }
//
//                break;
            case R.id.btn_no_de: //非对称解密
                String noDecode = etNoDe.getText().toString();
                boolean phone = ValidateUtil.phone(etPhone.getText().toString());
                if (!phone){
                    ToastUtil.showShort("请输入正确的手机号");
                    return  ;
                }

                if (noDecode.isEmpty()){
                    ToastUtil.showShort("请输入内容");
                }else {
                     noSymmetrDecode(noDecode, etPhone.getText().toString().trim());
                    ToastUtil.showShort("非对称解密成功");
                }
                break;
        }
    }


//    private String symmetryEnCode(String s) {
//        //待加密原文
////        String content = "哈哈哈，牛逼 plus ！";
//        LogUtil.e("InfoFragment", "test(InfoFragment.java:80) 内容" + s);
//        //随机生成密钥
////        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
//        byte[] key = {123, -22, -74, -60, 97, 21, 117, 43, 47, 12, 80, 6, -124, 112, -7, 35};
//        //构建
//        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
//        //加密为16进制表示
//        String encryptHex = aes.encryptHex(s);
//        LogUtil.e("InfoFragment", "test(InfoFragment.java:94) 加密后的内容" + encryptHex);
//        return encryptHex;
//    }

    private String symmetryDecode(String s){
        byte[] key = {123, -22, -74, -60, 97, 21, 117, 43, 47, 12, 80, 6, -124, 112, -7, 35};
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        //解密为字符串
        String decryptStr = aes.decryptStr(s, CharsetUtil.CHARSET_UTF_8);
        LogUtil.e("InfoFragment", "test(InfoFragment.java:95)  解密后的内容" + decryptStr);
        return decryptStr ;
    }


//    private String noSymmetryEncode(String str){
//        UserEntity singleton = UserEntity.getSingleton();
//        String priKey = singleton.getPriKey() ;
//        String pubKey = singleton.getPubKey() ;
//
//        RSA rsa = new RSA(Base64.decode(priKey), Base64.decode(pubKey));
//       //公钥加密，私钥解密
//        byte[] encrypt = rsa.encrypt(StrUtil.bytes(str, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
//      //  String str = StrUtil.str(encrypt, CharsetUtil.CHARSET_UTF_8);
//        String encode = Base64.encode(encrypt);
//        return encode ;
//
//    }


    //需要动态获取手机号，通过手机号查询解密秘钥
    private void noSymmetrDecode(String str ,String phoneNum){
        loadingPopup.show();
        UserBean userBean = new UserBean();
        userBean.setMobile(phoneNum);
        AppHttpClient.getInstance().getRetrofit().create(ApiService.class)
                .getKey(userBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CallBackWrapper<UserEntity>() {
                    @Override
                    public void onSuccess(UserEntity userEntity) {
                    LogUtil.e("InfoFragment","onSuccess(InfoFragment.java:289)"+userEntity.toString());
                        loadingPopup.dismiss();
                        RSA rsa = new RSA(Base64.decode(userEntity.getPriKey()), Base64.decode(userEntity.getPubKey()));
                        //公钥加密，私钥解密
                        LogUtil.e("InfoFragment", "test2(InfoFragment.java:129) encode " + str);
                        byte[] decrypt = rsa.decrypt(Base64.decode(str), KeyType.PrivateKey);

                        String deCode  = StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8) ;
                        LogUtil.e("InfoFragment","onSuccess(InfoFragment.java:297)"+deCode);
                        etNoDe.setText(deCode);

                    }

                    @Override
                    public void onError(String msg, int code) {
                        loadingPopup.dismiss();
                        ToastUtil.showShort(msg);

                    }
                });

    }

}
