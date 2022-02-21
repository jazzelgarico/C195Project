package utility;

import java.time.*;
import java.util.TimeZone;

/**
 * A collection of static methods and members to help with time conversion. Class contains the business time zone,
 * the client time zone, the server time zone, and the times the business opens and closes as well as methods to
 * convert in between the relevant time zones.
 */
public class TimeHelper {
    private static final ZoneId SERVER_ZONE_ID = ZoneId.of("UTC");
    private static final ZoneId BUSINESS_ZONE_ID = ZoneId.of("America/New_York");
    private static final ZoneId CLIENT_ZONE_ID = TimeZone.getDefault().toZoneId();
    private static final LocalTime TIME_OPEN = LocalTime.of(8,0);
    private static final LocalTime TIME_CLOSE = LocalTime.of(22,0);

    /**
     * Gets the ZoneId of the client.
     *
     * @return ZoneId of Client
     */
    public static ZoneId getClientZoneId() {
        return CLIENT_ZONE_ID;
    }

    /**
     * Converts the given local date and time from the client's time zone to the server's time zone.
     *
     * @param localDate the LocalDate in the client's time zone
     * @param localTime the LocalTime in the client's time zone
     * @return the LocalDateTime in the server's time zone
     */
    public static LocalDateTime clientToServerTime(LocalDate localDate, LocalTime localTime) {
        ZonedDateTime clientTimeZoned = ZonedDateTime.of(localDate,localTime, CLIENT_ZONE_ID);
        ZonedDateTime serverTimeZoned =  clientTimeZoned.withZoneSameInstant(SERVER_ZONE_ID);
        LocalDateTime serverTime = serverTimeZoned.toLocalDateTime();
        return serverTime;
    }

    /**
     * Converts the given local date and time from the client's time zone to the server's time zone.
     *
     * @param localDateTime the LocalDateTime in the client's time zone
     * @return the LocalDateTime in the server's time zone
     */
    public static LocalDateTime clientToServerTime(LocalDateTime localDateTime) {
        ZonedDateTime clientTimeZoned = ZonedDateTime.of(localDateTime, CLIENT_ZONE_ID);
        ZonedDateTime serverTimeZoned =  clientTimeZoned.withZoneSameInstant(SERVER_ZONE_ID);
        LocalDateTime serverTime = serverTimeZoned.toLocalDateTime();
        return serverTime;
    }

    /**
     * Converts the given LocalDateTime from the server time zone to the client's time zone.
     *
     * @param localDateTime the LocalDateTime in the server's time zone
     * @return the LocalDateTime in the client's time zone
     */
    public static LocalDateTime serverToClientTime(LocalDateTime localDateTime) {
        ZonedDateTime serverTimeZoned = ZonedDateTime.of(localDateTime, SERVER_ZONE_ID);
        ZonedDateTime clientTimeZoned =  serverTimeZoned.withZoneSameInstant(CLIENT_ZONE_ID);
        LocalDateTime clientTime = clientTimeZoned.toLocalDateTime();
        return clientTime;
    }

    /**
     * Converts the given LocalDate and LocalTime from the business's time zone to the client's time zone.
     *
     * @param localDate the LocalDate in the business's time zone
     * @param localTime the LocalTime in the business's time zone
     * @return the LocalDateTime in the client's time zone
     */
    public static LocalDateTime businessToClientTime(LocalDate localDate, LocalTime localTime) {
        ZonedDateTime businessTimeZ = ZonedDateTime.of(localDate, localTime, BUSINESS_ZONE_ID);
        ZonedDateTime clientTimeZ =  businessTimeZ.withZoneSameInstant(CLIENT_ZONE_ID);
        LocalDateTime clientTime = clientTimeZ.toLocalDateTime();
        return clientTime;
    }

    /**
     * Gets the LocalDateTime the business is opening in the client's time zone.
     *
     * @param localDate the LocalDate to check for business open
     * @return theLocalDateTime the business is opening in the client's time zone.
     */
    public static LocalDateTime getTimeOpen(LocalDate localDate) {
        return businessToClientTime(localDate, TIME_OPEN);
    }

    /**
     * Gets the LocalDateTime the business is closing in the client's time zone.
     *
     * @param localDate the LocalDate to check for business close
     * @return theLocalDateTime the business is closing in the client's time zone.
     */
    public static LocalDateTime getTimeClose(LocalDate localDate) {
        return businessToClientTime(localDate, TIME_CLOSE);
    }
}
