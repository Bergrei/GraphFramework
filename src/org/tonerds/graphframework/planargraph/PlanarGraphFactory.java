package org.tonerds.graphframework.planargraph;

public abstract class PlanarGraphFactory<
			Node extends PlanarNode<Node, Edge, Face>, 
			Edge extends PlanarEdge<Node, Edge, Face>, 
			Face extends PlanarFace<Node, Edge, Face>
	> {
	public abstract Node makeNode();
	public abstract Edge makeEdge(Node from, Node to);
	public abstract Edge makeEdge(Node top, Face right, Node bottom, Face left);
	public Edge makeEdge(Node from, Node to, Face right, Face left) {
		return makeEdge(to, right, from, left);
	}
	public abstract Face makeFace();
	abstract PlanarDirectedDart<Node, Edge, Face> makeDart(Node from, Node to, Face right);
}
