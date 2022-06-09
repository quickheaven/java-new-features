package ca.quickheaven.jdk.nf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * {@link <a href="https://www.baeldung.com/java-13-new-features">New Features in Java 13</a>}
 */
public class Java13NewFeaturesTest {

    @Test
    public void whenSwitchingOnOperationSquareMe_thenWillReturnSquare() {
        var me = 4;
        var operation = "squareMe";
        // Using yield, we can now effectively return values from a switch expression:
        var result = switch (operation) {
            case "doubleMe" -> {
                yield me * 2;
            }
            case "squareMe" -> {
                yield me * me;
            }
            default -> me;
        };
        assertEquals(16, result);
    }

    String JSON_STRING
            = "{\r\n" + "\"name\" : \"Arjie\",\r\n" + "\"website\" : \"https://www.%s.ca/\"\r\n" + "}";

    String TEXT_BLOCK_JSON = """
            {
                "name" : "Arjie",
                "website" : "https://www.%s.ca/"
            }
            """;

    @Test
    public void whenTextBlocks_thenStringOperationsWorkSame() {
        assertThat(TEXT_BLOCK_JSON.contains("Arjie")).isTrue();
        assertThat(TEXT_BLOCK_JSON.indexOf("www")).isGreaterThan(0);
        assertThat(TEXT_BLOCK_JSON.length()).isGreaterThan(0);
    }

    @Test
    public void whenTextBlocks_thenFormattedWorksAsFormat() {
        assertThat(TEXT_BLOCK_JSON.formatted("quickheaven").contains("www.quickheaven.ca")).isTrue();
        assertThat(String.format(JSON_STRING, "quickheaven").contains("www.quickheaven.ca")).isTrue();
    }
}
