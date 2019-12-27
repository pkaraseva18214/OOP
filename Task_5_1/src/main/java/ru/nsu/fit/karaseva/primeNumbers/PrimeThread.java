package ru.nsu.fit.karaseva.primeNumbers;

import java.util.List;

class PrimeThread extends Thread {
  private List<Integer> numbers;
  private boolean result = false;

  PrimeThread(List<Integer> numbers) {
    this.numbers = numbers;
  }

  /**
   * Get result is there a prime number in the sequence of numbers.
   *
   * @return true - if there is a least one not prime number, otherwise returns false.
   */
  public boolean getResult() {
    return result;
  }

  @Override
  public void run() {
    for (int i = 0; i < numbers.size(); i++)
      if (!PrimeTest.isPrime(numbers.get(i))) {
        result = true;
      }
  }
}
