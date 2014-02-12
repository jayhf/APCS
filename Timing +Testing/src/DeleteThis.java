public class DeleteThis {
	public static void main(String[] args) {
		new DeleteThis().testLinkedListPractice();
	}
	
	private int countNodes(ListNode<String> head) {
		int number = 0;
		while (head != null) {
			number++;
			head = head.getNext();
		}
		return number;
	}
	
	private ListNode<Integer> firstMin(ListNode<Integer> list) {
		ListNode<Integer> min = null;
		while (list != null) {
			if (min == null || list.getValue() < min.getValue())
				min = list;
			list = list.getNext();
		}
		return min;
	}
	
	public void printlist(ListNode<Integer> list) {
		ListNode<Integer> temp = list;
		while (temp != null) {
			System.out.print(temp.getValue() + "   ");
			temp = temp.getNext();
		}
		System.out.println();
	}
	
	private void removeDupMins(ListNode<Integer> list) {
		if (list == null)
			return;
		ListNode<Integer> firstMin = firstMin(list);
		list = firstMin;
		while (list.getNext() != null)
			if (list.getNext().getValue() == firstMin.getValue())
				removeNext(list);
			else
				list = list.getNext();
		
	}
	
	private void removeNext(ListNode<Integer> p) {
		if (p.getNext() != null)
			p.setNext(p.getNext().getNext());
	}
	
	private ListNode<String> removeNodes(ListNode<String> head, String stringToRemove) {
		ListNode<String> previous = head, first = head;
		head = head.getNext();
		while (first.getValue().equals(stringToRemove)) {
			first = first.getNext();
			previous = previous.getNext();
			if (head == null)
				return null;
			head = head.getNext();
		}
		while (head != null) {
			if (head.getValue().equals(stringToRemove))
				previous.setNext(head.getNext());
			else
				previous = previous.getNext();
			head = head.getNext();
		}
		return first;
	}
	
	public void testLinkedListPractice() {
		System.out.println("Testing countNodes - should be 0, 1, 2");
		ListNode<String> list = null;
		System.out.println(countNodes(list));
		ListNode<String> list2 = new ListNode<String>("A", null);
		System.out.println(countNodes(list2));
		ListNode<String> list3 = new ListNode<String>("A", new ListNode<String>("B", null));
		System.out.println(countNodes(list3));
		
		System.out.println("Testing remove -- should all say true");
		ListNode<String> list5 = new ListNode<String>("A", null);
		list5 = removeNodes(list5, "A");
		System.out.println(countNodes(list5) == 0);
		ListNode<String> list6 = new ListNode<String>("A", new ListNode<String>("B", new ListNode<String>("A", null)));
		list6 = removeNodes(list6, "A");
		System.out.println(countNodes(list6) == 1);
		ListNode<String> list7 = new ListNode<String>("A", new ListNode<String>("B", new ListNode<String>("A", null)));
		list7 = removeNodes(list7, "B");
		System.out.println(countNodes(list7) == 2);
		
		System.out.println("Testing firstMin - should show list from example");
		ListNode<Integer> list8 = new ListNode<Integer>(3, new ListNode<Integer>(0, new ListNode<Integer>(12,
				new ListNode<Integer>(0, null))));
		list8 = firstMin(list8);
		printlist(list8);
		ListNode<Integer> list9 = new ListNode<Integer>(5, new ListNode<Integer>(4, new ListNode<Integer>(5,
				new ListNode<Integer>(2, null))));
		list9 = firstMin(list9);
		printlist(list9);
		ListNode<Integer> list10 = new ListNode<Integer>(10, null);
		list10 = firstMin(list10);
		printlist(list10);
		
		System.out.println("Testing removeNext -- should show result from example");
		ListNode<Integer> list11 = new ListNode<Integer>(10, new ListNode<Integer>(5, new ListNode<Integer>(20, null)));
		ListNode<Integer> p = list11;
		removeNext(p);
		printlist(list11);
		ListNode<Integer> list12 = new ListNode<Integer>(10, new ListNode<Integer>(5, new ListNode<Integer>(20, null)));
		p = list12.getNext();
		removeNext(p);
		printlist(list12);
		ListNode<Integer> list13 = new ListNode<Integer>(10, new ListNode<Integer>(5, new ListNode<Integer>(20, null)));
		p = list13.getNext().getNext();
		removeNext(p);
		printlist(list13);
		
		System.out.println("Testing removeDupMins - should match examples");
		ListNode<Integer> list14 = new ListNode<Integer>(3, new ListNode<Integer>(0, new ListNode<Integer>(12,
				new ListNode<Integer>(0, null))));
		removeDupMins(list14);
		printlist(list14);
		ListNode<Integer> list15 = new ListNode<Integer>(5, new ListNode<Integer>(4, new ListNode<Integer>(5,
				new ListNode<Integer>(2, null))));
		removeDupMins(list15);
		printlist(list15);
		ListNode<Integer> list16 = new ListNode<Integer>(10, new ListNode<Integer>(10, new ListNode<Integer>(10,
				new ListNode<Integer>(20, new ListNode<Integer>(10, null)))));
		removeDupMins(list16);
		printlist(list16);
		
		ListNode<Integer> list17 = new ListNode<Integer>(10, new ListNode<Integer>(10, null));
		removeDupMins(list17);
		printlist(list17);
	}
	
}

class ListNode<E> {
	private ListNode<E> next;
	private E value;
	
	public ListNode(E initValue, ListNode<E> initNext) {
		value = initValue;
		next = initNext;
	}
	
	public ListNode<E> getNext() {
		return next;
	}
	
	public E getValue() {
		return value;
	}
	
	public void setNext(ListNode<E> theNewNext) {
		next = theNewNext;
	}
	
	public void setValue(E theNewValue) {
		value = theNewValue;
	}
	
}
