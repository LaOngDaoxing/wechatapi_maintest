package com.ljx.wamt.service;

import com.ljx.wamt.entity.WechatPayReqDTO;
import com.ljx.wamt.wxutils.WechatPayUtil;
import com.ljx.wamtneln.util.constantutil.ConstantUtil;
import com.ljx.wamtneln.util.constantutil.RegexExpConstantUtil;
import com.ljx.wamtneln.util.stringutil.RegexMatchUtil;

/**
 * @author ljx
 * @Description: 微信红包接口的业务逻辑层
 * @FR功能需求：
 * @ImportJar:
 * @ApiGrammer规则：
 * @Remark:
 * @AlibabaCodeStatuteScanError：
 * @CodeBug解决:
 * @Debug调试：
 * @date 2022/6/6 15:40
 */
public class WechatPayService {
    /**
     * 微信商户appkey
     */
    private static final String APP_KEY = "UYGWEDIUAWJSOF45256456465DSFSDFG";
    /**
     * 微信商户证书路径；java开发使用apiclient_cert.p12，php开发使用apiclient_cert.pem
     */
    private static final String CERT_PATH = "D:\\demo\\apiclient_cert.p12";
    /**
     * 微信支付接口
     */
    private static final String TRANS_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    public static void main(String[] args) {
        // 微信支付红包完整调用示例
        wxPayHbServiceFunTest();
    }

    /**
     * 微信支付红包完整调用示例
     * wxPayHbServiceFunTest()推荐写在微信红包接口的业务逻辑层
     * @return
     */
    public static String wxPayHbServiceFunTest(){
        // 微信接口请求参数, 根据实际情况填写
        WechatPayReqDTO wechatPayReqDTO = createWechatPayReqDTO();
        String rstMsg="";
        // 拼接微信红包支付接口请求xml报文
        String reqXmlStr= WechatPayUtil.jointWechatPayReqXml(wechatPayReqDTO).toString();
        try{
            // 调用http post请求
            String rstXmlStr = WechatPayUtil.hbCallHttpPost( reqXmlStr, wechatPayReqDTO);
            // 正则获取微信返回码
            String bb=  RegexMatchUtil.matchOneByPatternCompileMatchGroup(rstXmlStr, RegexExpConstantUtil.REGEX_RETURN_CODE,null,null);
            // SUCCESS
            String returnCode= RegexMatchUtil.regReplaceAll(bb,RegexExpConstantUtil.REGEX_RETURN_CODE_CONTENT);
            if(rstXmlStr.contains(ConstantUtil.STR_WX_RETURN_CODE_SUCCESS)&& !rstXmlStr.contains(ConstantUtil.STR_WX_RETURN_CODE_PAYFAIL)){
                rstMsg= "微信支付到零钱成功";
            }
            else{
                rstMsg= "调用微信接口失败, 具体信息请查看访问日志";
            }
        }
        catch (Exception e){
            e.printStackTrace();
            rstMsg= e.getMessage();
        }
        return rstMsg;
    }
    /**
     * 生成微信请求参数对象
     * @param
     * @param
     * @return
     */
    public static WechatPayReqDTO createWechatPayReqDTO(){
        // 微信接口请求参数, 根据实际情况填写
        WechatPayReqDTO wechatPayReqDTO = new WechatPayReqDTO();
        // 申请商户号的appid或商户号绑定的appid
        wechatPayReqDTO.setMch_appid("wx5578d2c602fbe8a6");
        // 商户号
        wechatPayReqDTO.setMchid("1372809402");
        // 商户名称
        wechatPayReqDTO.setMch_name("某某商户名称");
        // 商户appid下，绑定的某微信用户的openid；此微信用户零钱将收款3毛
        wechatPayReqDTO.setOpenid("开发人员|测试人员|收款者手机微信的openid");
        // 企业付款金额，这里单位为元
        wechatPayReqDTO.setAmount(0.3);
        // 微信商户appkey
        wechatPayReqDTO.setAppkey(APP_KEY);
        // 微信商户证书路径
        wechatPayReqDTO.setCert_path(CERT_PATH);
        // 微信支付接口
        wechatPayReqDTO.setTrans_url(TRANS_URL);
        // 业务相关需求字段：红包来源标志 0会员红包、1问卷红包
        String hblybz="1";
        if(ConstantUtil.STR_HBLYBZ_0.equals(hblybz)){
            wechatPayReqDTO.setDesc("会员红包");
        }else{
            wechatPayReqDTO.setDesc("问卷红包");
        }
        return wechatPayReqDTO;
    }
}
