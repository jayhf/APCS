public class Tester {
	public static void main(String[] args) {
		BinaryTree<Integer> test = new BinaryTree<Integer>();
		test.add(8);
		test.add(9);
		test.add(7);
		test.add(22);
		test.add(5);
		test.remove(22);
		System.out.println(test);
	}
}
