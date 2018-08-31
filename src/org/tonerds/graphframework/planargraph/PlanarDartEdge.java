package org.tonerds.graphframework.planargraph;

import org.tonerds.utilities.Pair;

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

	@Override
	public void replaceFace(PlanarNode top, PlanarNode bottom, PlanarFace rightfrom, PlanarFace rightto) {
		PlanarDirectedDart olddart = new DefaultPlanarDirectedDart(bottom, top, rightfrom);
		PlanarDirectedDart newdart = new DefaultPlanarDirectedDart(bottom, top, rightto);
		if (first.equals(olddart)) {
			rightfrom.removeDart(first);
			first = newdart;
		}
		if (second.equals(olddart)) {
			rightfrom.removeDart(second);
			second = newdart;
		}
		rightto.addEdge(this);
			
	}

	@Override
	public Pair<PlanarDirectedDart, PlanarDirectedDart> getDarts() {
		return new Pair<>(first, second);
	}

	@Override
	public Pair<PlanarNode, PlanarNode> getNodes() {
		return new Pair<>(first.getBottom(), first.getTop());
	}

}
