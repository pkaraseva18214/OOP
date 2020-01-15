package ru.nsu.fit.karaseva.primeNumbers;

import java.util.concurrent.RecursiveTask;

/** Class that implements a parallel check if the number is prime. */
class ForkJoinPrime extends RecursiveTask<Boolean> {
  private static final int THRESHOLD = 150;
  private final int[] numbers;
  private final int start;
  private final int end;

  ForkJoinPrime(int[] numbers) {
    this(numbers, 0, numbers.length);
  }

  private ForkJoinPrime(int[] numbers, int start, int end) {
    this.numbers = numbers;
    this.start = start;
    this.end = end;
  }

  @Override
  protected Boolean compute() {
    int length = end - start;
    if (length < THRESHOLD) {
      return computeSequentially();
    }
    ForkJoinPrime first = new ForkJoinPrime(numbers, start, start + length / 2);
    first.fork();
    ForkJoinPrime second = new ForkJoinPrime(numbers, start + length / 2, end);
    boolean secondResult = second.compute();
    boolean firstResult = first.join();
    return firstResult || secondResult;
  }

  private boolean computeSequentially() {
    for (int i = start; i < end; i++) {
      if (!PrimeTest.isPrime(numbers[i])) {
        return true;
      }
    }
    return false;
  }
}
