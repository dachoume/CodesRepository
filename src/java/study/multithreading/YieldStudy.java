package study.multithreading;

/**
 * describe:
 * 线程让步yield() 这个例子看得不是特别明白，感觉这个例子举得不是特别好。
 * 不过这个yield()方法也很难通过例子来说明，本来是想让内存中存在几条不同优先级的线程，
 * 然后通过再调用yield()方法来验证是否是调用优先级最高的线程，但是发现Java中让线程进入就绪状态的只有start()和yield()方法，
 * 而调用start()方法之后CPU一般会直接分配资源让其执行，而调用yield()方法则会直接唤醒另外一条线程，所以没办法实现。
 * <p>
 * 对于Thread.yield()静态方法，只需要了解它的作用就是让这个线程从运行状态变成就绪状态，
 * 并从就绪队列中取一条优先级比此线程高的线程运行就好了。
 * <p>
 * 这个例子没看出来
 *
 * @author dadou
 * @date 2018/12/14
 */
public class YieldStudy {

    public static void main(String[] args) {
        Thread myThread1 = new MyThread1();
        Thread myThread2 = new MyThread2();
        myThread1.setPriority(Thread.MAX_PRIORITY);
        myThread2.setPriority(Thread.MIN_PRIORITY);
        for (int i = 0; i < 100; i++) {
            System.out.println("main thread i = " + i);
            if (i == 20) {
                myThread1.start();
                myThread2.start();
                Thread.yield();
            }
        }
    }

}

class MyThread1 extends Thread {

    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("myThread 1 --  i = " + i);
        }
    }
}

class MyThread2 extends Thread {

    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("myThread 2 --  i = " + i);
        }
    }
}
