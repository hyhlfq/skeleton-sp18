import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testStudentArrayDeque() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        String message = "";
        for (int i = 0; i < 1000; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            Integer number = StdRandom.uniform(100);
            if (numberBetweenZeroAndOne < 0.5) {
                sad.addLast(number);
                ads.addLast(number);
                message += "addLast(" + number + ")\n";
            } else {
                sad.addFirst(number);
                ads.addFirst(number);
                message += "addFirst(" + number + ")\n";
            }
        }
        for (int i = 0; i < 1000; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            Integer actual = 0;
            Integer expected = 0;
            if (numberBetweenZeroAndOne < 0.5) {
                actual = sad.removeFirst();
                expected = ads.removeFirst();
                message += "removeFirst()\n";
            } else {
                actual = sad.removeLast();
                expected = ads.removeLast();
                message += "removeLast()\n";
            }
            assertEquals(message, expected, actual);
        }
    }
}
