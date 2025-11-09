package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class DateTimeUtils {

    public static final ZoneId VIETNAM_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");

    private static final DateTimeFormatter STRING_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private DateTimeUtils() {}

    public static LocalDateTime parseDateTimeString(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, STRING_FORMATTER);
        } catch (DateTimeParseException e) {
            log.error("Error invalid format string date time", dateTimeString, e);
            return null;
        }
    }

    public static ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now(VIETNAM_ZONE);
    }

    public static boolean isSortedDesc(List<LocalDateTime> dateList) {
        for (int i = 0; i < dateList.size() - 1; i++) {
            LocalDateTime current = dateList.get(i);
            LocalDateTime next = dateList.get(i + 1);

            if (current.isBefore(next)) {
                log.warn("Error sort: {} before {}", current, i, next);
                return false;
            }
        }
        return true;
    }
}
