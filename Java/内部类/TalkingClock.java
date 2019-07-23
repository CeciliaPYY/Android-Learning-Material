import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.sun.javafx.tk.Toolkit;

public class TalkingClock {

    public void start(int interval, boolean beep) {
        Timer t = new Timer(interval, event -> {
            System.out.println("At the time, the tone is " + new Date());
            if (beep) {
                Toolkit.getDefaultToolkit().beep();
            }
        });
        t.start();
    }

    public void test() {
        System.err.println("Something awful happened in :" + getClass());
        new TalkingClock(){}.getClass().getEnclosingClass();
    }

    // 如果不再需要这个数组列表，最好可以让它成为一个匿名列表。
    // 那么如何对一个匿名列表添加元素呢？

    public void addElementTest() {
        // 注意这里的双括号，外层括号建立了 ArrayList 的一个匿名子类；
        // 内层括号则是一个对象构造块；
        invite(new ArrayList<String>() {{add("Harry"); add("Tony");}});
    }


    
}
