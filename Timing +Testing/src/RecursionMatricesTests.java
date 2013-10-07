import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class RecursionMatricesTests {
	@Test
	public void testQuestion6_1() {
		char[][] board1 = { { ' ', '*', ' ', ' ', '*', '*' }, { '*', ' ', ' ', '*', '*', ' ' },
				{ '*', ' ', '*', ' ', ' ', ' ' }, { '*', '*', '*', ' ', ' ', ' ' }, { ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', '*', '*', '*', ' ', '*' } };
		; // given example
		int[][] moves11 = { { 0, 0 }, { 0, 2 } };
		char[][] expected11 = { { '2', '_', '2', '_', '_', '_' }, { '_', '_', '_', '_', '_', '_' },
				{ '_', '_', '_', '_', '_', '_' }, { '_', '_', '_', '_', '_', '_' }, { '_', '_', '_', '_', '_', '_' },
				{ '_', '_', '_', '_', '_', '_' } };
		char[][] res11 = Question6.playMoves(board1, moves11);
		assertTrue(Arrays.deepEquals(expected11, res11));
	}

	@Test
	public void testQuestion6_2() {
		char[][] board1 = { 
				{ ' ', '*', ' ', ' ', '*', '*' },
				{ '*', ' ', ' ', '*', '*', ' ' },
				{ '*', ' ', '*', ' ', ' ', ' ' }, 
				{ '*', '*', '*', ' ', ' ', ' ' }, 
				{ ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', '*', '*', '*', ' ', '*' } };
		; // given example
		int[][] moves12 = {};
		char[][] expected12 = { { '_', '_', '_', '_', '_', '_' }, { '_', '_', '_', '_', '_', '_' },
				{ '_', '_', '_', '_', '_', '_' }, { '_', '_', '_', '_', '_', '_' }, { '_', '_', '_', '_', '_', '_' },
				{ '_', '_', '_', '_', '_', '_' } };
		char[][] res12 = Question6.playMoves(board1, moves12);
		assertTrue(Arrays.deepEquals(expected12, res12));
	}

	@Test
	public void testQuestion6_3() {
		char[][] board1 = { { ' ', '*', ' ', ' ', '*', '*' }, { '*', ' ', ' ', '*', '*', ' ' },
				{ '*', ' ', '*', ' ', ' ', ' ' }, { '*', '*', '*', ' ', ' ', ' ' }, { ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', '*', '*', '*', ' ', '*' } };
		; // given example
		int[][] moves13 = { { 5, 1 }, { 0, 0 } };
		char[][] expected13 = { { '_', '_', '_', '_', '_', '_' }, { '_', '_', '_', '_', '_', '_' },
				{ '_', '_', '_', '_', '_', '_' }, { '_', '_', '_', '_', '_', '_' }, { '_', '_', '_', '_', '_', '_' },
				{ '_', '*', '_', '_', '_', '_' } };
		char[][] res13 = Question6.playMoves(board1, moves13);
		assertTrue(Arrays.deepEquals(expected13, res13));
	}

	@Test
	public void testQuestion6_4() {
		char[][] board1 = { { ' ', '*', ' ', ' ', '*', '*' }, { '*', ' ', ' ', '*', '*', ' ' },
				{ '*', ' ', '*', ' ', ' ', ' ' }, { '*', '*', '*', ' ', ' ', ' ' }, { ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', '*', '*', '*', ' ', '*' } };
		; // given example
		int[][] moves14 = { { 3, 5 }, { 5, 0 } };
		char[][] expected14 = { { '_', '_', '_', '_', '_', '_' }, { '_', '_', '_', '_', '_', '_' },
				{ '_', '_', '_', '4', '2', '1' }, { '_', '_', '_', '2', '0', '0' }, { '_', '_', '_', '3', '2', '1' },
				{ '1', '_', '_', '_', '_', '_' } };
		char[][] res14 = Question6.playMoves(board1, moves14);
		assertTrue(Arrays.deepEquals(expected14, res14));
	}
}
