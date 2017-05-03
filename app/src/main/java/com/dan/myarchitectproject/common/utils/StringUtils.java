package com.dan.myarchitectproject.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Dan
 * Date: 2017/4/10 上午9:52
 * Description:
 * PackageName: com.dan.myarchitectproject.common.utils
 * Copyright: 杭州安存网络科技有限公司
 **/

public class StringUtils {
    /**
     * 验证字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if (str == null || "".equals(str) || "null".equals(str))
            return true;
        return false;
    }

    /**
     * 验证手机号
     * @param value
     * @return
     */
    public static boolean isMobileNo(String value){
        if (isEmpty(value))
            return false;
        Pattern p = Pattern
                .compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean isEmailAddress(String email) {
        if (isEmpty(email))
            return false;
        Pattern p = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 去除空格
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 验证字符串长度
     * @param str
     * @return
     */
    public static boolean lengthVerify(String str){
        if (isEmpty(str))
            return false;
        if (str.length()>=6 && str.length()<=20)
            return true;
        return false;
    }

    /**
     * 验证是否有特殊字符
     * @param str
     * @return
     */
    public static boolean isSpecialWord(String str) {
        String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?",
                "^", "{", "}", "|", "@", "#", ",", "-", "_", "&", "%", "!", "`", "~"};
        for (String key : fbsArr) {
            if (str.contains(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测是否包含中文
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        String regEx = "[\\u4E00-\\u9FA5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
}
