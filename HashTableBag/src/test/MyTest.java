package test;

import MultiSetHashTable.HashTableBag;

public class MyTest {
	
	public static void main(String[] args) {
		HashTableBag<Integer> bag = new HashTableBag<Integer>(10);
		bag.add(5);
		bag.add(3);
		bag.add(5);
		bag.add(3);
		bag.add(5);
		bag.add(15);
		bag.remove(5, 3);
		bag.add(4);
		bag.add(5);
		bag.add(6);
		bag.add(9);
		bag.add(0);
		bag.add(19);
		bag.add(2);
		for (int i : bag)
			System.out.println(i);
		System.out.println(bag.toString());
		System.out.println(bag.count(15));
	}
	
}
