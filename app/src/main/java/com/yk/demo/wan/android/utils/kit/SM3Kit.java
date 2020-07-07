package com.yk.demo.wan.android.utils.kit;

//import lombok.experimental.UtilityClass;
//import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import java.io.UnsupportedEncodingException;

/**
 * 摘要算法
 * @author YS
 * Created by YS on 2017/2/18 0018.
 */
//@Slf4j
//@UtilityClass
public class SM3Kit {

	/**
	 * SM3摘要
	 *
	 * @param content 会转换成Base64编码
	 * @return 摘要值
	 */
	public static String sm3(final String content) {
		byte[] data = new byte[0];
		try {
			data = content.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
//			log.error("不支持的编码格式", e);
			return null;
		}
		return doDigest(Base64.encode(data), new SM3Digest());
	}

	private static String doDigest(byte[] data, Digest digest) {
		digest.update(data, 0, data.length);
		byte[] result = new byte[digest.getDigestSize()];
		digest.doFinal(result, 0);
		return Hex.toHexString(result);
	}

}
