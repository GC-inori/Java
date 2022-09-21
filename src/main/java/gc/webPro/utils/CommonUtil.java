package gc.webPro.utils;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public class CommonUtil {
    public static int StringToInt(String str, int defaultValue) {
        return  str != null ? isInteger(str) ? Integer.parseInt(str) : defaultValue : defaultValue;
    }
    public static double StringToDouble(String str, double defaultValue) {
        return  str != null ? isDouble(str) ? Double.parseDouble(str) : defaultValue : defaultValue;
    }
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("-?(0|[1-9]\\d{0,9})$");
        return pattern.matcher(str).matches();
    }
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("-?(0|[1-9]\\d{0,9})\\.?\\d{0,6}$");
        return pattern.matcher(str).matches();
    }

}
