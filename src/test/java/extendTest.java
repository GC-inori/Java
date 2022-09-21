import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class extendTest {

    @Test
    public void test(){
      List<Integer> list= new ArrayList<>();
      list.add(1);

    }

}

interface initTest{
    void init();
}
abstract class Person implements initTest{

    private String name = "Person";

    public Person() {

    }

    @Override
    public void init() {
        System.out.println("Person init");
    }
}

class Man extends Person{
    private String name = "Man";

    public Man() {

    }

    public void init() {
        super.init();
        System.out.println("Man init");
    }
}