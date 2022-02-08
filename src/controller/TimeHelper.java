package controller;

import java.time.*;
import java.util.TimeZone;

public class TimeHelper {

    private static final ZoneId SERVER_ZONE_ID = ZoneId.of("UTC");
    private static final ZoneId BUSINESS_ZONE_ID = ZoneId.of("America/New_York");
    private static final ZoneId CLIENT_ZONE_ID = ZoneId.of(TimeZone.getDefault().getID());
    private static final LocalTime TIME_OPEN = LocalTime.of(8,0);
    private static final LocalTime TIME_CLOSE = LocalTime.of(22,0);
    /**
     * Gets the ZoneId of the client
     *
     * @return ZoneId of Client
     */
    public static ZoneId getClientZoneId() {
        return CLIENT_ZONE_ID;
    }

    public static ZonedDateTime clientToServerTime(LocalDate ld, LocalTime lt) {
        ZonedDateTime clientTime = ZonedDateTime.of(ld,lt, CLIENT_ZONE_ID);
        ZonedDateTime serverTime =  clientTime.withZoneSameInstant(SERVER_ZONE_ID);
        return serverTime;
    }

    public static ZonedDateTime clientToServerTime(LocalDateTime ldt) {
        ZonedDateTime clientTime = ZonedDateTime.of(ldt, CLIENT_ZONE_ID);
        ZonedDateTime serverTime =  clientTime.withZoneSameInstant(SERVER_ZONE_ID);
        return serverTime;
    }

    public static ZonedDateTime serverToClientTime(LocalDateTime lt) {
        ZonedDateTime serverTime = ZonedDateTime.of(lt, SERVER_ZONE_ID);
        ZonedDateTime clientTime =  serverTime.withZoneSameInstant(CLIENT_ZONE_ID);
        return clientTime;
    }

    public static LocalDateTime businessToClientTime(LocalDate ld, LocalTime lt) {
        ZonedDateTime businessTimeZ = ZonedDateTime.of(ld, lt, BUSINESS_ZONE_ID);
        ZonedDateTime clientTimeZ =  businessTimeZ.withZoneSameInstant(CLIENT_ZONE_ID);
        LocalDateTime clientTime = clientTimeZ.toLocalDateTime();
        return clientTime;
    }

    public static LocalDateTime getTimeOpen(LocalDate ld) {
        return businessToClientTime(ld, TIME_OPEN);
    }

    public static LocalDateTime getTimeClose(LocalDate ld) {
        return businessToClientTime(ld, TIME_CLOSE);
    }

}
