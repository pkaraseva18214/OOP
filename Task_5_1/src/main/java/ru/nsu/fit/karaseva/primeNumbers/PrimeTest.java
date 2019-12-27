package ru.nsu.fit.karaseva.primeNumbers;

/** Class that checks if the number is prime. */
public class PrimeTest {

  static boolean isPrime(int n) {
    if (n < 2) {
      return false;
    }
    for (long i = 2; i * i <= n; i++) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }
}
