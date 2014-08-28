package platzerworld.com.biergarten;

import org.junit.Test;
import org.junit.runner.RunWith;
//import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

//@RunWith(RobolectricTestRunner.class)
public class RoboTest {
    @Test
    public void testTrueIsTrue() throws Exception {
        assertEquals(true, true);
    }

    @Test
    public void testTrueIsFalse() throws Exception {
        assertNotEquals(true, false);
    }

    @Test
    public void testNotNull() throws Exception {
        assertNotNull("");
    }

    @Test
    public void testNull() throws Exception {
        assertNull(null);
    }
}