package org.example;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

    private final Semaphore smp;

    public Tunnel(int carCount) { // кол-во машин в туннеле
        this.length = 80;
        this.description = "тоннель " + length + " метров";
        this.smp = new Semaphore(carCount);
    }

    @Override
    public void go(Car c) {
        try {
            if (!smp.tryAcquire()) {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                smp.acquire();
            }
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000L);
            System.out.println(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            smp.release();
        }

    }
}

