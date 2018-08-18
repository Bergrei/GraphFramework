package org.tonerds.graphframework.planargraph;

public class DefaultPlanarDirectedDart implements PlanarDirectedDart {

	private PlanarNode top;
	private PlanarNode bottom;
	private PlanarFace right;
	
	DefaultPlanarDirectedDart(PlanarNode bottom, PlanarNode top, PlanarFace right) throws UnsupportedOperationException {
		if (top == bottom) {
			throw new UnsupportedOperationException("Loop edges are not supported");
		}
		this.top = top;
		this.bottom = bottom;
		this.right = right;
	}
	
	@Override
	public boolean containsNode(PlanarNode node) {
		return top == node || bottom == node;
	}

	@Override
	public PlanarNode getNextNode(PlanarNode node) {
		if (node == bottom)
			return top;
		return null;
	}

	@Override
	public boolean containsFace(PlanarFace face) {
		return right == face;
	}

	@Override
	public PlanarFace getFace() {
		return right;
	}

	@Override
	public PlanarFace getFace(PlanarNode from) {
		if (from == bottom)
			return right;
		return null;
	}

	@Override
	public boolean hasSameNodes(PlanarDirectedDart dart) {
		return dart.containsNode(top) && dart.containsNode(bottom);
	}

	
}
