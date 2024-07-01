package BlockingQueue;

import java.util.concurrent.BlockingQueue;

public class ConsumerTask implements Runnable {
    private BlockingQueue<Integer> q;

    public ConsumerTask(BlockingQueue<Integer> q) {
        this.q = q;
    }

    private void AfterCare() throws InterruptedException {
        while (this.q.size() > 0) {
            Thread.sleep(1000);
            Integer output = this.q.take();
            System.out.println("Consume(After): " + output);
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(2000);
                Integer output = this.q.take();
                System.out.println("Consume: " + output);
            }
        } catch (InterruptedException e) {
        } finally {
            try {
                this.AfterCare();
            } catch (InterruptedException ee) {
            } finally {
                System.out.println("Consumer has stopeed");
            }
        }
    }
}
