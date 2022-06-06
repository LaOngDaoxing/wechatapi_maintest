package com.ljx.wamtneln.util;

import java.util.Random;

/**
 * @author ljx
 * @Description: 初始化并赋值Str
 * @FR功能需求：
 * @ImportJar:
 * @ApiGrammer规则：
 * @Remark:
 * @AlibabaCodeStatuteScanError：
 * @CodeBug解决:
 * @Debug调试：
 * @date 2022/6/6 14:35
 */
public class InitStrJsonStr {

    /**
     * 生成15位随机字符串
     * @return
     */
    public static String initLen15NonceStr()
    {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++ )
        {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
