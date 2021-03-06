package com.my.algorithms.week2.first;

import static com.my.algorithms.tools.Randoms.randInt;

/**
 * Multiplication algorithm with division by two
 */
public class Multiplication {

    private static final int MAX = Integer.MAX_VALUE;
    private static final int MIN = MAX >> 1;

    public static void main(final String[] args) {

        System.out.println("2 * 2 is " + multiply(2, 2));
        System.out.println("5 * 5 is " + multiply(5, 5));
        System.out.println("5 * 6 is " + multiply(5, 6));

        long now = System.currentTimeMillis();
        benchmark(false, new Multiplicator() {
            @Override
            public long multiply(int x, int y) {
                return Multiplication.multiply(x, y);
            }
        });
        final long end1 = System.currentTimeMillis() - now;

        now = System.currentTimeMillis();
        benchmark(false, new Multiplicator() {
            @Override
            public long multiply(int x, int y) {
                return (long) x * y;
            }
        });
        final long end2 = System.currentTimeMillis() - now;

        System.out.println("Division by two time spent: " + end1);
        System.out.println("Standart time spent: " + end2);
    }

    private static void benchmark(final boolean print, final Multiplicator mult) {
        for (int i = 0; i < 1000; i++) {
            final int x = randInt(MIN, MAX);
            final int y = randInt(MIN, MAX);
            final long result = mult.multiply(x, y);
            if (print) {
                System.out.println(x + " * " + y + " = " + result);
            }
        }
    }

    public static long multiply(final int x, final int y) {
        final long result;
        if (y == 0) {
            result = 0;
        } else {
            final long z = multiply(x, y >> 1);
            if (y % 2 == 0) {
                result = z << 1;
            } else {
                result = x + (z << 1);
            }
        }
        return result;
    }

    private static interface Multiplicator {
        long multiply(int x, int y);
    }
}
