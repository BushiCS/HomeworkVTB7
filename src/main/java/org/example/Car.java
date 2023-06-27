package org.example;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.*;

public class Car implements Runnable {
    private static int CARS_COUNT;

    private static final AtomicInteger atomicInteger;

    private final CyclicBarrier barrier;

    static {
        CARS_COUNT = 0;
        atomicInteger = new AtomicInteger(0);
    }

    private final Race race;
    private final int speed;
    private final String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier barrier) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.barrier = barrier;
    }

    @Override
    public void run() {

        try {
            out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            out.println(this.name + " готов");
            barrier.await();
            barrier.await();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }

            if (atomicInteger.incrementAndGet() == 1) {
                out.println(name + " - WIN");
            }
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

