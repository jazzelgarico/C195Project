package controller;

import java.time.ZoneId;
import java.util.TimeZone;

public class TimeHelper {

    /**
     * Gets the ZoneId of the client
     *
     * @return ZoneId of Client
     */
    public static ZoneId getClientZoneId() {
        return ZoneId.of(TimeZone.getDefault().getID());
    }
}
