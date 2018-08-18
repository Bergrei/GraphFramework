package org.tonerds.graphframework.planargraph;

public interface PlanarFace{	
	public void addEdge(PlanarNode fromnode, PlanarNode tonode, PlanarEdge edge);
	
	public boolean containsEdge(PlanarEdge edge);
	
	public boolean removeEdge(PlanarEdge edge);
	
	public PlanarEdge getEdge(PlanarNode fromNode);
}
