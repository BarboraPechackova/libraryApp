package cz.cvut.fel.ear.library.util;

import java.time.LocalDate;
import java.sql.Date;

public final class DateUtils {

    public static Date getDateFromLocalDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }
}
