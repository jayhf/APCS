package test;

import java.util.HashMap;
import java.util.Random;

import MultiSetHashTable.HashTableBag;

public class MyTest {
	
	public static void main(String[] args) {
		HashTableBag<Integer> bag = new HashTableBag<Integer>(10);
		HashMap<Integer, Integer> integers = new HashMap<Integer, Integer>();
		Random random = new Random();
		for (int i = 0; i < 1000000; i++) {
			int r = random.nextInt(Integer.MAX_VALUE), n = random.nextInt(20000) - 10000;
			if (n > 0)
				bag.add(r, n);
			else
				bag.remove(r, -n);
			integers.put(r, n);
		}
		for (int i : bag)
			if (!integers.containsKey(i))
				System.out.println("made up " + i);
		for (int i : integers.keySet())
			if (!bag.contains(i) && integers.get(i) > 0)
				System.out.println("lost " + i);
		System.out.println("DONE!");
	}
	
}
