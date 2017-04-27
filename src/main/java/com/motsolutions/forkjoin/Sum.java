/*
 * Copyright 2016 Motorola Solutions, Inc.
 * All Rights Reserved.
 * Motorola Solutions Confidential Restricted
 */
package com.motsolutions.forkjoin;

import java.util.concurrent.RecursiveTask;

public class Sum extends RecursiveTask<Long> {	
	private final int data[];
	private final int lo;
	private final int hi;
	private final int forks;
	
	public Sum(int data[], int lo, int hi, int forks) {
		this.data = data;
		this.lo = lo;
		this.hi = hi;
		this.forks = forks;
	}
	
	@Override
	protected Long compute() {
		if (isSmallestPossibleSize()) {
			return computeSequentially();
		} else {
			return forkAndJoin();
		}
	}
	
	private boolean isSmallestPossibleSize() {
		// FILL IN!
		return forks < 2;
	}

	private Long computeSequentially() {
		// FILL IN
		return Main.sequentialAlgorithm(data, 0, hi);
	}

	private Long forkAndJoin() {
		// FILL IN
		long result = 0;
		int cutPoint = findCutPoint();
		Sum left = createLeft(cutPoint);
		Sum right = createRight(cutPoint);

		left.fork();
		Long rightResult = right.compute();
		Long leftResult = left.join();
		return leftResult + rightResult;
	}

	private Sum createLeft(int cutPoint) {
		return new Sum(data, lo, cutPoint, forks + 1);
	}

	private Sum createRight(int cutPoint) {
		return new Sum(data, cutPoint + 1, hi, forks +1);
	}

	private int findCutPoint() {
		return hi/2;
	}
}
