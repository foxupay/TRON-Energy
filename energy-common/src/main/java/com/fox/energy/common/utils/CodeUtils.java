package com.fox.energy.common.utils;

/**
 * 生成不同格式的验证码
 */
public class CodeUtils {

    /**
     * 创建数字类型的验证码
     *
     * @param numbers 数字长度
     * @return
     */
    public static String createNumbers(int numbers) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < numbers; i++) {
            code.append((int) (Math.random() * 9 + 1));
        }
        return code.toString();
    }


}
