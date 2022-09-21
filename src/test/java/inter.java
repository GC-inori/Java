import org.junit.Test;

public class inter implements SuperInterface {
    @Test
    public void Test() {

        SuperInterface sup = new Impl(new inter());

      Impl im = (Impl) sup;
        im.show();


    }
    void show(){
        System.out.println("inter");
    }
}

interface SuperInterface {

}

interface SubInterface extends SuperInterface {
    SuperInterface test();
}

class Impl implements SubInterface {
    SuperInterface sup;

    public Impl(SuperInterface sup) {
        this.sup = sup;
    }

    @Override
    public SuperInterface test() {
        System.out.println("实现子接口类");
        return sup;
    }
    void show(){
        System.out.println("impl");
    }
}