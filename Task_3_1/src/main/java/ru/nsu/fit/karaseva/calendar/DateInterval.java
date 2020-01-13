package ru.nsu.fit.karaseva.calendar;

public class DateInterval {

  private static Date date1;
  private static Date date2;
  private int day;
  private int month;
  private int year;

  /**
   * Constructor of DateInterval class.
   * @param date1 first date in the interval.
   * @param date2 second date in the interval.
   */
  public DateInterval(Date date1, Date date2) {
    this.date1 = date1;
    this.date2 = date2;
    countInterval();
  }

  /** @return date in format "dd MM YY". */
  public String toString() {
    String str = day + " " + month + " " + year;
    return str;
  }

  private static long countDays() {
    long days = Calendar.daysBetweenDates(date1, date2);
    return days;
  }

  private void countInterval() {
    long days = countDays();
    final int NUMBER_OF_DAYS_IN_CENTURY = 36524;
    int newYear = 0;
    boolean flag = false;
    int newMonth = 0;
    int century = (int) days / NUMBER_OF_DAYS_IN_CENTURY;
    days = days - century * NUMBER_OF_DAYS_IN_CENTURY - century / 4;
    while (newYear == 0 || (newYear % 4 == 0 && days > 366) || (newYear % 4 != 0 && days > 365)) {
      newYear++;
      if (newYear % 4 != 0) {
        days -= 365;
      } else {
        days -= 366;
      }
    }
    for (int i = 0; !flag; ++i) {
      switch (i + 1) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
          if (days > 31) {
            days -= 31;
          } else {
            flag = true;
            newMonth = i;
          }
          break;
        case 4:
        case 6:
        case 9:
        case 11:
          if (days > 30) {
            days -= 30;
          } else {
            flag = true;
            newMonth = i;
          }
          break;
        case 2:
          if (newYear % 4 == 0 && newYear != 0) {
            if (days > 29) {
              days -= 29;
            } else {
              flag = true;
              newMonth = i;
            }
          } else {
            if (days > 28) {
              days -= 28;
            } else {
              flag = true;
              newMonth = i;
            }
          }
          break;
      }
    }
    day = (int) days;
    month = newMonth;
    year = newYear + 100 * century;
  }
}
