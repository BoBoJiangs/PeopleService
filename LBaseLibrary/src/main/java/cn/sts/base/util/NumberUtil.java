package cn.sts.base.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字操作
 * Created by weilin on 2017/9/13.
 */

public class NumberUtil {

    private static DecimalFormat decimalFormat0 = new DecimalFormat("##0");
    private static DecimalFormat decimalFormat1 = new DecimalFormat("##0.0");
    private static DecimalFormat decimalFormat2 = new DecimalFormat("##0.00");
    private static DecimalFormat decimalFormat2Cut = new DecimalFormat("##0.00");
    private static DecimalFormat decimalFormat3 = new DecimalFormat("##0.000");
    private static DecimalFormat decimalFormatComma = new DecimalFormat("#,##0");

    static {
        //四舍五入
        decimalFormat1.setRoundingMode(RoundingMode.HALF_UP);
        //四舍五入
        decimalFormat2.setRoundingMode(RoundingMode.HALF_UP);
        //不四舍五入
        decimalFormat2Cut.setRoundingMode(RoundingMode.DOWN);
        //四舍五入
        decimalFormat3.setRoundingMode(RoundingMode.HALF_UP);
        //四舍五入
        decimalFormatComma.setRoundingMode(RoundingMode.HALF_UP);
    }

    /**
     * 转换float 保留一位小数,四舍五入
     */
    public static float convertFloat1(float value) {
        return Float.parseFloat(decimalFormat1.format(value));
    }

    /**
     * 转换float 保留两位小数,四舍五入
     */
    public static float convertFloat2(float value) {
        return Float.parseFloat(decimalFormat2.format(value));
    }

    /**
     * 转换float 保留3位小数,四舍五入
     */
    public static float convertFloat3(float value) {
        return Float.parseFloat(decimalFormat3.format(value));
    }

    /**
     * 转换double 保留两位小数,四舍五入
     */
    public static double convertDouble2(double value) {
        return Double.valueOf(decimalFormat2.format(value));
    }

    /**
     * 转换double 保留两位小数，去掉小数点两位后的数据
     */
    public static double convertDouble2Cut(double value) {
        return Double.valueOf(decimalFormat2Cut.format(value));
    }

    /**
     * 转换double 保留两位小数,例如:100,000,000
     */
    public static String convertDoubleToMoney(double value) {
        return decimalFormatComma.format(value);
    }

    /**
     * double转换String 保留两位小数,四舍五入
     */
    public static String convertToDouble2String(double value) {
        return decimalFormat2.format(value);
    }

    /**
     * double转换String 去掉小数
     */
    public static String convertToDouble0String(double value) {
        return decimalFormat0.format(value);
    }

    /**
     * 钱转换成double
     *
     * @param value 例如:10,000
     * @return 10000
     */
    public static double convertMoneyToDouble(String value) {
        try {
            return decimalFormatComma.parse(value).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str 字符串
     * @return 是true or 否false
     */
    public static boolean isNumber(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否是整数
     *
     * @param str 字符串
     * @return 是true or 否false
     */
    public static boolean isIntNumber(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

}
