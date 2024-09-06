package org.example;

class Counters {
    private int count = 0;

    public void increment() {
        synchronized (this) {  // Synchronized block
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}

public class SynchronizedBlockDemo {
    public static void main(String[] args) throws InterruptedException {
        Counters counters = new Counters();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counters.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final count: " + counters.getCount());
    }
}

