package project.factory.history;

import java.time.Duration;

public enum TimeInterval {
    HOUR(Duration.ofHours(1)),
    DAY(Duration.ofDays(1)),
    WEEK(Duration.ofDays(7));

    private final Duration duration;

    TimeInterval(Duration duration) {
        this.duration = duration;
    }

    public long toMillis(){
        return duration.toMillis();
    }
}
