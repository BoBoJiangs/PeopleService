package cn.sts.base.util;

/**
 * 字符串字节转换工具类
 * Created by weilin on 2018/6/25.
 */
public class StringByteUtil {

    /**
     * 字符串转字节数组
     *
     * @param data       字符串数据
     * @param byteLength byte数组长度
     * @return 字节数组
     */
    public static byte[] stringToBytes(String data, int byteLength) {
        byte[] bytes = new byte[byteLength];
        if (data != null) {
            byte[] srcBytes = data.getBytes();
            System.arraycopy(srcBytes, 0, bytes, 0, srcBytes.length);
        }
        return bytes;
    }
}
