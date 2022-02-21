package main;


import utility.TimeHelper;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;


public class TestTime {

    public static void main(String[] args) {

        System.out.println("Eastern Time: ");
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        LocalDateTime easternLocal = LocalDateTime.now();

        ZonedDateTime easternZoned = ZonedDateTime.of(easternLocal,TimeZone.getDefault().toZoneId());
        ZonedDateTime uTCZonedEastern = easternZoned.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime utcLocalEastern = uTCZonedEastern.toLocalDateTime();

        LocalDateTime easternToUTC = TimeHelper.clientToServerTime(easternLocal);
        LocalDateTime easternLocalTwo = TimeHelper.serverToClientTime(easternToUTC);
        System.out.println("Local Time: " + easternLocal.format(DateTimeFormatter.ofPattern("h:mma")));
        System.out.println("Zoned Time: " + easternZoned.format(DateTimeFormatter.ofPattern("h:mma")));
        System.out.println("Zoned UTCTime: " + uTCZonedEastern.format(DateTimeFormatter.ofPattern("h:mma")));
        System.out.println("Zoned UTCTime to Local: " + utcLocalEastern.format(DateTimeFormatter.ofPattern("h:mma")));
        System.out.println("UTC Time: " + easternToUTC.format(DateTimeFormatter.ofPattern("h:mma")));
        System.out.println("Local Time Reconversion: " + easternLocalTwo.format(DateTimeFormatter.ofPattern("h:mma")));

        System.out.println("Central Time:");
        TimeZone.setDefault(TimeZone.getTimeZone("America/Chicago"));
        LocalDateTime centralLocal = LocalDateTime.now();
        ZonedDateTime centralZoned = ZonedDateTime.now();
        ZonedDateTime uTCZonedCentral = easternZoned.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime utcLocalCentral = uTCZonedCentral.toLocalDateTime();
        LocalDateTime centralToUTC = TimeHelper.serverToClientTime(centralLocal);
        LocalDateTime centralLocalTwo = TimeHelper.clientToServerTime(centralToUTC);
        System.out.println("Local Time: " + centralLocal.format(DateTimeFormatter.ofPattern("h:mma")));
        System.out.println("Zoned Time: " + centralZoned.format(DateTimeFormatter.ofPattern("h:mma")));
        System.out.println("Zoned UTCTime: " + uTCZonedCentral.format(DateTimeFormatter.ofPattern("h:mma")));
        System.out.println("Zoned UTCTime To Local: " + utcLocalCentral.format(DateTimeFormatter.ofPattern("h:mma")));
        System.out.println("UTC Time: " + centralToUTC.format(DateTimeFormatter.ofPattern("h:mma")));
        System.out.println("Local Time Reconversion: " + centralLocalTwo.format(DateTimeFormatter.ofPattern("h:mma")));
    }
}

