package com.huawei.android.hms.agent.game.checksignutil;

public class Base64Util {
    private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    private static byte[] codes = new byte[256];

    static {
        for (int i = 0; i < 256; i++) {
            codes[i] = -1;
        }
        for (int i2 = 65; i2 <= 90; i2++) {
            codes[i2] = (byte) (i2 - 65);
        }
        for (int i3 = 97; i3 <= 122; i3++) {
            codes[i3] = (byte) ((i3 + 26) - 97);
        }
        for (int i4 = 48; i4 <= 57; i4++) {
            codes[i4] = (byte) ((i4 + 52) - 48);
        }
        byte[] bArr = codes;
        bArr[43] = 62;
        bArr[47] = 63;
    }

    public static String encode(byte[] bArr) {
        return encode(bArr, bArr.length);
    }

    public static String encode(byte[] bArr, int i) {
        boolean z;
        char[] cArr = new char[(((i + 2) / 3) * 4)];
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int i4 = (bArr[i2] & -1) << 8;
            int i5 = i2 + 1;
            boolean z2 = true;
            if (i5 < i) {
                i4 |= bArr[i5] & -1;
                z = true;
            } else {
                z = false;
            }
            int i6 = i4 << 8;
            int i7 = i2 + 2;
            if (i7 < i) {
                i6 |= bArr[i7] & -1;
            } else {
                z2 = false;
            }
            int i8 = 64;
            cArr[i3 + 3] = alphabet[z2 ? i6 & 63 : 64];
            int i9 = i6 >> 6;
            int i10 = i3 + 2;
            char[] cArr2 = alphabet;
            if (z) {
                i8 = i9 & 63;
            }
            cArr[i10] = cArr2[i8];
            int i11 = i9 >> 6;
            int i12 = i3 + 1;
            char[] cArr3 = alphabet;
            cArr[i12] = cArr3[i11 & 63];
            cArr[i3 + 0] = cArr3[(i11 >> 6) & 63];
            i2 += 3;
            i3 += 4;
        }
        return new String(cArr);
    }

    public static byte[] decode(String str) {
        int length = str.length();
        int i = 0;
        while (i < str.length()) {
            if (str.charAt(i) > 255 || codes[str.charAt(i)] < 0) {
                length--;
            }
            i++;
        }
        int i2 = (length / 4) * 3;
        int i3 = length % 4;
        if (i3 == 3) {
            i2 += 2;
        }
        if (i3 == 2) {
            i2++;
        }
        byte[] bArr = new byte[i2];
        int i4 = 0;
        byte b = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < str.length(); i6++) {
            byte b2 = str.charAt(i6) > 255 ? -1 : codes[str.charAt(i6)];
            if (b2 >= 0) {
                i5 += 6;
                b = (byte) ((b << 6) | b2);
                if (i5 >= 8) {
                    i5 -= 8;
                    int i7 = i4 + 1;
                    bArr[i4] = (byte) ((b >> i5) & 255);
                    i4 = i7;
                }
            }
        }
        return i4 != bArr.length ? new byte[0] : bArr;
    }
}