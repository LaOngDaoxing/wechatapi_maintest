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
 * @date 2022/5/27 11:17
 */
public class ConstantUtil {
    /**
     * 连接超时时间，默认10秒
     */
    public static final int INT_SOCKET_TIMEOUT_10000 = 10000;
    /**
     * 传输超时时间，默认30秒
     */
    public static final int INT_CONNECT_TIMEOUT_30000 = 30000;
    /**
     * 业务相关需求字段：红包来源标志 0会员红包、1问卷红包
     */
    public static final String STR_HBLYBZ_0="0";
    public static final String STR_WX_RETURN_CODE_SUCCESS="<return_code><![CDATA[SUCCESS]]></return_code>";
    public static final String STR_WX_RETURN_MSG_SUCCESS="<return_msg><![CDATA[]]></return_msg>";
    public static final String STR_WX_RETURN_CODE_FAIL="CDATA[FAIL]";
    public static final String STR_WX_RETURN_CODE_PAYFAIL="CDATA[支付失败]";
}
