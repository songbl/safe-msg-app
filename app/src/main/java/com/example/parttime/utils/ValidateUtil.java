package com.example.parttime.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

    /**
     * 验证手机号码是否有效
     * @param phone
     * @return
     */
    public static boolean phone(String phone){
        //是否有效
        boolean isValid = false;
        if (!TextUtils.isEmpty(phone)) {
            String regex = "^1[3|4|5|7|8]\\d{9}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(phone);
            isValid = matcher.matches();
        }
        return isValid;
    }

    /**
     * 验证身份证号码是否有效
     * @param sfzh
     * @return
     */
    public static boolean sfzh(String sfzh){
        //是否有效
        boolean isValid = false;
        if (!TextUtils.isEmpty(sfzh)) {
            String regex = "^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(sfzh);
            isValid = matcher.matches();
        }
        return isValid;
    }


    /**
     * 验证邮箱地址是否有效
     * @param eamil
     * @return
     */
    public static boolean eamil(String eamil){
        //是否有效
        boolean isValid = false;
        if (!TextUtils.isEmpty(eamil)) {
            String regex = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(eamil);
            isValid = matcher.matches();
        }
        return isValid;
    }
}
