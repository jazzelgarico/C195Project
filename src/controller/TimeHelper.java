package controller;

import java.time.*;
import java.util.TimeZone;

public class TimeHelper {

    private static final ZoneId serverZoneId = ZoneId.of("America/New_York");
    private static final ZoneId clientZoneId = ZoneId.of(TimeZone.getDefault().getID());
    private static final LocalTime timeOpen = LocalTime.of(8,0);
    private static final LocalTime timeClose = LocalTime.of(22,0);
    /**
     * Gets the ZoneId of the client
     *
     * @return ZoneId of Client
     */
    public static ZoneId getClientZoneId() {
        return clientZoneId;
    }

    public static ZonedDateTime clientToServerTime(LocalDate ld, LocalTime lt) {
        ZonedDateTime orgZDT = ZonedDateTime.of(ld,lt,serverZoneId);
        ZonedDateTime localZDT =  orgZDT.withZoneSameInstant(clientZoneId);
        return localZDT;
    }

    public static ZonedDateTime clientToServerTime(LocalDateTime lt) {
        ZonedDateTime orgZDT = ZonedDateTime.of(lt,serverZoneId);
        ZonedDateTime localZDT =  orgZDT.withZoneSameInstant(clientZoneId);
        return localZDT;
    }

    public static ZonedDateTime serverToClientTime(LocalDateTime lt) {
        ZonedDateTime localZDT = ZonedDateTime.of(lt,clientZoneId);
        ZonedDateTime orgZDT =  localZDT.withZoneSameInstant(serverZoneId);
        return orgZDT;
    }

    public static LocalDateTime getTimeOpen(LocalDate ld) {
        return clientToServerTime(ld,timeOpen).toLocalDateTime();
    }

    public static LocalDateTime getTimeClose(LocalDate ld) {
        return clientToServerTime(ld,timeClose).toLocalDateTime();
    }
}
