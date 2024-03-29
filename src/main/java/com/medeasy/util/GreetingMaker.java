package com.medeasy.util;

import java.time.LocalTime;

public class GreetingMaker { // think of a better name then this.

  private static final LocalTime MORNING = LocalTime.of(0, 0, 0);
  private static final LocalTime AFTER_NOON = LocalTime.of(12, 0, 0);
  private static final LocalTime EVENING = LocalTime.of(16, 0, 0);
  private static final LocalTime NIGHT = LocalTime.of(21, 0, 0);

  private LocalTime now;

  public GreetingMaker(LocalTime now) {
    this.now = now;
  }

  public String printTimeOfDay() { // or return String in your case
    if (between(MORNING, AFTER_NOON)) {
      return "Good Morning";
    } else if (between(AFTER_NOON, EVENING)) {
      return "Good Afternoon";
    } else if (between(EVENING, NIGHT)) {
      return "Good Evening";
    } else {
      return "Good Night";
    }
  }

  private boolean between(LocalTime start, LocalTime end) {
    return (!now.isBefore(start)) && now.isBefore(end);
  }

}
