import gc.webPro.pojo.User;
import gc.webPro.utils.RequestBeanUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public class TestTime {
    private static final Logger logger = LoggerFactory.getLogger(TestTime.class);

    @Test
    public void test() {
        try {
            exception();
        } catch (SelfException e) {
            e.printStackTrace();
        }
    }

    void exception() {
        try {
            int i = 12 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SelfException();
        }
    }
}
class SelfException extends RuntimeException{

}