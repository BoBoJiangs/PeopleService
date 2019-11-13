package cn.sts.base.util;

/**
 * 整型字节转换工具
 * Created by weilin on 2018/6/13.
 */
public class IntByteUtil {

    /**
     * 将int转为低字节在前，高字节在后的byte数组
     */
    public static byte[] intToByteArrayLowToHigh(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * 将int转为低字节在前，高字节在后的byte数组，用两个字节保存
     */
    public static byte[] intTo2ByteArrayLowToHigh(int n) {
        byte[] b = new byte[2];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        return b;
    }


    /**
     * 将低字节在前，高字节在后的byte数组转为int
     */
    public static int byteArrayToIntLowToHigh(byte[] bArr) {

        if (bArr.length > 4) {
            return -1;
        }

        int byteLength = bArr.length;
        byte[] aArray = new byte[4];
        for (int i = 0; i < 4; i++) {
            if (i < byteLength) {
                aArray[i] = bArr[i];//从b的开始(即int值的高位)开始copy数据
            } else {
                aArray[i] = 0;//如果b.length不足4,则将高位补0
            }
        }

        return ((aArray[3] & 0xff) << 24)
                | ((aArray[2] & 0xff) << 16)
                | ((aArray[1] & 0xff) << 8)
                | (aArray[0] & 0xff);
    }


    /**
     * 将short转为低字节在前，高字节在后的byte数组
     */
    public static byte[] shortToByteArrayLowToHigh(short n) {
        byte[] b = new byte[2];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        return b;
    }


    /**
     * 将低字节在前，高字节在后的byte数组转为short
     */
    public static int byteArrayToShortLowToHigh(byte[] bArr) {

        if (bArr.length > 2) {
            return -1;
        }

        int byteLength = bArr.length;
        byte[] aArray = new byte[2];
        for (int i = 0; i < 2; i++) {
            if (i < byteLength) {
                aArray[i] = bArr[i];//从b的开始(即int值的高位)开始copy数据
            } else {
                aArray[i] = 0;//如果b.length不足4,则将高位补0
            }
        }

        return ((aArray[1] & 0xff) << 8)
                | (aArray[0] & 0xff);
    }

    /**
     * 将short转为低字节在后，高字节在前的byte数组
     */
    public static byte[] shortToByteArrayHighToLow(short value) {
        byte[] src = new byte[2];
        src[0] = (byte) ((value >> 8) & 0xFF);
        src[1] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将高字节在前，低字节在后的byte数组转为int
     */
    public static short byteArrayToShortHighToLow(byte[] bArr) {
        if (bArr.length > 2) {
            return -1;
        }

        int j = bArr.length - 1;
        byte[] aArray = new byte[2];
        for (int i = 1; i >= 0; i--, j--) {
            if (j < 0) {
                aArray[i] = 0;//如果b.length不足4,则将高位补0
            } else {
                aArray[i] = bArr[j];//从b的结尾(即int值的低位)开始copy数据
            }
        }

        return (short) (((aArray[0] & 0xff) << 8) | (aArray[1] & 0xff));
    }


    /**
     * 将int转为低字节在后，高字节在前的byte数组
     */
    public static byte[] intToByteArrayHighToLow(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将高字节在前，低字节在后的byte数组转为int
     */
    public static int byteArrayToIntHighToLow(byte[] bArr) {
        if (bArr.length > 4) {
            return -1;
        }

        int j = bArr.length - 1;
        byte[] aArray = new byte[4];
        for (int i = 3; i >= 0; i--, j--) {
            if (j < 0) {
                aArray[i] = 0;//如果b.length不足4,则将高位补0
            } else {
                aArray[i] = bArr[j];//从b的结尾(即int值的低位)开始copy数据
            }
        }

        return ((aArray[0] & 0xff) << 24)
                | ((aArray[1] & 0xff) << 16)
                | ((aArray[2] & 0xff) << 8)
                | (aArray[3] & 0xff);
    }

    /**
     * byte转int
     *
     * @param b byte
     * @return int
     */
    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xff;
    }

    /**
     * 两个byte数组合并成一个
     *
     * @param bytes1 byte数组1
     * @param bytes2 byte数组2
     * @return 合并后的数组
     */
    public static byte[] bytesMerger(byte[] bytes1, byte[] bytes2) {
        byte[] bytes = new byte[bytes1.length + bytes2.length];
        System.arraycopy(bytes1, 0, bytes, 0, bytes1.length);
        System.arraycopy(bytes2, 0, bytes, bytes1.length, bytes2.length);
        return bytes;
    }


    /**
     * 将低字节在前，高字节在后的字节数组转长整型
     *
     * @param bytes 8字节
     * @return 长整型
     */
    public static long bytesToLong(byte[] bytes) {

        if (bytes.length > 8) {
            return -1;
        }

        int byteLength = bytes.length;
        byte[] bytesNew;
        if (byteLength < 8) {
            bytesNew = new byte[8];
            for (int i = 0; i < 8; i++) {
                if (i < byteLength) {
                    bytesNew[i] = bytes[i];//从bytes的开始(即long值的高位)开始copy数据
                } else {
                    bytesNew[i] = 0;//如果bytes.length不足8,则将高位补0，例如：[1,2,3,4]，补位后[1,2,3,4,0,0,0,0];
                }
            }
        } else {
            bytesNew = bytes;
        }

        long longData = 0;
        for (int i = 7; i >= 0; i--) {
            longData <<= 8;
            longData |= (bytesNew[i] & 0xff);
        }
        return longData;
    }

//    public static void main(String args[]) {
//        short a = 6;
//        System.out.println("short to byte " + HexUtil.formatHexString(IntByteUtil.shortToByteArrayHighToLow(a), true));
//        System.out.println("short to byte " + HexUtil.formatHexString(IntByteUtil.shortToByteArrayLowToHigh(a), true));
//        byte[] bytes = new byte[2];
//        bytes[0] = 0;
//        bytes[1] = 6;
//        System.out.println("byte to short " + IntByteUtil.byteArrayToShortHighToLow(bytes));
//        bytes[0] = 6;
//        bytes[1] = 0;
//        System.out.println("byte to short " + IntByteUtil.byteArrayToShortLowToHigh(bytes));
//    }
}
