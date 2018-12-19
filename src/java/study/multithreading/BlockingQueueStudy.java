package study.multithreading;


import java.util.concurrent.ArrayBlockingQueue;

/**
 * describe:使用blockingqueue实现生产者消费者，比较简单
 *
 * @author dadou
 * @date 2018/12/17
 */
public class BlockingQueueStudy {
    //final成员变量表示常量，只能被赋值一次，赋值后值不再改变。
    private static final int queueSize = 5;
    private static final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(queueSize);
    private static final int produceSpeed = 2000;//生产速度(越小越快)
    private static final int consumeSpeed = 10;//消费速度(越小越快)

    //生产者
    public static class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("老板准备炸油条了，架子上还能放：" + (queueSize - queue.size()) + "根油条");
                    queue.put("1根油条");
                    System.out.println("老板炸好了1根油条，架子上还能放：" + (queueSize - queue.size()) + "根油条");
                    Thread.sleep(produceSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //消费者
    public static class Consumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("A 准备买油条了，架子上还剩" + queue.size() + "根油条");
                    queue.take();
                    System.out.println("A 买到1根油条，架子上还剩" + queue.size() + "根油条");
                    Thread.sleep(consumeSpeed);

                    System.out.println("B 准备买油条了，架子上还剩" + queue.size() + "根油条");
                    queue.take();
                    System.out.println("B 买到1根油条，架子上还剩" + queue.size() + "根油条");
                    Thread.sleep(consumeSpeed);

                    System.out.println("C 准备买油条了，架子上还剩" + queue.size() + "根油条");
                    queue.take();
                    System.out.println("C 买到1根油条，架子上还剩" + queue.size() + "根油条");
                    Thread.sleep(consumeSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());
        producer.start();
        consumer.start();
    }


}
