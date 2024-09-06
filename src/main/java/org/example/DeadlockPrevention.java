package org.example;

public class DeadlockPrevention {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void method1() {
        synchronized (lock1) {
            System.out.println("Thread 1: Holding lock 1...");
            synchronized (lock2) {
                System.out.println("Thread 1: Holding lock 1 & 2...");
            }
        }
    }

    public void method2() {
        synchronized (lock1) {  // Acquire locks in the same order
            System.out.println("Thread 2: Holding lock 1...");
            synchronized (lock2) {
                System.out.println("Thread 2: Holding lock 1 & 2...");
            }
        }
    }

    public static void main(String[] args) {
        DeadlockPrevention deadlockPrevention = new DeadlockPrevention();

        Thread t1 = new Thread(deadlockPrevention::method1);
        Thread t2 = new Thread(deadlockPrevention::method2);

        t1.start();
        t2.start();
    }
}
