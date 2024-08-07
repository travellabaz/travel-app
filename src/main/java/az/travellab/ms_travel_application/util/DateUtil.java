package az.travellab.ms_travel_application.util;

import lombok.SneakyThrows;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public enum DateUtil {
    DATE_UTIL;

    public LocalDateTime toStartOfDay() {
        return LocalDateTime.now().toLocalDate().atStartOfDay();
    }

    public LocalDateTime toNowPlusHours(long hour) {
        return LocalDateTime.now().plusHours(hour);
    }

    public LocalDateTime toEndOfDay() {
        return LocalDateTime.now().toLocalDate().atTime(23, 59, 59);
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    public LocalDateTime toStartOfNextDay() {
        return LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
    }

    public LocalDateTime toEndOfNextDay() {
        return LocalDateTime.now().plusDays(1).toLocalDate().atTime(23, 59, 59);
    }

    public LocalDateTime sync(LocalDateTime date) {
        return date.plusHours(4);
    }

    @SneakyThrows
    public XMLGregorianCalendar toXMLGregorianCalendar(String date) {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX").format(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-d'T'HH:mm:ss.SSSSSS")).atZone(ZoneId.systemDefault())));
    }


    public String formatDateTimeLocalDateTime(LocalDateTime dateTime) {
        var outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(outputFormatter);
    }

}