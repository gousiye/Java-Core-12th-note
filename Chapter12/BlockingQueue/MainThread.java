package BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MainThread {
    public static void main(String[] args) {
        int num = 10;
        BlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(5);
        Thread consumer = new Thread(new ConsumerTask(q));
        Thread producer = new Thread(new ProducerTask(q, num));
        consumer.start();
        producer.start();
        try {
            producer.join();
            consumer.interrupt();
        } catch (InterruptedException e) {
        } finally {
        }
    }

}
