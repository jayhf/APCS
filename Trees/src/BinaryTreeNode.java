public class BinaryTreeNode <T extends Comparable<T>> {
	private T data;
	private BinaryTreeNode<T> left = null, right = null;
	private int size;

	public BinaryTreeNode(T data) {
		this.data = data;
		size = 1;
	}

	public boolean add(T t) {
		int value = t.compareTo(data);
		if (value == 0)
			return false;
		BinaryTreeNode<T> current = value < 0 ? left : right;
		if (current == null) {
			BinaryTreeNode<T> newNode = new BinaryTreeNode<T>(t);
			if (value < 0)
				left = newNode;
			else
				right = newNode;
			return true;
		} else {
			boolean result = current.add(t);
			if (result)
				size++;
			return result;
		}
	}

	public boolean contains(T t) {
		int value = t.compareTo(data);
		if (value == 0)
			return true;
		BinaryTreeNode<T> current = value < 0 ? left : right;
		if (current == null)
			return false;
		else
			return current.contains(t);
	}

	public boolean remove(T t, BinaryTreeNode<T> parent) {
		int value = t.compareTo(data);
		if (value == 0)
			if (left != null) {
				BinaryTreeNode<T> rightmost = left, secondRightMost = this;
				while (rightmost.right != null) {
					secondRightMost = rightmost;
					rightmost = rightmost.right;
				}
				data = rightmost.data;
				secondRightMost.right = rightmost.left;
				return true;
			} else if (right != null) {
				this.data = right.data;
				this.left = right.left;
				this.right = right.right;
			}
		BinaryTreeNode<T> current = value < 0 ? left : right;
		if (current == null)
			return false;
		else {
			boolean result = current.remove(t, this);
			if (result)
				size--;
			return result;
		}
	}

	@Override
	public String toString() {
		return (left == null ? "" : left.toString() + ",") + data.toString()
				+ (right == null ? "" : "," + right.toString());
	}
}
