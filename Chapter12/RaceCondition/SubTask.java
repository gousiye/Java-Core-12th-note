package RaceCondition;

public class SubTask implements Runnable{
    @Override
    public void run(){
        int len = Bank.GetBankNum();
        try {
            while (true) {
                int from = (int) (Math.random() * len);
                int to = (int) (Math.random() * len);
                while(from == to){
                    to = (int) (Math.random() * len);
                }
                int account = (int) (Math.random() * 1000);
                Bank.Transer(from, to, account);
                Thread.sleep((int)(Math.random()*50));
            }
        }
        catch (InterruptedException e){}
    }
}
