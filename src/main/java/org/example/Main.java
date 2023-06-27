package org.example;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

import static java.lang.System.out;

public class Main {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        CyclicBarrier barrier = new CyclicBarrier(CARS_COUNT + 1); // 1 = main thread
        Race race = new Race(new Road(60), new Tunnel(2), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), barrier);
            new Thread(cars[i]).start();
        }
        try {
            barrier.await();
            out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            barrier.await();
            barrier.await();
            out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}