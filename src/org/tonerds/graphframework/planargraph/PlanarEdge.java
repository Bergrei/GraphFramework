package org.tonerds.graphframework.planargraph;

public interface PlanarEdge {
	
	boolean containsNode(PlanarNode node);

	PlanarNode getNextNode(PlanarNode node);

	boolean containsFace(PlanarFace face);

	PlanarFace getNextFace(PlanarFace face);

	PlanarFace getFace(PlanarNode from);
}