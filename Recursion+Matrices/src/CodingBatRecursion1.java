/**
 * Most of the solutions for the easier coding bat recursion problems. See codingbat.com
 * 
 * @author Jay Fleischer
 * @version .7 (10-3-13)
 */
public class CodingBatRecursion1 {
	public int array11(int[] nums, int index) {
		if (index == nums.length)
			return 0;
		return (nums[index] == 11 ? 1 : 0) + array11(nums, index + 1);
	}

	public boolean array6(int[] nums, int index) {
		if (index == nums.length)
			return false;
		return nums[index] == 6 || array6(nums, index + 1);
	}

	public int bunnyEars(int bunnies) {
		if (bunnies == 0)
			return 0;
		return 2 + bunnyEars(bunnies - 1);
	}

	public int bunnyEars2(int bunnies) {
		if (bunnies == 0)
			return 0;
		if (bunnies == 1)
			return 2;
		return bunnyEars2(bunnies - 2) + 5;
	}

	public String changePi(String str) {
		return str.replace("pi", "3.14");
	}

	public String changeXY(String str) {
		if (str.length() == 0)
			return str;
		char c = str.charAt(0);
		return (c == 'x' ? 'y' : c) + changeXY(str.substring(1));
	}

	public int count7(int n) {
		if (n == 0)
			return 0;
		return count7(n / 10) + (n % 10 == 7 ? 1 : 0);
	}

	public int count8(int n) {
		if (n == 0)
			return 0;
		return count8(n / 10) + (n % 10 == 8 ? n / 10 % 10 == 8 ? 2 : 1 : 0);
	}

	public int countHi(String str) {
		if (str.length() == 0 || str.length() == 1)
			return 0;
		return countHi(str.substring(1)) + (str.substring(0, 2).equals("hi") ? 1 : 0);
	}

	public int countX(String str) {
		if (str.length() == 0)
			return 0;
		return countX(str.substring(1)) + (str.charAt(0) == 'x' ? 1 : 0);
	}

	public int factorial(int n) {
		if (n == 0)
			return 1;
		return factorial(n - 1) * n;
	}

	public int fibonacci(int n) {
		return recursion(n, 0, 1);
	}

	public String noX(String str) {
		return str.replace("x", "");
	}

	public int powerN(int base, int n) {
		if (n == 0)
			return 1;
		return base * powerN(base, n - 1);
	}

	public int recursion(int n, int a, int b) {
		if (n == 0)
			return a;
		return recursion(n - 1, b, a + b);
	}

	public int sumDigits(int n) {
		if (n == 0)
			return 0;
		return sumDigits(n / 10) + n % 10;
	}

	public int triangle(int rows) {
		if (rows == 0)
			return 0;
		return triangle(rows - 1) + rows;
	}
}
