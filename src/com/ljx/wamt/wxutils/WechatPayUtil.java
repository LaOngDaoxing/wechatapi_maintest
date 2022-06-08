package com.ljx.wamt.wxutils;

import com.ljx.wamtneln.classextends.KeyStores;
import com.ljx.wamt.entity.WechatPayReqDTO;
import com.ljx.wamtneln.util.constantutil.ConstantUtil;
import com.ljx.wamtneln.util.InitStrJsonStr;
import com.ljx.wamtneln.util.stringutil.MapGetter;
import com.ljx.wamtneln.util.constantutil.RegexExpConstantUtil;
import com.ljx.wamtneln.util.stringutil.RegexMatchUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.*;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微信红包支付工具类
 * 微信公众平台https://mp.weixin.qq.com/
 * 只有企业认证的服务号才有微信支付功能，订阅号没有微信支付功能。
 * 微信支付|开发文档 境内普通用户https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
 */
public class WechatPayUtil {
    private static final Log LOG = LogFactory.getLog(WechatPayUtil.class);    /**
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
    /**
     * 请求器的配置
     */
    private static RequestConfig requestConfig;
    /**
     * HTTP请求器
     */
    private static CloseableHttpClient httpClient;

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
        wechatPayReqDTO.setMch_name("河北壹捌掌信息科技有限公司");
        // 商户appid下，绑定的某微信用户的openid（开发人员|测试人员|收款者手机微信的openid）；此微信用户零钱将收款3毛
        wechatPayReqDTO.setOpenid("oQATF5FVew5M44m8DSIu_Ut9nxOQ");
        // 企业付款金额，这里单位为元
        wechatPayReqDTO.setAmount(0.3);
        // 微信商户appkey
        wechatPayReqDTO.setAppkey(APP_KEY);
        // 微信商户证书路径
        wechatPayReqDTO.setCert_path(CERT_PATH);
        // 微信支付接口
        wechatPayReqDTO.setTrans_url(TRANS_URL);
        // 商户订单号
        wechatPayReqDTO.setPartner_trade_no("SPXTTZZFWB50142860");
        // 业务相关需求字段：红包来源标志 0会员红包、1问卷红包
        String hblybz="1";
        if(ConstantUtil.STR_HBLYBZ_0.equals(hblybz)){
            wechatPayReqDTO.setDesc("会员红包");
        }else{
            wechatPayReqDTO.setDesc("问卷红包");
        }
        return wechatPayReqDTO;
    }
    /**
     * 拼接微信红包支付接口请求xml报文
     * @param wechatPayReqDTO
     * @return
     */
    public static StringBuilder jointWechatPayReqXml(WechatPayReqDTO wechatPayReqDTO){
        // 1.计算参数签名
        String paramStr = WechatPayUtil.createLinkString(wechatPayReqDTO);
        String mysign = paramStr + "&key=" + wechatPayReqDTO.getAppkey();
        String sign = DigestUtils.md5Hex(mysign).toUpperCase();

        // 2.封装请求参数
        StringBuilder reqXmlSb = new StringBuilder();
        reqXmlSb.append("<xml>");
        reqXmlSb.append("<mchid>" + wechatPayReqDTO.getMchid() + "</mchid>");
        reqXmlSb.append("<mch_appid>" + wechatPayReqDTO.getMch_appid() + "</mch_appid>");
        reqXmlSb.append("<nonce_str>" + wechatPayReqDTO.getNonce_str() + "</nonce_str>");
        reqXmlSb.append("<check_name>" + wechatPayReqDTO.getCheck_name() + "</check_name>");
        reqXmlSb.append("<openid>" + wechatPayReqDTO.getOpenid() + "</openid>");
        reqXmlSb.append("<amount>" + wechatPayReqDTO.getAmount() + "</amount>");
        reqXmlSb.append("<desc>" + wechatPayReqDTO.getDesc() + "</desc>");
        reqXmlSb.append("<sign>" + sign + "</sign>");
        reqXmlSb.append("<partner_trade_no>" + wechatPayReqDTO.getPartner_trade_no() + "</partner_trade_no>");
        reqXmlSb.append("<spbill_create_ip>" + wechatPayReqDTO.getSpbill_create_ip() + "</spbill_create_ip>");
        reqXmlSb.append("</xml>");
        LOG.info("微信红包支付接口请求xml报文——" + reqXmlSb);
        return reqXmlSb;
    }
    /**
     * 加载证书
     *
     * @param path
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static void initCert(String path, WechatPayReqDTO transfer)
            throws IOException, KeyStoreException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyManagementException
    {
        // 拼接证书的路径
        KeyStore keyStore = KeyStores.getInstance("PKCS12", path, transfer.map());

        // 加载本地的证书进行https加密传输
        FileInputStream instream = new FileInputStream(new File(path));
        try
        {
            // 加载证书密码，默认为商户ID
            keyStore.load(instream, transfer.getMchid().toCharArray());
        }
        catch (CertificateException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        finally
        {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,
                transfer.getMchid().toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
                new String[] {"TLSv1"}, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        // 根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(ConstantUtil.INT_SOCKET_TIMEOUT_10000).setConnectTimeout(ConstantUtil.INT_CONNECT_TIMEOUT_30000).build();
    }
    /**
     * 调用http post请求
     * @param reqXmlStr
     * @return
     */
    public static String hbCallHttpPost(String reqXmlStr, WechatPayReqDTO wechatPayReqDTO) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        // 加载证书
        initCert(wechatPayReqDTO.getCert_path(), wechatPayReqDTO);
        String result = null;
        HttpPost httpPost = new HttpPost(wechatPayReqDTO.getTrans_url());
        // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(reqXmlStr, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        // 设置请求器的配置
        httpPost.setConfig(requestConfig);
        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            LOG.info(("微信红包支付接口返回xml报文——" + result));
        }
        catch (ConnectionPoolTimeoutException e)
        {
            e.printStackTrace();
        }
        catch (ConnectTimeoutException e)
        {
            e.printStackTrace();
        }
        catch (SocketTimeoutException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            httpPost.abort();
        }

        return result;
    }
    /**
     * 生成微信签名
     * @param wechatPayReqDTO
     * @return
     */
    public static String createLinkString(WechatPayReqDTO wechatPayReqDTO)
    {
        // 微信签名规则 https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=4_3
        Map<String, Object> paramMap = new HashMap<String, Object>(MapGetter.defaultInitialCapacity());

        // 商户订单号partner_trade_no：默认用商户号+时间戳+4位随机数+可以根据自己的规则进行调整；注意商户订单号将用于发红包公司和微信对账使用，必须唯一。
        wechatPayReqDTO.setAppkey(wechatPayReqDTO.getAppkey());
        wechatPayReqDTO.setNonce_str(InitStrJsonStr.initLen15NonceStr());
        // 仅main方法测试时使用随机生成的商户订单号
//        wechatPayReqDTO.setPartner_trade_no(wechatPayReqDTO.getMchid()
//                                  + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
//                                  + (int)((Math.random() * 9 + 1) * 1000));

        paramMap.put("mch_appid", wechatPayReqDTO.getMch_appid());
        paramMap.put("mchid", wechatPayReqDTO.getMchid());
        paramMap.put("openid", wechatPayReqDTO.getOpenid());
        paramMap.put("amount", wechatPayReqDTO.getAmount());
        paramMap.put("check_name", wechatPayReqDTO.getCheck_name());
        paramMap.put("desc", wechatPayReqDTO.getDesc());
        paramMap.put("partner_trade_no", wechatPayReqDTO.getPartner_trade_no());
        paramMap.put("nonce_str", wechatPayReqDTO.getNonce_str());
        paramMap.put("spbill_create_ip", wechatPayReqDTO.getSpbill_create_ip());

        List<String> keys = new ArrayList(paramMap.keySet());
        Collections.sort(keys);
        StringBuilder preSb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++ )
        {
            String key = keys.get(i);
            Object value = (Object)paramMap.get(key);
            if (i == keys.size() - 1)
            {
                // 拼接时，不包括最后一个&字符
                preSb.append(preSb + key + "=" + value);
            }
            else
            {
                preSb.append(preSb + key + "=" + value + "&");
            }
        }
        String prestr=preSb.toString();
        return prestr;
    }
}