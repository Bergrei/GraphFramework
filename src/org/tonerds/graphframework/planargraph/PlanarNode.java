package org.tonerds.graphframework.planargraph;

import java.util.Collection;

public interface PlanarNode{
	public void addEdge(PlanarEdge edge);
	
	public boolean containsEdge(PlanarEdge edge);
	
	public boolean removeEdge(PlanarEdge edge);
	
	public PlanarEdge nextEdge(PlanarEdge edge);
	
	public PlanarNode nextNode(PlanarFace face);
	
	public Collection<PlanarEdge> getEdges(); 
}
