package cz.cvut.fel.ear.library.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class DateUtils {

    public static Date getDateFromLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
