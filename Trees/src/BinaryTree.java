public class BinaryTree<E extends Comparable<E>> {
	private BinaryTreeNode<E> root = null;
	private int size;
	
	public BinaryTree() {
	}
	
	public boolean add(E e) {
		if (root == null) {
			root = new BinaryTreeNode<E>(e);
			size++;
			return true;
		} else {
			boolean result = root.add(e);
			if (result)
				size++;
			return result;
		}
	}
	
	public boolean contains(E e) {
		return root == null ? false : root.contains(e);
	}
	
	public boolean remove(E e) {
		return root == null ? false : root.remove(e, null);
	}
	
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		return "BinaryTree (" + size + "): " + root.toString();
	}
	
}
