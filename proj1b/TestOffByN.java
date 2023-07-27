import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offBy1 = new OffByN(1);
    static CharacterComparator offBy5 = new OffByN(5);

    @Test
    public void testEqualChars() {
        assertTrue(offBy1.equalChars('&', '%'));
        assertTrue(offBy1.equalChars('a', 'b'));
        assertTrue(offBy1.equalChars('r', 'q'));
        assertTrue(offBy1.equalChars('1', '2'));
        assertTrue(offBy1.equalChars('A', 'B'));
        assertTrue(offBy1.equalChars('B', 'A'));
        assertFalse(offBy1.equalChars('a', 'e'));
        assertFalse(offBy1.equalChars('z', 'a'));
        assertFalse(offBy1.equalChars('a', 'a'));
        assertFalse(offBy1.equalChars('a', 'B'));
        assertFalse(offBy1.equalChars('A', 'b'));

        assertTrue(offBy5.equalChars('a', 'f'));
        assertTrue(offBy5.equalChars('f', 'a'));
        assertTrue(offBy5.equalChars('f', 'k'));
        assertTrue(offBy5.equalChars('k', 'f'));
        assertFalse(offBy5.equalChars('a', 'e'));
        assertFalse(offBy5.equalChars('z', 'a'));
        assertFalse(offBy5.equalChars('a', 'a'));
        assertFalse(offBy5.equalChars('a', 'B'));
        assertFalse(offBy5.equalChars('A', 'b'));
    }
}
