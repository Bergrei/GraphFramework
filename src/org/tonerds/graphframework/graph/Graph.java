package org.tonerds.graphframework.graph;

public interface Graph {
	public void addNode(Node node);
	public void addEdge(Edge edge);
	public void removeNode(Node node);
	public void removeEdge(Edge edge);
	
}
