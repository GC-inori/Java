import org.junit.Test;

import java.util.function.Consumer;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public class ThreadTest {
    public static final TestClass threadInstance;
    
    static {
        threadInstance = new TestClass();
    }
    public void testThread(int param){
        while (param < 1000) {
            System.out.println(Thread.currentThread().getName() + " " + threadInstance.show());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            param++;
        }
    }

    public static void main(String[] args) {

        Runnable thread1 = () -> new ThreadTest().testThread(0);
        Thread t = new Thread(thread1);
        Thread t1 = new Thread(thread1);

        t.start();
        t1.start();
    }
    @Test
    public void Test(){

        Runnable thread1 = () -> testThread(0);
        Thread t = new Thread(thread1);
        Thread t1 = new Thread(thread1);

        t.start();
        t1.start();

    }
}
class TestClass{
    private int id;

    public String show(){
        id ++;
        return String.valueOf(id);
    }
}
