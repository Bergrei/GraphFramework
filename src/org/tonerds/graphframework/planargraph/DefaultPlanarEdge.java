package org.tonerds.graphframework.planargraph;

import org.tonerds.utilities.Pair;

public class DefaultPlanarEdge<
			Node extends PlanarNode<Node, DefaultPlanarEdge<Node, Face>, Face>, 
			Face extends PlanarFace<Node, DefaultPlanarEdge<Node, Face>, Face>
		> extends PlanarEdge<Node, DefaultPlanarEdge<Node, Face>, Face>{
	
	PlanarDirectedDart<Node, DefaultPlanarEdge<Node, Face>, Face> first, second;

	private PlanarGraphFactory<Node, DefaultPlanarEdge<Node, Face>, Face> factory;

	
	DefaultPlanarEdge(PlanarGraphFactory<Node, DefaultPlanarEdge<Node, Face>, Face> factory, 
					PlanarDirectedDart<Node, DefaultPlanarEdge<Node, Face>, Face> first, 
					PlanarDirectedDart<Node, DefaultPlanarEdge<Node, Face>, Face> second) {
		this.factory = factory;
		this.first = first;
		this.second = second;
	}
	
	DefaultPlanarEdge(PlanarGraphFactory<Node, DefaultPlanarEdge<Node, Face>, Face> factory, 
					Node top, Face right, Node bottom, Face left) {
		this(
			factory,
			factory.makeDart(bottom, top, right),
			factory.makeDart(top, bottom, left)
		); 
	}
	

	@Override
	public boolean containsNode(Node node) {
		return first.containsNode(node) && second.containsNode(node);
	}
	

	@Override
	public Node getNextNode(Node node) {
		Node retnode = first.getNextNode(node);
		if (retnode == null) {
			retnode = second.getNextNode(node);
		}
		return retnode;
	}
	

	@Override
	public boolean containsFace(Face face) {
		return first.containsFace(face) || second.containsFace(face);
	}
	
	
	@Override
	public Face getNextFace(Face face) {
		if (first.containsFace(face)) {
			return second.getFace();
		}
		if (second.containsFace(face)) {
			return first.getFace();
		}
		return null;
	}

	@Override
	public Face getFace(Node from) {
		Face ret = first.getFace(from);
		if (ret == null)
			ret = second.getFace(from);
		return ret;
	}

	@Override
	void replaceFace(Node top, Node bottom, Face rightfrom, Face rightto) {
		PlanarDirectedDart<Node, DefaultPlanarEdge<Node, Face>, Face> olddart = factory.makeDart(bottom, top, rightfrom);
		PlanarDirectedDart<Node, DefaultPlanarEdge<Node, Face>, Face> newdart = factory.makeDart(bottom, top, rightto);
		if (first.equals(olddart)) {
			first = newdart;
		}
		if (second.equals(olddart)) {
			second = newdart;
		}
	}

	@Override
	public Pair<PlanarDirectedDart<Node, DefaultPlanarEdge<Node, Face>, Face>, PlanarDirectedDart<Node, DefaultPlanarEdge<Node, Face>, Face>> getDarts() {
		return new Pair<>(first, second);
	}

	@Override
	public Pair<Node, Node> getNodes() {
		return new Pair<>(first.getBottom(), first.getTop());
	}

}
