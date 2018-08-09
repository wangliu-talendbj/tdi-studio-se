package org.talend.sdk.component.studio.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringsTest {

    @Test
    public void testRequireNonEmpty() {
        final String expected = "some string";
        final String actual = Strings.requireNonEmpty(expected);
        Assertions.assertEquals(expected, actual);
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> Strings.requireNonEmpty(""));
        Assertions.assertEquals("String arg should not be empty", e.getMessage());
    }

    @Test
    public void testRemoveQuotes() {
        Assertions.assertEquals("some string", Strings.removeQuotes("\"some string\""));
        Assertions.assertEquals("some\"inner\" string", Strings.removeQuotes("\"some\"inner\" string\""));
        Assertions.assertEquals("\"some string", Strings.removeQuotes("\"some string"));
        Assertions.assertEquals("some string\"", Strings.removeQuotes("some string\""));
    }

}
