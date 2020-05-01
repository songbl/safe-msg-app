package com.example.parttime.net;


import com.example.parttime.entity.node.AcceptPerson;
import com.example.parttime.entity.node.InfoMail;
import com.example.parttime.entity.node.UserEntity;
import com.example.parttime.net.bean.LoginBean;
import com.example.parttime.net.bean.RegisterBean;
import com.example.parttime.net.bean.UserBean;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
/**
 *  Create By  791243928@qq.com
 *
 *
 */

public interface ApiService {
    /**
     * 登录得
     */
    @POST("app/login")
    Observable<UserEntity> loginWithPassword(@Body LoginBean map);

    /**
     * 注册
     */
    @POST("app/register")
    Observable<Boolean> register(@Body RegisterBean map);

    /**
     * 获取祝福短信
     */
    @POST("app/getKey")
    Observable<UserEntity> getKey(@Body UserBean map);




}
