/*
 * Copyright 2016 Motorola Solutions, Inc.
 * All Rights Reserved.
 * Motorola Solutions Confidential Restricted
 */
package com.motsolutions.forkjoin;

import com.google.common.base.Stopwatch;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int DATA_SIZE = 1500;
    private static final int MAX_RANGE = 100;

    public static void main(String args[]) {
        int[] data = createDataset(DATA_SIZE);

        Stopwatch measure = Stopwatch.createStarted();
        long sequentialResult = sequentialAlgorithm(data, 0, DATA_SIZE);
        long sequentialTime = measure.elapsed(TimeUnit.MILLISECONDS);

        measure = Stopwatch.createStarted();
        long parallelResult = ForkJoinPool.commonPool().invoke(new Sum(data, 0, DATA_SIZE, 0));
        long parallelTime = measure.elapsed(TimeUnit.MILLISECONDS);

        System.out.println("Result sequential = " + sequentialResult);
        System.out.println("Result parallel = " + parallelResult);

        System.out.println("Time sequential = " + sequentialTime + " ms");
        System.out.println("Time parallel = " + parallelTime + " ms");
    }

    public static int[] createDataset(int size) {
        int data[] = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            data[i] = Math.abs(random.nextInt()) % MAX_RANGE;
        }
        return data;
    }

    public static long sequentialAlgorithm(int data[], int lo, int hi) {
        // algorithm goes here
        long result = 0;
        for (int i = lo; i < hi; i++) {
            if (i == lo) {
                result += (data[i] + data[i+1])/3;
            } else if (i == hi-1) {
                result += (data[i] + data[i-1])/3;
            } else {
                result += (data[i-1] + data[i] + data[i+1])/3;
            }
        }
        return result;
    }
}
