package ru.nsu.fit.karaseva.primeNumbers;

import java.util.List;

/**
 * Class that implements testing if the number is prime or not, using iterative, sequential and
 * parallel methods.
 */
public class PrimeNumbers {
  private List<Integer> numbers;

  PrimeNumbers(List<Integer> numbers) {
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
    int end = numbers.size();
    for (int i = 0; i < end; i++) {
      if (!PrimeTest.isPrime(numbers.get(i))) {
        return true;
      }
    }
    return false;
  }

  /**
   * Parallel method. Checks is there at least one not prime number using threads..
   *
   * @return true - if there is at least one not prime number, otherwise returns false.
   * @throws InterruptedException
   */
  public boolean parallel() throws InterruptedException {
    List<Integer> num1 = numbers.subList(0, numbers.size() / 4);
    List<Integer> num2 = numbers.subList(numbers.size() / 4, numbers.size() / 2);
    List<Integer> num3 = numbers.subList(numbers.size() / 2, (numbers.size() / 4) * 3);
    List<Integer> num4 = numbers.subList((numbers.size() / 4) * 3, numbers.size());

    PrimeThread thread1 = new PrimeThread(num1);
    PrimeThread thread2 = new PrimeThread(num2);
    PrimeThread thread3 = new PrimeThread(num3);
    PrimeThread thread4 = new PrimeThread(num4);
    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();
    thread1.join();
    thread2.join();
    thread3.join();
    thread4.join();
    return thread1.getResult() || thread2.getResult() || thread3.getResult() || thread4.getResult();
  }
}
