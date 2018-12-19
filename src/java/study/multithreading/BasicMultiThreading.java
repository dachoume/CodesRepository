package study.multithreading;

/**
 * describe:基础多线程操作
 *
 * @author dadou
 * @date 2018/12/19
 */
public class BasicMultiThreading {
    public static void main(String[] args) {
        //匿名内部类实现多线程
        Runnable world = new Runnable() {
            public void run() {
                System.out.println("Hello World");
            }
        };

        //lambda实现匿名内部类多线程，无参形式
        Thread t1 = new Thread(() -> System.out.println("ni hao"));

        Thread t2 = new Thread(world);


        t1.start();
        t2.start();
    }
}
