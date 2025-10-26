package com.wmc.guiaremision.shared.common;

import static com.wmc.guiaremision.shared.common.Constant.COUNTRY_CODE;
import static com.wmc.guiaremision.shared.common.Constant.LANGUAGE_CODE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;

public class Convert {
  private static final int MILLON = 1000000;
  private static final long BILLON = 1000000000000L;

  private static final ObjectMapper objectMapper = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

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

 // convertir un string de base 64 a un array de bytes
  public static byte[] convertBase64ToByteArray(String base64) {
    return Optional.ofNullable(base64)
        .map(b64 -> Base64.getDecoder().decode(b64))
        .orElseThrow(() -> new RuntimeException("Error al convertir Base64 a byte array"));
  }

  // convertir un array de bytes a un string de base 64
  public static String convertByteArrayToBase64(byte[] bytes) {
    return Optional.ofNullable(bytes)
        .map(b -> Base64.getEncoder().encodeToString(b))
        .orElseThrow(() -> new RuntimeException("Error al convertir byte array a Base64"));
  }

  /**
   * Convierte un objeto a formato JSON.
   * 
   * @param object El objeto a convertir
   * @return String en formato JSON, o null si hay error
   */
  public static String convertObjectToJson(Object object) {
    return Optional.ofNullable(object)
        .map(obj -> {
          try {
            return objectMapper.writeValueAsString(obj);
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        })
        .orElseThrow(() -> new RuntimeException("Error al convertir objeto a JSON"));
  }

  /**
   * Convierte un String en formato JSON a un objeto de la clase especificada.
   *
   * @param <T>       Tipo de objeto a retornar
   * @param json      String en formato JSON
   * @param valueType Clase del objeto a retornar
   * @return Objeto de la clase especificada, o null si hay error
   */
  public static <T> T convertJsonToObject(String json, Class<T> valueType) {
    return Optional.ofNullable(json)
        .map(j -> {
          try {
            return objectMapper.readValue(j, valueType);
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        })
        .orElseThrow(() -> new RuntimeException("Error al convertir JSON a objeto"));
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
