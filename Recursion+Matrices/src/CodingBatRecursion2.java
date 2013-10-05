/**
 * The solutions for the harder coding bat recursion problems. This is probably one of the worse times for the
 * auto-formatter to alphabetize methods... I renamed several methods all called recursion, to avoid conflicts.
 * See codingbat.com
 * 
 * @author Jay
 * @version 1.0 (10-2-13)
 */
public class CodingBatRecursion2 {
	public boolean add(int[] nums, int index, int total, boolean[] indexes) {
		if (index >= nums.length)
			return false;
		for (int i = index; i < nums.length; i++) {
			boolean[] copy = new boolean[indexes.length];
			copy(copy, indexes);
			int totalCopy = total + nums[i];
			copy[i] = true;
			if (add(nums, i + 1, totalCopy, copy) || total == otherSum(nums, indexes))
				return true;
		}
		return false;
	}

	public void copy(boolean[] copy, boolean[] original) {
		for (int i = 0; i < copy.length; i++)
			copy[i] = original[i];
	}

	public boolean groupNoAdj(int start, int[] nums, int target) {
		if (target == 0)
			return true;
		for (int i = start; i < nums.length; i++)
			if (groupNoAdj(i + 2, nums, target - nums[i]))
				return true;
		return false;
	}

	public boolean groupSum(int start, int[] nums, int target) {
		if (start == nums.length)
			return target == 0;
		return groupSum(start + 1, nums, target) || groupSum(start + 1, nums, target - nums[start]);
	}

	public boolean groupSum5(int start, int[] nums, int target) {
		if (start == nums.length)
			return target == 0;
		if (nums[start] % 5 == 0)
			return groupSum5(start + 1, nums, target - nums[start]);
		if (nums[start] == 1 && start > 0 && nums[start - 1] == 5)
			return groupSum5(start + 1, nums, target);
		return groupSum5(start + 1, nums, target) || groupSum5(start + 1, nums, target - nums[start]);
	}

	public boolean groupSum6(int start, int[] nums, int target) {
		int sixTotal = 0;
		for (int num : nums)
			if (num == 6)
				sixTotal += 6;
		return recursiongs6(start, nums, target, sixTotal);
	}

	public boolean groupSumClump(int start, int[] nums, int target) {
		return recursiongsc(0, nums, target, -1, false);
	}

	public int otherSum(int[] nums, boolean[] indexes) {
		int sum = 0;
		for (int i = 0; i < indexes.length; i++)
			if (!indexes[i])
				sum += nums[i];
		return sum;
	}

	public boolean recursiongs6(int start, int[] nums, int target, int sixTotal) {
		if (target == sixTotal)
			return true;
		if (nums.length > start && nums[start] == 6)
			return recursiongs6(start + 1, nums, target, sixTotal);
		else
			for (int i = start; i < nums.length; i++)
				if (recursiongs6(i + 1, nums, target - nums[i], sixTotal))
					return true;
		return false;
	}

	public boolean recursiongsc(int index, int[] nums, int target, int previous, boolean used) {
		if (index == nums.length)
			return target == 0;
		if (previous == nums[index])
			return recursiongsc(index + 1, nums, target - (used ? previous : 0), previous, used);
		else
			return recursiongsc(index + 1, nums, target - nums[index], nums[index], true)
					|| recursiongsc(index + 1, nums, target, nums[index], false);
	}

	public boolean recursions53(int start, int[] nums, int diff, int total) {
		if (start == nums.length)
			return total == diff;
		if (nums[start] % 3 == 0 || nums[start] % 5 == 0)
			return recursions53(start + 1, nums, diff, total);
		return recursions53(start + 1, nums, diff, total + nums[start])
				|| recursions53(start + 1, nums, diff, total - nums[start]);
	}

	public boolean recursionso10(int[] nums, int index, int group1, int group2) {
		if (index == nums.length)
			return group1 % 10 == 0 && group2 % 2 == 1;
		return recursionso10(nums, index + 1, group1 + nums[index], group2)
				|| recursionso10(nums, index + 1, group1, group2 + nums[index]);
	}

	public boolean split53(int[] nums) {
		int threeTotal = 0, fiveTotal = 0;
		for (int num : nums)
			if (num % 5 == 0)
				fiveTotal += 5;
			else if (num % 3 == 0)
				threeTotal += 3;
		return recursions53(0, nums, fiveTotal - threeTotal, 0);
	}

	public boolean splitArray(int[] nums) {
		if (nums.length == 0)
			return true;
		return add(nums, 0, 0, new boolean[nums.length]);
	}

	public boolean splitOdd10(int[] nums) {
		return recursionso10(nums, 0, 0, 0);
	}
}
