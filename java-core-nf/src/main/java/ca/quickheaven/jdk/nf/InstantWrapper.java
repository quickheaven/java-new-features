package ca.quickheaven.jdk.nf;

import java.time.*;

/**
 * InstantSource was added in Java 17 to provide a safe way to access the current instant.
 */
public class InstantWrapper {

    Clock clock;

    public InstantWrapper() {
        this.clock = Clock.systemDefaultZone();
    }

    public InstantWrapper(ZonedDateTime zonedDateTime) {
        this.clock = Clock.fixed(zonedDateTime.toInstant(), zonedDateTime.getZone());
    }

    public InstantWrapper(LocalDateTime localDateTime) {
        this.clock = Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
    }

    public static InstantWrapper of() {
        return new InstantWrapper();
    }

    public static InstantWrapper of(ZonedDateTime zonedDateTime) {
        return new InstantWrapper(zonedDateTime);
    }

    public static InstantWrapper of(LocalDateTime localDateTime) {
        return new InstantWrapper(localDateTime);
    }

    Instant instant() {
        return clock.instant();
    }

}
