package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SharedResource {
    private int count = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();
        try {
            count++;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void waitForCount(int target) throws InterruptedException {
        lock.lock();
        try {
            while (count < target) {
                condition.await();
            }
            System.out.println("Reached target: " + count);
        } finally {
            lock.unlock();
        }
    }
}

class LockConditionDemo {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        Thread incrementer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                sharedResource.increment();
            }
        });

        Thread waiter = new Thread(() -> {
            try {
                sharedResource.waitForCount(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        waiter.start();
        incrementer.start();
    }
}

