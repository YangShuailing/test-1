package concurrent;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {
    private static final Exchanger<String> exgr = new Exchanger<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(() -> {
            try {
                String A = "bank pipeline A\n";//pipeline A get data of the bank
                exgr.exchange(A);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            try {
                String B = "bank pipeline B\n";//pipeline A get data of the bank
                String A = exgr.exchange("B");
                System.out.println("the data of pipeline A and B equals?\n" + A.equals(B) + "\ndata from pipeline A:"
                        + A + "data from pipeline B:" + B);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPool.shutdown();
    }
}
