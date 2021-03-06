package com.my.algorithms.domain;

public class Counter {

    private int value;

    public Counter(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public int incrementAndGet() {
        return ++value;
    }

    public int decrementAndGet() {
        return --value;
    }

    public int getAndIncrement() {
        return value++;
    }

    public int getAndDecrement() {
        return value--;
    }
}
