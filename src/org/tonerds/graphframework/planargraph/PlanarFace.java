package org.tonerds.graphframework.planargraph;

public interface PlanarFace{

	public void addNode(PlanarNode node);
	
	public boolean containsNode(PlanarNode node);
	
	public boolean removeNode(PlanarNode node);
	
	public void addEdge(PlanarEdge edge);
	
	public boolean containsDart(PlanarDirectedDart dart);
	
	public boolean removeDart(PlanarDirectedDart dart);
	
	public PlanarDirectedDart getNextDart(PlanarDirectedDart dart);
}
