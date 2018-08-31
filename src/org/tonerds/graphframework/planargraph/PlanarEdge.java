package org.tonerds.graphframework.planargraph;

import org.tonerds.utilities.Pair;

public interface PlanarEdge {
	
	boolean containsNode(PlanarNode node);

	PlanarNode getNextNode(PlanarNode node);

	boolean containsFace(PlanarFace face);

	PlanarFace getNextFace(PlanarFace face);

	PlanarFace getFace(PlanarNode from);
	
	void replaceFace(PlanarNode top, PlanarNode bottom, PlanarFace rightfrom, PlanarFace rightto);
	
	Pair<PlanarDirectedDart, PlanarDirectedDart> getDarts();
	
	Pair<PlanarNode, PlanarNode> getNodes();
}