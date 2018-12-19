package study.multithreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * describe:
 * 当a线程lock后，b线程阻塞，此时如果是lockInterruptibly，那么在调用b.interrupt()之后，b线程退出阻塞，
 * 并放弃对资源的争抢，进入catch块。（如果使用后者，必须throw interruptable exception 或catch）
 * <p>
 * lockInterruptibly()允许在等待时由其他线程的Thread.interrupt()方法来中断等待线程而直接返回，这时是不用获取锁的，
 * 而会抛出一个InterruptException。而ReentrantLock.lock()方法则不允许Thread.interrupt()中断，
 * 即使检测到了Thread.interruptted一样会继续尝试获取锁，失败则继续休眠。
 * 只是在最后获取锁成功之后在把当前线程置为interrupted状态。
 * <p>
 * 不管使用lock()还是lockinterruptibly都需要注意不要将该方法置于try块中！
 * 不要将获取锁的过程写在try块中，因为如果在获取锁（自定义锁的实现）时发生了异常，异常抛出的同时，也会导致锁无故释放。
 *
 * @author dadou
 * @date 2018/12/17
 */
public class LockInterruptiblyStudy {
    public static void main(String[] args) {
        Thread t1 = new Thread(new RunIt());
        Thread t2 = new Thread(new RunIt());

        t1.setName("t1");
        t2.setName("t2");

        t1.start();
        t2.start();
        t2.interrupt();
    }
}

class RunIt implements Runnable {

    private static Lock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            runJob();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " Interrrupted from runJob.");
        }
    }

    private void runJob() throws InterruptedException {
        lock.lockInterruptibly();
        System.out.println(Thread.currentThread().getName() + " 到此一游....");
        try {
            System.out.println(Thread.currentThread().getName() + " running");
            TimeUnit.SECONDS.sleep(3);
            System.out.println(Thread.currentThread().getName() + " finished");

        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interrupted");
        } finally {
            lock.unlock();
        }
    }
}
