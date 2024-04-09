package currency.enums;

import java.time.Duration;

public enum TimeIntervalEnum {
    HOUR(Duration.ofHours(1)),
    DAY(Duration.ofDays(1)),
    WEEK(Duration.ofDays(7));

    private final Duration duration;

    TimeIntervalEnum(Duration duration) {
        this.duration = duration;
    }

    public long toMillis() {
        return duration.toMillis();
    }
}