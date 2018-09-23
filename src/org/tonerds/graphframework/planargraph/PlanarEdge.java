package org.tonerds.graphframework.planargraph;

import org.tonerds.utilities.Pair;

public abstract class PlanarEdge<
			Node extends PlanarNode<Node, Edge, Face>, 
			Edge extends PlanarEdge<Node, Edge, Face>, 
			Face extends PlanarFace<Node, Edge, Face>
		> {
	
	//consider making this an abstract class. It could help greatly, if the methods used to change faces weren't visible
	public abstract boolean containsNode(Node node);

	public abstract Node getNextNode(Node node);

	public abstract boolean containsFace(Face face);

	public abstract Face getNextFace(Face face);

	public abstract Face getFace(Node from);
	
	abstract void replaceFace(Node top, Node bottom, Face rightfrom, Face rightto);
	
	abstract Pair<PlanarDirectedDart<Node, Edge, Face>, PlanarDirectedDart<Node, Edge, Face>> getDarts();
	
	public abstract Pair<Node, Node> getNodes();
}