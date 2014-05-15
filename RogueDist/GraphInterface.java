 


/**
 * class GraphInterface is an interface for an
 * Undirected Graph interface
 * 
 * @author J. Smith 
 * @version February 2013
 */
public interface GraphInterface<E>
{
    void addEdge (E v, E w);
    void addVertex(E vertex);
    int numEdges ();
    int numVertices();
    boolean hasEdge (E v, E w);
    boolean hasVertex(E vertex);
    Iterable <E> adjacentTo (E vertex);
    Iterable <E> vertices();
    int degree (E vertex);
}
