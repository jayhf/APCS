import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class LinkedGraph<E> implements GraphInterface<E> {
	private Map<E, GraphNode> map = new HashMap<E, GraphNode>();
	
	public void addDirectedEdge(E start, E end) {
		GraphNode n1 = map.get(start);
		GraphNode n2 = map.get(end);
		if (n1 != null && n2 != null)
			n1.connected.add(n2);
	}
	
	@Override
	public void addEdge(E v, E w) {
		GraphNode n1 = map.get(v);
		GraphNode n2 = map.get(w);
		if (n1 != null && n2 != null) {
			n1.connected.add(n2);
			n2.connected.add(n1);
		}
	}
	
	@Override
	public void addVertex(E vertex) {
		if (!map.containsKey(vertex))
			map.put(vertex, new GraphNode(vertex));
	}
	
	@Override
	public Iterable<E> adjacentTo(E vertex) {
		final GraphNode node = map.get(vertex);
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {
					private Iterator<GraphNode> itr = node.connected.iterator();
					
					@Override
					public boolean hasNext() {
						return itr.hasNext();
					}
					
					@Override
					public E next() {
						return itr.next().e;
					}
					
					@Override
					public void remove() {
					}
				};
			}
		};
	}
	
	@Override
	public int degree(E vertex) {
		return map.get(vertex).connected.size();
	}
	
	@Override
	public boolean hasEdge(E v, E w) {
		for (GraphNode node : map.get(v).connected)
			if (node.e.equals(w))
				return true;
		return false;
	}
	
	@Override
	public boolean hasVertex(E vertex) {
		return map.containsKey(vertex);
	}
	
	@Override
	public int numEdges() {
		int total = 0;
		for (Entry<E, GraphNode> entry : map.entrySet())
			total += entry.getValue().connected.size();
		return total / 2;
	}
	
	@Override
	public int numVertices() {
		return map.size();
	}
	
	@Override
	public Iterable<E> vertices() {
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {
					private Iterator<Entry<E, GraphNode>> itr = map.entrySet().iterator();
					
					@Override
					public boolean hasNext() {
						return itr.hasNext();
					}
					
					@Override
					public E next() {
						return itr.next().getKey();
					}
					
					@Override
					public void remove() {
					}
				};
			}
		};
	}
	
	private class GraphNode {
		Collection<GraphNode> connected;
		E e;
		
		public GraphNode(E e) {
			this.e = e;
			connected = new LinkedList<GraphNode>();
		}
		
	}
}
