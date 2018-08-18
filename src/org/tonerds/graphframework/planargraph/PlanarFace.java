package org.tonerds.graphframework.planargraph;

public interface PlanarFace{

	public void addNode(PlanarNode node);
	
	public boolean containsNode(PlanarNode node);
	
	public boolean removeNode(PlanarNode node);
	
	public void addEdge(PlanarNode fromnode, PlanarNode tonode, PlanarEdge edge);
	
	public boolean containsEdge(PlanarEdge edge);
	
	public boolean removeEdge(PlanarEdge edge);
	
	public PlanarEdge getEdge(PlanarNode fromNode);
}
