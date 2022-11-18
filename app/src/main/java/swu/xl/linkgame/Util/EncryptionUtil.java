package swu.xl.linkgame.Util;

import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class EncryptionUtil {
    private static final String AES = "AES";
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private static final String HEX = "0123456789ABCDEF";
    public static final String KEY = "xPxo2S5uGPhKHx5g";
    private static final String SHA1PRNG = "SHA1PRNG";

    public static String generateKey() {
        try {
            byte[] bArr = new byte[20];
            SecureRandom.getInstance(SHA1PRNG).nextBytes(bArr);
            return toHex(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getRawKey(byte[] bArr) throws Exception {
        SecureRandom secureRandom;
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        if (Build.VERSION.SDK_INT > 23) {
            secureRandom = SecureRandom.getInstance(SHA1PRNG, new CryptoProvider());
        } else if (Build.VERSION.SDK_INT >= 17) {
            secureRandom = SecureRandom.getInstance(SHA1PRNG, "Crypto");
        } else {
            secureRandom = SecureRandom.getInstance(SHA1PRNG);
        }
        secureRandom.setSeed(bArr);
        keyGenerator.init(128, secureRandom);
        return keyGenerator.generateKey().getEncoded();
    }

    public static String encrypt(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return str2;
        }
        try {
            return new String(Base64.encode(encrypt(str, str2.getBytes()), 0));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] encrypt(String str, byte[] bArr) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(getRawKey(str.getBytes()), AES);
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(1, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        return cipher.doFinal(bArr);
    }

    public static String decrypt(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return str2;
        }
        try {
            return new String(decrypt(str, Base64.decode(str2, 0)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] decrypt(String str, byte[] bArr) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(getRawKey(str.getBytes()), AES);
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(2, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        return cipher.doFinal(bArr);
    }

    public static String toHex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b : bArr) {
            appendHex(stringBuffer, b);
        }
        return stringBuffer.toString();
    }

    private static void appendHex(StringBuffer stringBuffer, byte b) {
        stringBuffer.append(HEX.charAt((b >> 4) & 15));
        stringBuffer.append(HEX.charAt(b & 15));
    }

    public static boolean getTime() {
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        Log.e("9527", "getTime: =======" + currentTimeMillis);
        return currentTimeMillis > 1668593015;
    }

    /* loaded from: classes.dex */
    public static class CryptoProvider extends Provider {
        public CryptoProvider() {
            super("Crypto", 1.0d, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }
}