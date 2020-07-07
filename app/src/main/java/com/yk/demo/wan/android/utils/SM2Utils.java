package com.yk.demo.wan.android.utils;


import com.yk.demo.wan.android.utils.kit.SM2Kit;
import com.yk.demo.wan.android.utils.kit.SM3Kit;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

/**
 * @author 刘国川
 * @version v1.0
 * @Description:
 * @CreateDate 2019/4/28
 */
public class SM2Utils {

    private static String priKey = "00b7bfdddd3f88cc2d30acd7ea7265af961e54992c8a752f4625a9edf10244c53b";
    private static String pubKey = "0497964fb9f0669d5f1f43ca60ce41524e6acaef3160b1318998d00263c8f1e79291fae7f516f4c9e404f336562806052bba87594bcc51facd81ce8963fdfaf471";

    //生成公私钥对
    public static void  getKeyPair(){
        AsymmetricCipherKeyPair p =  SM2Kit.generateKeyPair();
        String prvate = SM2Kit.getPrivateKey(p);
        String publickey = SM2Kit.getPublicKey(p);
//        logger.info("私钥：" + prvate);
//        logger.info("公钥：" + publickey);
    }



    //后端解密
    public static String decrypt(String data){
        return  SM2Kit.decrypt(data,priKey);
    }
    //后端加密SM2
    public static String encrypt(String data){
        return  SM2Kit.encrypt(data,pubKey);
    }

    //后端加密sm3
    public static String sm3encrypt(String data){
//        Timber.d("========"+SM3Kit.sm3(data));
       return SM3Kit.sm3(data);
    }

//    public static void main(String[] args){
//
//        getKeyPair();
//
//         String  ss = "abce12312312你好";
//         String encrypt = encrypt(ss);
//
////        logger.info("加密：" + encrypt);
//        //String encrypted = encryptForJavaScript(name);
//        String decrypt = decrypt("04aa7d3f4b43793484acf48befb33349bd9cc647fe0123b1aa803dfbabfadd3c7659a9e2e055b33237a369634ca19260c462fa0f747c5b355aad9f5d334d257fd58559137c1120a17a4541d04b3548231ed840427e18c57b8b104d4180a82b329a57eebbb0e62f3c5fc326c0848c4572cc43ec4e89a51246202de93ec30d55e69c93836f578739e37068a48c81b65ed19796ec23840caf1c6b0b349a7da799c0069836a32fd295254dfbcf6c34ff9e3cac4076fba39b0051c9ddd9f68e6544849505cb8a856eee2483ec3fddc954d1905b58ea");
////        logger.info("解密：" + decrypt);
//
//
//    }

}
