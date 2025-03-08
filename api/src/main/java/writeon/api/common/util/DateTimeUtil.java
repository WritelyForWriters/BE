package writeon.api.common.util;

import writeon.api.common.enums.exception.ParameterException;
import writeon.api.common.exception.BaseException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

@UtilityClass
public class DateTimeUtil {

  public final String DATE_DEFAULT_PATTERN = "yyyy-MM-dd";
  public final String DATE_NORMAL_PATTERN = "yyyyMMdd";
  public final String DATE_DOT_PATTERN = "yyyy.MM.dd";

  public final String DATETIME_DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

  public final TimeZone KOREA_TIME_ZONE = TimeZone.getTimeZone(ZoneId.of("Asia/Seoul"));

  public LocalDateTime convertToDateTime(String dateString, String pattern) {
    verifyValidPattern(dateString, pattern);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDateTime.parse(dateString, formatter);
  }

  public LocalDateTime convertToDateTime(String dateString) {
    return convertToDateTime(dateString, DATETIME_DEFAULT_PATTERN);
  }

  public LocalDate convertToDate(String dateString, String pattern) {
    verifyValidPattern(dateString, DATE_DEFAULT_PATTERN);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDate.parse(dateString, formatter);
  }

  public LocalDate convertToDate(String dateString) {
    return convertToDate(dateString, DATE_DEFAULT_PATTERN);
  }

  public String convertToString(LocalDateTime dateTime, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.KOREA);
    return dateTime.format(formatter);
  }

  public String convertToString(LocalDateTime dateTime) {
    return convertToString(dateTime, DATETIME_DEFAULT_PATTERN);
  }

  public String convertToString(LocalDate date, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.KOREA);
    return date.format(formatter);
  }

  public String convertToString(LocalDate date) {
    return convertToString(date, DATE_DEFAULT_PATTERN);
  }

  public LocalDate getDate() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_DEFAULT_PATTERN);
    return LocalDate.parse(LocalDate.now().format(formatter), formatter);
  }

  public LocalDateTime getDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_DEFAULT_PATTERN);
    return LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
  }

  private void verifyValidPattern(String dateString, String pattern) {
    if (StringUtils.isBlank(dateString)) {
      throw new BaseException(ParameterException.PARAMETER_INVALID);
    }

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    try {
      if (StringUtils.containsAny(pattern, "H", "m", "s")) {
        LocalDateTime.parse(dateString, formatter);
      } else {
        LocalDate.parse(dateString, formatter);
      }
    } catch (Exception e) {
      throw new BaseException(ParameterException.PARAMETER_INVALID);
    }
  }
}
