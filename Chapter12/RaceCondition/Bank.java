package RaceCondition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private static int NCount = 0;
    private static List<Bank> BankList = new ArrayList<>();
    private static ReentrantLock mutexLock = new ReentrantLock();

    public static void Transer(int f, int t, int account) {
        // mutexLock.lock();
        // mutexLock.lock();
        var from = GetBankByIndex(f);
        var to = GetBankByIndex(t);
        // if(from.account < account) return;
        from.account -= account;
        to.account += account;
        StringBuilder output = new StringBuilder();
        output.append(Thread.currentThread());
        // output.append(" "+mutexLock.getHoldCount());
        output.append(": ");
        output.append(String.format("%d to %d, account is %d. Sum is ", f, t, account));
        output.append(SumAccount());
        System.out.println(output.toString());
        // mutexLock.unlock();
    }

    static public int GetBankNum() {
        return NCount;
    }

    static public Bank GetBankByIndex(int index) {
        if (index >= NCount)
            return null;
        return BankList.get(index);
    }

    static private int SumAccount() {
        int sum = 0;
        Iterator<Bank> iter = BankList.iterator();
        while (iter.hasNext()) {
            Bank currBank = iter.next();
            sum += currBank.account;
        }
        return sum;
    }

    private int account = 0;

    public Bank() {
        NCount += 1;
        BankList.add(this);
    }

    public Bank(int _account) {
        this();
        this.account = _account;
    }

}
