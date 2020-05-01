package com.example.parttime.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.parttime.R;
import com.example.parttime.base.BaseFragment;
import com.example.parttime.entity.node.AcceptPerson;
import com.example.parttime.entity.node.UserEntity;
import com.example.parttime.net.ApiService;
import com.example.parttime.net.AppHttpClient;
import com.example.parttime.net.callback.CallBackWrapper;
import com.example.parttime.utils.LogUtil;
import com.example.parttime.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *  Create By  791243928@qq.com
 *
 *
 */
public class MyFragment extends BaseFragment {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.cl_person_info)
    ConstraintLayout clPersonInfo;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_id)
    TextView tvId;

    private Unbinder unbinder;
    private TimePickerView pvTime;
    private UserEntity userEntity ;
    private String dateTime ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            //界面可见
            LogUtil.e("BlessInfoFragment", "可见");
            //不在这里面设置，翻车
            ImmersionBar.with(this).keyboardEnable(true).init();
            ImmersionBar.with(this)
                    .statusBarDarkFont(true, 0.2f)
                    .titleBar(R.id.toolbar) //设置的其实是相应的布局
                    .navigationBarColor(R.color.btn3)
                    .init();
        } else {
            //界面不可见 相当于onPause
            LogUtil.e("BlessInfoFragment", "不可见");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        userEntity = UserEntity.getSingleton();
        initDate();
    }




    private void initDate(){
        tvName.setText(userEntity.getUsername());
        tvPhone.setText(userEntity.getMobile());
        tvId.setText(userEntity.getUserId()+"");
    }



    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();//视图销毁时必须解绑
        Log.e("songbl ", "销毁CFragment====");
    }






    private void callPhone(String phone){
        LogUtil.e("MyFragment","callPhone(MyFragment.java:247)"+"拨打电话");
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" ));
//            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                // 如果进入这个方法，则说明没有申请通过拨打电话权限，在这里申请就好了
//                return;
//            }
            startActivity(intent);
    }
}
