import MultiSetHashTable.HashTableBag;

/**
 * A speed test for a HashTableBag Typical times (desktops in B48) are: Add: 1.1s Contains: .45s 10 Iterate: .49s
 * Remove: .43s (sometimes .73s???) Readd:.48s
 * 
 * @author Jay Fleischer
 * 
 */
public class SpeedTest {
	
	public static void main(String[] args) {
		HashTableBag<Integer> bag = new HashTableBag<Integer>();
		for (int i = 0; i < 1000000; i++)
			bag.add(i * i + i);
		bag = new HashTableBag<Integer>();
		long time = System.nanoTime();
		for (int i = 0; i < 1000000; i++)
			bag.add(i * i + i);
		System.out.println("Add Time: " + (System.nanoTime() - time) / 1000000000D + " seconds");
		time = System.nanoTime();
		for (int i = 0; i < 1000000; i++)
			if (!bag.contains(i * i + i))
				System.out.println("ERROR: " + (i * i + i) + "not in Bag!");
		System.out.println("Contains Time: " + (System.nanoTime() - time) / 1000000000D + " seconds");
		int total = 0;
		time = System.nanoTime();
		for (int j = 0; j < 10; j++)
			for (int i : bag)
				total += i;
		System.out.println("10 Iterate Time: " + (System.nanoTime() - time) / 1000000000D + " seconds");
		bag.add(total);
		bag.remove(total);
		time = System.nanoTime();
		for (int i = 0; i < 1000000; i++)
			if (!bag.remove(i * i + i))
				System.out.println("ERROR: " + (i * i + i) + "not in Bag!");
		System.out.println("Remove Time: " + (System.nanoTime() - time) / 1000000000D + " seconds");
		time = System.nanoTime();
		for (int i = 0; i < 1000000; i++)
			bag.add(i * i + i);
		System.out.println("Readd Time: " + (System.nanoTime() - time) / 1000000000D + " seconds");
	}
	
}
