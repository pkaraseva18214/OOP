package ru.nsu.fit.karaseva.calendar;

import org.junit.Assert;
import org.junit.Test;

public class CalendarTests {
  private Date now = new Date(1, 1, 2020);

  @Test
  public void test1() {
    Date d = new Date(1, 1, 2020);
    Calendar c = new Calendar(now);
    Calendar c1 = new Calendar(d);
    Assert.assertEquals(0, c.daysBetweenDates(now, c1.getToday()));
  }

  @Test
  public void test2() {
    Calendar c = new Calendar(now);
    Date newYear = new Date(1, 1, 2021);
    long day = Calendar.daysBetweenDates(c.getToday(), newYear);
    Assert.assertEquals(366, day);
    Date yearBefore = new Date(1, 1, 2019);
    Assert.assertEquals(365, c.daysBetweenDates(now, yearBefore));
  }

  @Test
  public void test3() {
    Date now = new Date(1, 1, 2020);
    Date myBirthday = new Date(16, 10, 2000);
    Assert.assertEquals("October 16, 2000 Wednesday", myBirthday.toString());
    Assert.assertTrue(now.isAfter(myBirthday));
  }

  @Test
  public void test4() {
    Date current;
    Calendar c = new Calendar(now);
    current = c.getNearestWeekdayMonthDay(5, 13);
    Assert.assertEquals(current.toString(), "March 13, 2020 Friday");
    current = c.getNearestDayMonth(29, 2);
    Assert.assertEquals(current.toString(), "February 29, 2020 Saturday");
  }

  @Test
  public void test5() {
    try {
      Date d1 = new Date(1, 13, 2021);
      Assert.fail();
    } catch (IllegalArgumentException ignored) {
    }
    ;
    try {
      Calendar c = new Calendar(null);
      Assert.fail();
    } catch (NullPointerException ignored) {
    }
    ;
  }

  @Test
  public void test6() {
    Calendar c = new Calendar(now);
    Date current = c.addYears(10);
    Assert.assertEquals("January 1, 2030 Tuesday", current.toString());
    current = c.addYears(-4);
    Assert.assertEquals("January 1, 2016 Friday", current.toString());
    current = c.addMonths(3);
    Assert.assertEquals("April 1, 2020 Wednesday", current.toString());
    current = c.addMonths(0);
    Assert.assertEquals("January 1, 2020 Wednesday", current.toString());
    current = c.addDays(15);
    Assert.assertTrue(current.isEquals(new Date(16, 1, 2020)));
  }

  @Test
  public void test7() {
    Date check = new Date(23, 5, 2018);
    try {
      check.isAfter(null);
      Assert.fail();
    } catch (NullPointerException ignored) {
    }
    ;
    try {
      check.isEquals(null);
      Assert.fail();
    } catch (NullPointerException ignored) {
    }
    ;
  }

  @Test
  public void test8(){
    Date d1 = new Date(9, 5, 2020);
    Date d2 = new Date(9, 5, 1945);
    Calendar c = new Calendar(d1);
    long days = Calendar.daysBetweenDates(d1, d2);
    Date between = c.convertIntoDate(days);
    Assert.assertEquals(between.dateToString(between), "1 0 75");
  }
}
