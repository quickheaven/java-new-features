package ca.quickheaven.jdk.nf;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * {@link <a href="https://www.baeldung.com/java-14-new-features">New Features in Java 14</a>}
 */
public class Java14NewFeaturesTest {

    @Test
    public void givenDayOfWeek_thenCheckHolidayOrNot() {
        boolean isTodayHoliday;
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        String day = dayOfWeek.name();
        switch (day) {
            case "MONDAY":
            case "TUESDAY":
            case "WEDNESDAY":
            case "THURSDAY":
            case "FRIDAY":
                isTodayHoliday = false;
                break;
            case "SATURDAY":
            case "SUNDAY":
                isTodayHoliday = true;
                break;
            default:
                throw new IllegalArgumentException("What's a " + day);
        }
        System.out.println(String.format("day: %s isTodayHoliday: %s", day, isTodayHoliday));

        isTodayHoliday = switch (day) {
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> false;
            case "SATURDAY", "SUNDAY" -> true;
            default -> throw new IllegalArgumentException("What's a " + day);
        };
        System.out.println(String.format("day: %s isTodayHoliday: %s", day, isTodayHoliday));
    }

    /**
     * text blocks now have two new escape sequences:
     * <p>
     * \: to indicate the end of the line, so that a new line character is not introduced
     * \s: to indicate a single space
     */
    @Test
    public void testTextBlocks_JEP_368() {
        String multiline = "A quick brown fox jumps over a lazy dog; the lazy dog howls loudly.";
        System.out.println(multiline);
        multiline = """
                A quick brown fox jumps over a lazy dog; \
                the lazy dog howls loudly.""";
        System.out.println(multiline);
    }

    @Test
    public void testPatternMatchingForInstanceOf_JEP_305() {
        Object obj = "Hello World!";
        if (obj instanceof String) {
            String str = (String) obj;
            int len = str.length();
            // ...
        }
        if (obj instanceof String str) {
            int len = str.length();
            // ...
        }
    }

    // Records JEP 359
    public record User(int id, String password) {
    }

    private User user1 = new User(0, "UserOne");

    @Test
    public void givenRecord_whenObjInitialized_thenValuesCanBeFetchedWithGetters() {
        assertEquals(0, user1.id());
        assertEquals("UserOne", user1.password());
    }

    @Test
    public void whenRecord_thenEqualsImplemented() {
        User user2 = user1;
        assertEquals(user1, user2);
    }

    @Test
    public void whenRecord_thenToStringImplemented() {
        assertTrue(user1.toString().contains("UserOne"));
    }

    @Test(expected = NullPointerException.class)
    public void testHelpfulNullPointerExceptions_JEP_358() {
        int[] arr = null;
        arr[0] = 1; // java.lang.NullPointerException: Cannot store to int array because "arr" is null
    }
}
