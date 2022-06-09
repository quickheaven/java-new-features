package ca.quickheaven.jdk.nf;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * {@link <a href="https://www.baeldung.com/java-12-new-features">New Features in Java 12</a>}
 */
public class Java12NewFeaturesTest {

    @Test
    public void givenString_thenIndentValue() {
        String text = "Hello Arjie!\nThis is Java 12 new feature".toLowerCase(Locale.ROOT);

        text = text.indent(4);
        System.out.println(text);

        text = text.indent(-10);
        System.out.println(text);
    }

    @Test
    public void givenString_thenRevertValue() {
        String text = "Arjie";
        String transformed = text.transform(value -> new StringBuilder(value).reverse().toString());
        assertEquals("eijrA", transformed);
    }

    @Test
    public void givenIdenticalFiles_thenShouldNotFindMismatch() throws IOException {
        Path filePath1 = Files.createTempFile("file1", ".txt");
        Path filePath2 = Files.createTempFile("file2", ".txt");
        Files.writeString(filePath1, "Java 12 Article");
        Files.writeString(filePath2, "Java 12 Article");

        long mismatch = Files.mismatch(filePath1, filePath2);
        assertEquals(-1, mismatch);
    }

    @Test
    public void givenIdenticalFiles_thenShouldFindMismatch() throws IOException {
        Path filePath3 = Files.createTempFile("file3", ".txt");
        Path filePath4 = Files.createTempFile("file4", ".txt");
        Files.writeString(filePath3, "Java 12 Article");
        Files.writeString(filePath4, "Java 12 Tutorial");

        long mismatch = Files.mismatch(filePath3, filePath4);
        assertEquals(8, mismatch);
    }

    @Test
    public void givenSetOfNumbers_thenCalculateAverage() { // Teeing Collector
        double mean = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.teeing(Collectors.summingDouble(i -> i),
                        Collectors.counting(), (sum, count) -> sum / count));
        System.out.println(mean);
        assertEquals(3.0, mean, 1);
    }

    @Test
    public void givenNumber_thenCompactValues() {
        NumberFormat likesShort =
                NumberFormat.getCompactNumberInstance(new Locale("en", "US"), NumberFormat.Style.SHORT);
        likesShort.setMaximumFractionDigits(2);
        assertEquals("2.59K", likesShort.format(2592));

        NumberFormat likesLong =
                NumberFormat.getCompactNumberInstance(new Locale("en", "US"), NumberFormat.Style.LONG);
        likesLong.setMaximumFractionDigits(2);
        assertEquals("2.59 thousand", likesLong.format(2592));
    }

    @Test
    public void givenDayOfWeek_thenCheckWorkingDayOrDayOff() {
        // old syntax
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        String typeOfDay = "";
        switch (dayOfWeek) {
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY:
                typeOfDay = "Working Day";
                break;
            case SATURDAY:
            case SUNDAY:
                typeOfDay = "Day Off";
        }
        System.out.println(typeOfDay);

        // new syntax, no need for break statements
        typeOfDay = switch (dayOfWeek) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Working Day";
            case SATURDAY, SUNDAY -> "Day Off";
        };
        System.out.println(typeOfDay);
    }

    @Test
    public void givenObject_thenExplicitlyTypeCastTheObject() {
        Object obj = "Hello World!";
        if (obj instanceof String) {
            String s = (String) obj; //explicitly typecast - no longer needed in Java 12
            int length = s.length();
        }

        if (obj instanceof String s) {
            int length = s.length();
        }
    }
}
