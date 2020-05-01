package com.example.parttime.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.parttime.R;
import com.example.parttime.utils.LogUtil;
import com.lxj.xpopup.core.CenterPopupView;

import java.util.List;

import androidx.annotation.NonNull;

public  class AddContractDialog extends CenterPopupView {

    private OnSureClickListener onSureClickListener ;
    Spinner spinner;
    List<Integer> list ;
    public AddContractDialog(@NonNull Context context) {
        super(context);
    }

    public AddContractDialog(@NonNull Context context, List<Integer> list) {
        super(context);
        this.list = list ;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.add_contact_popup;
    }
    @Override
    protected void onCreate() {
        super.onCreate();
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_list_item_1, list));

        findViewById(R.id.tv_commit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String name =( (EditText) findViewById(R.id.et_name)).getText().toString();
                String mobile =( (EditText) findViewById(R.id.et_mobile)).getText().toString();
                LogUtil.e("ChangeDialog","onClick(ChangeDialog.java:32)"+name+mobile);
                String s = spinner.getSelectedItem().toString();
                LogUtil.e("AddContractDialog","onClick(AddContractDialog.java:50)"+s);
                onSureClickListener.onClickOk(name,mobile,Integer.parseInt(s));
                dismiss();
            }
        });
    }
    protected void onShow() {
        super.onShow();
    }



    public interface OnSureClickListener {
        /**
         * 点击确定事件
         */
        void onClickOk(String name, String mobile,int groupId);

    }

    public void setOnSureClickListener(OnSureClickListener listener) {
        this.onSureClickListener = listener;
    }
}