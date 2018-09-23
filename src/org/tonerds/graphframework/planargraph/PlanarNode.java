package org.tonerds.graphframework.planargraph;

import java.util.Collection;

public interface PlanarNode<
			Node extends PlanarNode<Node, Edge, Face>, 
			Edge extends PlanarEdge<Node, Edge, Face>, 
			Face extends PlanarFace<Node, Edge, Face>
		>{
	public void addEdge(Edge edge);
	
	public boolean containsEdge(Edge edge);
	
	public boolean removeEdge(Edge edge);
	
	public Edge nextEdge(Edge edge);
	
	public Node nextNode(Face face);
	
	public Collection<Edge> getEdges(); 
}
