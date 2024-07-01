package BlockingQueue;

import java.util.concurrent.BlockingQueue;

public class ProducerTask implements Runnable {
    private BlockingQueue<Integer> q;
    private int num;
    private int cnt = 0;

    public ProducerTask(BlockingQueue<Integer> q, int num) {
        this.q = q;
        this.num = num;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                while (cnt < num) {
                    Thread.sleep(200);
                    this.q.put(cnt);
                    System.out.println("Produce: " + cnt);
                    cnt += 1;
                }
                Thread.currentThread().interrupt();
            }
        } catch (InterruptedException e) {
        } finally {
            System.out.println("Producer has stopped");
        }
    }
}
