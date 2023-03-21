package com.kakao.search.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

  private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

  public static String getDateTime(long timestamp) {
    return format.format(new Date(timestamp));
  }
}
