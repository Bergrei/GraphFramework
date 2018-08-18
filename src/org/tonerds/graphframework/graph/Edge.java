package org.tonerds.graphframework.graph;

public interface Edge {
	
	public boolean containsNode(Node node);
	
	public Node getNextNode(Node node);
}
