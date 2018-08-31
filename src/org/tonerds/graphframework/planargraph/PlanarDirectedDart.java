package org.tonerds.graphframework.planargraph;

public interface PlanarDirectedDart {
	
	public boolean containsNode(PlanarNode node);
	
	public PlanarNode getNextNode(PlanarNode node);
	
	public boolean containsFace(PlanarFace face);
	
	public PlanarFace getFace();
	
	public PlanarFace getFace(PlanarNode from);
	
	public boolean hasSameNodes(PlanarDirectedDart dart);
	
	public boolean equals(PlanarDirectedDart dart);
	
	PlanarNode getTop();
	PlanarNode getBottom();
	PlanarFace getRight();
}
