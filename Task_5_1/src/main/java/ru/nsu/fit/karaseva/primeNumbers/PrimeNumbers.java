package ru.nsu.fit.karaseva.primeNumbers;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Class that implements testing if the number is prime or not, using iterative, sequential and
 * parallel methods.
 */
public class PrimeNumbers {
  private int[] numbers;

  PrimeNumbers(int[] numbers) {
    if (numbers != null) {
      this.numbers = numbers;
    } else throw new NullPointerException();
  }
  /**
   * Iterative method. Checks is there at least one not prime number.
   *
   * @return 'true' if a given array has at least one not prime number, 'false' otherwise
   */
  public boolean testIteratively() {
    int end = numbers.length;
    for (int i = 0; i < end; i++) {
      if (!PrimeTest.isPrime(numbers[i])) {
        return true;
      }
    }
    return false;
  }

  /**
   * Parallel method. Checks is there at least one not prime number.
   *
   * @return true - if there is at least one not prime number, otherwise returns false.
   * @throws InterruptedException
   */
  public boolean parallel() {
    ForkJoinTask<Boolean> task = new ForkJoinPrime(numbers.clone());
    return new ForkJoinPool().invoke(task);
  }
}
