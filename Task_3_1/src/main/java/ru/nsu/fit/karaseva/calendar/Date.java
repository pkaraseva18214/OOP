package ru.nsu.fit.karaseva.calendar;

public class Date {

  private int day;
  private int month;
  private int year;
  private long numberOfDays;

  /**
   * Date class constructor using dates.
   *
   * @param newDay - number of days in month [1..30/31].
   * @param newMonth - month number from beginning of the year [1..12].
   * @param newYear - year number from era beginning [1..).
   * @throw IllegalArgumentException if one of argument incorrect.
   */
  public Date(int newDay, int newMonth, int newYear) throws IllegalArgumentException {
    if (!isCorrect(newDay, newMonth, newYear)) {
      throw new IllegalArgumentException();
    }
    day = newDay;
    month = newMonth;
    year = newYear;
    numberOfDays = countDays(newDay, newMonth, newYear);
  }


  /** @return date in format "dd MM YY weekday". */
  public String toString() {
    int weekday = ((int) (numberOfDays - 1) % 7);
    String date =
        Calendar.MONTH[month - 1] + " " + day + ", " + year + " " + Calendar.WEEKDAY[weekday];
    return date;
  }

  /**
   * Method that returns number of the day in date.
   *
   * @return number of the day.
   */
  public int getDay() {
    return day;
  }

  /**
   * Method that returns number of the month in date.
   *
   * @return number of the month.
   */
  public int getMonth() {
    return month;
  }

  /**
   * Method that returns number of the year in date.
   *
   * @return number of the year.
   */
  public int getYear() {
    return year;
  }

  /** @return number of days from beginning of the era. */
  public long getDaysFromBeginningOfTheEra() {
    return numberOfDays;
  }

  /**
   * Method that checks if the date is correct.
   *
   * @param newDay - number of days in month [1..30/31]
   * @param newMonth - number of month from beginning of the year [1..12].
   * @param newYear - number of the year from beginning of the year [1...).
   * @return true - if the data is correct, otherwise - false.
   */
  public static boolean isCorrect(int newDay, int newMonth, int newYear) {
    if (newDay < 1 || newMonth > 12 || newMonth < 1 || newYear < 1) {
      return false;
    }
    switch (newMonth) {
      case 1:
      case 3:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12:
        if (newDay > 31) {
          return false;
        }
        break;
      case 4:
      case 6:
      case 9:
      case 11:
        if (newDay > 30) {
          return false;
        }
        break;
      case 2:
        if (((newYear % 4 == 0) && !(newYear % 100 == 0)) || (newYear % 400 == 0)) {
          if (newDay > 29) {
            return false;
          }
        } else if (newDay > 28) {
          return false;
        }
        break;
    }
    return true;
  }

  /**
   * Method that counts number of days from beginning of the era to input date.
   *
   * @param day - number of the day in the month [1..30/31].
   * @param month - number of the month [1..12].
   * @param year - number of the year [1...)
   * @throw IllegalArgumentException if one of argument incorrect.
   */
  public static long countDays(int day, int month, int year) throws IllegalArgumentException {
    if (!isCorrect(day, month, year)) {
      throw new IllegalArgumentException();
    }
    long countDays;
    countDays = (year / 100) * 36524 + year / 400;
    switch (month - 1) {
      case 0:
        break;
      case 1:
        countDays += 31;
        break;
      case 2:
        countDays += 59;
        break;
      case 3:
        countDays += 90;
        break;
      case 4:
        countDays += 120;
        break;
      case 5:
        countDays += 151;
        break;
      case 6:
        countDays += 181;
        break;
      case 7:
        countDays += 212;
        break;
      case 8:
        countDays += 243;
        break;
      case 9:
        countDays += 273;
        break;
      case 10:
        countDays += 304;
        break;
      case 11:
        countDays += 334;
        break;
    }
    if ((((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0)) && month > 2) {
      countDays += 1;
    }
    for (int i = 1; i < year % 100; ++i) {
      if (i % 4 == 0) {
        countDays += 366;
      } else {
        countDays += 365;
      }
    }
    countDays += day;
    return countDays;
  }

  /** @return weekday as int. */
  public int weekDayToInt() {
    return (int) (numberOfDays - 1) % 7 + 1;
  }

  /**
   * @param newDate - compared date.
   * @return true if this date after newDate.
   * @throw NullPointerExceptions if newDate == null.
   */
  public boolean isAfter(Date newDate) {
    if (newDate == null) {
      throw new NullPointerException();
    }
    return (newDate.numberOfDays < this.numberOfDays);
  }

  /**
   * @param newDate - compared date.
   * @return true if this date before newDate.
   * @throw NullPointerExceptions if newDate == null.
   */
  public boolean isBefore(Date newDate) {
    if (newDate == null) {
      throw new NullPointerException();
    }
    return (newDate.numberOfDays > this.numberOfDays);
  }

  /**
   * @param newDate - compared date.
   * @return true if this date equals newDate.
   * @throw NullPointerExceptions if newDate == null.
   */
  public boolean isEquals(Date newDate) {
    if (newDate == null) {
      throw new NullPointerException();
    }
    return newDate.numberOfDays == this.numberOfDays;
  }

}
