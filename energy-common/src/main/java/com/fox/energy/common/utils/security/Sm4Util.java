package com.fox.energy.common.utils.security;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;

public class Sm4Util {
    private static String sm4_key = "3259V2BQwNOv9Mxu";

    public static String encrypt(String content) {
        SM4 sM4 = SmUtil.sm4(sm4_key.getBytes());
        return sM4.encryptHex(content);
    }

    public static String decrypt(String content) {
        SM4 sM4 = SmUtil.sm4(sm4_key.getBytes());
        return sM4.decryptStr(content, CharsetUtil.CHARSET_UTF_8);
    }
}
