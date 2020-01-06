package ru.nsu.fit.karaseva.calendar;

public class Calendar {
  private Date today;
  private int todayDay;
  private int todayMonth;
  private int todayYear;

  public static final String[] MONTH = {
    "January", "February",
    "March", "April",
    "May", "June",
    "July", "August",
    "September", "October",
    "November", "December"
  };

  public static final String[] WEEKDAY = {
    "Monday", "Tuesday",
    "Wednesday", "Thursday",
    "Friday", "Saturday",
    "Sunday"
  };

  /**
   * Calendar constructor with input days, month, year.
   *
   * @param day number of the day in month.
   * @param month number of the month.
   * @param year number of the year.
   * @throws IllegalArgumentException if the input arguments are incorrect.
   */
  public Calendar(int day, int month, int year) throws IllegalArgumentException {
    if (!Date.isCorrect(day, month, year)) {
      throw new IllegalArgumentException();
    }
    today = new Date(day, month, year);
    todayDay = today.getDay();
    todayYear = today.getYear();
    todayMonth = today.getMonth();
  }
  /**
   * Calendar constructor with input Date date.
   *
   * @param date input date.
   * @throws NullPointerException if date is null.
   */
  public Calendar(Date date) throws NullPointerException {
    if (date == null) {
      throw new NullPointerException();
    }
    this.today = date;
    todayDay = date.getDay();
    todayYear = date.getYear();
    todayMonth = date.getMonth();
  }

  /** @return current date. */
  public Date getToday() {
    return today;
  }

  /** @return current date as string. */
  public String toString() {
    return today.toString();
  }

  /**
   * Method that adds days in the Calendar.
   *
   * @param days - number of the days to add.
   * @return Date with added days.
   */
  public Date addDays(int days) {
    long currentDays = today.getDaysFromBeginningOfTheEra() + days;
    return new Date(currentDays);
  }

  /**
   * Method that adds month to the calendar.
   *
   * @param months - number of the months to add.
   * @return new Date with added months.
   */
  public Date addMonths(int months) {
    int currentMonth = todayMonth;
    currentMonth += months;
    int currentYear;
    if (currentMonth >= 0) {
      currentYear = todayYear + (currentMonth - 1) / 11;
      currentMonth = currentMonth % 12;
    } else {
      int times = Math.abs(currentMonth / 11) + 1;
      currentYear = todayYear - times;
      currentMonth %= 12;
      if (currentMonth == 0) {
        currentMonth = 1;
      }
    }
    return new Date(todayDay, currentMonth, currentYear);
  }

  /**
   * Method that adds years to the Calendar.
   *
   * @param years - number of the years to add to date.
   * @return new Date with added years.
   */
  public Date addYears(int years) {
    years += todayYear;
    return new Date(todayDay, todayMonth, years);
  }
  /**
   * Method that adds weeks to the Calendar.
   *
   * @param weeks - number of the weeks to add.
   * @return Date with added weeks.
   */
  public Date addWeeks(int weeks) {
    return addDays(7 * weeks);
  }

  /**
   * Method that sets new day as today.
   *
   * @param newDate - set this day in current date.
   * @throws IllegalArgumentException if date with newD become incorrect.
   */
  public void setDay(int newDate) throws IllegalArgumentException {
    Date newToday = new Date(newDate, todayMonth, todayYear);
    todayDay = newDate;
    today = newToday;
  }
  /**
   * Method that sets new month as the current.
   *
   * @param newMonth - new month to set in current date.
   * @throws IllegalArgumentException if date with new months incorrect.
   */
  public void setMonths(int newMonth) throws IllegalArgumentException {
    Date tryDate = new Date(todayDay, newMonth, todayYear);
    todayMonth = newMonth;
    today = tryDate;
  }
  /**
   * Method that sets new year as the current.
   *
   * @param newYear - new year to set in current date.
   * @throws IllegalArgumentException if Date today become incorrect with new year.
   */
  public void setYear(int newYear) throws IllegalArgumentException {
    Date tryDate = new Date(todayDay, todayMonth, newYear);
    todayYear = newYear;
    today = tryDate;
  }

  /**
   * Method that returns date after current that will have weekday and day in month as input.
   *
   * @param weekday - weekday in return Date.
   * @param monthsDay - day in month int return Date.
   * @return nearest Date after today with input parameters.
   * @throws IllegalArgumentException if input is incorrect.
   */
  public Date getNearestWeekdayMonthDay(int weekday, int monthsDay) {
    if (weekday > 7 || weekday < 1 || monthsDay < 1 || monthsDay > 31) {
      throw new IllegalArgumentException();
    }
    int currentMonths = todayMonth;
    int currentYear = todayYear;
    boolean isAfterToday = (monthsDay >= todayDay);
    Date current = today;
    while (current.weekDayToInt() != weekday || !isAfterToday) {
      if (Date.isCorrect(monthsDay, currentMonths, currentYear)) {
        current = new Date(monthsDay, currentMonths, currentYear);
        if (current.weekDayToInt() == weekday && isAfterToday) {
          break;
        }
      }
      currentMonths++;
      currentYear += (currentMonths - 1) / 12;
      currentMonths %= 13;
      if (currentMonths == 0) {
        currentMonths = 1;
      }
      isAfterToday = true;
    }
    return current;
  }
  /**
   * Method that returns nearest Date with input day and month.
   *
   * @param day - day in month in return Date.
   * @param month - month in return Date.
   * @return nearest Date after today with input parameters.
   * @throws IllegalArgumentException if Date with input day and month is incorrect.
   */
  public Date getNearestDayMonth(int day, int month) {
    boolean isCorrect = Date.isCorrect(day, month, todayYear);
    int delta = 0;
    for (; delta < 8 && !isCorrect; ) {
      ++delta;
      isCorrect = Date.isCorrect(day, month, todayYear + delta);
    }
    if (!isCorrect) throw new IllegalArgumentException();
    Date current = new Date(day, month, todayYear + delta);
    if (today.isAfter(current)) {
      isCorrect = false;
      for (; delta < 16 && !isCorrect; ++delta) {
        isCorrect = Date.isCorrect(day, month, todayYear + delta);
      }
      if (!isCorrect) throw new IllegalArgumentException();
      current = new Date(day, month, todayYear + delta);
    }
    return current;
  }
  /**
   * Method that returns number of the days between two dates.
   *
   * @param date1 first date.
   * @param date2 second date.
   * @return number of the days between date1 and date2.
   * @throws NullPointerException if the date1 or date2 is null.
   */
  public static long daysBetweenDates(Date date1, Date date2) throws NullPointerException {
    if (date1 == null || date2 == null) {
      throw new NullPointerException();
    }
    return Math.abs(date1.getDaysFromBeginningOfTheEra() - date2.getDaysFromBeginningOfTheEra());
  }
}
