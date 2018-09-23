package org.tonerds.graphframework.planargraph;

import java.util.List;

public interface PlanarFace<
			Node extends PlanarNode<Node, Edge, Face>, 
			Edge extends PlanarEdge<Node, Edge, Face>, 
			Face extends PlanarFace<Node, Edge, Face>
		>{

	public void addNode(Node node);
	
	public boolean containsNode(Node node);
	
	public boolean removeNode(Node node);
	
	public Face addEdge(Edge edge);
	
	public boolean containsDart(PlanarDirectedDart<Node, Edge, Face> dart);
	
	public boolean mergeByEdgeWithFace(Edge edge, Face face);
	
	public boolean removeEdge(Edge edge);
	
	public PlanarDirectedDart<Node, Edge, Face> getNextDart(PlanarDirectedDart<Node, Edge, Face> dart);
	
	public void addCircle(List<PlanarDirectedDart<Node, Edge, Face>> circle);
}
