package RaceCondition;

import java.util.ArrayList;
import java.util.List;

public class BankTest {
    public static void main(String... args){
        List<Bank> bankList = new ArrayList<>();
        bankList.add(new Bank(1000));
        bankList.add(new Bank(1000));
        for(int i = 0; i < 30; i++){
            Runnable subTask = new SubTask();
            Thread subThread = new Thread(subTask);
            subThread.start();
        }
    }

}
