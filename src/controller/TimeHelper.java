package controller;

import java.time.*;
import java.util.TimeZone;

public class TimeHelper {

    private static final ZoneId serverZoneId = ZoneId.of("America/NewYork");
    private static final ZoneId clientZoneId = ZoneId.of(TimeZone.getDefault().getID());

    /**
     * Gets the ZoneId of the client
     *
     * @return ZoneId of Client
     */
    public static ZoneId getClientZoneId() {
        return clientZoneId;
    }

    public static ZonedDateTime orgToLocal(LocalDate ld, LocalTime lt) {
        ZonedDateTime orgZDT = ZonedDateTime.of(ld,lt,serverZoneId);
        ZonedDateTime localZDT =  orgZDT.withZoneSameInstant(clientZoneId);
        return localZDT;
    }

    public static ZonedDateTime orgToLocal(LocalDateTime lt) {
        ZonedDateTime orgZDT = ZonedDateTime.of(lt,serverZoneId);
        ZonedDateTime localZDT =  orgZDT.withZoneSameInstant(clientZoneId);
        return localZDT;
    }

    public static ZonedDateTime localToOrg(LocalDateTime lt) {
        ZonedDateTime localZDT = ZonedDateTime.of(lt,clientZoneId);
        ZonedDateTime orgZDT =  localZDT.withZoneSameInstant(serverZoneId);
        return orgZDT;
    }
}
