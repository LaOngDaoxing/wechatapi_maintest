package com.ljx.wamtneln.util.constantutil;

/**
 * @author ljx
 * @Description:
 * @FR功能需求：
 * @ImportJar:
 * @ApiGrammer规则：
 * @Remark:
 * @AlibabaCodeStatuteScanError：
 * @CodeBug解决:
 * @Debug调试：
 * @date 2022/5/27 11:16
 */
public class RegexExpConstantUtil {
    /**
     * 正则匹配双标签包括内容，即匹配结果为"<return_code>及中间的内容</return_code>"
     */
    public static final String REGEX_RETURN_CODE="<return_code[^>]*>(.|\\n)*<\\/return_code>";
    /**
     * 正则匹配标签不包括内容，即"<result_code><![CDATA["和"]]></result_code>"
     */
    public static final String REGEX_RETURN_CODE_CONTENT="(<return_code><!\\[CDATA\\[)|(]]></return_code>)";
}
