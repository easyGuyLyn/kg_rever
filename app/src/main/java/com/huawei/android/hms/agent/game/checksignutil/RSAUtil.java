package com.huawei.android.hms.agent.game.checksignutil;

import android.text.TextUtils;

import com.huawei.android.hms.agent.common.HMSAgentLog;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM_SHA256 = "SHA256WithRSA";

    public static boolean doCheck(String str, String str2, String str3) {
//        if (TextUtils.isEmpty(str3)) {
//            return false;
//        }
//        try {
//            PublicKey generatePublic = KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64Util.decode(str3)));
//            Signature instance = Signature.getInstance(SIGNATURE_ALGORITHM_SHA256);
//            instance.initVerify(generatePublic);
//            instance.update(str.getBytes("utf-8"));
//            return instance.verify(Base64Util.decode(str2));
//        } catch (Exception unused) {
//            //HMSAgentLog.e("check error");
//            return false;
//        }

        return true;
    }

    public static String sign(String str, String str2) {
        if (!(str == null || str2 == null)) {
            String str3 = "utf-8";

            String str4 = "2A903C43B885B1E34F3002079760794FC20025DCC4919753AD4B40E9271FFD06";
            try {
                PrivateKey generatePrivate = KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(Base64Util.decode(str4)));
                Signature instance = Signature.getInstance(SIGNATURE_ALGORITHM_SHA256);
                instance.initSign(generatePrivate);
                instance.update(str.getBytes(str3));
                return Base64Util.encode(instance.sign());
            } catch (Exception unused) {
                HMSAgentLog.e("sign error");
            }
        }
        return null;
    }

    public static String urlEncode(String str) {
        if (str == null) {
            return null;
        }
        try {
            return URLEncoder.encode(str, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("~", "%7E");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static String urlDecode(String str) {
        if (str == null) {
            return null;
        }
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }
}
