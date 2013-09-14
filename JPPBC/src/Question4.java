/**
 * A bonus problem involving spending as much as possible in a hardware store.
 * Another Philly Classic Problem.
 * 
 * @author Jay
 * @version 1.0 (9-13-13)
 */
public class Question4 {
	/**
	 * Recursively calculates the most that can be spent starting with what has
	 * already been spent.
	 * 
	 * @param amounts
	 *            - the prices of the available items
	 * @param currentTotal
	 *            - the amount already spent
	 * @param previousIndex
	 *            - the index of the amount spent in only the previous recursive step
	 * @return the most that can be spent
	 * @throws Exception
	 *             thrown when the amount reaches exactly $100, to avoid continuing testing.
	 */
	private static double best(double[] amounts, double currentTotal, int previousIndex) throws Exception {
		if (currentTotal > 100.0)
			return 0;
		else if (currentTotal == 100.0)
			throw new Exception("Finished!");
		double best = currentTotal;
		for (int i = previousIndex; i < amounts.length; i++) {
			double value = best(amounts, currentTotal + amounts[i], i);
			if (value > best)
				best = value;
		}
		return best;
	}

	/**
	 * Calculates the most that can be spent given the prices of the various items.
	 * 
	 * @param amounts
	 *            - the different prices
	 * @return the most that can be spent
	 */
	public static double maxSpending(double[] amounts) {
		sort(amounts);
		double max = 0;
		for (double amount : amounts) {
			double value = (int) (100.0 / amount) * amount;
			if (value > max)
				max = value;
		}
		if (max == 100.0)
			return max;
		try {
			double value = best(amounts, 0, 0);
			if (value > max)
				max = value;
		} catch (Exception e) {
			return 100.0;
		}
		return max;
	}

	/**
	 * Sorts the prices from highest to lowest to improve speed. Slightly modified from question 3.
	 * 
	 * @param amounts
	 *            - the array to be sorted
	 */
	private static void sort(double[] amounts) {
		boolean modified = true;
		while (modified) {
			modified = false;
			for (int i = 0; i < amounts.length - 1; i++)
				if (amounts[i] < amounts[i + 1]) {
					modified = true;
					double temp = amounts[i];
					amounts[i] = amounts[i + 1];
					amounts[i + 1] = temp;
				}
		}
	}
}
