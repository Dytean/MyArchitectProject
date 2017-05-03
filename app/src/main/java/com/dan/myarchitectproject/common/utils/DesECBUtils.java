package com.dan.myarchitectproject.common.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author: Dan
 * Date: 2017/4/10 下午6:03
 * Description: DES加密  解密
 * PackageName: com.dan.myarchitectproject.common.utils
 * Copyright: 杭州安存网络科技有限公司
 **/

public class DesECBUtils {
    private static final String ENCRYPT_KEY = "cugo1126";

    /**
     * DES 加密
     *
     * @param encryptString
     * @return
     * @throws Exception
     */
    public static String encryptDES(String encryptString) throws Exception {
        SecretKeySpec key = new SecretKeySpec(getKey(ENCRYPT_KEY), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        // 若采用NoPadding模式，data长度必须是8的倍数
        // Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return bytesToHexString(encryptedData);
    }

    /**
     * 自定义一个key
     *
     * @param keyRule
     * @return
     */
    private static byte[] getKey(String keyRule) {
        Key key = null;
        byte[] keyByte = keyRule.getBytes();
        // 创建一个空的八位数组,默认情况下为0
        byte[] byteTemp = new byte[8];
        // 将用户指定的规则转换成八位数组
        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
            byteTemp[i] = keyByte[i];
        }
        key = new SecretKeySpec(byteTemp, "DES");
        return key.getEncoded();
    }

    /**
     * DES 解密
     *
     * @param decryptString
     * @return
     * @throws Exception
     */
    public static String decryptDES(String decryptString) throws Exception {
        SecretKeySpec key = new SecretKeySpec(getKey(ENCRYPT_KEY), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        // 若采用NoPadding模式，data长度必须是8的倍数
        // Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte decryptedData[] = cipher.doFinal(hexStringToByte(decryptString));
        return new String(decryptedData);
    }

    /**
     * 把16进制字符串转换成字节数组
     *
     * @param hex
     * @return
     */
    private static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    /**
     * 把字节数组转换成16进制字符串
     *
     * @param bArray
     * @return
     */
    private static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }
}
