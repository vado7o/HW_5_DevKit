import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread {
    private String name;
    private Fork forkLeft;
    private Fork forkRight;
    private Boolean thinking = true;
    private int countEat = 0;
    private CountDownLatch cdl;

    public Philosopher(String name, Fork forkLeft, Fork forkRight, CountDownLatch cdl) {
        this.name = name;
        this.forkLeft = forkLeft;
        this.forkRight = forkRight;
        this.cdl = cdl;
    }

    @Override
    public void run() {
        while (countEat < 3) {
            try {
                think();
                sleep(new Random().nextInt(1000, 2000));
                eat();
                sleep(new Random().nextInt(2000, 3000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void eat() {
        if (!forkLeft.busy && !forkRight.busy && thinking) {
            countEat++;
            System.out.println("Философ " + name + " кушает! Это его " + countEat + " обед.");
            forkLeft.busy = true;
            forkRight.busy = true;
            thinking = false;
            if (countEat == 3) {
                System.out.println("Философ " + name + " закончил трапезу (поел все три раза!)");
                cdl.countDown();
            }
        }
        else System.out.println("Вилки философа " + name + " пока заняты. " + name + " пока будет размышлять!");
    }
    public void think() {
        System.out.println("Философ " + name + " размышляет.");
        this.thinking = true;
        forkLeft.busy = false;
        forkRight.busy = false;
    }
}
