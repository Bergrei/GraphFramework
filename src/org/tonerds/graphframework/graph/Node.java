package org.tonerds.graphframework.graph;

public interface Node {
	public void addEdge(Edge edge);
	
	public boolean containsEdge(Edge edge);
	
	public boolean removeEdge(Edge edge);
}
