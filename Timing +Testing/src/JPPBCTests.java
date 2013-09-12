import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JPPBCTests {
	@Test
	public void testQuestion1_1() {
		assertEquals(3.1625, Question1.calculateGPA("A A A B+ D- C+ A B"), .001);
	}

	@Test
	public void testQuestion1_2() {
		assertEquals(2, Question1.calculateGPA("C"), .001);
	}

	@Test
	public void testQuestion2_1() {
		assertArrayEquals(new String[] {"JUICE$", "EAGLES", "EAGERS", "W$#RDS", "W$$RDS"},
				Question2.wackySort(new String[] {"JUICE$", "W$#RDS", "EAGLES", "EAGERS", "W$$RDS"}));
	}
}
