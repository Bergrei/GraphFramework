package org.tonerds.graphframework.planargraph;

public final class PlanarDartEdge implements PlanarEdge{
	
	PlanarDirectedDart first, second;
	
	public PlanarDartEdge(PlanarDirectedDart first, PlanarDirectedDart second) {
		this.first = first;
		this.second = second;
	}
	
	public PlanarDartEdge(PlanarNode top, PlanarFace right, PlanarNode bottom, PlanarFace left) {
		this(
			new DefaultPlanarDirectedDart(top, bottom, left), 
			new DefaultPlanarDirectedDart(bottom, top, right) ); 
	}
	

	@Override
	public boolean containsNode(PlanarNode node) {
		return first.containsNode(node) && second.containsNode(node);
	}
	

	@Override
	public PlanarNode getNextNode(PlanarNode node) {
		PlanarNode retnode = first.getNextNode(node);
		if (retnode == null) {
			retnode = second.getNextNode(node);
		}
		return retnode;
	}
	

	@Override
	public boolean containsFace(PlanarFace face) {
		return first.containsFace(face) || second.containsFace(face);
	}
	
	
	@Override
	public PlanarFace getNextFace(PlanarFace face) {
		if (first.containsFace(face)) {
			return second.getFace();
		}
		if (second.containsFace(face)) {
			return first.getFace();
		}
		return null;
	}

	@Override
	public PlanarFace getFace(PlanarNode from) {
		PlanarFace ret = first.getFace(from);
		if (ret == null)
			ret = second.getFace(from);
		return ret;
	}

}
