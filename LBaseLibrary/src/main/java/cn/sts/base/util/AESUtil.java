package cn.sts.base.util;

import android.text.TextUtils;
import android.util.Base64;

import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密
 * <p>
 * Created by weilin on 2018/6/26.
 */
public class AESUtil {

    /**
     * 16进制
     */
    private final static String HEX = "0123456789ABCDEF";

    /**
     * AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
     */
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";

    /**
     * AES 加密
     */
    private static final String AES = "AES";

    /**
     * SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
     */
    private static final String SHA1PRNG = "SHA1PRNG";

    /**
     * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
     *
     * @return 随机秘钥
     */
    public static String generateKey() {
        try {
            SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
            byte[] bytesKey = new byte[20];
            localSecureRandom.nextBytes(bytesKey);
            return byteToHex(bytesKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理秘钥
     *
     * @param seed 秘钥
     * @return 处理后的秘钥
     * @throws Exception 异常
     */
    private static byte[] getKey(byte[] seed) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        //for android
        SecureRandom secureRandom;
        // 在4.2以上版本中，SecureRandom获取方式发生了改变
        int sdk_version = android.os.Build.VERSION.SDK_INT;
        if (sdk_version > 23) {  // Android  6.0 以上
            secureRandom = SecureRandom.getInstance(SHA1PRNG, new CryptoProvider());
        } else if (android.os.Build.VERSION.SDK_INT >= 17) {   //4.2及以上
            secureRandom = SecureRandom.getInstance(SHA1PRNG, "Crypto");
        } else {
            secureRandom = SecureRandom.getInstance(SHA1PRNG);
        }
        // for Java
        // secureRandom = SecureRandom.getInstance(SHA1PRNG);
        secureRandom.setSeed(seed);
        keyGenerator.init(128, secureRandom); //256 bits or 128 bits,192bits
        //AES中128位密钥版本有10个加密循环，192比特密钥版本有12个加密循环，256比特密钥版本则有14个加密循环。
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 加密
     *
     * @param key  秘钥
     * @param data 明文
     * @return 加密后的数据
     */
    public static String encrypt(String key, String data) {
        if (TextUtils.isEmpty(data)) {
            return data;
        }
        try {
            byte[] result = encrypt(key, data.getBytes());
            return new String(Base64.encode(result, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 加密
     *
     * @param key  秘钥
     * @param data 明文
     * @return 加密后的数据
     */
    private static byte[] encrypt(String key, byte[] data) throws Exception {
        byte[] raw = getKey(key.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param key       秘钥
     * @param encrypted 密文
     * @return 解密后数据
     */
    public static String decrypt(String key, String encrypted) {
        if (TextUtils.isEmpty(encrypted)) {
            return encrypted;
        }
        try {
            return new String(decrypt(key, Base64.decode(encrypted, Base64.DEFAULT)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解密
     *
     * @param key       秘钥
     * @param encrypted 密文
     * @return 解密后数据
     */
    private static byte[] decrypt(String key, byte[] encrypted) {
        try {
            byte[] raw = getKey(key.getBytes());
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * 二进制转字符
     *
     * @param bytes 字节数组
     * @return 16进制
     */
    private static String byteToHex(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(2 * bytes.length);
        for (byte b : bytes) {
            appendHex(result, b);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }


    /**
     * 增加  CryptoProvider  类
     */
    private static class CryptoProvider extends Provider {
        /**
         * Creates a Provider and puts parameters
         */
        private CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }

}
