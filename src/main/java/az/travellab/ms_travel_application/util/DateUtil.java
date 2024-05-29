package az.travellab.ms_travel_application.util;

import lombok.SneakyThrows;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public enum DateUtil {
    DATE_UTIL;

    public LocalDateTime toLocalDateTime(XMLGregorianCalendar xmlGregorianCalendar) {
        return LocalDateTime.of(xmlGregorianCalendar.getYear(), xmlGregorianCalendar.getMonth(), xmlGregorianCalendar.getDay(), xmlGregorianCalendar.getHour(), xmlGregorianCalendar.getMinute(), xmlGregorianCalendar.getSecond());
    }

    public LocalDateTime toStartOfDay() {
        return LocalDateTime.now().toLocalDate().atStartOfDay();
    }
    public LocalDateTime toEndOfDay() {
        return LocalDateTime.now().toLocalDate().atTime(23, 59, 59);
    }

    public LocalDateTime toStartOfNextDay() {
        return LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
    }

    // Метод для получения конца следующего дня (23:59:59.999999999)
    public LocalDateTime toEndOfNextDay() {
        return LocalDateTime.now().plusDays(1).toLocalDate().atTime(23, 59, 59);
    }

    @SneakyThrows
    public XMLGregorianCalendar toXMLGregorianCalendar(String date) {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX").format(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-d'T'HH:mm:ss.SSSSSS")).atZone(ZoneId.systemDefault())));
    }
}