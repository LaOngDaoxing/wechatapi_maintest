package com.ljx.wamtneln.classextends;

import com.ljx.wamtneln.util.RsfValidatorUtil;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.Provider;

/**
 * @Description：

 */
/**
 * @author ljx
 * @Description: KeyStores继承KeyStore
 * @文件来源：   commons-cli-1.2.1.jar解压后，找到文件KeyStores.class；
                用反编译工具JD-GUI或IDEA，打开文件KeyStores.class，复制文件中的所有java代码(明文)；
                新建KeyStores.java，并粘贴java代码明文。
 * @FR功能需求：
 * @ImportJar:
 * @ApiGrammer规则：
 * @Remark:
 * @AlibabaCodeStatuteScanError：
 * @CodeBug解决:
 * @Debug调试：
 * @date 2022/5/16 9:01
 */
public class KeyStores extends KeyStore{
    protected KeyStores(KeyStoreSpi keyStoreSpi, Provider provider, String type)
    {
        super(keyStoreSpi, provider, type);
    }

    /**
     * 重写父类方法getInstance
     * @param type
     * @param rule
     * @param regularObj
     * @return
     * @throws KeyStoreException
     */
    public static KeyStore getInstance(String type, String rule, Object regularObj) throws KeyStoreException{
        RsfValidatorUtil.validate(rule, regularObj);
        return getInstance(type);
    }
}
