package com.wmc.guiaremision.infrastructure.common;

import static com.wmc.guiaremision.infrastructure.common.constant.FormatsConstant.COUNTRY_CODE;
import static com.wmc.guiaremision.infrastructure.common.constant.FormatsConstant.LANGUAGE_CODE;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Convert {
  private static final int MILLON = 1000000;
  private static final long BILLON = 1000000000000L;

  public static String convertToLetters(BigDecimal number) {
    long whole = number.setScale(0, RoundingMode.DOWN).longValue();
    int decimals = number.subtract(new BigDecimal(whole)).multiply(new BigDecimal(100))
        .setScale(0, RoundingMode.HALF_UP).intValue();
    String decimalsToText = decimals > 0 ? " CON " + decimals + "/100" : " CON 00/100";

    return convertNumberToText(whole) + decimalsToText;
  }

  public static LocalDate convertDateStringToLocalDate(String date, String dateFormat) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    return LocalDate.parse(date, formatter);
  }

  public static String convertLocalDateToDateString(LocalDate date, String dateFormat) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    return date.format(formatter);
  }

  public static String convertLocalTimeToTimeString(LocalTime time, String timeFormat) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
    return time.format(formatter);
  }

  public static LocalTime convertTimeStringToLocalTime(String time, String timeFormat) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
    return LocalTime.parse(time, formatter);
  }

  public static String convertBigDecimalToString(BigDecimal value, String numericFormat) {
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale(LANGUAGE_CODE, COUNTRY_CODE));
    DecimalFormat decimalFormat = new DecimalFormat(numericFormat, symbols);
    return decimalFormat.format(value);
  }

  private static String convertNumberToText(long number) {
    String numberToText;
    if (number == 0)
      numberToText = "CERO";
    else if (number == 1)
      numberToText = "UNO";
    else if (number == 2)
      numberToText = "DOS";
    else if (number == 3)
      numberToText = "TRES";
    else if (number == 4)
      numberToText = "CUATRO";
    else if (number == 5)
      numberToText = "CINCO";
    else if (number == 6)
      numberToText = "SEIS";
    else if (number == 7)
      numberToText = "SIETE";
    else if (number == 8)
      numberToText = "OCHO";
    else if (number == 9)
      numberToText = "NUEVE";
    else if (number == 10)
      numberToText = "DIEZ";
    else if (number == 11)
      numberToText = "ONCE";
    else if (number == 12)
      numberToText = "DOCE";
    else if (number == 13)
      numberToText = "TRECE";
    else if (number == 14)
      numberToText = "CATORCE";
    else if (number == 15)
      numberToText = "QUINCE";
    else if (number < 20)
      numberToText = "DIECI" + convertNumberToText(number - 10);
    else if (number == 20)
      numberToText = "VEINTE";
    else if (number < 30)
      numberToText = "VEINTI" + convertNumberToText(number - 20);
    else if (number == 30)
      numberToText = "TREINTA";
    else if (number == 40)
      numberToText = "CUARENTA";
    else if (number == 50)
      numberToText = "CINCUENTA";
    else if (number == 60)
      numberToText = "SESENTA";
    else if (number == 70)
      numberToText = "SETENTA";
    else if (number == 80)
      numberToText = "OCHENTA";
    else if (number == 90)
      numberToText = "NOVENTA";
    else if (number < 100)
      numberToText = convertNumberToText(number / 10 * 10) + " Y " + convertNumberToText(number % 10);
    else if (number == 100)
      numberToText = "CIEN";
    else if (number < 200)
      numberToText = "CIENTO " + convertNumberToText(number - 100);
    else if (number == 200 || number == 300 || number == 400 || number == 600 || number == 800)
      numberToText = convertNumberToText(number / 100) + "CIENTOS";
    else if (number == 500)
      numberToText = "QUINIENTOS";
    else if (number == 700)
      numberToText = "SETECIENTOS";
    else if (number == 900)
      numberToText = "NOVECIENTOS";
    else if (number < 1000)
      numberToText = convertNumberToText(number / 100 * 100) + " " + convertNumberToText(number % 100);
    else if (number == 1000)
      numberToText = "MIL";
    else if (number < 2000)
      numberToText = "MIL " + convertNumberToText(number % 1000);
    else if (number < MILLON) {
      numberToText = convertNumberToText(number / 1000) + " MIL";
      if (number % 1000 > 0)
        numberToText += " " + convertNumberToText(number % 1000);
    } else if (number == MILLON)
      numberToText = "UN MILLON";
    else if (number < 2 * MILLON)
      numberToText = "UN MILLON " + convertNumberToText(number % MILLON);
    else if (number < BILLON) {
      numberToText = convertNumberToText(number / MILLON) + " MILLONES";
      if (number % MILLON > 0)
        numberToText += " " + convertNumberToText(number % MILLON);
    } else if (number == BILLON)
      numberToText = "UN BILLON";
    else if (number < 2 * BILLON)
      numberToText = "UN BILLON " + convertNumberToText(number % BILLON);
    else {
      numberToText = convertNumberToText(number / BILLON) + " BILLONES";
      if (number % BILLON > 0)
        numberToText += " " + convertNumberToText(number % BILLON);
    }
    return numberToText;
  }
}
