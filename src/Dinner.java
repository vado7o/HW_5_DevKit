import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Dinner extends Thread {
    private List<Philosopher> philosophers;
    private CountDownLatch cdl;

    public Dinner() {
        Fork fork1 = new Fork("1");
        Fork fork2 = new Fork("2");
        Fork fork3 = new Fork("3");
        Fork fork4 = new Fork("4");
        Fork fork5 = new Fork("5");

        cdl = new CountDownLatch(5);

        philosophers = new ArrayList<>();
        philosophers.add(new Philosopher("Платон", fork1, fork2, cdl));
        philosophers.add(new Philosopher("Аристотель", fork2, fork3, cdl));
        philosophers.add(new Philosopher("Сократ", fork3, fork4, cdl));
        philosophers.add(new Philosopher("Декарт", fork4, fork5, cdl));
        philosophers.add(new Philosopher("Вольтер", fork5, fork1, cdl));
    }

    @Override
    public void run() {
        try {
            startEating();
            cdl.await();
            System.out.println("\nВСЕ ФИЛОСОФЫ ЗАКОНЧИЛИ ОБЕД (поели по три раза!)");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startEating() {
        for (Philosopher philosopher : philosophers) {
            philosopher.start();
        }
    }
}
