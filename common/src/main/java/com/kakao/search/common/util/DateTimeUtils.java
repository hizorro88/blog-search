package com.kakao.search.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

  private static final SimpleDateFormat isoFormat = new SimpleDateFormat(
      "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

  private static final SimpleDateFormat simpleFormat = new SimpleDateFormat(
      "yyyyMMdd"); //20230224

  public static String getDateTime(long timestamp) {
    return isoFormat.format(new Date(timestamp));
  }

  public static Date parseDate(String date) throws ParseException {
    return simpleFormat.parse(date);
  }
}
