package org.tonerds.graphframework.planargraph;

import org.tonerds.graphframework.graph.Graph;

public interface PlanarGraph {
	public void addNodeToFace(PlanarFace toface, PlanarNode node);
	public void addEdgeToFaceBetweenNodes(PlanarFace toface, PlanarNode fromnode, PlanarNode tonode, PlanarEdge edge);
	public void removeNode(PlanarNode node);
	public void removeEdge(PlanarEdge edge);
	
	public PlanarNode getNextNode(PlanarFace face, PlanarNode node);
	
	public Graph extractGraph();
}
